package com.dd.entities;

import com.dd.Stats;
import com.dd.entities.Player.EquipmentException;
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
	public Item equip(Item item) throws InventoryException, EquipmentException {
		Item retItem = super.equip(item);
		if(item instanceof TwoHandedWeapon) {
			retItem = (TwoHandedWeapon) item;
			if(leftHand == null && rightHand == null) {
				retItem = leftHand = rightHand = (TwoHandedWeapon)item;
				equipSuccess = true;
			}
			else {
				throw new EquipmentException(item.getName() 
						+ " could not be equipped because both of " 
						+ getName() + "'s hands need to be empty. ");
			}
		}
		else if(item instanceof OneHandedWeapon) {
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
		else if(item instanceof Magical) {
			throw new EquipmentException(item.getName() 
					+ " could not be equipped because "
					+ "fighters cannot use " + item.typeToString() + " items. ");
		}
		else {
			throw new EquipmentException(item.getName()
					+ " is of an unknown type. ");
		}
		stats.changeStat(item.getStatChange());
		return retItem;
	}

}