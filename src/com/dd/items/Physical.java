package com.dd.items;

public abstract class Physical extends Item {
	
	public Physical(String name, int attackGain, int defenseGain) {
		super(name);
		super.setStatChange(0, 0, attackGain, defenseGain);
	}
	
	public Physical() {
		super();
	}
}