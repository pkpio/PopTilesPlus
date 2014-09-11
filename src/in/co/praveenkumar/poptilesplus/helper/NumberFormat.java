package in.co.praveenkumar.poptilesplus.helper;

public class NumberFormat {

	public static String out(int num) {
		switch (Session.gameMode) {
		case Session.GAME_MODE_DEC:
			return num + "";
		case Session.GAME_MODE_BIN:
			return Integer.toBinaryString(num);
		case Session.GAME_MODE_HEX:
			return Integer.toHexString(num);
		default:
			return num + "";
		}
	}
}
