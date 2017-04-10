package com.dd.entities;

import com.dd.Stats;
import com.dd.entities.Player.EquipmentException;
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
	public Item equip(Item item) throws InventoryException, EquipmentException {
		Item retItem = super.equip(item);
		if(item instanceof Magical) {
			retItem = (Magical) item;
			Equip bodyArea = ((Magical) item).getBodyAread();
			switch(bodyArea) {
			case HANDS:
				if(leftHand == null) {
					retItem = leftHand = (Magical)item;
					equipSuccess = true;
					
				}
				else if(rightHand == null) {
					retItem = rightHand = (Magical)item;
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
					retItem = suit = (Suit)item;
					equipSuccess = true;
				}
				else {
					throw new EquipmentException(item.getName() 
							+ " could not be equipped because " 
							+ getName() + " is already wearing a suit. ");
				}
				break;
			case NONE:
				addtoInventory((Magical) item);
				equipSuccess = true;
				break;
			default:
				throw new EquipmentException(item.getName() 
						+ " could not be equipped because " 
						+ getName() + " Magical items need a specified body area. ");
			}
		}
		else if(item instanceof OneHandedWeapon) {
			((OneHandedWeapon) item).setStatForWizard();
			retItem = (OneHandedWeapon) item;
			if(leftHand == null) {
				retItem = leftHand = (OneHandedWeapon)item;
				equipSuccess = true;
				
			}
			else if(rightHand == null) {
				retItem = rightHand = (OneHandedWeapon)item;
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
		return retItem;
	}

}