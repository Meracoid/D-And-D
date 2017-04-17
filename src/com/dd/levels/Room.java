package com.dd.levels;

import com.dd.entities.Monster;
import com.dd.entities.equipments.ItemList;
import com.dd.exceptions.*;
import com.dd.items.*;
import com.dd.dd_util.ConflictHandlingMap;
import java.util.Map;

public class Room {
	
	private ItemList itemList;
	//private Map<String, Item> itemMap;
	private Map<String, Monster> monsterMap;
	
	public Room() {
		itemList = new ItemList();
		//itemMap = new ConflictHandlingMap<Item>();
		monsterMap = new ConflictHandlingMap<Monster>();
	}
	
	public String examineItems() {
		StringBuilder outputSB = new StringBuilder();
		if(hasItems()) {
			getItemMap().values().forEach((v) -> outputSB.append(
					v.titleToString() + " "
					+ v.examineToString() + "\n"));
		}
		else {
			outputSB.append("There are no items in this room. ");
		}
		return outputSB.toString();
	}
	
	public String enterRoomText() {
		StringBuilder outputText = new StringBuilder();
		if(isEmpty()){
			outputText.append("This room is empty.");
			return outputText.toString();
		}
		if(hasMonster()) {	
			getMonsterList().values().forEach((v) -> outputText.append(v.confrontText() + " "));
		}
		if(hasItems()) {
			outputText.append("This room contains ");
			getItemMap().forEach((k, v) -> outputText.append("a " + v.typeToString() + " called \"" + k + "\" "));
		}
		return outputText.toString();
	}

	public boolean isEmpty() {
		return getItemMap().isEmpty() && getMonsterList().isEmpty();
	}
	
	public boolean hasMonster() {
		return !getMonsterList().isEmpty();
	}
	
	public boolean hasItems() {
		return !getItemMap().isEmpty();
	}
	
	public void addItem(Item item) throws UnknownItemException {
		if(item instanceof Artifact) {
			itemList.add((Artifact) item);
		}
		else if(item instanceof Magical) {
			itemList.add((Magical) item);
		}
		else if(item instanceof OneHandedWeapon) {
			itemList.add((OneHandedWeapon) item);
		}
		else if(item instanceof Potion) {
			itemList.add((Potion) item);
		}
		else if(item instanceof Shield) {
			itemList.add((Shield) item);
		}
		else if(item instanceof Suit) {
			itemList.add((Suit) item);
		}
		else if(item instanceof TwoHandedWeapon) {
			itemList.add((TwoHandedWeapon) item);
		}
		else {
			throw new UnknownItemException("Item added to room has no type. ");
		}
	}
	
	public void removeItem(String itemName) throws UnknownItemException {
		if(!getItemMap().containsKey(itemName)) {
			throw new UnknownItemException("Item not found in room. ");
		}
		try {
			if(getItemMap().get(itemName) instanceof Artifact) {
				itemList.remove((Artifact) getItemMap().get(itemName));
			}
			else if(getItemMap().get(itemName) instanceof Magical) {
				itemList.remove((Magical) getItemMap().get(itemName));
			}
			else if(getItemMap().get(itemName) instanceof OneHandedWeapon) {
				itemList.remove((OneHandedWeapon) getItemMap().get(itemName));
			}
			else if(getItemMap().get(itemName) instanceof Potion) {
				itemList.remove((Potion) getItemMap().get(itemName));
			}
			else if(getItemMap().get(itemName) instanceof Shield) {
				itemList.remove((Shield) getItemMap().get(itemName));
			}
			else if(getItemMap().get(itemName) instanceof Suit) {
				itemList.remove((Suit) getItemMap().get(itemName));
			}
			else if(getItemMap().get(itemName) instanceof TwoHandedWeapon) {
				itemList.remove((TwoHandedWeapon) getItemMap().get(itemName));
			}
			else {
				throw new UnknownItemException(getItemMap().get(itemName).getName() + " has no type. ");
			}
		}
		catch(InventoryException IE) {
			throw new UnknownItemException(getItemMap().get(itemName).titleToString() + " is not found in the room. ");
		}
	}
	
	public void removeItem(Item item) throws UnknownItemException {
		try {
			if(item instanceof Artifact) {
				itemList.remove((Artifact) item);
			}
			else if(item instanceof Magical) {
				itemList.remove((Magical) item);
			}
			else if(item instanceof OneHandedWeapon) {
				itemList.remove((OneHandedWeapon) item);
			}
			else if(item instanceof Potion) {
				itemList.remove((Potion) item);
			}
			else if(item instanceof Shield) {
				itemList.remove((Shield) item);
			}
			else if(item instanceof Suit) {
				itemList.remove((Suit) item);
			}
			else if(item instanceof TwoHandedWeapon) {
				itemList.remove((TwoHandedWeapon) item);
			}
			else {
				throw new UnknownItemException(item.getName() + " has no type. ");
			}
		}
		catch(InventoryException IE) {
			throw new UnknownItemException(item.titleToString() + " is not found in the room. ");
		}
	}

	public void addMonster(Monster monster) {
		monsterMap.put(monster.getName(),monster);
	}

	public Monster removeMonster(String monsterName) throws UnknownMonsterException {
		Monster retMonster;
		if(!monsterMap.containsKey(monsterName)){
			throw new UnknownMonsterException("The monster \""
												+ monsterName
												+ "\" does not exist in this room. Removal failed. ");
		}
		retMonster = monsterMap.get(monsterName);
		monsterMap.remove(monsterName);
		return retMonster;
	}

	public void discardMonster(String monsterName) throws UnknownMonsterException {
		if(monsterMap.remove(monsterName) != null) {
			throw new UnknownMonsterException("The monster \""
												+ monsterName
												+ "\" does not exist in this room. Removal failed. ");
		}
	}

	public Map<String, Item> getItemMap() {
		return this.itemList.getItemMap();
	}

	public Map<String, Monster> getMonsterList() {
		return monsterMap;
	}
	
	public Monster getMonster(String name) {
		return monsterMap.get(name);
	}
	
	public Item getItem(String name) throws UnknownItemException {
		if(!itemList.getItemMap().containsKey(name)) {
			throw new UnknownItemException(name + " is not found in this room. ");
		}
		if(itemList.getItemMap().get(name) instanceof Artifact) {
			return (Artifact) itemList.getItemMap().get(name);
		}
		else if(itemList.getItemMap().get(name) instanceof Magical) {
			return (Magical) itemList.getItemMap().get(name);
		}
		else if(itemList.getItemMap().get(name) instanceof OneHandedWeapon) {
			return (OneHandedWeapon) itemList.getItemMap().get(name);
		}
		else if(itemList.getItemMap().get(name) instanceof Potion) {
			return (Potion) itemList.getItemMap().get(name);
		}
		else if(itemList.getItemMap().get(name) instanceof Shield) {
			return (Shield) itemList.getItemMap().get(name);
		}
		else if(itemList.getItemMap().get(name) instanceof Suit) {
			return (Suit) itemList.getItemMap().get(name);
		}
		else if(itemList.getItemMap().get(name) instanceof TwoHandedWeapon) {
			return (TwoHandedWeapon) itemList.getItemMap().get(name);
		}
		else {
			throw new UnknownItemException(itemList.getItemMap().get(name).getName() + " has no type. ");
		}
	}
	
	public Item getItem(Item item) throws UnknownItemException {
		if(item instanceof Artifact) {
			return (Artifact) item;
		}
		else if(item instanceof Magical) {
			return (Magical) item;
		}
		else if(item instanceof OneHandedWeapon) {
			return (OneHandedWeapon) item;
		}
		else if(item instanceof Potion) {
			return (Potion) item;
		}
		else if(item instanceof Shield) {
			return (Shield) item;
		}
		else if(item instanceof Suit) {
			return (Suit) item;
		}
		else if(item instanceof TwoHandedWeapon) {
			return (TwoHandedWeapon) item;
		}
		else {
			throw new UnknownItemException(item + " has no type. ");
		}
	}
	
//	public Item getItem(String name) throws UnknownItemException {
//		if(!itemMap.containsKey(name)) {
//			throw new UnknownItemException(name + " is not found in this room. ");
//		}
//		return itemMap.get(name);
//	}
	
//	public Item getItem(Item item) throws UnknownItemException {
//		if(!itemMap.containsValue(item)) {
//			throw new UnknownItemException(item.titleToString() + " is not found in this room. ");
//		}
//		return itemMap.get(item.getName());
//	}
}
