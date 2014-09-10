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

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View gridView;

		if (convertView == null) {
			gridView = new View(context);
			gridView = inflater.inflate(R.layout.number_layout, null);

			// set value into textview
			TextView textView = (TextView) gridView.findViewById(R.id.number);
			textView.setText(cells[position].getValue() + "");

		} else {
			gridView = (View) convertView;
		}

		return gridView;
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

}
