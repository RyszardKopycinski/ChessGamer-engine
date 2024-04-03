package chessboard.tools;
/**
 * @author Ryszard Kopyci�ski
 * Typy akcji, kt�re wyst�puj� w grze
 */
public enum ActionType {
	BASICMOVE("Przej�cie"),
	PAWNDOUBLE("Podw�jny"),
	ATTACK("Atak"),
	CASTLING("Roszada"),
	PROMOTION("Promocja pionka"),
	ENPASSANT("En passant"),//specjalna wersja bicia
	START("Rozpocz�cie gry"),
	CHECKMATE("Szach mat!");
	String opis;
	ActionType(String opis){
		this.opis = opis;
	}
}