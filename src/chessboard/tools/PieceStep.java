package chessboard.tools;
import java.util.Arrays;
/**
 * Klasa okreœlaj¹ca mo¿liwe kroki, kierunki poruszania siê oraz ich typ dla figury.
 * Wed³óg nich dana figura porusza siê bez bicia, atakuje na planszy lub wykonuje ruchy specjalne.
 * @author Ryszard Kopyciñski
 */
public class PieceStep {
	/**
	 * 2-wymiarowa tablica, która okreœla mo¿liwe kroki figury. Pierwszy element dotyczy przesuwania siê po osi A-H, drugi po osi 1-8
	 */
	private final int[] step;
	/**
	 * Typ przemieszczania siê
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
	 * @return {@code int[]} tablica z mo¿liwymi krokami
	 */
	public int[] getStep(){
		return step;
	}
	@Override
	public String toString(){
		return "PieceStep{"+"step="+Arrays.toString(step)+", actionType="+actionType+'}';
	}
}