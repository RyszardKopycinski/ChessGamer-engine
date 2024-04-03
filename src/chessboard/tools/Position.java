package chessboard.tools;
/**
 * Klasa reprezentuj�ca pozycj� na planszy
 * @author Ryszard Kopyci�ski
 */
public class Position {
	private int _AH;
	private int _18;
	/**
	 * Nowa pozycja na podstawie wsp�rz�dnych.
	 * @param _AH pozycja na osi A-H
	 * @param _18 pozycja na osi 1-8
	 */
	public Position(int _AH, int _18){
		this._AH = _AH;
		this._18 = _18;
	}
	/**
	 * Nowa pozycja wygenerowana z ju� istniej�cej.
	 * @param position pozycja do skopiowania
	 */
	public Position(Position position){
		this._AH = position.get_AH();
		this._18 = position.get_18();
	}
	public Position(){
	}
	/**
	 * Statyczna metoda generuj�ca pozycj� z tekstu.
	 * @param test tekst, z kt�rego ma by� stworzona nowa pozycja
	 * @return {@code null} je�li test nie jest poprawnym zapisem pozycji na planszy
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
	 * Por�wnanie dw�ch pozycji, czy s� identyczne
	 * @param other druga pozycja, do kt�rej por�wnujemy
	 * @return {@code true} je�li pozycje wskazuj� to samo miejsce
	 */
	public boolean equals(Position other){
        return this.get_AH() == other.get_AH() && this.get_18() == other.get_18();
    }
	/**
	 * Nadpisuje pozycj� nowymi warto�ciami
	 * @param position pozycja do skopiowania
	 */
	public void update(Position position){
		this._AH = position.get_AH();
		this._18 = position.get_18();
	}
	/**
	 * Nadpisuje pozycj� nowymi warto�ciami
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
	 * Zmienia aktualn� pozycj� o zadane warto�ci
	 * @param xy tablica z warto�ciami zmian
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