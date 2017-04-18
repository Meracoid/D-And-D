package com.dd.entities.adt;

import com.dd.exceptions.ItemTypeException;
import com.dd.items.*;

public class Hand {
	
	private ItemType handType = ItemType.NONE;
	private OneHandedWeapon oneHandedWeapon;
	private Shield shield;
	private Magical magical;
	
	public Hand() {
		this.handType = ItemType.NONE;
	}

	public Hand(Item item) {
		try {
			setHand(item);
		} catch (ItemTypeException ITE) {
			System.out.println(ITE.getMessage());
		}
	}
	
	public void setHand(Item item) throws ItemTypeException {
		clearType();
		if(item instanceof OneHandedWeapon) {
			oneHandedWeapon = (OneHandedWeapon) item;
			handType = ItemType.ONEHANDEDWEAPON;
		}
		else if(item instanceof Shield) {
			shield = (Shield) item;
			handType = ItemType.SHIELD;
		}
		else if(item instanceof Magical) {
			magical = (Magical) item;
			handType = ItemType.MAGICAL;
		}
		else {
			throw new ItemTypeException(item.titleToString() + " cannot be equipped to left hand. ");
		}
	}
	
	public Item getHand() {
		if(handType == ItemType.ONEHANDEDWEAPON) {
			return (OneHandedWeapon) oneHandedWeapon;
		}
		else if(handType == ItemType.SHIELD) {
			return (Shield) shield;
		}
		else if(handType == ItemType.MAGICAL) {
			return (Magical) magical;
		}
		else {
			return null;
		}
	}
	
	public void dropHand() {
		if(!isEmpty()) {
			oneHandedWeapon = null;
			shield = null;
			magical = null;
			clearType();
		}
	}
	
	public ItemType getHandType() {
		return handType;
	}
	
	public boolean isEmpty() {
		return handType == ItemType.NONE;
	}
	
	public void clearType() {
		handType = ItemType.NONE;
	}
	
}