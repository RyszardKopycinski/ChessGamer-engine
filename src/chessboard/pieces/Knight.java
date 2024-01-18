package chessboard.pieces;
import chessboard.tools.ActionType;
import chessboard.tools.PieceStep;

import java.awt.*;
/**
 * @author Ryszard Kopyciñski
 */
public class Knight
		extends Piece {
	public Knight(Color color){
		super(color, 3);
		int[] mix = new int[]{2, -2, 1, -1};
		for (int a : mix) {
			for (int b : mix) {
				if ( Math.abs(a*b) == 2 ) {
					addMoveType(new PieceStep(new int[]{a, b}, ActionType.BASICMOVE));
					addMoveType(new PieceStep(new int[]{a, b}, ActionType.ATTACK));
				}
			}
		}
	}
	public boolean isMultiSteps(){
		return false;
	}
	@Override
	public String toString(){
		return super.toString()+"Sk";
	}
}