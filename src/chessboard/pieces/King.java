package chessboard.pieces;
import chessboard.tools.ActionType;
import chessboard.tools.PieceStep;

import java.awt.*;
/**
 * @author Ryszard Kopyciñski
 */
public class King
		extends Piece {
	private boolean used;
	public King(Color color){
		super(color, 0);
		used = false;
		int[] mix = new int[]{1, 0, -1};
		for (int a : mix) {
			for (int b : mix) {
				if ( a != 0 || b != 0 ) {
					addMoveType(new PieceStep(new int[]{a, b}, ActionType.BASICMOVE));
					addMoveType(new PieceStep(new int[]{a, b}, ActionType.ATTACK));
				}
			}
		}
		addMoveType(new PieceStep(new int[]{-2, 0}, ActionType.CASTLING));
		addMoveType(new PieceStep(new int[]{2, 0}, ActionType.CASTLING));
	}
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
		return super.toString()+"Kr";
	}
}