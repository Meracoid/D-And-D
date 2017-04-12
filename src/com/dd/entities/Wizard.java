package com.dd.entities;

import com.dd.Stats;
import com.dd.entities.Player.EquipmentException;
import com.dd.entities.Player.InventoryException;
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
				equipSuccess = true;
			} catch (InventoryException e) {
				throw new EquipmentException(item.titleToString() 
						+ " could not be picked up because " + titleToString() + "'s "
						+ "inventory is full");
			}
		}
		else if(item instanceof Shield){
			if(leftHand == null) {
				leftHand = (Shield)item;
				equipSuccess = true;
			}
			else if(rightHand == null) {
				rightHand = (Shield)item;
				equipSuccess = true;
			}
			else {
				throw new EquipmentException(item.titleToString() 
						+ " could not be picked up because both of " 
						+ titleToString() + "'s hands are full. ");
			}
		}
		else if(item instanceof Suit) {
			if(suit == null) {
				suit = (Suit)item;
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
		else if(item instanceof Magical) {
			Equip bodyArea = ((Magical) item).getBodyArea();
			switch(bodyArea) {
			case LEFTHAND:
			case RIGHTHAND:
			case HANDS:
				if(leftHand == null) {
					leftHand = (Magical)item;
					equipSuccess = true;
				}
				else if(rightHand == null) {
					rightHand = (Magical)item;
					equipSuccess = true;
				}
				else {
					throw new EquipmentException(item.getName() 
							+ " could not be equipped because both of " 
							+ getName() + "'s hands are full. ");
				}
				break;
			case SUIT:
				if(suit == null) {
					suit = (Suit)item;
					equipSuccess = true;
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
					equipSuccess = true;
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
			if(leftHand == null) {
				leftHand = (OneHandedWeapon)item;
				equipSuccess = true;
				
			}
			else if(rightHand == null) {
				rightHand = (OneHandedWeapon)item;
				equipSuccess = true;
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
		stats.changeStat(item.getStatChange());
	}

}
