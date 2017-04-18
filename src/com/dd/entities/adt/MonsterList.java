package com.dd.entities.adt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.dd.entities.Monster;
import com.dd.entities.monsters.*;
import com.dd.exceptions.*;

public class MonsterList {

	private int size;
	private ArrayList<Beholder> beholders;
	private ArrayList<Dragon> dragons;
	private ArrayList<Goblin> goblins;
	private ArrayList<Skeleton> skeletons;
	private ArrayList<Zombie> zombies;
	
	public MonsterList() {
		this.size = 0;
		this.beholders = new ArrayList<Beholder>();
		this.dragons = new ArrayList<Dragon>();
		this.goblins = new ArrayList<Goblin>();
		this.skeletons = new ArrayList<Skeleton>();
		this.zombies = new ArrayList<Zombie>();
	}
	
	//this method assumes there is only one monster in room.
	public Monster getOne() {
		if(!isEmpty()) {
			if(!beholders.isEmpty()) {
				return beholders.get(0);
			}
			else if(!dragons.isEmpty()) {
				return dragons.get(0);
			}
			else if(!goblins.isEmpty()) {
				return goblins.get(0);
			}
			else if(!skeletons.isEmpty()) {
				return skeletons.get(0);
			}
			else if(!zombies.isEmpty()) {
				return zombies.get(0);
			}
			else {
				throw new UnknownMonsterException("No monsters in list. ");
			}
		}
		else {
			throw new UnknownMonsterException("No monsters in list. ");
		}
	}
	
	public Beholder get(Beholder Beholder) throws UnknownMonsterException {
		if(!beholders.contains(Beholder)) {
			throw new UnknownMonsterException(Beholder.titleToString() + " is not in your list. ");
		}
		return beholders.get(beholders.indexOf(Beholder));
	}
	
	public Dragon get(Dragon dragon) throws UnknownMonsterException {
		if(!dragons.contains(dragon)) {
			throw new UnknownMonsterException(dragon.titleToString() + " is not in your list. ");
		}
		return dragons.get(dragons.indexOf(dragon));
	}
	
	public Goblin get(Goblin goblin) throws UnknownMonsterException {
		if(!goblins.contains(goblin)) {
			throw new UnknownMonsterException(goblin.titleToString() + " is not in your list. ");
		}
		return goblins.get(goblins.indexOf(goblin));
	}
	
	public Skeleton get(Skeleton skeleton) throws UnknownMonsterException {
		if(!skeletons.contains(skeleton)) {
			throw new UnknownMonsterException(skeleton.titleToString() + " is not in your list. ");
		}
		return skeletons.get(skeletons.indexOf(skeleton));
	}
	
	public Zombie get(Zombie zombie) throws UnknownMonsterException {
		if(!zombies.contains(zombie)) {
			throw new UnknownMonsterException(zombie.titleToString() + " is not in your list. ");
		}
		return zombies.get(zombies.indexOf(zombie));
	}
	
	public void add(Monster monster) {
		if(monster instanceof Beholder) {
			add((Beholder) monster);
		}
		else if(monster instanceof Dragon) {
			add((Dragon) monster);
		}
		else if(monster instanceof Goblin) {
			add((Goblin) monster);
		}
		else if(monster instanceof Skeleton) {
			add((Skeleton) monster);
		}
		else if(monster instanceof Zombie) {
			add((Zombie) monster);
		}
		else {
			throw new UnknownMonsterException(monster.getName() + " is of unknown type. ");
		}
	}
	
	public void add(Beholder beholder) {
		this.beholders.add(beholder);
		size++;
	}
	
	public void add(Dragon Dragon) {
		this.dragons.add(Dragon);
		size++;
	}
	
	public void add(Goblin goblin) {
		this.goblins.add(goblin);
		size++;
	}
	
	public void add(Skeleton skeleton) {
		this.skeletons.add(skeleton);
		size++;
	}
	
	public void add(Zombie skeleton) {
		this.zombies.add(skeleton);
		size++;
	}
	
	public void remove(Beholder beholder) throws UnknownMonsterException {
		if(beholders.contains(beholder)) {
			this.beholders.remove(beholder);
			size--;
		}
		else {
			throw new UnknownMonsterException(beholder.titleToString() + " is not in your list. ");
		}
	}
	
	public void remove(Dragon dragon) throws UnknownMonsterException {
		if(dragons.contains(dragon)) {
			this.dragons.remove(dragon);
			size--;
		}
		else {
			throw new UnknownMonsterException(dragon.titleToString() + " is not in your list. ");
		}
	}
	
	public void remove(Goblin goblin) throws UnknownMonsterException {
		if(goblins.contains(goblin)) {
			this.goblins.remove(goblin);
			size--;
		}
		else {
			throw new UnknownMonsterException(goblin.titleToString() + " is not in your list. ");
		}
	}
	
	public void remove(Skeleton skeleton) throws UnknownMonsterException {
		if(skeletons.contains(skeleton)) {
			this.skeletons.remove(skeleton);
			size--;
		}
		else {
			throw new UnknownMonsterException(skeleton.titleToString() + " is not in your list. ");
		}
	}
	
	public void remove(Zombie zombie) throws UnknownMonsterException {
		if(zombies.contains(zombie)) {
			this.zombies.remove(zombie);
			size--;
		}
		else {
			throw new UnknownMonsterException(zombie.titleToString() + " is not in your list. ");
		}
	}
	
	public Map<String, Monster> getMonsterMap() {
		Map<String, Monster> itemMap = new HashMap<String, Monster>();
		for(Beholder beholder : beholders) {
			itemMap.put(beholder.getName(), beholder);
		}
		for(Dragon dragon : dragons) {
			itemMap.put(dragon.getName(), dragon);
		}
		for(Goblin goblin : goblins) {
			itemMap.put(goblin.getName(), goblin);
		}
		for(Skeleton skeleton: skeletons) {
			itemMap.put(skeleton.getName(), skeleton);
		}
		for(Zombie zombie : zombies) {
			itemMap.put(zombie.getName(), zombie);
		}
		return itemMap;
	}
	
	public int getSize() {
		return size;
	}
	
	public ArrayList<Beholder> getBeholders() {
		return beholders;
	}
	
	public ArrayList<Dragon> getDragons() {
		return dragons;
	}
	public ArrayList<Goblin> getGoblins() {
		return goblins;
	}

	public ArrayList<Skeleton> getSkeletons() {
		return skeletons;
	}

	public ArrayList<Zombie> getzombies() {
		return zombies;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
}	
