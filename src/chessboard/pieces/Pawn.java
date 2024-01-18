package chessboard.pieces;
import chessboard.tools.ActionType;
import chessboard.tools.PieceStep;

import java.awt.*;
/**
 * @author Ryszard Kopyciñski
 */
public class Pawn
		extends Piece {
	private boolean used;
	private final int direction;
	public Pawn(Color color){
		super(color, 1);
		used = false;
		direction = color == Color.WHITE ? 1 : -1;
		addMoveType(new PieceStep(new int[]{0, direction}, ActionType.BASICMOVE));
		addMoveType(new PieceStep(new int[]{0, 2*direction}, ActionType.PAWNDOUBLE));
		addMoveType(new PieceStep(new int[]{-1, direction}, ActionType.ATTACK));
		addMoveType(new PieceStep(new int[]{1, direction}, ActionType.ATTACK));
		addMoveType(new PieceStep(new int[]{-1, 0}, ActionType.ENPASSANT));
		addMoveType(new PieceStep(new int[]{1, 0}, ActionType.ENPASSANT));
	}
	public int getDirection(){
		return direction;
	}
	@Override
	public boolean isMultiSteps(){
		return false;
	}
	@Override
	public boolean wasUsed(){
		return used;
	}
	@Override
	public void setused(boolean newUsed){
		used = newUsed;
	}
	@Override
	public String toString(){
		return super.toString()+"Pi";
	}
}