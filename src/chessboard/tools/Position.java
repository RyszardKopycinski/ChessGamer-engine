package chessboard.tools;
/**
 * Klasa reprezentuj¹ca pozycjê na planszy
 * @author Ryszard Kopyciñski
 */
public class Position {
	private int _AH;
	private int _18;
	/**
	 * Nowa pozycja na podstawie wspó³rzêdnych.
	 * @param _AH pozycja na osi A-H
	 * @param _18 pozycja na osi 1-8
	 */
	public Position(int _AH, int _18){
		this._AH = _AH;
		this._18 = _18;
	}
	/**
	 * Nowa pozycja wygenerowana z ju¿ istniej¹cej.
	 * @param position pozycja do skopiowania
	 */
	public Position(Position position){
		this._AH = position.get_AH();
		this._18 = position.get_18();
	}
	public Position(){
	}
	/**
	 * Statyczna metoda generuj¹ca pozycjê z tekstu.
	 * @param test tekst, z którego ma byæ stworzona nowa pozycja
	 * @return {@code null} jeœli test nie jest poprawnym zapisem pozycji na planszy
	 * @return {@code Position} nowa pozycja na podstawie tekstu
	 */
	public static Position getInstance(String test){
		test = test.trim();
		if ( test != null && test.length() == 2 ) {
			char _ah = test.toUpperCase().charAt(0);
			if ( _ah >= 'A' && _ah <= 'H' ) {
				if ( test.charAt(1) >= '1' && test.charAt(1) <= '8' ) {
					char _18 = test.charAt(1);
					if ( _18 >= '1' && _18 <= '8' ) {
						return new Position(_ah-'A'+1, Character.getNumericValue(_18));
					}
				}
			}
		}
		return null;
	}
	/**
	 * Porównanie dwóch pozycji, czy s¹ identyczne
	 * @param other druga pozycja, do której porównujemy
	 * @return {@code true} jeœli pozycje wskazuj¹ to samo miejsce
	 */
	public boolean equals(Position other){
        return this.get_AH() == other.get_AH() && this.get_18() == other.get_18();
    }
	/**
	 * Nadpisuje pozycjê nowymi wartoœciami
	 * @param position pozycja do skopiowania
	 */
	public void update(Position position){
		this._AH = position.get_AH();
		this._18 = position.get_18();
	}
	/**
	 * Nadpisuje pozycjê nowymi wartoœciami
	 * @param _AH pozycja na osi A-H
	 * @param _18 pozycja na osi 1-8
	 */
	public void update(int _AH, int _18){
		this._AH = _AH;
		this._18 = _18;
	}
	/**
	 * @return pozycja na osi A-H
	 */
	public int get_AH(){
		return _AH;
	}
	/**
	 * @return pozycja na osi 1-8
	 */
	public int get_18(){
		return _18;
	}
	/**
	 * Zmienia aktualn¹ pozycjê o zadane wartoœci
	 * @param xy tablica z wartoœciami zmian
	 */
	public void inc(int[] xy){
		inc(xy[0], xy[1]);
	}
	/**
	 *
	 */
	public void inc(int x, int y){
		_AH += x;
		_18 += y;
	}
	@Override
	public String toString(){
		switch (_AH) {
			case 1:
				return "A"+_18;
			case 2:
				return "B"+_18;
			case 3:
				return "C"+_18;
			case 4:
				return "D"+_18;
			case 5:
				return "E"+_18;
			case 6:
				return "F"+_18;
			case 7:
				return "G"+_18;
			case 8:
				return "H"+_18;
			default:
				return "XX";
		}
	}
}