package chessboard.pieces;
import chessboard.tools.ActionType;
import chessboard.tools.PieceStep;

import java.awt.*;
/**
 * @author Ryszard Kopyciñski
 */
public class Queen
		extends Piece {
	public Queen(Color color){
		super(color, 10);
		int[] mix = new int[]{1, 0, -1};
		for (int a : mix) {
			for (int b : mix) {
				if ( a != 0 || b != 0 ) {
					addMoveType(new PieceStep(new int[]{a, b}, ActionType.BASICMOVE));
					addMoveType(new PieceStep(new int[]{a, b}, ActionType.ATTACK));
				}
			}
		}
	}
	@Override
	public String toString(){
		return super.toString()+"Da";
	}
}