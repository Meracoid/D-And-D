package com.dd.entities;

public abstract class Monster extends Entity {

	public Monster(String name, int health, int attack, int defense) {
		super(name, health, health, attack, defense);
	}
	
	public String confrontText() {
        return "You encounter " + titleToString() + ". ";
    }
	
	public String examineText() {
        return titleToString();
    }
	
	@Override
	public String typeToString() {
		return "Monster";
	}
	
	@Override
	public String titleToString() {
		return "\"" + getName() + "\" the " + typeToString();
	}
}
