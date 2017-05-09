package com.dd.entities;

import com.dd.Stats;

import java.io.Serializable;

public abstract class Entity implements Serializable{

	protected String name;
	protected Stats stats;
	protected static String text = "";

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
        text += getTitle() + " deals " + damage + " damage to " + entity.getTitle() + ". ";
    }
	
	public void attack(Entity entity, String altText) {
        int damage = entity.takeDamage(attackDamage());
        text += altText + getTitle() + " deals " + damage + " damage to " + entity.getTitle() + ". ";
    }
	
	public void attack(Entity entity, int damage) {
        damage = entity.takeDamage(damage);
        text += getTitle() + " deals " + damage + " damage to " + entity.getTitle() + ". ";
    }
	
	public void attack(Entity entity, int damage, String altText) {
        damage = entity.takeDamage(damage);
        text += altText + getTitle() + " deals " + damage + " damage to " + entity.getTitle() + ". ";
    }
	
	public int takeDamage(int damage){
        int damageDealt = damage - stats.getDefense();
        if(damageDealt <= 0){
        	damageDealt = 1;
        }
		stats.setHealth(stats.getHealth() - damageDealt);
		if(!survives()){
			text += getTitle() +" just died! ";
		}
		return damageDealt;
	}
	
	public int takeDamage(int damage, String addText){
        int damageDealt = damage - stats.getDefense();
        if(damageDealt <= 0){
        	damageDealt = 1;
        }
		stats.setHealth(stats.getHealth() - damageDealt);
		if(!survives()){
			text += addText+ getTitle() +" just died! ";
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
	
	public void die(String addText) {
		text += addText;
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
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void clearText() {
		text = "";
	}
	
	public boolean isDead() {
		return stats.getHealth() == 0;
	}
}
