package in.co.praveenkumar.poptilesplus;

import in.co.praveenkumar.poptilesplus.helper.Param;
import in.co.praveenkumar.poptilesplus.helper.Session;
import in.co.praveenkumar.poptilesplus.model.Cell;
import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

public class MainActivity extends Activity {
	GameGridAdapter gameView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();

		GridView gridView = (GridView) findViewById(R.id.gridView1);
		gameView = new GameGridAdapter(this);
		gridView.setAdapter(gameView);

		GameRunner game = new GameRunner(gameView);
		game.execute("");
	}

	private void init() {
		Cell[] cells = new Cell[Param.cells];
		for (int i = 0; i < Param.cells; i++)
			cells[i] = new Cell();
		Session.init(cells, (TextView) findViewById(R.id.score_view));
	}
}