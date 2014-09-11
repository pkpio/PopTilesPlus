package in.co.praveenkumar.poptilesplus;

import in.co.praveenkumar.poptilesplus.MainActivity.GamePlayService;
import in.co.praveenkumar.poptilesplus.helper.Param;
import in.co.praveenkumar.poptilesplus.helper.RandIndex;
import in.co.praveenkumar.poptilesplus.helper.Session;
import android.os.AsyncTask;

public class GameRunner extends AsyncTask<String, Integer, Boolean> {
	private GameGridAdapter gameView;
	private GamePlayService playService;

	public GameRunner(GameGridAdapter gameView, GamePlayService playService) {
		this.gameView = gameView;
		this.playService = playService;
	}

	@Override
	protected void onProgressUpdate(Integer... progress) {
		gameView.notifyDataSetChanged();
	}

	@Override
	protected Boolean doInBackground(String... params) {
		int count = 1;
		long cellTime = 0;
		while (Session.gamming) {
			int randIndex = RandIndex.unFilled();
			if (randIndex >= Param.cells) {
				Session.gamming = false;
				break;
			}
			Session.cells[randIndex].setValue(count);
			publishProgress(0);
			try {
				cellTime = Param.cellTime - count;
				cellTime = (Param.minCellTime < cellTime) ? cellTime
						: Param.minCellTime;
				Thread.sleep(cellTime);
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
		playService.submitScore(Session.score());
	}

}
