package com.dd.entities;

import com.dd.Stats;
import com.dd.command_util.CommandOutputLog;

import java.io.Serializable;

public abstract class Entity implements Serializable{

	protected String name;
	protected Stats stats;

	public Entity(String name, int health, int maxHealth, int attack, int defense) {
		setName(name);
		setStats(new Stats(health, maxHealth, attack, defense));
	}
	
	public Entity(String name, Stats stats) {
		setName(name);
		setStats(stats);
	}
	
	public Entity(String name) {
		setName(name);
		setStats(new Stats(20, 20, 1, 0));
	}
	
	public Entity() {
		setName("player");
		setStats(new Stats(20, 20, 1, 0));
	}
	
	public void attack(Entity entity) {
        int damage = entity.takeDamage(attackDamage());
        print(getTitle() + " deals " + damage + " damage to " + entity.getTitle() + ". ");
    }
	
	public void attack(Entity entity, int damage) {
        damage = entity.takeDamage(damage);
        print(getTitle() + " deals " + damage + " damage to " + entity.getTitle() + ". ");
    }
	
	public int takeDamage(int damage){
        int damageDealt = damage - stats.getDefense();
        if(damageDealt <= 0){
        	damageDealt = 1;
        }
		stats.setHealth(stats.getHealth() - damageDealt);
		if(!survives()){
			print(getTitle() +" just died! ");
		}
		return damageDealt;
	}
	
	public boolean survives() {
		if(stats.getHealth() <= 0) {
			die();
		}
		return true;
	}
	
	public void die() {
		stats.setHealth(0);
	}

	public int attackDamage() {
		return stats.getAttack();
	}
	
	public String getType() {
		return getClass().toString().substring(30);
	}
	
	public String getTitle() {
		return getName() + " the " + getType();
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
	
	public boolean isDead() {
		return stats.getHealth() == 0;
	}
	
	protected static void print(String text) {
    	CommandOutputLog.print(text);
    }
}
