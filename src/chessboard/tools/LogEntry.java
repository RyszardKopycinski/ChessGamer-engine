package chessboard.tools;
import java.awt.*;
import java.time.LocalDateTime;
/**
 * @author Ryszard Kopyciñski
 */
public class LogEntry {
	private final LocalDateTime eventTime;
	private final Color color;
	private final Position startPos;
	private final Position endPos;
	private final ActionType actionType;
	public LogEntry(Color color, Position startPos, Position endPos, ActionType actionType){
		eventTime = LocalDateTime.now();
		this.color = color;
		this.startPos = startPos == null ? null : new Position(startPos);
		this.endPos = endPos == null ? null : new Position(endPos);
		this.actionType = actionType;
	}
	public Color getColor(){
		return color;
	}
	public Position getStartPos(){
		return startPos;
	}
	public Position getEndPos(){
		return endPos;
	}
	public ActionType getActionType(){
		return actionType;
	}
}