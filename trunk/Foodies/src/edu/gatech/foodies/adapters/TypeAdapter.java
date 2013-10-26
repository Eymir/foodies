package edu.gatech.foodies.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import edu.gatech.foodies.R;

public class TypeAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Integer> typePicsNoId;

	public TypeAdapter(Context context, ArrayList<Integer> typePicsNoId) {
		this.context = context;
		this.typePicsNoId = typePicsNoId;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View gridView;

		if (convertView == null) {
			gridView = new View(context);

			// get layout from mobile.xml
			gridView = inflater.inflate(R.layout.ingredient_grid_item, null);

			// set image based on selected text
			ImageView imageView = (ImageView) gridView.findViewById(R.id.grid_item_image);

			imageView.setImageResource(typePicsNoId.get(position));

		} else {
			gridView = (View) convertView;
		}
		return gridView;
	}

	@Override
	public int getCount() {
		return typePicsNoId.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

}
