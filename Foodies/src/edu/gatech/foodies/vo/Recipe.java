package edu.gatech.foodies.vo;

public class Recipe {
	
	private String Type;
	private String Name;
	private String Ingredients;
	private int Time;
	private int Servings;
	private String Instruction;
	
	public Recipe(){}

	
	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getIngredients() {
		return Ingredients;
	}

	public void setIngredients(String ingredients) {
		Ingredients = ingredients;
	}

	public int getTime() {
		return Time;
	}

	public void setTime(int time) {
		Time = time;
	}

	public int getServings() {
		return Servings;
	}

	public void setServings(int servings) {
		Servings = servings;
	}

	public String getInstruction() {
		return Instruction;
	}

	public void setInstruction(String instruction) {
		Instruction = instruction;
	}

}
