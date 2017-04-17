package com.dd.entities.equipments;

import com.dd.exceptions.ItemTypeException;
import com.dd.items.*;

public class SuitArea {
	
	private ItemType suitType = ItemType.NONE;
	private Suit suit;
	
	public SuitArea() {
		this.suitType = ItemType.NONE;
	}

	public SuitArea(Item item) throws ItemTypeException {
		try {
			setSuitArea(item);
		} catch (ItemTypeException ITE) {
			System.out.println(ITE.getMessage());
		}
	}
	
	public void setSuitArea(Suit suit) {
		this.suit = suit;
		suitType = ItemType.SUIT;
	}
	
	public void setSuitArea(Item item) throws ItemTypeException {
		clearType();
		if(item instanceof Suit) {
			suit = (Suit) item;
			suitType = ItemType.SUIT;
		}
		else {
			throw new ItemTypeException(item.titleToString() + " cannot be equipped to suit. ");
		}
	}
	
	public Suit getSuitArea() {
		if(suitType == ItemType.SUIT) {
			return (Suit) suit;
		}
		else { 
			return null;
		}
	}
	
	public void dropSuitArea() {
		if(!isEmpty()) {
			suit = null;
			clearType();
		}
	}
	
	public ItemType getSuitType() {
		return suitType;
	}
	
	public boolean isEmpty() {
		return suitType == ItemType.NONE;
	}
	
	public void clearType() {
		suitType = ItemType.NONE;
	}
	
}
