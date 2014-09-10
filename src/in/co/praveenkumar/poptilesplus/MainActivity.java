package in.co.praveenkumar.poptilesplus;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends Activity {
	GameGridAdapter gameView;
	static Cell[] cells = new Cell[Param.cells];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();

		GridView gridView = (GridView) findViewById(R.id.gridView1);
		gameView = new GameGridAdapter(this, cells);
		gridView.setAdapter(gameView);

		cells[4].setValue(4);
		gameView.notifyDataSetChanged();

		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Toast.makeText(getApplicationContext(), position + "",
						Toast.LENGTH_SHORT).show();
			}
		});

		GameRunner game = new GameRunner(cells, gameView);
		game.execute("");
	}

	private void init() {
		for (int i = 0; i < Param.cells; i++)
			cells[i] = new Cell();
	}
}
