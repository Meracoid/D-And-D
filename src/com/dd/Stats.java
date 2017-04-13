package com.dd;

public class Stats {
	
	private int health;
	private int maxHealth;
	
	private int attack;
	private int defense;
	
	public Stats(int health, int maxHealth, int attack, int defense) {
		setHealth(health);
		setMaxHealth(maxHealth);
		setAttack(attack);
		setDefense(defense);
	}
	
	public void changeStat(Stats statChange){
		addHealth(statChange.getHealth());
		addMaxHealth(statChange.getMaxHealth());
		addAttack(statChange.getAttack());
		addDefense(statChange.getDefense());
	}
	
	public void addMaxHealth(int maxHealthInc){
		maxHealth += maxHealthInc;
	}
	
	public void addHealth(int healthInc){
		setHealth(getHealth() + healthInc);
	}
	
	public void addAttack(int attackInc){
		attack += attackInc;
	}
	
	public void addDefense(int defenseInc){
		defense += defenseInc;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}
	
	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health) {
		if(health > getMaxHealth()){
			this.health = getMaxHealth();
		}
		else{
			this.health = health;
		}
	}
	
	public int getAttack() {
		return attack;
	}
	
	public void setAttack(int attack) {
		this.attack = attack;
	}
	
	public int getDefense() {
		return defense;
	}
	
	public void setDefense(int defense) {
		this.defense = defense;
	}
	
	@Override
	public String toString(){
		return "Health:\t\t" 
				+ getHealth()
				+ "/" + getMaxHealth()
				+ "\nAttack/Defense\t"
				+ getAttack()
				+ "/" + getDefense();
	}
}
