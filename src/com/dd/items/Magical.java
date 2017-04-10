package com.dd.items;

import com.dd.entities.Equip;

public class Magical extends Item {
	
	private Equip bodyArea;
	
	public Magical(String name, Equip bodyArea, int healthGain, int maxHealthGain, int attackGain,int defenseGain) {
		super(name);
		super.setStatChange(healthGain, maxHealthGain, attackGain, defenseGain);
		this.bodyArea = bodyArea;
	}
	
	public Magical() {
		super();
	}
	
	public Equip getBodyAread() {
		return bodyArea;
	}
}