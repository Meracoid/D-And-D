package com.dd.items;

import java.io.Serializable;

import com.dd.Stats;

public abstract class Item implements Serializable {
	
	protected String name;
	protected Stats StatModifyer;
	
	public Item(String name) {
		setName(name);
	}
	
	public Item() {
		setName("blank item");
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setStatChange(int health, int maxHealth, int attack, int defense) {
		StatModifyer=new Stats(health,maxHealth,attack,defense);
	}
	
	public Stats getStatChange() { 
		return StatModifyer;
	}
	
	public Stats getNegStatChange() {
		int negHealth = -1 * StatModifyer.getHealth();
		int negMaxHealth = -1 * StatModifyer.getMaxHealth();
		int negAttack = -1 * StatModifyer.getAttack();
		int negDefense = -1 * StatModifyer.getDefense();
		Stats negStats = new Stats(negHealth, negMaxHealth, negAttack, negDefense);
		return negStats;
	}
	
	public String statModToString() {
		StringBuilder modStr=new StringBuilder("[");
		if(getStatChange().getHealth() > 0){
			modStr.append("(+" + getStatChange().getHealth() + " Health)");
		}
		if(getStatChange().getMaxHealth() > 0){
			modStr.append("(+" + getStatChange().getMaxHealth() + " Max Health)");
		}
		if(getStatChange().getAttack() > 0) {
			modStr.append("(+" + getStatChange().getAttack() + " Attack)");
		}
		if(getStatChange().getDefense() > 0){
			modStr.append("(+" + getStatChange().getDefense() + " Defense)");
		}
		modStr.append("]");
		return modStr.toString();
	}
	
	public String typeToString() {
		if(getClass().toString().length() > 19) {
			return getClass().toString().substring(19);
		}
		else {
			return "item";
		}
	}
	
	public String examineToString(){
		return statModToString() + " (" + typeToString() + ")";
	}
	
	public String titleToString() {
		return "\"" + getName() + "\" the " + typeToString();
	}
}