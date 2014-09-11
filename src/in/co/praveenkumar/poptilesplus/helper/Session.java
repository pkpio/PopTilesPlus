package in.co.praveenkumar.poptilesplus.helper;

import in.co.praveenkumar.poptilesplus.model.Cell;
import android.graphics.Typeface;
import android.widget.TextView;

public class Session {
	private static int score = 0;
	private static TextView scoreView = null;
	public static Cell[] cells = new Cell[Param.cells];
	public static Typeface font;
	/**
	 * Current status of the game. True if Running. False if Gameover
	 */
	public static Boolean gamming;
	public static int deviceWidth;
	public static int deviceHeight;

	/**
	 * Initialize a game session.
	 * 
	 * @param cells
	 *            Cells used for game
	 * @param scoreView
	 *            score displaying textview widget
	 */
	public static void init(Cell[] cells, TextView scoreView) {
		Session.cells = cells;
		Session.scoreView = scoreView;
		Session.gamming = true;
		Session.score = 0;
	}

	/**
	 * Update score onto UI
	 * 
	 * @param score
	 */
	public static void setScore(int score) {
		Session.score = score;
		if (scoreView != null)
			scoreView.setText(score + "");
	}

	/**
	 * Get current score
	 * 
	 */
	public static int score() {
		return Session.score;
	}

}
