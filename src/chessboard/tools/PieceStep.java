package chessboard.tools;
import java.util.Arrays;
/**
 * Klasa okre�laj�ca mo�liwe kroki, kierunki poruszania si� oraz ich typ dla figury.
 * Wed��g nich dana figura porusza si� bez bicia, atakuje na planszy lub wykonuje ruchy specjalne.
 * @author Ryszard Kopyci�ski
 */
public class PieceStep {
	/**
	 * 2-wymiarowa tablica, kt�ra okre�la mo�liwe kroki figury. Pierwszy element dotyczy przesuwania si� po osi A-H, drugi po osi 1-8
	 */
	private final int[] step;
	/**
	 * Typ przemieszczania si�
	 */
	private final ActionType actionType;
	public PieceStep(int[] step, ActionType actionType){
		this.step = step;
		this.actionType = actionType;
	}
	/**
	 * @return {@code ActionType} typ akcji, jakiej dotyczy krok
	 */
	public ActionType getActionType(){
		return actionType;
	}
	/**
	 * @return {@code int[]} tablica z mo�liwymi krokami
	 */
	public int[] getStep(){
		return step;
	}
	@Override
	public String toString(){
		return "PieceStep{"+"step="+Arrays.toString(step)+", actionType="+actionType+'}';
	}
}