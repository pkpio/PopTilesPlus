package in.co.praveenkumar.poptilesplus;

import in.co.praveenkumar.poptilesplus.helper.Database;
import in.co.praveenkumar.poptilesplus.helper.Param;
import in.co.praveenkumar.poptilesplus.helper.Session;
import in.co.praveenkumar.poptilesplus.model.Cell;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	GameGridAdapter gameView;
	static LinearLayout gameoverView;
	static TextView finalScoreView;
	static TextView highScoreView;
	static Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();

		GridView gridView = (GridView) findViewById(R.id.gridView1);
		gameView = new GameGridAdapter(this);
		gridView.setAdapter(gameView);

		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				TextView cellText = (TextView) v.findViewById(R.id.number);
				if (Session.score() + 1 == Session.cells[position].getValue()
						&& Session.gamming) {
					Session.setScore(Session.score() + 1);
					Session.cells[position].setFilled(false);
					cellText.setVisibility(TextView.INVISIBLE);
				} else {
					Session.gamming = false;
					MainActivity.gameover();
				}
			}
		});
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
	}

	public void startGame(View v) {
		if (v != null)
			v.setVisibility(LinearLayout.GONE);
		init();
		GameRunner game = new GameRunner(gameView);
		game.execute("");
	}

	public void restartGame(View v) {
		startGame(null);
	}

	public static void gameover() {
		gameoverView.setVisibility(LinearLayout.VISIBLE);
		finalScoreView.setText(Session.score() + "");
		Database db = new Database(context);
		if (db.getHighscore() < Session.score())
			db.setHighscore(Session.score());
		highScoreView.setText(db.getHighscore() + "");
	}
}
