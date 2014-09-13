package in.co.praveenkumar.poptilesplus;

import in.co.praveenkumar.poptilesplus.MainActivity.GamePlayService;
import in.co.praveenkumar.poptilesplus.helper.Param;
import in.co.praveenkumar.poptilesplus.helper.RandIndex;
import in.co.praveenkumar.poptilesplus.helper.Session;
import android.os.AsyncTask;

public class GameRunner extends AsyncTask<String, Integer, Boolean> {
	private GameGridAdapter gameView;
	private GamePlayService playService;
	private AcheivementUnlocker medalUnlocker;

	public GameRunner(GameGridAdapter gameView, GamePlayService playService,
			AcheivementUnlocker medalUnlocker) {
		this.gameView = gameView;
		this.playService = playService;
		this.medalUnlocker = medalUnlocker;
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
				// Speed adjustments
				if (count < 20)
					cellTime = Param.cellTime - count * 4;
				else if (count < 40)
					cellTime = Param.cellTime - 80 - (count - 20) * 3;
				else if (count < 80)
					cellTime = Param.cellTime - 140 - (count - 40) * 2;
				else
					cellTime = Param.cellTime - 220 - (count - 80);

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
		medalUnlocker.checkForGameCountUnlocks();
	}

}
