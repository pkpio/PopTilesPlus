package in.co.praveenkumar.poptilesplus.helper;

import java.util.ArrayList;
import java.util.Random;

public class RandIndex {

	/**
	 * Returns a random index among the currently unfilled cell indices. Returns
	 * Param.cells + 1 if no unfilled cells are left.
	 * 
	 * @return
	 */
	public static int unFilled() {
		ArrayList<Integer> availPos = new ArrayList<Integer>();

		// Make an arraylist of available indices
		for (int i = 0; i < Session.cells.length; i++) {
			if (!Session.cells[i].isFilled())
				availPos.add(Integer.valueOf(i));
		}

		// Return if no empty cells available
		if (availPos.size() == 0)
			return Param.cells + 1;

		// Pick a random index from the arraylist
		Random rand = new Random();
		int randIndex = rand.nextInt(availPos.size());
		return availPos.get(randIndex);
	}

}
