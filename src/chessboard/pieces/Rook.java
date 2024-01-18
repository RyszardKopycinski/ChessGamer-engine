package chessboard.pieces;
import chessboard.tools.ActionType;
import chessboard.tools.PieceStep;

import java.awt.*;
/**
 * @author Ryszard Kopyciñski
 */
public class Rook
		extends Piece {
	private boolean used;
	public Rook(Color color){
		super(color, 5);
		used = false;
		int[] mix = new int[]{1, 0, -1};
		for (int a : mix) {
			for (int b : mix) {
				if ( Math.abs(a+b) == 1 ) {
					addMoveType(new PieceStep(new int[]{a, b}, ActionType.BASICMOVE));
					addMoveType(new PieceStep(new int[]{a, b}, ActionType.ATTACK));
				}
			}
		}
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
		return super.toString()+"Wi";
	}
}