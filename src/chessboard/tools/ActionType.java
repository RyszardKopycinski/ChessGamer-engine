package chessboard.tools;
/**
 * @author Ryszard Kopyciñski
 * Typy akcji, które wystêpuj¹ w grze
 */
public enum ActionType {
	BASICMOVE("Przejœcie"),
	PAWNDOUBLE("Podwójny"),
	ATTACK("Atak"),
	CASTLING("Roszada"),
	PROMOTION("Promocja pionka"),
	ENPASSANT("En passant"),//specjalna wersja bicia
	START("Rozpoczêcie gry"),
	CHECKMATE("Szach mat!");
	String opis;
	ActionType(String opis){
		this.opis = opis;
	}
}