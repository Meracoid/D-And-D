package com.dd.entities;

import com.dd.Stats;
import com.dd.items.*;
import com.dd.levels.MapPosition;
import com.dd.dd_util.ConflictHandlingMap;
import com.dd.entities.equipments.*;
import com.dd.exceptions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Player extends Entity {

	protected MapPosition mapPosition;

	public Hand leftHand;
	public Hand rightHand;
	public TwoHands twoHands;
	public SuitArea suitArea;
	
	protected boolean pickupSuccess;
	protected boolean dropSuccess;

	private Map<String, Item> inventory = new ConflictHandlingMap<Item>();
	private int inventoryUsed = 0;
	private int maxInventorySize = 10;

	public Player(String name, MapPosition pos, Stats stats) {
		super(name, stats);
		setMapPosition(pos);
		initBody();
	}

	public Player(String name) {
		super(name);
		setMapPosition(new MapPosition());
		initBody();
	}
	
	public Player() {
		super();
		setMapPosition(new MapPosition());
		initBody();
	}
	
	public void initBody() {
		this.leftHand = new Hand();
		this.rightHand  = new Hand();
		this.twoHands = new TwoHands();
		this.suitArea = new SuitArea();
	}
	
	public void pickup(Item item) throws InventoryException, EquipmentException {
		
	}
	
	public void drop(Equip bodyArea) throws EquipmentException {
		resetDropSuccess();
		Item dropItem = null;
		String errorTrailer = "";
		switch(bodyArea) {
			case LEFTHAND:
				if(!leftHand.isEmpty()) {
					dropItem = leftHand.getHand();
					if(dropItem instanceof Magical) {
						dropItem = (Magical) dropItem;
					}
					else if(dropItem instanceof OneHandedWeapon) {
						dropItem = (OneHandedWeapon) dropItem;
					}
					else if(dropItem instanceof Shield) {
						dropItem = (Shield) dropItem;
					}
					else {
						throw new EquipmentException("Drop item has no type. ");
					}
					leftHand.dropHand();
					dropSuccess = true;
				}
				else {
					errorTrailer = "the left hand is empty. ";
				}
				break;
			case RIGHTHAND:
				if(!rightHand.isEmpty()) {
					dropItem = rightHand.getHand();
					if(dropItem instanceof Magical) {
						dropItem = (Magical) dropItem;
					}
					else if(dropItem instanceof OneHandedWeapon) {
						dropItem = (OneHandedWeapon) dropItem;
					}
					else if(dropItem instanceof Shield) {
						dropItem = (Shield) dropItem;
					}
					else {
						throw new EquipmentException("Drop item has no type. ");
					}
					rightHand.dropHand();
					dropSuccess = true;
				}
				else {
					errorTrailer = "the right hand is empty. ";
				}
				break;
			case HANDS:
				if(!twoHands.isEmpty()) {
					dropItem = twoHands.getTwoHands();
					if(dropItem instanceof TwoHandedWeapon) {
						dropItem = (TwoHandedWeapon) dropItem;
					}
					else {
						throw new EquipmentException("Drop item has no type. ");
					}
					rightHand.dropHand();
					dropSuccess = true;
				}
				else {
					errorTrailer = "both hands are not holding the same item. ";
				}
				break;
			case SUIT:
				if(!suitArea.isEmpty()) {
					dropItem = suitArea.getSuitArea();
					if(dropItem instanceof Suit) {
						dropItem = (Suit) dropItem;
					}
					else {
						throw new EquipmentException("Drop item has no type. ");
					}
					suitArea.dropSuitArea();
					dropSuccess = true;
				}
				else {
					errorTrailer = "no suit is being worn. ";
				}
				break;
			default:
				dropSuccess = false;
				errorTrailer = "no body area was specified. ";
		}
		if(!isDropSuccess()) {
			throw new EquipmentException("The item could not be dropped because "
											+ errorTrailer);
		}
		else {
			changeStats(dropItem.getNegStatChange());
		}
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
		if(!inventory.containsValue(item)){
			throw new InventoryException("Item is not in the inventory of "
					+ titleToString()
					+ ". Item not dropped. ");
		}
		inventory.remove(item);
	}

	public List<Item> removeAllEquipment() throws EquipmentException {
		List<Item> itemList = new ArrayList<Item>();
		if(leftHand.isEmpty() || rightHand.isEmpty()) {
			if(leftHand.getHand().equals(rightHand.getHand())) {
				itemList.add(leftHand.getHand());
			}
			else {
				if(leftHand.isEmpty()) {
					itemList.add(leftHand.getHand());
				}
				if(rightHand.isEmpty()) {
					itemList.add(rightHand.getHand());
				}
			}
		}
		if(suitArea.isEmpty()) {
			itemList.add(suitArea.getSuitArea());
		}
		twoHands.dropTwoHands();
		suitArea.dropSuitArea();
		return itemList;
	}

	public void discardAllEquipment() {
		twoHands.dropTwoHands();
		suitArea.dropSuitArea();
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
	
	public Item getLeftHand() {
		return leftHand.getHand();
	}
	
	public ItemType leftHandType() {
		return leftHand.getHandType();
	}

	public Item getRightHand() {
		return rightHand.getHand();
	}
	
	public ItemType rightHandType() {
		return rightHand.getHandType();
	}

	public TwoHandedWeapon getTwoHands() {
		return twoHands.getTwoHands();
	}

	public Suit getSuitArea() {
		return suitArea.getSuitArea();
	}

	public void setSuitArea(SuitArea suitArea) {
		this.suitArea = suitArea;
	}
	
	public Map<String, Item> getInventory() {
		return inventory;
	}
	
	public boolean isPickupSuccess() {
		return pickupSuccess;
	}
	
	public void resetPickupSuccess() {
		pickupSuccess = false;
	}
	
	public boolean isDropSuccess() {
		return dropSuccess;
	}
	
	public void resetDropSuccess() {
		dropSuccess = false;
	}
	
	public String equipToString() {
		StringBuilder lh = new StringBuilder();
		if(!leftHand.isEmpty())
			lh.append(leftHand.getHand().getName() + " " + leftHand.getHand().examineToString());
		else
			lh.append("empty");
		StringBuilder rh = new StringBuilder();
		if(!rightHand.isEmpty())
			rh.append(rightHand.getHand().getName() + " " + rightHand.getHand().examineToString());
		else
			rh.append("empty");
		StringBuilder s = new StringBuilder();
		if(!suitArea.isEmpty())
			s.append(suitArea.getSuitArea().getName() + " " + suitArea.getSuitArea().examineToString());
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
	
	public String statboardToString() throws EquipmentException {
		return	stats.toString()
				+ "\n" + equipToString()
				+ "\n" + inventoryToString();
				
	}
}
