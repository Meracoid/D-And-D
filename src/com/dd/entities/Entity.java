package com.dd.entities;

import com.dd.Stats;

public abstract class Entity {

	protected String name;
	protected Stats stats;
	protected boolean isAlive;
	protected String text;

	public Entity(String name, int health, int maxHealth, int attack, int defense) {
		setName(name);
		setStats(new Stats(health, maxHealth, attack, defense));
		isAlive = true;
	}
	
	public Entity(String name, Stats stats) {
		setName(name);
		setStats(stats);
		isAlive = true;
	}
	
	public Entity(String name) {
		setName(name);
		setStats(new Stats(20, 20, 1, 0));
		isAlive = true;
	}
	
	public Entity() {
		setName("player");
		setStats(new Stats(20, 20, 1, 0));
		isAlive = true;
	}
	
	public void attack(Entity entity) {
        int damage = entity.takeDamage(attackDamage());
        text += titleToString() + " deals " + damage + " damage to " + entity.titleToString() + ". ";
    }
	
	public int takeDamage(int damage){
        int damageDealt = damage - stats.getDefense();
        if(damageDealt <= 0){
        	damageDealt = 1;
        }
		stats.setHealth(stats.getHealth() - damageDealt);
		if(!survives()){
			text += titleToString() +" just died! ";
		}
		return damageDealt;
	}
	
	public boolean survives() {
		if(stats.getHealth() <= 0) {
			die();
			return false;
		}
		return true;
	}
	
	public void die() {
		stats.setHealth(0);
		isAlive = false;
	}

	public int attackDamage() {
		return stats.getAttack();
	}
	
	public String typeToString() {
		return getClass().toString().substring(22);
	}
	
	public String titleToString() {
		return getName() + " the " + typeToString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Stats getStats() {
		return stats;
	}
	
	public void setStats(Stats stats) {
		this.stats = stats;
	}
	
	public void changeStats(Stats statModifier) {
		this.stats.changeStat(statModifier);
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void clearText() {
		text = "";
	}
}
