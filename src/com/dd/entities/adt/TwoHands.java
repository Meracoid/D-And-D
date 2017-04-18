package com.dd.entities.adt;

import com.dd.exceptions.ItemTypeException;
import com.dd.items.*;

public class TwoHands {
	
	private ItemType twoHandsType = ItemType.NONE;
	private TwoHandedWeapon twoHandedWeapon;
	
	public TwoHands() {
		this.twoHandsType = ItemType.NONE;
	}

	public TwoHands(Item item) throws ItemTypeException {
		try {
			setTwoHands(item);
		} catch (ItemTypeException ITE) {
			System.out.println(ITE.getMessage());
		}
	}
	
	public void setTwoHands(TwoHandedWeapon twoHandedWeapon) {
		this.twoHandedWeapon = twoHandedWeapon;
		twoHandsType = ItemType.TWOHANDEDWEAPON;
	}
	
	public void setTwoHands(Item item) throws ItemTypeException {
		clearType();
		if(item instanceof TwoHandedWeapon) {
			setTwoHands((TwoHandedWeapon) item);
			twoHandsType = ItemType.TWOHANDEDWEAPON;
		}
		else {
			throw new ItemTypeException(item.titleToString() + " cannot be equipped to both hands. ");
		}
	}
	
	public TwoHandedWeapon getTwoHands() {
		if(twoHandsType == ItemType.TWOHANDEDWEAPON) {
			return twoHandedWeapon;
		}
		else {
			return null;
		}
	}
	
	public void dropTwoHands() {
		if(!isEmpty()) {
			twoHandedWeapon = null;
			clearType();
		}
	}
	
	public ItemType getTwoHandsType() {
		return twoHandsType;
	}
	
	public boolean isEmpty() {
		return twoHandsType == ItemType.NONE;
	}
	
	public void clearType() {
		twoHandsType = ItemType.NONE;
	}
}
