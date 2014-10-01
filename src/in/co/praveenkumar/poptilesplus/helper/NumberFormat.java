package in.co.praveenkumar.poptilesplus.helper;

import java.util.ArrayList;

public class NumberFormat {

	public static String out(int num) {
		switch (Session.gameMode) {
		case Session.GAME_MODE_DEC:
			return num + "";
		case Session.GAME_MODE_BIN:
			return Integer.toBinaryString(num);
		case Session.GAME_MODE_HEX:
			return Integer.toHexString(num);
		case Session.GAME_MODE_FIB:
			return finalAdjustedDouble(fibonacci(num));
		case Session.GAME_MODE_KID:
			return getAlphabet(num);
		default:
			return num + "";
		}
	}

	/**
	 * We shall be adding all the fibonacci numbers we found so far to this list
	 * so that we can use later without having to recompute.
	 */
	static ArrayList<Double> fibonacciSeries = new ArrayList<Double>();

	private static double fibonacci(int n) {
		try {
			// Because index in list starts from 0
			n = n - 1;

			// If not initialized. Do it with just 2 values.
			if (fibonacciSeries.size() == 0) {
				fibonacciSeries.add(Double.valueOf(0));
				fibonacciSeries.add(Double.valueOf(1));
			}

			if (fibonacciSeries.size() > n)
				return fibonacciSeries.get(n);

			// Compute the new number in the series and save it for later.
			else {
				fibonacciSeries.add(fibonacciSeries.get(n - 1)
						+ fibonacciSeries.get(n - 2));
				return fibonacciSeries.get(n);
			}
		} catch (Exception e) {
			return 0;
		}
	}

	public static String finalAdjustedDouble(double value) {
		String adjust = value + "";
		if (adjust.contains("E")) {
			int ePos = adjust.indexOf("E");
			adjust = adjust.substring(0, 4) + " "
					+ adjust.substring(ePos, adjust.length());
		} else {
			int dPos = adjust.indexOf(".");
			adjust = adjust.substring(0, dPos);
		}
		return adjust;
	}

	public static String getAlphabet(int i) {
		int index = (i - 1) % 26;
		switch (index) {
		case 0:
			return "A";
		case 1:
			return "B";
		case 2:
			return "C";
		case 3:
			return "D";
		case 4:
			return "E";
		case 5:
			return "F";
		case 6:
			return "G";
		case 7:
			return "H";
		case 8:
			return "I";
		case 9:
			return "J";
		case 10:
			return "K";
		case 11:
			return "L";
		case 12:
			return "M";
		case 13:
			return "N";
		case 14:
			return "O";
		case 15:
			return "P";
		case 16:
			return "Q";
		case 17:
			return "R";
		case 18:
			return "S";
		case 19:
			return "T";
		case 20:
			return "U";
		case 21:
			return "V";
		case 22:
			return "W";
		case 23:
			return "X";
		case 24:
			return "Y";
		case 25:
			return "Z";
		default:
			return "Err";
		}
	}
}
