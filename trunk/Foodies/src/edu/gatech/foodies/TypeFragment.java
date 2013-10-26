package edu.gatech.foodies;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import edu.gatech.foodies.adapters.TypeAdapter;
import edu.gatech.foodies.database.DBAdapter;

public class TypeFragment extends Fragment {
	DBAdapter myDB;

	ArrayList<String> typePicsNo;
	ArrayList<Integer> typePicsNoId;

	ArrayList<String> typePicsYes;
	ArrayList<Integer> typePicsYesId;

	ArrayList<Boolean> typeBoolList; 
	ArrayList<String> typePicsName;
	ArrayList<String> typeResult;
	GridView gridView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		myDB = new DBAdapter(getActivity());
		myDB.createDB();
		myDB.openDB();

		typeBoolList = new ArrayList<Boolean>();
		typeResult = new ArrayList<String>();
		typePicsName = myDB.getTypePicsName();

		typePicsNo = myDB.getTypePics("No");
		typePicsNoId = new ArrayList<Integer>();

		for(String s : typePicsNo) {
			int id = getActivity().getResources().getIdentifier(s, "drawable", getActivity().getPackageName());
			typePicsNoId.add(id);
			typeBoolList.add(false);
		}

		typePicsYes = myDB.getTypePics("Yes");
		typePicsYesId = new ArrayList<Integer>();

		for(String s : typePicsYes) {
			int id = getActivity().getResources().getIdentifier(s, "drawable", getActivity().getPackageName());
			typePicsYesId.add(id);
		}

		View rootView = inflater.inflate(R.layout.fragment_main_type,
				container, false);
		gridView = (GridView) rootView.findViewById(R.id.type_gridview);
		gridView.setAdapter(new TypeAdapter(rootView.getContext(), typePicsNoId));

		gridView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				boolean result = typeBoolList.get(position);
				if(result){
					((ImageView)v).setImageResource(typePicsNoId.get(position));
				}else{
					((ImageView)v).setImageResource(typePicsYesId.get(position));
				}
				typeBoolList.set(position, !result);
			}
		});
		return rootView;
	}
	
	@Override
	public void onPause() {
		super.onPause();
		for(int i = 0; i < typeBoolList.size(); i++){
			if(typeBoolList.get(i)) {
				typeResult.add(typePicsName.get(i));
			}
		}
		Bundle b = new Bundle();
		for(int i = 0; i < typeResult.size(); i++) {
			b.putString(""+i, typeResult.get(i));
			Log.v("bunble 2", typeResult.get(i));
		}
		MainActivity a = (MainActivity)getActivity();
		a.saveData(2, b);
		myDB.closeDB();
	}
}