package edu.gatech.foodies;

import edu.gatech.foodies.adapters.ImageAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class TypeFragment extends Fragment {
	GridView gridView;
	static final String[] MOBILE_OS = new String[] { "Android", "iOS",
		"Windows", "Blackberry" };
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main_type,
				container, false);
		gridView = (GridView) rootView.findViewById(R.id.ingredients_gridview);
		
		gridView.setAdapter(new ImageAdapter(rootView.getContext(), MOBILE_OS));
		gridView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				
				
			}
			
		});
		
		return rootView;
	}
}
