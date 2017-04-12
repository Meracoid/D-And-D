package com.dd.entities;

import com.dd.Stats;
import com.dd.items.*;
import com.dd.levels.DungeonMap;
import com.dd.levels.MapPosition;
import com.dd.dd_util.ConflictHandlingMap;
import com.dd.entities.Player.EquipmentException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Player extends Entity {

	private MapPosition mapPosition;
	protected Item suit;
	protected Item leftHand;
	protected Item rightHand;
	protected boolean equipSuccess;

	private Map<String, Item> inventory = new ConflictHandlingMap<Item>();
	private int inventoryUsed = 0;
	private int maxInventorySize = 10;

	public Player(String name, MapPosition pos, Stats stats) {
		super(name, stats);
		setMapPosition(pos);
	}

	public Player(String name) {
		super(name);
		setMapPosition(new MapPosition());
	}
	
	public Player() {
		super();
		setMapPosition(new MapPosition());
	}

	public void usePotion(Item item) {
		if(item instanceof Potion){
			stats.changeStat(item.getStatChange());
		}
		else{
			System.out.println("ERROR item is not a potion. ");
		}
	}

	public void usePotionFromInventory(String potionName) throws InventoryException {
		Item potion = inventory.get(potionName);
		if(potion == null){
			throw new InventoryException("There are no potion of \""
					+ potionName
					+ "\" in the inventory of player \""
					+ name
					+ "\". Item removal failed. ");
		}
		if(!(potion instanceof Potion)) {
			throw new InventoryException("The item \""
											+ potionName
											+ "\" in the inventory of player \""
											+ "\" is not a potion. Item not used. ");
		}
		stats.changeStat(potion.getStatChange());
		inventory.remove(potionName);
	}

	public void addtoInventory(Item item) throws InventoryException {
		if(inventoryUsed == maxInventorySize){
			throw new InventoryException("The inventory of " + titleToString()
											+ " is already full. Item not added to inventory. ");
		}
		inventory.put(item.getName(), item);
		++inventoryUsed;
	}

	public void removefromInventory(String itemName) throws InventoryException {
		if(inventory.get(itemName) == null) {
			throw new InventoryException("There are no items of \""
											+ itemName
											+ "\" in the inventory of player \""
											+ name
											+ "\". Item removal failed. ");
		}
		inventory.remove(itemName);
	}

	public void discardfromInventory(String itemName) throws InventoryException {
		if(inventory.remove(itemName) == null) {
			throw new InventoryException("\"" + itemName + "\" "
					+ "is not in the inventory of "
					+ titleToString()
					+ ". Item not dropped. ");
		}
	}
	
	public void discardfromInventory(Item item) throws InventoryException {
		if(inventory.remove(item) == null) {
			throw new InventoryException("Item is not in the inventory of "
											+ titleToString()
											+ ". Item not dropped. ");
		}
	}
	
	public void pickup(Item item) throws InventoryException, EquipmentException {
		stats.changeStat(item.getStatChange());
	}

	public Item removeEquipment(Equip bodyArea) throws EquipmentException {
		Item retItem = null;
		String errorTrailer = "";
		boolean hadError = false;

		switch(bodyArea){
			case LEFTHAND:
				if(leftHand == null){
					hadError = true;
					errorTrailer = "the left hand is empty. ";
				}
				else {
					retItem = leftHand;
					leftHand = null;
				}
				break;
			case RIGHTHAND:
				if(rightHand == null){
					hadError = true;
					errorTrailer = "the right hand is empty. ";
				}
				else {
					retItem = rightHand;
					rightHand = null;
				}
				break;
			case HANDS:
				if(leftHand == null
						|| rightHand == null
						|| leftHand != rightHand){
					hadError = true;
					errorTrailer = "both hands are not holding the same item. ";
				}
				else {
					retItem = leftHand;
					leftHand = rightHand = null;
				}
				break;
			case SUIT:
				if(suit == null) {
					hadError = true;
					errorTrailer = "no armor is being worn. ";
				}
				else {
					retItem = suit;
					suit = null;
				}
				break;
			default:
				hadError = true;
				errorTrailer = "no body area was specified. ";
		}
		if(hadError) {
			throw new EquipmentException("The item at the requested body area could not be removed becasue "
											+ errorTrailer);
		}
		return retItem;
	}

	public void discardEquipment(Equip bodyArea) throws EquipmentException {
		String errorTrailer = "";
		boolean hadError = false;

		switch(bodyArea) {
			case LEFTHAND:
				if(leftHand == null) {
					hadError = true;
					errorTrailer = "the left hand is empty. ";
				}
				else {
					leftHand = null;
				}
				break;
			case RIGHTHAND:
				if(rightHand == null) {
					hadError = true;
					errorTrailer = "the right hand is empty. ";
				}
				else {
					rightHand = null;
				}
				break;
			case HANDS:
				if(leftHand == null
						|| rightHand == null
						|| leftHand != rightHand) {
					hadError = true;
					errorTrailer = "both hands are not holding the same item. ";
				}
				else {
					leftHand = rightHand = null;
				}
				break;
			case SUIT:
				if(suit == null) {
					hadError = true;
					errorTrailer = "no suit is being worn. ";
				}
				else {
					suit = null;
				}
				break;
			default:
				hadError = true;
				errorTrailer = "no body area was specified. ";
		}
		if(hadError) {
			throw new EquipmentException("The item at the requested body area could not be removed becasue "
											+ errorTrailer);
		}
	}

	public List<Item> removeAllEquipment() {
		List<Item> itemList = new ArrayList<Item>();
		if(leftHand != null || rightHand != null) {
			if(leftHand == rightHand) {
				itemList.add(leftHand);
			}
			else {
				if(leftHand != null) {
					itemList.add(leftHand);
				}
				if(rightHand != null) {
					itemList.add(rightHand);
				}
			}
		}
		if(suit != null) {
			itemList.add(suit);
		}
		leftHand = rightHand = suit = null;
		return itemList;
	}

	public void discardAllEquipment() {
		leftHand = rightHand = suit = null;
	}

	public MapPosition getPostion() {
		return mapPosition;
	}

	public void setMapPosition(MapPosition p) {
		mapPosition = p;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Item getSuit() {
		return suit;
	}

	public void setSuit(Item suit) {
		this.suit = suit;
	}

	public Item getLeftHand() {
		return leftHand;
	}

	public void setLeftHand(Item leftHand) {
		this.leftHand = leftHand;
	}

	public Item getRightHand() {
		return rightHand;
	}

	public void setRightHand(Item rightHand) {
		this.rightHand = rightHand;
	}
	
	public Map<String, Item> getInventory() {
		return inventory;
	}
	
	public boolean isEquipSuccess() {
		return equipSuccess;
	}
	
	public void resetEquipSuccess() {
		equipSuccess = false;
	}
	
	public String equipToString() {
		StringBuilder lh = new StringBuilder();
		if(leftHand != null)
			lh.append(leftHand.getName() + " " + leftHand.examineToString());
		else
			lh.append("empty");
		StringBuilder rh = new StringBuilder();
		if(rightHand!=null)
			rh.append(rightHand.getName() + " " + rightHand.examineToString());
		else
			rh.append("empty");
		StringBuilder s = new StringBuilder();
		if(suit!=null)
			s.append(suit.getName() + " " + suit.examineToString());
		else
			s.append("empty");
		return "Left Hand:  " + lh.toString() + "\n"
				+ "Right Hand: " + rh.toString() + "\n"
				+ "Suit:       " + s.toString();
	}

	public String inventoryToString() {
		StringBuilder sb = new StringBuilder("Inventory:\n");
		int i = 0;
		for(String itemName : inventory.keySet()){
			sb.append("\t" + ++i + " " + itemName + " " + inventory.get(itemName).examineToString() + "\n");
		}	
		return sb.toString();
	}
	
	public String statboardToString() {
		return	stats.toString()
				+ "\n" + equipToString()
				+ "\n" + inventoryToString();
				
	}
	
	public class InventoryException extends Exception {
		
		public InventoryException(String message){
			super(message);
		}
		
		@Override
		public String toString() {
			return super.toString().substring(43);
		}
	}

	public class EquipmentException extends Exception {
		
		public EquipmentException(String message) {
			super(message);
		}
		
		@Override
		public String toString() {
			return super.toString().substring(43);
		}
	}
}
