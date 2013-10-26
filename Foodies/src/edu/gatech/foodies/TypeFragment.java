package edu.gatech.foodies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import edu.gatech.foodies.adapters.TypeAdapter;

public class TypeFragment extends Fragment {
	GridView gridView;
	static final String[] MOBILE_OS = new String[] { "Android", "iOS",
		"Windows", "Blackberry" };
	static final boolean ingredients[]= {false,false,false,false}; 
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main_type,
				container, false);
		gridView = (GridView) rootView.findViewById(R.id.type_gridview);
		
		gridView.setAdapter(new TypeAdapter(rootView.getContext(), MOBILE_OS));
		gridView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {

				if(ingredients[position]){
				((ImageView)v).setImageResource(R.drawable.ic_breakfast);
				
				}else{
					((ImageView)v).setImageResource(R.drawable.ic_breakfast_pressed);
				}
				ingredients[position]=!ingredients[position];
				
			}
			
		});
		
		return rootView;
	}
}
