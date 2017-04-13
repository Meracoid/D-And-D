package com.dd.entities;

import com.dd.Stats;
import com.dd.items.*;
import com.dd.levels.MapPosition;

public class Fighter extends Player {

	public Fighter(String name, MapPosition pos, Stats stats) {
		super(name, pos, stats);
	}

	public Fighter(String name, MapPosition startPosition) {
		super(name);
		setMapPosition(startPosition);
	}
	
	public Fighter() {
		super();
		setMapPosition(new MapPosition());
	}

	@Override
	public void pickup(Item item) throws InventoryException, EquipmentException {
		try {
			addtoInventory(item);
			equipSuccess = true;
		} catch (InventoryException e) {
			throw new EquipmentException(item.titleToString() 
					+ " could not be picked up because " + titleToString() + "'s "
					+ "inventory is full");
		}
		
		if(item instanceof Artifact) {
			try {
				addtoInventory((Artifact) item);
				equipSuccess = true;
			} catch (InventoryException e) {
				throw new EquipmentException(item.titleToString() 
						+ " could not be picked up because " + titleToString() + "'s "
						+ "inventory is full");
			}
		}
		else if(item instanceof Shield){
			if(isLeftHandEmpty()) {
				setLeftHand((Shield) item);
				equipSuccess = true;
			}
			else if(isRightHandEmpty()) {
				setRightHand((Shield) item);
				equipSuccess = true;
			}
			else {
				throw new EquipmentException(item.titleToString() 
						+ " could not be picked up because both of " 
						+ titleToString() + "'s hands are full. ");
			}
		}
		else if(item instanceof Suit) {
			if(isSuitEmpty()) {
				setSuit((Suit) item);
				equipSuccess = true;
			}
			else {
				throw new EquipmentException(item.titleToString() 
						+ " could not be equipped because " 
						+ titleToString() + " is already wearing a suit. ");
			}
		}
		else if(item instanceof Potion) {
			try {
				addtoInventory((Potion) item);
				equipSuccess = true;
			} catch (InventoryException e) {
				throw new EquipmentException(item.titleToString() 
						+ " could not be picked up because " + titleToString() + "'s "
						+ "inventory is full");
			}
		}
		else if(item instanceof TwoHandedWeapon) {
			if(isHandsEmpty()) {
				setHands((TwoHandedWeapon) item);
				equipSuccess = true;
			}
			else {
				throw new EquipmentException(item.titleToString() 
						+ " could not be equipped because both of " 
						+ titleToString() + "'s hands need to be empty. ");
			}
		}
		else if(item instanceof OneHandedWeapon) {
			if(isLeftHandEmpty()) {
				setLeftHand((OneHandedWeapon) item);
				equipSuccess = true;
				
			}
			else if(isRightHandEmpty()) {
				setRightHand((OneHandedWeapon) item);
				equipSuccess = true;
			}
			else {
				throw new EquipmentException(item.titleToString() 
						+ " could not be equipped because both of " 
						+ titleToString() + "'s hands are full. ");
			}
		}
		else if(item instanceof Magical) {
			throw new EquipmentException(item.titleToString() 
					+ " could not be equipped because "
					+ "fighters cannot use " + item.typeToString() + " items. ");
		}
		else {
			throw new EquipmentException(item.getName()
					+ " is of an unknown type. ");
		}
		stats.changeStat(item.getStatChange());
	}

}
