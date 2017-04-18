package com.dd.entities;

import com.dd.Stats;
import com.dd.exceptions.*;
import com.dd.items.*;
import com.dd.levels.MapPosition;

public class Wizard extends Player {

	public Wizard(String name, MapPosition pos, Stats stats) {
		super(name, pos, stats);
	}

	public Wizard(String name, MapPosition startPosition) {
		super(name);
		setMapPosition(startPosition);
	}
	
	public Wizard() {
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
				catch (ItemTypeException ITE) {
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
			} catch (InventoryException IE) {
				throw new EquipmentException(item.titleToString() 
						+ " could not be picked up because " + titleToString() + "'s "
						+ "inventory is full");
			}
		}
		else if(item instanceof Magical) {
			Equip bodyArea = ((Magical) item).getBodyArea();
			switch(bodyArea) {
			case LEFTHAND:
			case RIGHTHAND:
			case HANDS:
				if(leftHand.isEmpty()) {
					try {
						leftHand.setHand((Magical) item);
						pickupSuccess = true;
					}
					catch (ItemTypeException ITE) {
						throw new EquipmentException(item.titleToString() + "could not be equpped to " + titleToString() +"'s left hand. ");
					}
				}
				else if(rightHand.isEmpty()) {
					try {
						rightHand.setHand((Magical) item);
						pickupSuccess = true;
					}
					catch (ItemTypeException ITE) {
						throw new EquipmentException(item.titleToString() + "could not be equpped to " + titleToString() +"'s right hand. ");
					}
				}
				else {
					throw new EquipmentException(item.getName() 
							+ " could not be equipped because both of " 
							+ getName() + "'s hands are full. ");
				}
				break;
			case SUIT:
				if(suitArea.isEmpty()) {
					suitArea.setSuitArea((Suit) item);
					pickupSuccess = true;
				}
				else {
					throw new EquipmentException(item.getName() 
							+ " could not be equipped because " 
							+ getName() + " is already wearing a suit. ");
				}
				break;
			case NONE:
				try {
					addtoInventory((Magical) item);
					pickupSuccess = true;
				} catch (InventoryException e) {
					throw new EquipmentException(item.titleToString() 
							+ " could not be picked up because " + titleToString() + "'s "
							+ "inventory is full");
				}
				break;
			default:
				throw new EquipmentException(item.titleToString() 
						+ " could not be equipped to " 
						+ titleToString() + ", because Magical items need a specified body area. ");
			}
		}
		else if(item instanceof OneHandedWeapon) {
			((OneHandedWeapon) item).setStatForWizard();
			if(leftHand.isEmpty()) {
				try {
					leftHand.setHand((OneHandedWeapon) item);
					pickupSuccess = true;
				} 
				catch (ItemTypeException ITE) {
					throw new EquipmentException(item.titleToString() + "could not be equpped to " + titleToString() +"'s left hand. ");
				}
				
			}
			else if(rightHand.isEmpty()) {
				try {
					rightHand.setHand((OneHandedWeapon) item);
					pickupSuccess = true;
				} 
				catch (ItemTypeException ITE) {
					throw new EquipmentException(item.titleToString() + "could not be equpped to " + titleToString() +"'s right hand. ");
				}
			}
			else {
				throw new EquipmentException(item.getName() 
						+ " could not be equipped because both of " 
						+ getName() + "'s hands are full. ");
			}
		}
		else if(item instanceof TwoHandedWeapon) {
			throw new EquipmentException(item.getName() 
					+ " could not be equipped because "
					+ "wizards cannot use " + item.typeToString() + "s. ");
		}
		changeStats(item.getStatChange());
	}

}
