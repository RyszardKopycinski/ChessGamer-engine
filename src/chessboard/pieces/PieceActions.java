package chessboard.pieces;
/**
 * @author Ryszard Kopyci�ski
 * <p>
 * interfejs do komunikacji z figurami na szachownicy
 */
public interface PieceActions {
	/**
	 * @return informacja czy dana figura wykona�a wcze�niej jaki� ruch, dla Kr�la dodatkowo czy by� szachowany
	 */
	default boolean wasUsed(){
		return false;
	}
	/**
	 * Ustawia now� warto�� u�ycia figury
	 * @param newUsed now� warto�� u�ycia figury
	 */
	default void setused(boolean newUsed){}
	/**
	 * @return informacja czy dana figura jest koloru czarnego
	 */
	boolean isBlack();
	/**
	 * Informacja czy dana figura mo�e porusza� si� wi�cej ni� jedno pole na raz
	 * @return {@code true} domy�lna warto��
	 */
	default boolean isMultiSteps(){
		return true;
	}
	/**
	 * Test nazw
	 * @return uproszczon� nazwa klasy
	 */
	String getName();
}