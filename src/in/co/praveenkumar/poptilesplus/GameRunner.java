package in.co.praveenkumar.poptilesplus;

import in.co.praveenkumar.poptilesplus.helper.Param;
import in.co.praveenkumar.poptilesplus.helper.RandIndex;
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
		int count = 1;
		while (Session.gamming) {
			int randIndex = RandIndex.unFilled();
			if (randIndex >= Param.cells) {
				Session.gamming = false;
				break;
			}
			Session.cells[randIndex].setValue(count);
			publishProgress(0);
			try {
				Thread.sleep(Param.cellTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
			count++;
		}
		return true;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		MainActivity.gameover();
		
	}

}
