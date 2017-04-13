package com.dd;

import com.dd.entities.*;
import com.dd.levels.DungeonMap;
import java.lang.IllegalArgumentException;
import java.util.ArrayList;
import java.util.List;

public class GameState {
    protected String name;
	protected Fighter activeFighter;
	protected Wizard activeWizard;
	protected PlayerType playerType;
	protected int maxNumPlayers;
    protected List<Player> allActivePlayers = new ArrayList<Player>();
	protected DungeonMap map;
	
	public GameState(String name, Player newPlayer, DungeonMap map, int maxNumPlayers) {
	    this.name = name;
	    if(newPlayer instanceof Fighter) {
	    	this.activeFighter = (Fighter) newPlayer;
	    	this.playerType = PlayerType.FIGHTER;
	    }
	    else if(newPlayer instanceof Wizard) {
	    	this.activeWizard = (Wizard) newPlayer;
	    	this.playerType = PlayerType.WIZARD;
	    }
        this.maxNumPlayers = maxNumPlayers;
        allActivePlayers = new ArrayList<Player>();
        this.map = map;
	}

	public GameState(String name, Player newPlayer, DungeonMap map) {
        this.name = name;
        if(newPlayer instanceof Fighter) {
	    	this.activeFighter = (Fighter) newPlayer;
	    	this.playerType = PlayerType.FIGHTER;
	    }
	    else if(newPlayer instanceof Wizard) {
	    	this.activeWizard = (Wizard) newPlayer;
	    	this.playerType = PlayerType.WIZARD;
	    }
        this.maxNumPlayers = 1;
        allActivePlayers = new ArrayList<Player>();
        this.map = map;
    }
	
	public GameState(String name, DungeonMap map) {
        this.name = name;
        this.maxNumPlayers = 1;
        allActivePlayers = new ArrayList<Player>();
        this.map = map;
    }

    public Player getActivePlayer() {
    	if(playerType == PlayerType.FIGHTER) {
    		return (Fighter) activeFighter;
    	}
    	else if(playerType == PlayerType.WIZARD) {
    		return (Wizard) activeWizard;
    	}
    	else {
    		return null;
    	}
    }
    
    public void setActivePlayer(Fighter fighter) {
    	activeFighter = fighter;
    }
    
    public void setActivePlayer(Wizard wizard) {
    	activeWizard = wizard;
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
