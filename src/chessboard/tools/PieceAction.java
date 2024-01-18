package chessboard.tools;
/**
 * Klasa okreœlaj¹ca mo¿liwe zachowania figury oraz ich typ.
 * @author Ryszard Kopyciñski
 */
public class PieceAction
		extends Position {
	private ActionType actionType;
	public PieceAction(int _AH, int _18, ActionType actionType){
		super(_AH, _18);
		this.actionType = actionType;
	}
	public PieceAction(Position position, ActionType actionType){
		super(position);
		this.actionType = actionType;
	}
	public PieceAction(PieceAction pieceAction){
		super(pieceAction.getPos());
		this.actionType = pieceAction.getActionType();
	}
	public void update(Position position, ActionType actionType){
		this.update(position);
		this.actionType = actionType;
	}
	/**
	 * @return typ akcji
	 */
	public ActionType getActionType(){
		return actionType;
	}
	/**
	 * @return kopia pozycji jak¹ sama okreœla
	 */
	public Position getPos(){
		return new Position(get_AH(), get_18());
	}
	@Override
	public String toString(){
		return actionType+"{"+super.toString()+"}";
	}
}