package com.dd.entities.players;

import com.dd.Stats;
import com.dd.dataTypes.enums.Equip;
import com.dd.entities.Player;
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
	public void pickup(Item item) throws EquipmentException {
		if(item instanceof Artifact
				|| item instanceof Potion
				|| item instanceof Magical) {
			try {
				addtoInventory(item);
			} 
			catch (InventoryException IE) {
				throw new EquipmentException(IE.getMessage());
			}
		}
		else if(item instanceof Shield || item instanceof OneHandedWeapon){
			if(leftHand.isEmpty()) {
				try {
					leftHand.set(item);
				}
				catch (NullItemException ITE) {
					throw new EquipmentException(item.getTitle() + "could not be equpped to " + getTitle() +"'s left hand. ");
				}
			}
			else if(rightHand.isEmpty()) {
				try {
					rightHand.set(item);
				}
				catch (NullItemException ITE) {
					throw new EquipmentException(item.getTitle() + "could not be equpped to " + getTitle() +"'s right hand. ");
				}
			}
			else {
				throw new EquipmentException(item.getTitle() 
						+ " could not be picked up because both of " 
						+ getTitle() + "'s hands are full. ");
			}
		}
		else if(item instanceof Suit) {
			if(suitArea.isEmpty()) {
				suitArea.set(item);
			}
			else {
				throw new EquipmentException(item.getTitle() 
						+ " could not be equipped because " 
						+ getTitle() + " is already wearing a suit. ");
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
						leftHand.set((Magical) item);
					}
					catch (NullItemException ITE) {
						throw new EquipmentException(item.getTitle() + "could not be equpped to " + getTitle() +"'s left hand. ");
					}
				}
				else if(rightHand.isEmpty()) {
					try {
						rightHand.set((Magical) item);
					}
					catch (NullItemException ITE) {
						throw new EquipmentException(item.getTitle() + "could not be equpped to " + getTitle() +"'s right hand. ");
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
					suitArea.set((Suit) item);
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
				} catch (InventoryException e) {
					throw new EquipmentException(item.getTitle() 
							+ " could not be picked up because " + getTitle() + "'s "
							+ "inventory is full");
				}
				break;
			default:
				throw new EquipmentException(item.getTitle() 
						+ " could not be equipped to " 
						+ getTitle() + ", because Magical items need a specified body area. ");
			}
		}
		else if(item instanceof TwoHandedWeapon) {
			throw new EquipmentException(item.getName() 
					+ " could not be equipped because "
					+ "wizards cannot use " + item.getType() + "s. ");
		}
		changeStats(item.getStatChange());
	}

}
