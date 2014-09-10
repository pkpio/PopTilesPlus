package in.co.praveenkumar.poptilesplus;

import in.co.praveenkumar.poptilesplus.helper.Session;
import android.os.AsyncTask;

public class GameRunner extends AsyncTask<String, Integer, Boolean> {
	private GameGridAdapter gameView;

	public GameRunner(GameGridAdapter gameView) {
		this.gameView = gameView;
	}

	@Override
	protected void onProgressUpdate(Integer... progress) {
		gameView.notifyDataSetChanged();
	}

	@Override
	protected Boolean doInBackground(String... params) {
		int i = 0;
		while (Session.gamming) {
			Session.cells[i].setValue(i);
			publishProgress(0);
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			i++;
		}
		return null;
	}

}
