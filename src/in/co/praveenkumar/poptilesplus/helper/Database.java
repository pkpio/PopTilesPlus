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
	
	public Database(Context context){
		this.appSharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS,
				Activity.MODE_PRIVATE);
		this.prefsEditor = appSharedPrefs.edit();
	}

	/**
	 * Get user highscore
	 * 
	 * @return score
	 */
	public int getHighscore() {
		return appSharedPrefs.getInt("highscore", 0);
	}

	/**
	 * Set the user highscore
	 * 
	 * @param score
	 */
	public void setHighscore(int score) {
		Log.d(DEBUG_TAG, "Highscore updated");
		prefsEditor.putInt("highscore", score);
		prefsEditor.commit();
	}

}
