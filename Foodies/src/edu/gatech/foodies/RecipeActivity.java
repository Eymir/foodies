package edu.gatech.foodies;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class RecipeActivity extends Activity {
	String recipe;
	TextView recipe_name;
	TextView recipe_type;
	TextView recipe_time;
	TextView recipe_serving;
	TextView recipe_ingredients;
	TextView recipe_instruction;
	ImageView recipe_picture;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipe);
		recipe = getIntent().getExtras().getString("RECIPE_NAME");
		recipe_name = (TextView)findViewById(R.id.recipe_name);
		recipe_type = (TextView)findViewById(R.id.recipe_type);
		recipe_time = (TextView)findViewById(R.id.recipe_time);
		recipe_serving = (TextView)findViewById(R.id.recipe_serving);
		recipe_ingredients = (TextView)findViewById(R.id.recipe_ingredients);
		recipe_instruction = (TextView)findViewById(R.id.recipe_instruction);
		recipe_picture = (ImageView)findViewById(R.id.recipe_image);
		
		recipe_name.setText(recipe);
		recipe_picture.setImageResource(R.drawable.pic_egg_drop_soup);
		

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recipe, menu);
		return true;
	}

}
