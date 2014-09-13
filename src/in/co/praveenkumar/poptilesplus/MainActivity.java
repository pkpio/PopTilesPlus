package in.co.praveenkumar.poptilesplus;

import in.co.praveenkumar.poptilesplus.helper.Database;
import in.co.praveenkumar.poptilesplus.helper.Param;
import in.co.praveenkumar.poptilesplus.helper.Session;
import in.co.praveenkumar.poptilesplus.model.Cell;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.BaseGameActivity;

public class MainActivity extends BaseGameActivity {
	GameGridAdapter gameView;
	static FrameLayout gameoverView;
	static TextView finalScoreView;
	static TextView highScoreView;
	static ImageView gameoverModeView;
	static ImageView gamestartModeView;
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

		gameoverView = (FrameLayout) findViewById(R.id.gameover_layout);
		finalScoreView = (TextView) findViewById(R.id.final_score_view);
		highScoreView = (TextView) findViewById(R.id.high_score_view);
		gameoverModeView = (ImageView) findViewById(R.id.gameover_mode_button);
		gamestartModeView = (ImageView) findViewById(R.id.gamestart_mode_button);
		gameoverView.setVisibility(FrameLayout.GONE);

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
		Session.gamming = true;
		GameRunner game = new GameRunner(gameView, playService, medalUnlocker);
		game.execute("");
	}

	public void restartGame(View v) {
		startGame(null);
	}

	public static void gameover() {
		gameoverView.setVisibility(FrameLayout.VISIBLE);
		finalScoreView.setText(Session.score() + "");
		if (db.getHighscore(Session.gameMode) < Session.score())
			db.setHighscore(Session.score(), Session.gameMode);
		highScoreView.setText(db.getHighscore(Session.gameMode) + "");
	}

	public void changeGameMode(View v) {
		Bitmap icon = null;
		if (Session.gameMode == Session.GAME_MODE_DEC) {
			Session.gameMode = Session.GAME_MODE_BIN;
			icon = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.icon_mode_bin);
		} else if (Session.gameMode == Session.GAME_MODE_BIN) {
			Session.gameMode = Session.GAME_MODE_HEX;
			icon = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.icon_mode_hex);
		} else if (Session.gameMode == Session.GAME_MODE_HEX) {
			Session.gameMode = Session.GAME_MODE_DEC;
			icon = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.icon_mode_dec);
		}
		gameoverModeView.setImageBitmap(icon);
		gamestartModeView.setImageBitmap(icon);
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
		if (getApiClient().isConnected())
			startActivityForResult(
					Games.Achievements.getAchievementsIntent(getApiClient()), 1);
		else
			Toast.makeText(this, "Connect to Playservices first",
					Toast.LENGTH_LONG).show();
	}

	public void showLeaderBoards(View v) {
		if (!getApiClient().isConnected()) {
			Toast.makeText(this, "Connect to Playservices first",
					Toast.LENGTH_LONG).show();
			return;
		}

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

		public void incrementAchievement(int id) {
			if (isSignedIn())
				Games.Achievements.increment(getApiClient(), getString(id), 1);
		}
	}
}
