package com.dd.entities;

import com.dd.Stats;

public abstract class Entity {

	protected String name;
	protected Stats stats;
	protected boolean isAlive;

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

	public void takeDamage(int damage){
        int newDamage;
        if(damage - stats.getDefense() <= 0){
            newDamage = 1;
        }
        else {
            newDamage = damage - stats.getDefense();
        }
		stats.setHealth(stats.getHealth() - newDamage);
		if(!survives()){
			System.out.println("You just killed a " + name + "!");
		}
	}
	
	public String typeToString() {
		return getClass().toString().substring(22);
	}
	
	public String titleToString() {
		return typeToString() + " " + getName();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void attack(Entity entity){
		entity.takeDamage(stats.getAttack());
	}
	
	public Stats getStats() {
		return stats;
	}
	
	public void setStats(Stats stats) {
		this.stats = stats;
	}
}