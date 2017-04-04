package com.dd.levels;

import com.dd.entities.Monster;
import com.dd.items.Item;
import com.dd.dd_util.ConflictHandlingMap;
import java.util.Map;
import java.util.Set;

public class Room {
	private Map<String, Item> itemMap;
	private Map<String, Monster> monsterMap;
	
	public Room() {
		itemMap = new ConflictHandlingMap<Item>();
		monsterMap = new ConflictHandlingMap<Monster>();
	}
	
	public String examineString() {
		StringBuilder examineStr = new StringBuilder();
		if(isEmpty()){
			examineStr.append("This room is empty.");
		}
		if(hasMonster()) {
			if(getMonsterList().size() == 1) {
				examineStr.append("This room has a ");
				getMonsterList().forEach((k) -> examineStr.append(k + ".\n"));
			}
			else {
				examineStr.append("This room has multiple monsters:");
				getMonsterList().forEach((k) -> examineStr.append("\n~" + k + "\n"));
			}
			examineStr.append("Time to fight!\n");
		}
		if(hasItems()) {
			if(getItemList().size() == 1) {
				examineStr.append("This room contains a ");
				getItemList().forEach((k) -> examineStr.append(k + "\n"));
			}
			else if(getItemList().size() > 1) {
				examineStr.append("This room contains the following items:");
				getItemList().forEach((k) -> examineStr.append("\n~" + k));
			}
		}
		else {
			examineStr.append("This room has no items");
		}
		return examineStr.toString() + "\n";
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

	public Item removeItem(String itemName) throws UnknownItemException {
		Item retItem;
		if(!itemMap.containsKey(itemName)) {
			throw new UnknownItemException("The item \""
											+ itemName
											+ "\" does not exist in this room. Removal failed.");
		}
		retItem = itemMap.get(itemName);
		itemMap.remove(itemName);
		return retItem;
	}

	public void discardItem(String itemName) throws UnknownItemException {
		if(itemMap.remove(itemName) != null){
			throw new UnknownItemException("The item \""
											+ itemName
											+ "\" does not exist in this room. Discard failed.");
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
												+ "\" does not exist in this room. Removal failed.");
		}
		retMonster = monsterMap.get(monsterName);
		monsterMap.remove(monsterName);
		return retMonster;
	}

	public void discardMonster(String monsterName) throws UnknownMonsterException {
		if(monsterMap.remove(monsterName) != null) {
			throw new UnknownMonsterException("The monster \""
												+ monsterName
												+ "\" does not exist in this room. Removal failed.");
		}
	}

	public Set<String> getItemList() {
		return itemMap.keySet();
	}

	public Set<String> getMonsterList() {
		return monsterMap.keySet();
	}
	
	public Monster getMonster(String name) {
		return monsterMap.get(name);
	}
	
	public Item getItem(String name) {
		return itemMap.get(name);
	}

	public class UnknownItemException extends Exception {
		public UnknownItemException(String message) {
			super(message);
		}
	}

	public class UnknownMonsterException extends Exception {
		public UnknownMonsterException(String message){
			super(message);
		}
	}
}
