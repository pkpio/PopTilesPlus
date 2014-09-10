package in.co.praveenkumar.poptilesplus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GameGridAdapter extends BaseAdapter {
	private Context context;
	public Cell[] cells;

	public GameGridAdapter(Context context, Cell[] cells) {
		this.context = context;
		this.cells = cells;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
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
		if (cells[position].isFilled())
			viewHolder.cellValue.setText(cells[position].getValue() + "");
		else
			viewHolder.cellValue.setVisibility(TextView.INVISIBLE);

		return convertView;
	}

	@Override
	public int getCount() {
		return cells.length;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public void notifyDataSetChanged() {
		this.cells = MainActivity.cells;
		super.notifyDataSetChanged();
	}

	static class ViewHolder {
		TextView cellValue;
	}

}
