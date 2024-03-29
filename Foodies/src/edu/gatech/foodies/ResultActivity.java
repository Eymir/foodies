/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.gatech.foodies;

import java.util.ArrayList;

import edu.gatech.foodies.database.DBAdapter;
import edu.gatech.foodies.vo.Recipe;
import edu.gatech.foodies.vo.SQL_args;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Demonstrates how to write an efficient list adapter. The adapter used in this example binds
 * to an ImageView and to a TextView for each row in the list.
 *
 * To work efficiently the adapter implemented here uses two techniques:
 * - It reuses the convertView passed to getView() to avoid inflating View when it is not necessary
 * - It uses the ViewHolder pattern to avoid calling findViewById() when it is not necessary
 *
 * The ViewHolder pattern consists in storing a data structure in the tag of the view returned by
 * getView(). This data structures contains references to the views we want to bind data to, thus
 * avoiding calls to findViewById() every time getView() is invoked.
 */
public class ResultActivity extends ListActivity {
	
    DBAdapter myDB;
    static ArrayList<Recipe> result;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDB = new DBAdapter(this);
        myDB.createDB();
        myDB.openDB();
        
        ArrayList<SQL_args> args = new ArrayList<SQL_args>();
        Intent i = getIntent();
        Bundle in = i.getBundleExtra("in");
        Bundle type = i.getBundleExtra("type");
        String time = i.getStringExtra("time");
        
        for(int k = 0; k < in.size(); k++) {
        	SQL_args a = new SQL_args("Ingredients", in.getString(""+i));
        	args.add(a);
        }
        for(int k = 0; k < type.size(); k++) {
        	SQL_args a = new SQL_args("Type", type.getString(""+i));
        	args.add(a);
        }
        args.add(new SQL_args("Time", time));
        
        result = myDB.getRecipe(args);
        
        setListAdapter(new EfficientAdapter(this));
        getListView().setBackgroundColor(getResources().getColor(R.color.green));
    }
    
    private static class EfficientAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private Bitmap mIcon1;
        private Bitmap mIcon2;
        private Bitmap mIcon3;
        private Bitmap mIcon4;
        private ArrayList<Bitmap> iconList;

        public EfficientAdapter(Context context) {
            // Cache the LayoutInflate to avoid asking for a new one each time.
            mInflater = LayoutInflater.from(context);

            // Icons bound to the rows.
            mIcon1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_pancakes);
            mIcon2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_egg_drop);
            mIcon3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_salmon);
            mIcon4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_cookies);
            iconList = new ArrayList<Bitmap>();
            iconList.add(mIcon1);
            iconList.add(mIcon2);
            iconList.add(mIcon3);
            iconList.add(mIcon4);
        }

        /**
         * The number of items in the list is determined by the number of speeches
         * in our array.
         *
         * @see android.widget.ListAdapter#getCount()
         */
        public int getCount() {
            return result.size();
        }

        /**
         * Since the data comes from an array, just returning the index is
         * sufficent to get at the data. If we were using a more complex data
         * structure, we would return whatever object represents one row in the
         * list.
         *
         * @see android.widget.ListAdapter#getItem(int)
         */
        public Object getItem(int position) {
            return position;
        }

        /**
         * Use the array index as a unique id.
         *
         * @see android.widget.ListAdapter#getItemId(int)
         */
        public long getItemId(int position) {
            return position;
        }

        /**
         * Make a view to hold each row.
         *
         * @see android.widget.ListAdapter#getView(int, android.view.View,
         *      android.view.ViewGroup)
         */
        public View getView(int position, View convertView, ViewGroup parent) {
            // A ViewHolder keeps references to children views to avoid unneccessary calls
            // to findViewById() on each row.
            ViewHolder holder;

            // When convertView is not null, we can reuse it directly, there is no need
            // to reinflate it. We only inflate a new View when the convertView supplied
            // by ListView is null.
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_item_icon_text, null);

                // Creates a ViewHolder and store references to the two children views
                // we want to bind data to.
                holder = new ViewHolder();
                holder.text = (TextView) convertView.findViewById(R.id.recipe_text);
                holder.icon = (ImageView) convertView.findViewById(R.id.recipe_icon);

                convertView.setTag(holder);
            } else {
                // Get the ViewHolder back to get fast access to the TextView
                // and the ImageView.
                holder = (ViewHolder) convertView.getTag();
            }

            // Bind the data efficiently with the holder.
            holder.text.setText(result.get(position).getName());
            //holder.icon.setImageBitmap((position & 1) == 1 ? mIcon1 : mIcon2);
            holder.icon.setImageBitmap(iconList.get(position));

            return convertView;
        }

        static class ViewHolder {
            TextView text;
            ImageView icon;
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id){
    	super.onListItemClick(l, v, position, id);
    	Intent i = new Intent(this, RecipeActivity.class);
    	
    	i.putExtra("RECIPE_NAME", result.get(position).getName());
    	startActivity(i);
    	
    }

}
