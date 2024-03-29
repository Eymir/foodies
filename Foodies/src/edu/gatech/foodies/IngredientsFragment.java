package edu.gatech.foodies;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import edu.gatech.foodies.adapters.IngredientAdapter;
import edu.gatech.foodies.database.DBAdapter;

public class IngredientsFragment extends Fragment {
	DBAdapter myDB;

	ArrayList<String> inPicsNo;
	ArrayList<Integer> inPicsNoId;

	ArrayList<String> inPicsYes;
	ArrayList<Integer> inPicsYesId;

	ArrayList<Boolean> inBoolList; 
	ArrayList<String> inPicsName;
	ArrayList<String> inResult;

	GridView gridView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		myDB = new DBAdapter(getActivity());
		myDB.createDB();
		myDB.openDB();

		inBoolList = new ArrayList<Boolean>();
		inResult = new ArrayList<String>();
		inPicsName = myDB.getInPicsName();

		inPicsNo = myDB.getInPics("No");
		inPicsNoId = new ArrayList<Integer>();

		for(String s : inPicsNo) {
			int id = getActivity().getResources().getIdentifier(s, "drawable", getActivity().getPackageName());
			inPicsNoId.add(id);
			inBoolList.add(false);
		}

		inPicsYes = myDB.getInPics("Yes");
		inPicsYesId = new ArrayList<Integer>();

		for(String s : inPicsYes) {
			int id = getActivity().getResources().getIdentifier(s, "drawable", getActivity().getPackageName());
			inPicsYesId.add(id);
		}

		View rootView = inflater.inflate(R.layout.fragment_main_ingredients,
				container, false);
		gridView = (GridView) rootView.findViewById(R.id.ingredients_gridview);
		gridView.setAdapter(new IngredientAdapter(rootView.getContext(), inPicsNoId));

		gridView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				boolean result = inBoolList.get(position);
				if(result){
					((ImageView)v).setImageResource(inPicsNoId.get(position));
				}else{
					((ImageView)v).setImageResource(inPicsYesId.get(position));
				}
				inBoolList.set(position, !result);
			}
		});
		return rootView;
	}
	
	@Override
	public void onPause() {
		super.onPause();
		for(int i = 0; i < inBoolList.size(); i++){
			if(inBoolList.get(i)) {
				inResult.add(inPicsName.get(i));
			}
		}
		Bundle b = new Bundle();
		for(int i = 0; i < inResult.size(); i++) {
			b.putString(""+i, inResult.get(i));
			Log.v("bunble 1", inResult.get(i));
		}
		MainActivity a = (MainActivity)getActivity();
		a.saveData(1, b);
		myDB.closeDB();
	}
}