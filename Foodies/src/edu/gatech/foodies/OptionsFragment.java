package edu.gatech.foodies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;

public class OptionsFragment extends Fragment {
	
	String time;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main_options,
				container, false);
		NumberPicker timePicker = (NumberPicker) rootView.findViewById(R.id.picker);
		String[] a={"~10 min","~20 min","~30 min",">30 min"};
        timePicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        timePicker.setDisplayedValues(a);
        timePicker.setMaxValue(3);
        timePicker.setMinValue(0);       
        timePicker.setOnValueChangedListener(new OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            	switch (newVal) {
				case 0:
					time = "10";
					break;
				case 1:
					time = "20";
					break;
				case 2:
					time = "30";
					break;
				case 3:
					time = "999";
					break;
				default:
					time = "999";
					break;
				}
            }
        });
        
        Button btn_result = (Button)rootView.findViewById(R.id.btn_findrecipes);
        btn_result.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				Intent i = new Intent(view.getContext(), ResultActivity.class);
				MainActivity a = (MainActivity)getActivity();
				Bundle in = a.getData(1);
				Bundle type = a.getData(2);
				i.putExtra("in", in);
				i.putExtra("type", type);
				i.putExtra("time", time);
				startActivity(i);
				
			}
        	
        });
		return rootView;
	}
}
