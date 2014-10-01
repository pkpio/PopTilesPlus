package in.co.praveenkumar.poptilesplus;

import in.co.praveenkumar.poptilesplus.helper.Database;
import in.co.praveenkumar.poptilesplus.helper.Param;
import in.co.praveenkumar.poptilesplus.helper.Session;
import in.co.praveenkumar.poptilesplus.model.Cell;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
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
	private BillingProcessor donate;
	private AcheivementUnlocker medalUnlocker;
	private static Database db;

	// Play Billing related
	private static final String PRODUCT_ID = "in.co.praveenkumar.poptiles.donate";
	private static final String LICENSE_KEY = "02425045829981832447";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Setup billing
		donate = new BillingProcessor(this, LICENSE_KEY,
				new BillingProcessor.IBillingHandler() {
					@Override
					public void onProductPurchased(String productId,
							TransactionDetails details) {
						Toast.makeText(context,
								"You donated already. Thank you :)",
								Toast.LENGTH_LONG).show();
					}

					@Override
					public void onBillingError(int errorCode, Throwable error) {
						Toast.makeText(context, "Purchase failed!",
								Toast.LENGTH_LONG).show();
					}

					@Override
					public void onBillingInitialized() {
					}

					@Override
					public void onPurchaseHistoryRestored() {
					}
				});

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
		CharSequence playServices[] = new CharSequence[] { "Decimal", "Binary",
				"Hexadecimal", "Fibonnaci", "Kid" };
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Game mode");
		builder.setItems(playServices, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int optionChose) {
				Bitmap icon = null;
				switch (optionChose) {
				case 0:
					Session.gameMode = Session.GAME_MODE_DEC;
					icon = BitmapFactory.decodeResource(context.getResources(),
							R.drawable.icon_mode_dec);
					break;
				case 1:
					Session.gameMode = Session.GAME_MODE_BIN;
					icon = BitmapFactory.decodeResource(context.getResources(),
							R.drawable.icon_mode_bin);
					break;
				case 2:
					Session.gameMode = Session.GAME_MODE_HEX;
					icon = BitmapFactory.decodeResource(context.getResources(),
							R.drawable.icon_mode_hex);
					break;
				case 3:
					Session.gameMode = Session.GAME_MODE_FIB;
					icon = BitmapFactory.decodeResource(context.getResources(),
							R.drawable.icon_mode_fib);
					break;
				case 4:
					Session.gameMode = Session.GAME_MODE_KID;
					icon = BitmapFactory.decodeResource(context.getResources(),
							R.drawable.icon_mode_kid);
					break;

				default:
					Session.gameMode = Session.GAME_MODE_DEC;
					icon = BitmapFactory.decodeResource(context.getResources(),
							R.drawable.icon_mode_dec);
					break;
				}
				gameoverModeView.setImageBitmap(icon);
				gamestartModeView.setImageBitmap(icon);
			}
		});
		builder.show();
	}

	@Override
	public void onSignInFailed() {
		System.out.println("Damn login failed!");

	}

	@Override
	public void onSignInSucceeded() {
		System.out.println("Yo logged in!");

	}

	public void showPlayServices(View v) {
		CharSequence playServices[] = new CharSequence[] { "Highscores",
				"Achievements" };
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Play Services");
		builder.setItems(playServices, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int optionChose) {
				if (optionChose == 0)
					showLeaderBoards();
				else
					showAchievements();
			}
		});
		builder.show();
	}

	public void toggleChaos(View v) {
		Session.chaos = !Session.chaos;
		ImageView chaosImageView = (ImageView) v;
		if (Session.chaos)
			chaosImageView.setImageResource(R.drawable.icon_chaos_enabled);
		else
			chaosImageView.setImageResource(R.drawable.icon_chaos_disabled);
	}

	public void showAchievements() {
		if (getApiClient().isConnected())
			startActivityForResult(
					Games.Achievements.getAchievementsIntent(getApiClient()),
					11);
		else
			beginUserInitiatedSignIn();
	}

	public void showLeaderBoards() {
		if (!getApiClient().isConnected()) {
			beginUserInitiatedSignIn();
			return;
		}

		switch (Session.gameMode) {
		case Session.GAME_MODE_BIN:
			if (Session.chaos)
				startActivityForResult(Games.Leaderboards.getLeaderboardIntent(
						getApiClient(),
						getString(R.string.leaderboard_geek_chaos)), 1);
			else
				startActivityForResult(Games.Leaderboards.getLeaderboardIntent(
						getApiClient(), getString(R.string.leaderboard_geek)),
						2);
			break;
		case Session.GAME_MODE_DEC:
			if (Session.chaos)
				startActivityForResult(Games.Leaderboards.getLeaderboardIntent(
						getApiClient(),
						getString(R.string.leaderboard_regular_chaos)), 3);
			else
				startActivityForResult(
						Games.Leaderboards.getLeaderboardIntent(getApiClient(),
								getString(R.string.leaderboard_regular)), 4);
			break;
		case Session.GAME_MODE_HEX:
			if (Session.chaos)
				startActivityForResult(Games.Leaderboards.getLeaderboardIntent(
						getApiClient(),
						getString(R.string.leaderboard_programmer_chaos)), 5);
			else
				startActivityForResult(Games.Leaderboards.getLeaderboardIntent(
						getApiClient(),
						getString(R.string.leaderboard_programmer)), 6);
			break;
		case Session.GAME_MODE_FIB:
			if (Session.chaos)
				startActivityForResult(Games.Leaderboards.getLeaderboardIntent(
						getApiClient(),
						getString(R.string.leaderboard_fibonnaci_chaos)), 7);
			else
				startActivityForResult(Games.Leaderboards.getLeaderboardIntent(
						getApiClient(),
						getString(R.string.leaderboard_fibonnaci)), 8);
			break;
		case Session.GAME_MODE_KID:
			if (Session.chaos)
				startActivityForResult(Games.Leaderboards.getLeaderboardIntent(
						getApiClient(),
						getString(R.string.leaderboard_chaos_kid)), 9);
			else
				startActivityForResult(Games.Leaderboards.getLeaderboardIntent(
						getApiClient(), getString(R.string.leaderboard_kid)),
						10);
			break;

		default:
			break;
		}
	}

	public void donate(View v) {
		donate.consumePurchase(PRODUCT_ID);
		donate.purchase(PRODUCT_ID);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (!donate.handleActivityResult(requestCode, resultCode, data))
			super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onDestroy() {
		if (donate != null)
			donate.release();

		super.onDestroy();
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
				if (Session.chaos)
					Games.Leaderboards.submitScore(getApiClient(),
							getString(R.string.leaderboard_geek_chaos), score);
				else
					Games.Leaderboards.submitScore(getApiClient(),
							getString(R.string.leaderboard_geek), score);
				break;
			case Session.GAME_MODE_DEC:
				if (Session.chaos)
					Games.Leaderboards.submitScore(getApiClient(),
							getString(R.string.leaderboard_regular_chaos),
							score);
				else
					Games.Leaderboards.submitScore(getApiClient(),
							getString(R.string.leaderboard_regular), score);
				break;
			case Session.GAME_MODE_HEX:
				if (Session.chaos)
					Games.Leaderboards.submitScore(getApiClient(),
							getString(R.string.leaderboard_programmer_chaos),
							score);
				else
					Games.Leaderboards.submitScore(getApiClient(),
							getString(R.string.leaderboard_programmer), score);
			case Session.GAME_MODE_FIB:
				if (Session.chaos)
					Games.Leaderboards.submitScore(getApiClient(),
							getString(R.string.leaderboard_fibonnaci_chaos),
							score);
				else
					Games.Leaderboards.submitScore(getApiClient(),
							getString(R.string.leaderboard_fibonnaci), score);
				break;
			case Session.GAME_MODE_KID:
				if (Session.chaos)
					Games.Leaderboards.submitScore(getApiClient(),
							getString(R.string.leaderboard_chaos_kid), score);
				else
					Games.Leaderboards.submitScore(getApiClient(),
							getString(R.string.leaderboard_kid), score);
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
