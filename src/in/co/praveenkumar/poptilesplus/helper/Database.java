package in.co.praveenkumar.poptilesplus.helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class Database {
	private final String APP_SHARED_PREFS = "POP_TILE_PLUS_PREFERENCES";
	private SharedPreferences appSharedPrefs;
	private Editor prefsEditor;

	private String DEBUG_TAG = "POP_TILE_PLUS_PREFS";

	public Database(Context context) {
		this.appSharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS,
				Activity.MODE_PRIVATE);
		this.prefsEditor = appSharedPrefs.edit();
	}

	/**
	 * Get user highscore
	 * 
	 * @return score
	 */
	public int getHighscore(int gameMode) {
		return appSharedPrefs.getInt("highscore" + gameMode, 0);
	}

	/**
	 * Set the user highscore
	 * 
	 * @param score
	 */
	public void setHighscore(int score, int gameMode) {
		prefsEditor.putInt("highscore" + gameMode, score);
		prefsEditor.commit();
	}

	/**
	 * Get number of times the game has been played in a mode
	 * 
	 * @param gameMode
	 * @return count
	 */
	public int getPlaycount(int gameMode) {
		return appSharedPrefs.getInt("playcount" + gameMode, 0);
	}

	/**
	 * Increase the number of times the game has been played in a mode
	 * 
	 * @param gameMode
	 */
	public void increasePlaycount(int gameMode) {
		prefsEditor.putInt("highscore" + gameMode, getPlaycount(gameMode) + 1);
		prefsEditor.commit();
	}

	/**
	 * Returns the number of days in the current game streak
	 */
	public int getCurrentStreak() {
		return appSharedPrefs.getInt("currentstreak", 1);
	}

	/**
	 * Increment the number of days in the current game streak
	 */
	public void incrementCurrentStreak() {
		Log.d(DEBUG_TAG, "Streak incremented");
		prefsEditor.putInt("currentstreak", getCurrentStreak() + 1);
		prefsEditor.commit();
	}

	/**
	 * Reset the number of days in the current game streak
	 */
	public void resetCurrentStreak() {
		prefsEditor.putInt("currentstreak", 1);
		prefsEditor.commit();
	}

	/**
	 * Get the last played time stamp
	 */
	public long getLastPlayedTime() {
		long test = appSharedPrefs.getLong("lastplayedtime",
				System.currentTimeMillis());
		Log.d(DEBUG_TAG, "Last played " + test);
		return test;
	}

	/**
	 * Set the last played time stamp to now
	 */
	public void setLastPlayedTime() {
		prefsEditor.putLong("lastplayedtime", System.currentTimeMillis());
		prefsEditor.commit();
	}

}
