package chessboard.pieces;
import chessboard.tools.PieceStep;

import java.awt.*;
import java.util.ArrayList;
/**
 * Abstrakcyjna klasa figury, która implementuje interfejs akcji figur
 * @author Ryszard Kopyciñski
 */
public abstract class Piece
        implements PieceActions {
    private final Color color;
    private final int value;
    /**
     * Lista mo¿liwych kroków, jakie mo¿e zrobiæ dana figura
     */
    private final ArrayList<PieceStep> pieceSteps;
    /**
     * Konstruktor figury inicjuje podstawowe dane
     * @param color kolor figury
     * @param value wartoœæ liczbowa figury
     */
    public Piece(Color color, int value) {
        this.color = color;
        this.value = value;
        pieceSteps = new ArrayList<>();
    }
    /**
     * @return {@code true} jeœli jest kolor czarny
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
     * @return wartoœæ figury
     */
    public int getValue() {
        return value;
    }
    /**
     * @return {@code ArrayList} lista z mo¿liwymi krokami figury
     */
    public ArrayList<PieceStep> getPieceSteps() {
        return pieceSteps;
    }
    /**
     * Porównuje kolor figury z zadanym
     * @param color kolor, który chcemy porównaæ
     * @return {@code true} jeœli kolory takie same
     */
    public boolean isSameColor(Color color) {
        return this.color == color;
    }
    /**
     * Porównuje kolor figury z zadanym
     * @param color kolor, który chcemy porównaæ
     * @return {@code true} jeœli kolory s¹ ró¿ne
     */
    public boolean isDiffColor(Color color) {
        return this.color != color;
    }
    /**
     * Dodaje nowy mo¿liwy krok figury do listy
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