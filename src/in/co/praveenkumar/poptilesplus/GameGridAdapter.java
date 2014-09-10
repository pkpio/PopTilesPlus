package in.co.praveenkumar.poptilesplus;

import in.co.praveenkumar.poptilesplus.helper.Session;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GameGridAdapter extends BaseAdapter {
	private Context context;

	public GameGridAdapter(Context context) {
		this.context = context;
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
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (Session.cells[position].isFilled()) {
			viewHolder.cellValue.setVisibility(TextView.VISIBLE);
			viewHolder.cellValue.setText(Session.cells[position].getValue()
					+ "");
		} else
			viewHolder.cellValue.setVisibility(TextView.INVISIBLE);

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (Session.score() + 1 == Session.cells[position].getValue()
						&& Session.gamming) {
					Session.setScore(Session.score() + 1);
					Session.cells[position].setFilled(false);
					viewHolder.cellValue.setVisibility(TextView.INVISIBLE);
				} else {
					Session.gamming = false;
					MainActivity.gameover();
				}
			}
		});

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

	static class ViewHolder {
		TextView cellValue;
	}

}
