package com.dd.levels;

import com.dd.entities.Monster;
import com.dd.exceptions.*;
import com.dd.items.Item;
import com.dd.dd_util.ConflictHandlingMap;
import java.util.Map;

public class Room {
	private Map<String, Item> itemMap;
	private Map<String, Monster> monsterMap;
	
	public Room() {
		itemMap = new ConflictHandlingMap<Item>();
		monsterMap = new ConflictHandlingMap<Monster>();
	}
	
	public String examineItems() {
		StringBuilder outputSB = new StringBuilder();
		if(hasItems()) {
			getItemList().values().forEach((v) -> outputSB.append(
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
			getItemList().forEach((k,v) -> outputText.append("a " + v.typeToString() + " called \"" + k + "\" "));
		}
		return outputText.toString();
	}

	public boolean isEmpty() {
		return getItemList().isEmpty() && getMonsterList().isEmpty();
	}
	
	public boolean hasMonster() {
		return !getMonsterList().isEmpty();
	}
	
	public boolean hasItems() {
		return !getItemList().isEmpty();
	}

	public void addItem(Item item) {
		itemMap.put(item.getName(), item);
	}

	public void removeItem(String itemName) throws UnknownItemException {
		if(!itemMap.containsKey(itemName)) {
			throw new UnknownItemException("The item \""
											+ itemName
											+ "\" does not exist in this room. Removal failed. ");
		}
		itemMap.get(itemName);
		itemMap.remove(itemName);
	}

	public void discardItem(String itemName) throws UnknownItemException {
		if(itemMap.remove(itemName) != null){
			throw new UnknownItemException("The item \""
											+ itemName
											+ "\" does not exist in this room. Discard failed. ");
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

	public Map<String, Item> getItemList() {
		return itemMap;
	}

	public Map<String, Monster> getMonsterList() {
		return monsterMap;
	}
	
	public Monster getMonster(String name) {
		return monsterMap.get(name);
	}
	
	public Item getItem(String name) throws UnknownItemException {
		if(!itemMap.containsKey(name)) {
			throw new UnknownItemException(name + " is not found in this room. ");
		}
		return itemMap.get(name);
	}
}
