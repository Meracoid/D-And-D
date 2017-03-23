package com.dd.entities;

import com.dd.Stats;

public class Monster extends Entity {

	private String description;

	public Monster(String name, int health, int attack, int defense) {
		super(name, health, health, attack, defense);
	}

	@Override
	public String toString(){
		return name
				+"\nHealth:\t\t"+stats.getHealth()+"/"+stats.getMaxHealth()
				+"\nAttack/Defense:\t"+stats.getAttack()+"/"+stats.getDefense();
	}

	public String getDescription(){
		return description;
	}

	public void setDescription(String description){
		this.description = description;
	}
	
}
