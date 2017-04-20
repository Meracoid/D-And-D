package com.dd.dataTypes.bodyAreas;

import java.io.Serializable;

import com.dd.exceptions.EquipmentException;
import com.dd.exceptions.ItemTypeException;
import com.dd.exceptions.NullValueException;
import com.dd.items.*;

public class SuitArea implements Serializable {
	
	private Suit suit;
	
	public SuitArea() {
		this.suit = null;
	}

	public SuitArea(Item suit) throws EquipmentException {
		try {
			set(suit);
		} catch (ItemTypeException ITE) {
			throw new EquipmentException(ITE.getMessage());
		}
	}
	
	public void set(Item item) throws ItemTypeException {
		if(item instanceof Suit) {
			this.suit = (Suit) item;
		}
		else {
			throw new ItemTypeException(item.titleToString() + " cannot be equipped to suit area. ");
		}
	}
	
	public void set(Suit suit) {
		this.suit = suit;
	}
	
	public Suit get() throws NullValueException {
		if(isEmpty()) {
			throw new NullValueException("Suit area is empty. ");
		}
		return (Suit) suit;
	}
	
	public void drop() throws NullValueException {
		if(isEmpty()) {
			throw new NullValueException("Suit area is empty. ");
		}
		suit = null;
	}
	
	public boolean isEmpty() {
		return this.suit == null;
	}
}
