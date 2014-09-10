package in.co.praveenkumar.poptilesplus;

import android.os.AsyncTask;

public class GameRunner extends AsyncTask<String, Integer, Boolean> {
	private Cell[] cells;
	private GameGridAdapter gameView;

	public GameRunner(Cell[] cells, GameGridAdapter gameView) {
		this.cells = cells;
		this.gameView = gameView;
	}

	@Override
	protected void onProgressUpdate(Integer... progress) {
		MainActivity.cells = this.cells;
		gameView.notifyDataSetChanged();
	}

	@Override
	protected Boolean doInBackground(String... params) {
		int i = 0;
		while (i < 20) {
			cells[i].setValue(i);
			publishProgress(0);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

}
