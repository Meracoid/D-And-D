package com.dd.entities;

import com.dd.Stats;
import com.dd.entities.adt.*;
import com.dd.items.*;
import com.dd.levels.MapPosition;
import com.dd.exceptions.*;

public class Player extends Entity {

	protected MapPosition mapPosition;

	protected Hand leftHand;
	protected Hand rightHand;
	protected TwoHands twoHands;
	protected SuitArea suitArea;
	protected Inventory inventory;
	protected boolean pickupSuccess;
	protected boolean dropSuccess;

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
		this.inventory = new Inventory(10);
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
	
	public void usePotion(Potion potion) throws EquipmentException {
		try {
			inventory.remove(potion);
			changeStats(potion.getStatChange());
		} 
		catch (InventoryException IE) {
			throw new EquipmentException(IE.getMessage());
		}
	}

	public void usePotionFromInventory(Potion potion) throws EquipmentException {
		try {
			inventory.get(potion);
			usePotion(potion);
			dropSuccess = false;
		} 
		catch (InventoryException IE) {
			throw new EquipmentException(IE.getMessage());
		}
		
	}
	
	public void addtoInventory(Item item) throws InventoryException {
		if(item instanceof Potion) {
			this.inventory.add((Potion) item);
		}
		else if(item instanceof Artifact) {
			this.inventory.add((Artifact) item);
		}
		else if(item instanceof Magical) {
			this.inventory.add((Magical) item);
		}
		else {
			throw new InventoryException(item.titleToString() + " cannot be added to your inventory. "
					+ "The item must be a potion, artifact, or magical item. ");
		}
	}

	public void removeFromInventory(Item item) throws InventoryException {
		if(item instanceof Potion) {
			this.inventory.remove((Potion) item);
		}
		else if(item instanceof Artifact) {
			this.inventory.remove((Artifact) item);
		}
		else if(item instanceof Magical) {
			this.inventory.remove((Magical) item);
		}
		else {
			throw new InventoryException(item.titleToString() + " cannot be added to your inventory. "
					+ "The item must be a potion, artifact, or magical item. ");
		}
		dropSuccess = true;
		
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
	
	public Inventory getInventory() {
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
		for(Item item : inventory.getInventoryMap().values()){
			sb.append("\t" + ++i + " " + item.getName() + " " + item.examineToString() + "\n");
		}
		return sb.toString();
	}
	
	public String statboardToString() throws EquipmentException {
		return	stats.toString()
				+ "\n" + equipToString()
				+ "\n" + inventoryToString();
				
	}
}
