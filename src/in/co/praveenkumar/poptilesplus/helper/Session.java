package in.co.praveenkumar.poptilesplus.helper;

import in.co.praveenkumar.poptilesplus.model.Cell;
import android.graphics.Typeface;
import android.widget.TextView;

public class Session {
	public static final int GAME_MODE_DEC = 0;
	public static final int GAME_MODE_HEX = 1;
	public static final int GAME_MODE_BIN = 2;
	public static final int GAME_MODE_DEFAULT = GAME_MODE_DEC;

	private static int score = 0;
	private static TextView scoreView = null;
	public static Cell[] cells = new Cell[Param.cells];
	public static Typeface font;
	/**
	 * Current status of the game. True if Running. False if Gameover
	 */
	public static Boolean gamming = false;
	public static int deviceWidth;
	public static int deviceHeight;
	/**
	 * Takes one of the 3 supported modes indicated by GameMode_
	 */
	public static int gameMode;

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
		Session.score = 0;
		scoreView.setText("0");
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
