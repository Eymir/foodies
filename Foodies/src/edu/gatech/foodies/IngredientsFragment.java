package edu.gatech.foodies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import edu.gatech.foodies.adapters.IngredientAdapter;

public class IngredientsFragment extends Fragment {
	GridView gridView;
	static final String[] MOBILE_OS = new String[] { "Android", "iOS",
		"Windows", "Blackberry" };
	static final boolean ingredients[]= {false,false,false,false}; 

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main_ingredients,
				container, false);
		gridView = (GridView) rootView.findViewById(R.id.ingredients_gridview);
		
		gridView.setAdapter(new IngredientAdapter(rootView.getContext(), MOBILE_OS));
		gridView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				
				if(ingredients[position]){
				((ImageView)v).setImageResource(R.drawable.ic_eggs);
				
				}else{
					((ImageView)v).setImageResource(R.drawable.ic_eggs_pressed);
				}
				ingredients[position]=!ingredients[position];
			}
			
		});
		
		return rootView;
	}
}
