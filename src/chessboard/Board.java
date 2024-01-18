package chessboard;

import chessboard.pieces.*;
import chessboard.tools.*;

import java.awt.*;
import java.util.LinkedList;
import java.util.ListIterator;

import static chessboard.tools.ActionType.*;

/**
 * @author Ryszard Kopyciński
 * Klasa zarządza grą, porusza figurami
 * sprawdz poprawność ruchów, czy jest szach
 * prowadzi Log gry
 */
public class Board {
    private final Piece[][] board;
    private final LinkedList<Piece> whitesFree;
    private final LinkedList<Piece> blacksFree;
    private final LinkedList<Piece> whitesCaptured;
    private final LinkedList<Piece> blacksCaptured;
    private final LinkedList<LogEntry> log;
    private Position whiteKingPos;
    private Position blackKingPos;
    private Color currentColor;

    /**
     * Tworzy podstawową planszę, ustawia figury i inicjuje
     */
    public Board() {
        board = new Piece[8][8];
        whitesFree = new LinkedList<>();
        blacksFree = new LinkedList<>();
        whitesCaptured = new LinkedList<>();
        blacksCaptured = new LinkedList<>();
        whiteKingPos = null;
        blackKingPos = null;
        log = new LinkedList<>();
        log.add(new LogEntry(Color.WHITE, null, null, START));
        currentColor = Color.WHITE;
        createEmptyBoard();
        createBasicSetup();
    }

    /**
     * Generuje pustą planszą bez figur
     */
    private void createEmptyBoard() {
        for (int i = 0; i <= 7; i++) {
            for (int ii = 0; ii <= 7; ii++) {
                board[i][ii] = null;
            }
        }
    }

    /**
     * Ustawia figury na pustej planszy
     */
    private void createBasicSetup() {
        Position position = new Position();
        Color[] c = {Color.WHITE, Color.BLACK};
        int[] row = {2, 7};
        int[] stepAH = {1, 0};
        position.update(1, 8);
        for (int set : new int[]{0, 1}) {
            position.update(1, row[set]);
            for (int i = 0; i < 8; i++) {
                addPiece(new Pawn(c[set]), position);
                position.inc(stepAH);
            }
        }
        row = new int[]{1, 8};
        for (int set : new int[]{0, 1}) {
            position.update(1, row[set]);
            addPiece(new Rook(c[set]), position);
            position.inc(stepAH);
            addPiece(new Knight(c[set]), position);
            position.inc(stepAH);
            addPiece(new Bishop(c[set]), position);
            position.inc(stepAH);
            addPiece(new Queen(c[set]), position);
            position.inc(stepAH);
            addPiece(new King(c[set]), position);
            position.inc(stepAH);
            addPiece(new Bishop(c[set]), position);
            position.inc(stepAH);
            addPiece(new Knight(c[set]), position);
            position.inc(stepAH);
            addPiece(new Rook(c[set]), position);
            position.inc(stepAH);
            blackKingPos = new Position(5, 8);
            whiteKingPos = new Position(5, 1);
        }
    }

    /**
     * Zwraca figurę znajdującą się na danej pozycji
     * @param position pozycja, ktąra jest sprawdzana
     * @return {@code null} jeśli na danej pozycji nie znajduje się aktualnie żadna figure
     */
    private Piece getPiece(Position position) {
        return board[position.get_AH() - 1][position.get_18() - 1];
    }

    /**
     * Ustawia na danej pozycji figurę
     * @param piece    figura do wstawienia
     * @param position pozycja, na której trzeba wstawić
     */
    private void setPiece(Piece piece, Position position) {
        board[position.get_AH() - 1][position.get_18() - 1] = piece;
    }

    /**
     * Dodaje do planszy figurę w wyznaczonym miejscu. Aktualizuje listę figur odpowiedniego gracza
     */
    private void addPiece(Piece piece, Position position) {
        setPiece(piece, position);
        if (piece.isBlack()) {
            blacksFree.add(piece);
        } else {
            whitesFree.add(piece);
        }
    }

    /**
     * Sprawdza, czy dana pozycja prawidłowo określa lokalizację na planszy.
     * @param position pozycja, która jest sprawdzana
     * @return {@code true} jeśli obie współrzędne mieszczą się w przedziale od 1 do 8
     */
    private boolean isOnBoard(Position position) {
        return position.get_AH() >= 1 && position.get_AH() <= 8 && position.get_18() >= 1 && position.get_18() <= 8;
    }

    /**
     * Sprawdza, czy dana pozycja jest pusta
     * @param position pozycja, która jest sprawdzana
     * @return {@code true} jeśli pozycja jest prawidłowa i nie jest zajęta przez figurę
     */
    private boolean isEmpty(Position position) {
        return isOnBoard(position) && board[position.get_AH() - 1][position.get_18() - 1] == null;
    }

    /**
     * Sprawdza, czy dana pozycja jest zajęta
     * @param position pozycja, która jest sprawdzana
     * @return {@code true} jeśli pozycja jest prawidłowa i jest zajęta przez figurę
     */
    private boolean isOcupied(Position position) {
        return isOnBoard(position) && board[position.get_AH() - 1][position.get_18() - 1] != null;
    }

    /**
     * Tworzy listę możliwych przesunięć figury
     * @param position pozycja figury
     * @return {@code null} jeśli figura nie ma możliwości przesunąć się
     */
    private LinkedList<PieceAction> possibleMoves(Position position) {
        var result = new LinkedList<PieceAction>();
        Piece testPiece = getPiece(position);
        Position testPos = new Position();
        boolean repeat;
        for (PieceStep pieceStep : testPiece.getPieceSteps()) {//dla ka�dego mo�liwego kroku
            switch (pieceStep.getActionType()) {
                case BASICMOVE:
                    testPos.update(position);
                    testPos.inc(pieceStep.getStep());
                    repeat = isEmpty(testPos);
                    while (repeat) {
                        result.add(new PieceAction(new Position(testPos), BASICMOVE));
                        testPos.inc(pieceStep.getStep());
                        repeat = testPiece.isMultiSteps() && isEmpty(testPos);
                    }
                    break;
                case PAWNDOUBLE:
                    testPawnDouble(result, position);
                    break;
                case CASTLING:
                    testCastling(result, position, pieceStep);
                    break;
            }
        }
        return result;
    }

    /**
     * Tworzy sprawdzoną listę możliwych przesunięć figury, po sprawdzeniu, czy nie generują one szachu na swoim królu
     * @param position pozycja figury
     * @return {@code null} jeśli figura nie ma możliwości ruszyć się
     */
    private LinkedList<PieceAction> getPossibleMoves(Position position) {
        var result = possibleMoves(position);
        verifyMoves(result, position);
        return result;
    }

    /**
     * Tworzy listę możliwych ataków figury
     * @param position pozycja figury
     * @return {@code null} jeśli figura nie ma możliwości atakowania
     */
    private LinkedList<PieceAction> possibleAttacks(Position position) {
        var result = new LinkedList<PieceAction>();
        Piece testPiece = getPiece(position);
        Position testPos = new Position();
        for (PieceStep pieceStep : testPiece.getPieceSteps()) {
            switch (pieceStep.getActionType()) {
                case ATTACK:
                    testPos.update(position);
                    testPos.inc(pieceStep.getStep());
                    boolean repeat = isEmpty(testPos) && testPiece.isMultiSteps();
                    while (repeat) {
                        testPos.inc(pieceStep.getStep());
                        repeat = testPiece.isMultiSteps() && isEmpty(testPos);
                    }
                    if (isOcupied(testPos) && testPiece.isDiffColor(getPiece(testPos).getColor())) {
                        result.add(new PieceAction(testPos, ATTACK));
                    }
                    break;
                case ENPASSANT:
                    testEnPassant(result, position, pieceStep);
                    break;
            }
        }
        return result;
    }

    /**
     * Tworzy sprawdzoną listę możliwych ataków figury, po sprawdzeniu, czy nie generują one szachu na swoim królu
     * @param position pozycja figury
     * @return {@code null} jeśli figura nie ma możliwości atakowania
     */
    private LinkedList<PieceAction> getPossibleAttacks(Position position) {
        var result = possibleAttacks(position);
        verifyMoves(result, position);
        return result;
    }

    /**
     * Sprawdza, czy dla danego pionka jest możliwość wykonania podwójnego przesunięcia
     * @param result   lista z ruchami pionka, do której dodawany jest ruch
     * @param position pozycja pionka
     */
    private void testPawnDouble(LinkedList<PieceAction> result, Position position) {
        Pawn testPawn = (Pawn) getPiece(position);
        if (!testPawn.wasUsed()) {
            Position testPos = new Position(position);
            int direction = testPawn.getDirection();
            testPos.inc(new int[]{0, direction});
            if (isEmpty(testPos)) {
                testPos.inc(new int[]{0, direction});
                if (isEmpty(testPos)) {
                    result.add(new PieceAction(testPos, PAWNDOUBLE));
                }
            }
        }
    }

    /**
     * Sprawdza, czy Król może wykonać roszadę
     * @param result   lista z ruchami króla, do której dodawany jest ruch
     * @param position pozycja króla
     */
    private void testCastling(LinkedList<PieceAction> result, Position position, PieceStep pieceStep) {
        if (!getPiece(position).wasUsed() && !isCheck(getPiece(position).getColor())) {//kr�l nie by� u�yty, nie jest szachowany
            int direction = pieceStep.getStep()[0] == 2 ? 1 : -1;
            Position testRookPos = new Position(direction > 0 ? 8 : 1, position.get_18());
            if (getPiece(testRookPos) != null && getPiece(testRookPos).getClass() == Rook.class && !getPiece(testRookPos).wasUsed()) {//sprawdzanie wie�y
                Position testPos = new Position(position);
                testPos.inc(direction, 0);
                boolean isCastlingPossible;
                do {
                    isCastlingPossible = isEmpty(testPos);
                    testPos.inc(direction, 0);
                } while (isCastlingPossible && testPos.get_AH() != testRookPos.get_AH());
                if (isCastlingPossible) {
                    testPos.update(position);
                    testPos.inc(direction, 0);
                    boolean kingUsedStatus = getPiece(position).wasUsed();
                    boolean rookUsedStatus = getPiece(testRookPos).wasUsed();
                    action(position, testPos, BASICMOVE);
                    if (!isCheck(getPiece(testPos).getColor())) {
                        testPos.update(position);
                        testPos.inc(pieceStep.getStep());
                        result.add(new PieceAction(testPos, CASTLING));
                    }
                    undoLastAction();
                    getPiece(position).setused(kingUsedStatus);
                    getPiece(testRookPos).setused(rookUsedStatus);
                }
            }
        }
    }

    /**
     * Sprawdza, czy dla danego pionka jest możliwość wykonania bicia w przelocie
     * @param result   lista z ruchami pionka, do którego dodawany jest ruch
     * @param position pozycja pionka
     */
    private void testEnPassant(LinkedList<PieceAction> result, Position position, PieceStep pieceStep) {
        if (log.getLast().getActionType() == PAWNDOUBLE) {//ostatni ruch by� podwujnym ruchem pionka
            if (log.getLast().getEndPos().get_18() == position.get_18()) {//wsp�rz�dna 18 si� zgadza
                Position testPos = new Position(position);
                testPos.inc(pieceStep.getStep());
                if (testPos.get_AH() == log.getLast().getEndPos().get_AH()) {//wsp�rz�dna AH si� zgadza
                    if (getPiece(log.getLast().getEndPos()).isDiffColor(getPiece(position).getColor())) {
                        result.add(new PieceAction(testPos, ENPASSANT));
                    }
                }
            }
        }
    }

    /**
     * Weryfikuje listę ruchów dla danej figury pod kątem możliwego stworzenia szachu na własnym królu. W przypadku wykrycia takiej sytuacji ruch jest usuwany z listy
     * @param result       lista ruchów danej figury
     * @param testPosition pozycja sprawdzanej figury
     */
    private void verifyMoves(LinkedList<PieceAction> result, Position testPosition) {
        boolean oldUsedStatus = getPiece(testPosition).wasUsed();//zapisanie starej warto�ci dla sprawdzanego pionka
        PieceAction testedAction;
        Color testedColor = getPiece(testPosition).getColor();
        ListIterator<PieceAction> iterator = result.listIterator();
        while (iterator.hasNext()) {
            testedAction = iterator.next();
            //System.out.println(testedAction);
            action(testPosition, testedAction.getPos(), testedAction.getActionType());
            if (isCheck(testedColor)) {
                iterator.remove();
            }
            undoLastAction();
            getPiece(testPosition).setused(oldUsedStatus);
        }
    }

    /**
     * Drukuje plansze wraz z ustawieniem figur oraz podstawowymi informacjami na temat gry
     */
    public void print() {
        System.out.println("   A   B   C   D   E   F   G   H");
        System.out.println(" +---+---+---+---+---+---+---+---+");
        StringBuilder line;
        int[] stepAH = {1, 0};
        int[] step18 = {-8, -1};
        Position pos = new Position(1, 8);
        for (int i = 8; i >= 1; i--) {
            line = new StringBuilder(i + "|");
            for (int j = 0; j < 8; j++) {
                if (isEmpty(pos)) {
                    line.append("   |");
                } else {
                    line.append(getPiece(pos)).append("|");
                }
                pos.inc(stepAH);
            }
            System.out.println(line.toString() + i);
            System.out.println(" +---+---+---+---+---+---+---+---+");
            pos.inc(step18);
        }
        System.out.println("   A   B   C   D   E   F   G   H");
        System.out.println(gameScore());
        System.out.println("Bialy krol: " + whiteKingPos + " Czarny krol: " + blackKingPos);
        System.out.println("Szach Bia�ych: " + isCheck(Color.WHITE) + ". Szach Czarnych:" + isCheck(Color.BLACK));
    }

    /**
     * Zwraca kolor aktualnie wykonujący ruch
     */
    public Color getCurrentColor() {
        return currentColor;
    }

    /**
     * Próbuje wykonać akcję, jeśli jest taka możliwa w danej chwili
     * @param start pozycja początkowa
     * @param end   pozycja końcowa
     * @return {@code true} jeśli ruch udało się wykonać
     */
    public boolean makeAction(Position start, Position end) {
        Piece selected = getPiece(start);
        if (selected != null && selected.isSameColor(currentColor)) {
            Piece target = getPiece(end);
            if (target == null) {
                for (PieceAction pa : getPossibleMoves(start)) {
                    if (end.equals(pa.getPos())) {
                        action(start, end, pa.getActionType());
                        currentColor = currentColor == Color.WHITE ? Color.BLACK : Color.WHITE;
                        return true;
                    }
                }
            } else if (target.isDiffColor(currentColor)) {
                for (PieceAction pa : getPossibleAttacks(start)) {
                    if (end.equals(pa.getPos())) {
                        action(start, end, pa.getActionType());
                        currentColor = currentColor == Color.WHITE ? Color.BLACK : Color.WHITE;
                        return true;
                    }
                }
            } else return false;
        }
        return false;
    }

    /**
     * Określa możliwe ruchy danej figury, tj. pozycje, na które może się ruszyć
     * @param target pozycja, która jest testowana
     * @return {@code false} jeśli pozycja nie zawiera żadnej figury
     */
    public boolean checkPossibleAvtions(Position target) {
        Piece selected = getPiece(target);
        if (selected != null) {
            StringBuilder text = new StringBuilder();
            for (PieceAction pa : getPossibleMoves(target)) {
                if (text.length() > 0) {
                    text.append(" ");
                }
                text.append(pa.getPos().toString());
            }
            System.out.print(target + "[" + selected.getName() + "] <" + text);
            text = new StringBuilder();
            for (PieceAction pa : getPossibleAttacks(target)) {
                if (text.length() > 0) {
                    text.append(" ");
                }
                text.append(pa.getPos().toString());
            }
            System.out.println("><" + text + ">");
            return true;
        }
        return false;
    }

    /**
     * Wykonuje akcję z jednego miejsca do drugiego
     * @param start      pozycja początkowa
     * @param end        pozycja końcowa
     * @param actionType typ akcji, jaka jest wykonywana
     */
    private void action(Position start, Position end, ActionType actionType) {
        Color actualColor = getPiece(start).getColor();
        switch (actionType) {
            case CASTLING:
                move(new Position(end.get_AH() > start.get_AH() ? 8 : 1, start.get_18()), new Position(end.get_AH() > start.get_AH() ? 6 : 4, start.get_18()));
            case PAWNDOUBLE:
            case BASICMOVE:
                move(start, end);
                break;
            case ATTACK:
                attack(start, end);
                break;
            case ENPASSANT:
                attack(start, end);
                Position pos = new Position(end.get_AH(), start.get_18() + ((Pawn) getPiece(start)).getDirection());
                move(end, pos);
                break;
        }
        log.add(new LogEntry(currentColor, start, end, actionType));
        if (getPiece(end).getClass() == Pawn.class && (end.get_18() == 1 || end.get_18() == 8)) {
            if (actualColor == Color.BLACK) {
                blacksFree.remove(getPiece(end));
            } else {
                whitesFree.remove(getPiece(end));
            }
            addPiece(new Queen(actualColor), end);
            log.add(new LogEntry(actualColor, end, null, PROMOTION));
        }
    }

    /**
     * Cofa ostatni ruch
     */
    private void undoLastAction() {
        LogEntry lastAction = log.getLast();
        switch (lastAction.getActionType()) {
            case PROMOTION:
                if (getPiece(lastAction.getStartPos()).isBlack()) {
                    blacksFree.remove(getPiece(lastAction.getStartPos()));
                } else {
                    whitesFree.remove(getPiece(lastAction.getStartPos()));
                }
                addPiece(new Pawn(lastAction.getColor()), lastAction.getStartPos());
                log.removeLast();
                undoLastAction();
                break;
            case CASTLING:
                move(new Position(lastAction.getEndPos().get_AH() > lastAction.getStartPos().get_AH() ? 6 : 4, lastAction.getStartPos().get_18()), new Position(lastAction.getEndPos().get_AH() > lastAction.getStartPos().get_AH() ? 8 : 1, lastAction.getStartPos().get_18()));
                getPiece(new Position(lastAction.getEndPos().get_AH() > lastAction.getStartPos().get_AH() ? 8 : 1, lastAction.getStartPos().get_18())).setused(false);
            case BASICMOVE:
            case PAWNDOUBLE:
                move(lastAction.getEndPos(), lastAction.getStartPos());
                log.removeLast();
                break;
            case ATTACK:
                move(lastAction.getEndPos(), lastAction.getStartPos());
                LinkedList<Piece> captured;
                if (lastAction.getColor() == Color.BLACK) {
                    captured = whitesCaptured;
                } else {
                    captured = blacksCaptured;
                }
                addPiece(captured.getLast(), lastAction.getEndPos());
                captured.removeLast();
                log.removeLast();
                break;
            case ENPASSANT:
                Position pos = new Position(lastAction.getEndPos());
                pos.inc(0, lastAction.getColor() == Color.WHITE ? 1 : -1);
                move(pos, lastAction.getStartPos());
                if (lastAction.getColor() == Color.BLACK) {
                    captured = whitesCaptured;
                } else {
                    captured = blacksCaptured;
                }
                addPiece(captured.getLast(), lastAction.getEndPos());
                captured.removeLast();
                log.removeLast();
                break;
        }
    }

    /**
     * Przesuwa figurę
     * @param start pozycja startowa
     * @param end   pozycja końcowa
     */
    private void move(Position start, Position end) {
        setPiece(getPiece(start), end);
        setPiece(null, start);
        getPiece(end).setused(true);
        if (getPiece(end).getClass() == King.class) {
            (getPiece(end).getColor() == Color.WHITE ? whiteKingPos : blackKingPos).update(end);
        }
    }

    /**
     * Zbija figurę
     * @param start miejsce początkowe
     * @param end   miejsce końcowe, jednocześnie lokalizacja figury przeciwnika
     */
    private void attack(Position start, Position end) {
        remove(end);
        move(start, end);
    }

    /**
     * Usuwa figurę z planszy, usuwa z listy wolnych figur i dodaje ją do listy zbitych
     * @param target pozycja do usunięcia
     */
    private void remove(Position target) {
        Piece targetPiece = getPiece(target);
        LinkedList<Piece> free;
        LinkedList<Piece> captured;
        if (targetPiece.isBlack()) {
            free = blacksFree;
            captured = blacksCaptured;
        } else {
            free = whitesFree;
            captured = whitesCaptured;
        }
        free.remove(targetPiece);
        captured.add(targetPiece);
        setPiece(null, target);
    }

    /**
     * Sprawdzanie, czy jest szach.
     * @param testColor kolor figur, które są potencjalnie szachowane
     */
    private boolean isCheck(Color testColor) {
        var enemyKing = testColor == Color.WHITE ? whiteKingPos : blackKingPos;
        var testPosition = new Position(1, 1);
        var stepAH = new int[]{1, 0};
        var step18 = new int[]{-8, 1};
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (isOcupied(testPosition) && !getPiece(testPosition).isSameColor(testColor)) {
                    for (var testAction : possibleAttacks(testPosition)) {
                        if (testAction.getPos().equals(enemyKing)) {
                            return true;
                        }
                    }
                }
                testPosition.inc(stepAH);
            }
            testPosition.inc(step18);
        }
        return false;
    }

    /**
     * Tworzy wynik gry
     * @return tekst informujący o wyniku
     */
    private String gameScore() {
        int whiteScore = 0;
        int blackScore = 0;
        for (Piece p : whitesFree) {
            whiteScore += p.getValue();
        }
        for (Piece p : blacksFree) {
            blackScore += p.getValue();
        }
        return whiteScore + " vs. " + blackScore;
    }
}