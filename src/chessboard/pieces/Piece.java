package chessboard.pieces;
import chessboard.tools.PieceStep;

import java.awt.*;
import java.util.ArrayList;
/**
 * Abstrakcyjna klasa figury, kt�ra implementuje interfejs akcji figur
 * @author Ryszard Kopyci�ski
 */
public abstract class Piece
        implements PieceActions {
    private final Color color;
    private final int value;
    /**
     * Lista mo�liwych krok�w, jakie mo�e zrobi� dana figura
     */
    private final ArrayList<PieceStep> pieceSteps;
    /**
     * Konstruktor figury inicjuje podstawowe dane
     * @param color kolor figury
     * @param value warto�� liczbowa figury
     */
    public Piece(Color color, int value) {
        this.color = color;
        this.value = value;
        pieceSteps = new ArrayList<>();
    }
    /**
     * @return {@code true} je�li jest kolor czarny
     */
    @Override
    public boolean isBlack() {
        return color == Color.BLACK;
    }
    /**
     * @return kolor figury
     */
    public Color getColor() {
        return color;
    }
    /**
     * @return warto�� figury
     */
    public int getValue() {
        return value;
    }
    /**
     * @return {@code ArrayList} lista z mo�liwymi krokami figury
     */
    public ArrayList<PieceStep> getPieceSteps() {
        return pieceSteps;
    }
    /**
     * Por�wnuje kolor figury z zadanym
     * @param color kolor, kt�ry chcemy por�wna�
     * @return {@code true} je�li kolory takie same
     */
    public boolean isSameColor(Color color) {
        return this.color == color;
    }
    /**
     * Por�wnuje kolor figury z zadanym
     * @param color kolor, kt�ry chcemy por�wna�
     * @return {@code true} je�li kolory s� r�ne
     */
    public boolean isDiffColor(Color color) {
        return this.color != color;
    }
    /**
     * Dodaje nowy mo�liwy krok figury do listy
     * @param newStep nowy krok do dodania
     */
    public void addMoveType(PieceStep newStep) {
        pieceSteps.add(newStep);
    }
    /**
     * @return litera koloru
     */
    @Override
    public String toString() {
        return isBlack() ? "c" : "b";
    }
    /**
     * @return Uproszczona nazwa klasy
     */
    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }
}