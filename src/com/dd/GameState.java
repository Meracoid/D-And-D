package com.dd;

import com.dd.entities.*;
import com.dd.levels.DungeonMap;

import java.io.Serializable;
import java.lang.IllegalArgumentException;
import java.util.ArrayList;
import java.util.List;

public class GameState implements Serializable {
    protected String name;
	protected Player activePlayer;
	protected int maxNumPlayers;
    protected List<Player> allActivePlayers = new ArrayList<Player>();
	protected DungeonMap map;
	
	public GameState(String name, Player activePlayer, DungeonMap map, int maxNumPlayers) {
	    this.name = name;
	    this.activePlayer = activePlayer;
        this.maxNumPlayers = maxNumPlayers;
        allActivePlayers = new ArrayList<Player>();
        this.map = map;
	}

	public GameState(String name, Player activePlayer, DungeonMap map) {
        this(name, activePlayer, map, 1);
    }

    public Player getActivePlayer() {
    	if(activePlayer instanceof Fighter) {
    		return (Fighter) activePlayer;
    	}
    	else if(activePlayer instanceof Wizard) {
    		return (Wizard) activePlayer;
    	}
    	return activePlayer;
    }
    
    public void setActivePlayer(Fighter fighter) {
    	activePlayer = fighter;
    }
    
    public void setActivePlayer(Wizard wizard) {
    	activePlayer = wizard;
    }

    public List<Player> getPlayerList() {
        return allActivePlayers;
    }

    public DungeonMap getMap() {
        return map;
    }

    public void setMap(DungeonMap map) {
        this.map = map;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxNumPlayers() {
        return maxNumPlayers;
    }

    public void setMaxNumPlayers(int maxNumPlayers) {
        this.maxNumPlayers = maxNumPlayers;
    }

    public void addActivePlayer(Player player) {
        if(player == null)
            throw new IllegalArgumentException("Player passed to GameState is null. Addition failed.");
        if(allActivePlayers.contains(player))
            throw new IllegalArgumentException("Player \""
                                                + player.getName()
                                                + "is already active in this GameState. Addition failed.");
        allActivePlayers.add(player);
    }

    public void removeActivePlayer(Player player) {
        if(player == null)
            throw new IllegalArgumentException("Player passed to GameState is null. Removal failed.");
        if(!allActivePlayers.remove(player))
            throw new IllegalArgumentException("Player \""
                                                + player.getName()
                                                + "is not active in this GameState. Removal failed.");
    }
}
