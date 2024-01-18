package chessboard.pieces;
import chessboard.tools.ActionType;
import chessboard.tools.PieceStep;

import java.awt.*;
/**
 * @author Ryszard Kopyciñski
 */
public class Bishop
		extends Piece {
	public Bishop(Color color){
		super(color, 3);
		int[] mix = new int[]{1, -1};
		for (int a : mix) {
			for (int b : mix) {
				addMoveType(new PieceStep(new int[]{a, b}, ActionType.BASICMOVE));
				addMoveType(new PieceStep(new int[]{a, b}, ActionType.ATTACK));
			}
		}
	}
	/**
	 * Uproszczona nazwy pionka wraz z kolorem
	 * @return
	 */
	@Override
	public String toString(){
		return super.toString()+"Go";
	}
}