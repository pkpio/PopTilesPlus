package in.co.praveenkumar.poptilesplus;

import in.co.praveenkumar.poptilesplus.MainActivity.GamePlayService;
import in.co.praveenkumar.poptilesplus.helper.NumberFormat;
import in.co.praveenkumar.poptilesplus.helper.Session;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GameGridAdapter extends BaseAdapter {
	private Context context;
	private int scoreHeight;
	private GamePlayService playService;
	private AcheivementUnlocker medalUnlocker;

	public GameGridAdapter(Context context, GamePlayService playService,
			AcheivementUnlocker medalUnlocker) {
		this.context = context;
		this.playService = playService;
		this.scoreHeight = dpToPx(30);
		this.medalUnlocker = medalUnlocker;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.number_layout, null);
			viewHolder = new ViewHolder();
			viewHolder.cellValue = (TextView) convertView
					.findViewById(R.id.number);
			viewHolder.cellValue.setTypeface(Session.font);
			LayoutParams params = viewHolder.cellValue.getLayoutParams();
			// I have no idea why 6
			params.height = (int) (Session.deviceHeight - 100) / 5;
			viewHolder.cellValue.setLayoutParams(params);
			convertView.setTag(viewHolder);
			convertView.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (Session.score() + 1 == Session.cells[position]
							.getValue() && Session.gamming) {
						Session.setScore(Session.score() + 1);
						medalUnlocker.checkForScoreUnlocks();
						Session.cells[position].setFilled(false);
						viewHolder.cellValue.setVisibility(TextView.INVISIBLE);
					} else {
						Session.gamming = false;
						MainActivity.gameover();
						playService.submitScore(Session.score());
					}
					return false;
				}
			});
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (Session.cells[position].isFilled()) {
			viewHolder.cellValue.setVisibility(TextView.VISIBLE);
			viewHolder.cellValue.setText(NumberFormat
					.out(Session.cells[position].getValue()));
		} else
			viewHolder.cellValue.setVisibility(TextView.INVISIBLE);

		return convertView;
	}

	@Override
	public int getCount() {
		return Session.cells.length;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public int dpToPx(int dp) {
		DisplayMetrics displayMetrics = context.getResources()
				.getDisplayMetrics();
		int px = Math.round(dp
				* (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
		return px;
	}

	static class ViewHolder {
		TextView cellValue;
	}

}
