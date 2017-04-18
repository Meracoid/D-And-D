package com.dd.entities;

import com.dd.Stats;
import com.dd.exceptions.*;
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
		if(item instanceof Artifact) {
			try {
				addtoInventory((Artifact) item);
				pickupSuccess = true;
			} catch (InventoryException e) {
				throw new EquipmentException(item.titleToString() 
						+ " could not be picked up because " + titleToString() + "'s "
						+ "inventory is full");
			}
		}
		else if(item instanceof Shield){
			if(leftHand.isEmpty()) {
				try {
					leftHand.setHand((Shield) item);
					pickupSuccess = true;
				}
				catch (ItemTypeException ITE) {
					throw new EquipmentException(item.titleToString() + "could not be equpped to " + titleToString() +"'s left hand. ");
				}
			}
			else if(rightHand.isEmpty()) {
				try {
					rightHand.setHand((Shield) item);
					pickupSuccess = true;
				}
				catch (ItemTypeException e) {
					throw new EquipmentException(item.titleToString() + "could not be equpped to " + titleToString() +"'s right hand. ");
				}
			}
			else {
				throw new EquipmentException(item.titleToString() 
						+ " could not be picked up because both of " 
						+ titleToString() + "'s hands are full. ");
			}
		}
		else if(item instanceof Suit) {
			if(suitArea.isEmpty()) {
				suitArea.setSuitArea((Suit) item);
				pickupSuccess = true;
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
				pickupSuccess = true;
			} catch (InventoryException e) {
				throw new EquipmentException(item.titleToString() 
						+ " could not be picked up because " + titleToString() + "'s "
						+ "inventory is full");
			}
		}
		else if(item instanceof TwoHandedWeapon) {
			if(twoHands.isEmpty()) {
				twoHands.setTwoHands((TwoHandedWeapon) item);
				pickupSuccess = true;
			}
			else {
				throw new EquipmentException(item.titleToString() 
						+ " could not be equipped because both of " 
						+ titleToString() + "'s hands need to be empty. ");
			}
		}
		else if(item instanceof OneHandedWeapon) {
			if(leftHand.isEmpty()) {
				try {
					leftHand.setHand((OneHandedWeapon) item);
					pickupSuccess = true;
				} 
				catch (ItemTypeException e) {
					throw new EquipmentException(item.titleToString() + "could not be equipped to " + titleToString() + "'s left hand");
				}
			}
			else if(rightHand.isEmpty()) {
				try {
					rightHand.setHand((OneHandedWeapon) item);
					pickupSuccess = true;
				} catch (ItemTypeException e) {
					throw new EquipmentException(item.titleToString() + "could not be equipped to " + titleToString() + "'s right hand");
				}
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
		changeStats(item.getStatChange());
	}

}
