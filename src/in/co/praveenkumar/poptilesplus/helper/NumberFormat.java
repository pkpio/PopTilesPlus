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
			return fibonacci(num) + "";
		default:
			return num + "";
		}
	}

	/**
	 * We shall be adding all the fibonacci numbers we found so far to this list
	 * so that we can use later without having to recompute.
	 */
	static ArrayList<Long> fibonacciSeries = new ArrayList<Long>();

	private static long fibonacci(int n) {
		// Because index in list starts from 0
		n = n - 1;

		// If not initialized. Do it with just 2 values.
		if (fibonacciSeries.size() == 0) {
			fibonacciSeries.add(Long.valueOf(0));
			fibonacciSeries.add(Long.valueOf(1));
		}

		if (fibonacciSeries.size() > n)
			return fibonacciSeries.get(n);

		// Compute the new number in the series and save it for later.
		else {
			fibonacciSeries.add(fibonacciSeries.get(n - 1)
					+ fibonacciSeries.get(n - 2));
			return fibonacciSeries.get(n);
		}
	}
}
