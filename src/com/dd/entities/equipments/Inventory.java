package com.dd.entities.equipments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.dd.items.*;
import com.dd.exceptions.*;

public class Inventory {

	private int size;
	private int maxSize;
	private ArrayList<Artifact> artifacts;
	private ArrayList<Potion> potions;
	private ArrayList<Magical> magicals;
	
	public Inventory(int maxSize) {
		this.size = 0;
		this.maxSize = maxSize;
		this.potions = new ArrayList<Potion>();
		this.artifacts = new ArrayList<Artifact>();
		this.magicals = new ArrayList<Magical>();
	}
	
	public Potion get(Potion potion) throws InventoryException {
		if(!potions.contains(potion)) {
			throw new InventoryException(potion.titleToString() + " is not in your inventory. ");
		}
		return potions.get(potions.indexOf(potion));
	}
	
	public Artifact get(Artifact artifact) throws InventoryException {
		if(!artifacts.contains(artifact)) {
			throw new InventoryException(artifact.titleToString() + " is not in your inventory. ");
		}
		return artifacts.get(artifacts.indexOf(artifact));
	}
	
	public Potion get(Magical magical) throws InventoryException {
		if(!magicals.contains(magical)) {
			throw new InventoryException(magical.titleToString() + " is not in your inventory. ");
		}
		return potions.get(potions.indexOf(magical));
	}
	
	public void remove(Potion potion) throws InventoryException {
		if(potions.contains(potion)) {
			this.potions.remove(potion);
		}
		else {
			throw new InventoryException(potion.titleToString() + " is not in your inventory. ");
		}
		size--;
	}
	
	public void remove(Artifact artifact) throws InventoryException {
		if(artifacts.contains(artifact)) {
			this.artifacts.remove(artifact);
		}
		else {
			throw new InventoryException(artifact.titleToString() + " is not in your inventory. ");
		}
		size--;
	}
	
	public void remove(Magical magical) throws InventoryException {
		if(magicals.contains(magical)) {
			this.magicals.remove(magical);
		}
		else {
			throw new InventoryException(magical.titleToString() + " is not in your inventory. ");
		}
		size--;
	}
	
	public Map<String, Item> getInventoryMap() {
		Map<String, Item> inventoryMap = new HashMap<String, Item>();
		for(Potion potion : potions) {
			inventoryMap.put(potion.getName(), potion);
		}
		for(Artifact artifact: artifacts) {
			inventoryMap.put(artifact.getName(), artifact);
		}
		for(Magical magical: magicals) {
			inventoryMap.put(magical.getName(), magical);
		}
		return inventoryMap;
	}

	public ArrayList<Artifact> getArtifacts() {
		return artifacts;
	}

	public ArrayList<Potion> getPotions() {
		return potions;
	}

	public ArrayList<Magical> getMagicals() {
		return magicals;
	}
}	
