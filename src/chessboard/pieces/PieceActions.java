package chessboard.pieces;
/**
 * @author Ryszard Kopyciñski
 * <p>
 * interfejs do komunikacji z figurami na szachownicy
 */
public interface PieceActions {
	/**
	 * @return informacja czy dana figura wykona³a wczeœniej jakiœ ruch, dla Króla dodatkowo czy by³ szachowany
	 */
	default boolean wasUsed(){
		return false;
	}
	/**
	 * Ustawia now¹ wartoœæ u¿ycia figury
	 * @param newUsed now¹ wartoœæ u¿ycia figury
	 */
	default void setused(boolean newUsed){}
	/**
	 * @return informacja czy dana figura jest koloru czarnego
	 */
	boolean isBlack();
	/**
	 * Informacja czy dana figura mo¿e poruszaæ siê wiêcej ni¿ jedno pole na raz
	 * @return {@code true} domyœlna wartoœæ
	 */
	default boolean isMultiSteps(){
		return true;
	}
	/**
	 * Test nazw
	 * @return uproszczon¹ nazwa klasy
	 */
	String getName();
}