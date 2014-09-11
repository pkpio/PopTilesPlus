package in.co.praveenkumar.poptilesplus;

import in.co.praveenkumar.poptilesplus.helper.Database;
import in.co.praveenkumar.poptilesplus.helper.Param;
import in.co.praveenkumar.poptilesplus.helper.Session;
import in.co.praveenkumar.poptilesplus.model.Cell;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.BaseGameActivity;

public class MainActivity extends BaseGameActivity {
	GameGridAdapter gameView;
	static LinearLayout gameoverView;
	static TextView finalScoreView;
	static TextView highScoreView;
	static Context context;
	private GamePlayService playService = new GamePlayService();
	private AcheivementUnlocker medalUnlocker;
	private static Database db;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();

		db = new Database(context);
		medalUnlocker = new AcheivementUnlocker(playService, db);
		GridView gridView = (GridView) findViewById(R.id.gridView1);
		gameView = new GameGridAdapter(this, playService, medalUnlocker);
		gridView.setAdapter(gameView);
	}

	private void init() {
		Cell[] cells = new Cell[Param.cells];
		for (int i = 0; i < Param.cells; i++)
			cells[i] = new Cell();
		Session.init(cells, (TextView) findViewById(R.id.score_view));

		gameoverView = (LinearLayout) findViewById(R.id.gameover_layout);
		finalScoreView = (TextView) findViewById(R.id.final_score_view);
		highScoreView = (TextView) findViewById(R.id.high_score_view);
		gameoverView.setVisibility(LinearLayout.GONE);

		MainActivity.context = this;

		DisplayMetrics displayMetrics = context.getResources()
				.getDisplayMetrics();
		Session.deviceWidth = displayMetrics.widthPixels;
		Session.deviceHeight = displayMetrics.heightPixels;

		Typeface font = Typeface.createFromAsset(getAssets(), "candal.ttf");
		if (font != null)
			Session.font = font;
		finalScoreView.setTypeface(font);
	}

	public void startGame(View v) {
		if (v != null)
			v.setVisibility(LinearLayout.GONE);
		init();
		GameRunner game = new GameRunner(gameView, playService);
		game.execute("");
	}

	public void restartGame(View v) {
		startGame(null);
	}

	public static void gameover() {
		gameoverView.setVisibility(LinearLayout.VISIBLE);
		finalScoreView.setText(Session.score() + "");
		if (db.getHighscore(Session.gameMode) < Session.score())
			db.setHighscore(Session.score(), Session.gameMode);
		highScoreView.setText(db.getHighscore(Session.gameMode) + "");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.layout.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.mode_bin:
			if (!Session.gamming)
				Session.gameMode = Session.GAME_MODE_BIN;
			return true;

		case R.id.mode_dec:
			if (!Session.gamming)
				Session.gameMode = Session.GAME_MODE_DEC;
			return true;

		case R.id.mode_hex:
			if (!Session.gamming)
				Session.gameMode = Session.GAME_MODE_HEX;
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onSignInFailed() {
		System.out.println("Damn login failed!");

	}

	@Override
	public void onSignInSucceeded() {
		System.out.println("Yo logged in!");

	}

	public void showAchievements(View v) {
		startActivityForResult(
				Games.Achievements.getAchievementsIntent(getApiClient()), 1);
	}

	public void showLeaderBoards(View v) {
		switch (Session.gameMode) {
		case Session.GAME_MODE_BIN:
			startActivityForResult(Games.Leaderboards.getLeaderboardIntent(
					getApiClient(), getString(R.string.leaderboard_geek)), 2);
			break;
		case Session.GAME_MODE_DEC:
			startActivityForResult(Games.Leaderboards.getLeaderboardIntent(
					getApiClient(), getString(R.string.leaderboard_regular)), 3);
			break;
		case Session.GAME_MODE_HEX:
			startActivityForResult(
					Games.Leaderboards.getLeaderboardIntent(getApiClient(),
							getString(R.string.leaderboard_programmer)), 4);
			break;

		default:
			break;
		}
	}

	/**
	 * For all Google play services related actions
	 * 
	 * @author Praveen Kumar Pendyala (praveen@praveenkumar.co.in)
	 * 
	 */
	class GamePlayService {
		public void submitScore(long score) {
			if (!isSignedIn())
				return;

			switch (Session.gameMode) {
			case Session.GAME_MODE_BIN:
				Log.d("Scores submit", "Submit requested!");
				Games.Leaderboards.submitScore(getApiClient(),
						getString(R.string.leaderboard_geek), score);
				break;
			case Session.GAME_MODE_DEC:
				Log.d("Scores submit", "Submit requested!");
				Games.Leaderboards.submitScore(getApiClient(),
						getString(R.string.leaderboard_regular), score);
				break;
			case Session.GAME_MODE_HEX:
				Log.d("Scores submit", "Submit requested!");
				Games.Leaderboards.submitScore(getApiClient(),
						getString(R.string.leaderboard_programmer), score);
				break;

			default:
				break;
			}
		}

		public void unLockAchievement(int id) {
			if (isSignedIn())
				Games.Achievements.unlock(getApiClient(), getString(id));
		}
	}
}
