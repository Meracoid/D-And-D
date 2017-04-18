package com.dd.entities.adt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.dd.items.*;
import com.dd.exceptions.*;

public class ItemList {

	private int size;
	private ArrayList<Artifact> artifacts;
	private ArrayList<Magical> magicals;
	private ArrayList<OneHandedWeapon> oneHandedWeapons;
	private ArrayList<Potion> potions;
	private ArrayList<Shield> shields;
	private ArrayList<Suit> suits;
	private ArrayList<TwoHandedWeapon> twoHandedWeapons;
	
	public ItemList() {
		this.size = 0;
		this.artifacts = new ArrayList<Artifact>();
		this.magicals = new ArrayList<Magical>();
		this.oneHandedWeapons = new ArrayList<OneHandedWeapon>();
		this.potions = new ArrayList<Potion>();
		this.shields = new ArrayList<Shield>();
		this.suits = new ArrayList<Suit>();
		this.twoHandedWeapons = new ArrayList<TwoHandedWeapon>();
	}
	
	public Artifact get(Artifact artifact) throws InventoryException {
		if(!artifacts.contains(artifact)) {
			throw new InventoryException(artifact.titleToString() + " is not in your inventory. ");
		}
		return artifacts.get(artifacts.indexOf(artifact));
	}
	
	public Magical get(Magical magical) throws InventoryException {
		if(!magicals.contains(magical)) {
			throw new InventoryException(magical.titleToString() + " is not in your inventory. ");
		}
		return magicals.get(magicals.indexOf(magical));
	}
	
	public OneHandedWeapon get(OneHandedWeapon oneHandedWeapon) throws InventoryException {
		if(!oneHandedWeapons.contains(oneHandedWeapon)) {
			throw new InventoryException(oneHandedWeapon.titleToString() + " is not in your inventory. ");
		}
		return oneHandedWeapons.get(oneHandedWeapons.indexOf(oneHandedWeapon));
	}
	
	public Potion get(Potion potion) throws InventoryException {
		if(!potions.contains(potion)) {
			throw new InventoryException(potion.titleToString() + " is not in your inventory. ");
		}
		return potions.get(potions.indexOf(potion));
	}
	
	public Shield get(Shield shield) throws InventoryException {
		if(!shields.contains(shield)) {
			throw new InventoryException(shield.titleToString() + " is not in your inventory. ");
		}
		return shields.get(shields.indexOf(shield));
	}
	
	public Suit get(Suit suit) throws InventoryException {
		if(!suits.contains(suit)) {
			throw new InventoryException(suit.titleToString() + " is not in your inventory. ");
		}
		return suits.get(suits.indexOf(suit));
	}
	
	public TwoHandedWeapon get(TwoHandedWeapon twoHandedWeapon) throws InventoryException {
		if(!twoHandedWeapons.contains(twoHandedWeapon)) {
			throw new InventoryException(twoHandedWeapon.titleToString() + " is not in your inventory. ");
		}
		return twoHandedWeapons.get(twoHandedWeapons.indexOf(twoHandedWeapon));
	}
	
	public void add(Artifact artifact) {
		this.artifacts.add(artifact);
		size++;
	}
	
	public void add(Magical magical) {
		this.magicals.add(magical);
		size++;
	}
	
	public void add(OneHandedWeapon oneHandedWeapon) {
		this.oneHandedWeapons.add(oneHandedWeapon);
		size++;
	}
	
	public void add(Potion potion) {
		this.potions.add(potion);
		size++;
	}
	
	public void add(Shield shield) {
		this.shields.add(shield);
		size++;
	}
	
	public void add(Suit suit) {
		this.suits.add(suit);
		size++;
	}
	
	public void add(TwoHandedWeapon twoHandedWeapon) {
		this.twoHandedWeapons.add(twoHandedWeapon);
		size++;
	}
	
	public void remove(Artifact artifact) throws InventoryException {
		if(artifacts.contains(artifact)) {
			this.artifacts.remove(artifact);
			size--;
		}
		else {
			throw new InventoryException(artifact.titleToString() + " is not in your inventory. ");
		}
	}
	
	public void remove(Magical magical) throws InventoryException {
		if(magicals.contains(magical)) {
			this.magicals.remove(magical);
			size--;
		}
		else {
			throw new InventoryException(magical.titleToString() + " is not in your inventory. ");
		}
	}
	
	public void remove(OneHandedWeapon oneHandedWeapon) throws InventoryException {
		if(oneHandedWeapons.contains(oneHandedWeapon)) {
			this.oneHandedWeapons.remove(oneHandedWeapon);
			size--;
		}
		else {
			throw new InventoryException(oneHandedWeapon.titleToString() + " is not in your inventory. ");
		}
	}
	
	public void remove(Potion potion) throws InventoryException {
		if(potions.contains(potion)) {
			this.potions.remove(potion);
			size--;
		}
		else {
			throw new InventoryException(potion.titleToString() + " is not in your inventory. ");
		}
	}
	
	public void remove(Shield shield) throws InventoryException {
		if(shields.contains(shield)) {
			this.shields.remove(shield);
			size--;
		}
		else {
			throw new InventoryException(shield.titleToString() + " is not in your inventory. ");
		}
	}
	
	public void remove(Suit suit) throws InventoryException {
		if(suits.contains(suit)) {
			this.suits.remove(suit);
			size--;
		}
		else {
			throw new InventoryException(suit.titleToString() + " is not in your inventory. ");
		}
	}
	
	public void remove(TwoHandedWeapon twoHandedWeapon) throws InventoryException {
		if(twoHandedWeapons.contains(twoHandedWeapon)) {
			this.twoHandedWeapons.remove(twoHandedWeapon);
			size--;
		}
		else {
			throw new InventoryException(twoHandedWeapon.titleToString() + " is not in your inventory. ");
		}
	}
	
	public Map<String, Item> getItemMap() {
		Map<String, Item> itemMap = new HashMap<String, Item>();
		for(OneHandedWeapon oneHandedWeapon : oneHandedWeapons) {
			itemMap.put(oneHandedWeapon.getName(), oneHandedWeapon);
		}
		for(TwoHandedWeapon twoHandedWeapon : twoHandedWeapons) {
			itemMap.put(twoHandedWeapon.getName(), twoHandedWeapon);
		}
		for(Shield shield : shields) {
			itemMap.put(shield.getName(), shield);
		}
		for(Suit suit : suits) {
			itemMap.put(suit.getName(), suit);
		}
		for(Magical magical: magicals) {
			itemMap.put(magical.getName(), magical);
		}
		for(Potion potion : potions) {
			itemMap.put(potion.getName(), potion);
		}
		for(Artifact artifact: artifacts) {
			itemMap.put(artifact.getName(), artifact);
		}
		return itemMap;
	}
	
	public int getSize() {
		return size;
	}
	
	public ArrayList<Magical> getMagicals() {
		return magicals;
	}

	public ArrayList<Potion> getPotions() {
		return potions;
	}

	public ArrayList<Suit> getSuits() {
		return suits;
	}
	
	public ArrayList<Artifact> getArtifacts() {
		return artifacts;
	}

	public ArrayList<OneHandedWeapon> getOneHandedWeapons() {
		return oneHandedWeapons;
	}

	public ArrayList<Shield> getShields() {
		return shields;
	}

	public ArrayList<TwoHandedWeapon> getTwoHandedWeapons() {
		return twoHandedWeapons;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
}	
