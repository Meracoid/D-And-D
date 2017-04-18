package com.dd.command_util;

import com.dd.GameState;
import com.dd.entities.*;
import com.dd.entities.adt.ItemType;
import com.dd.exceptions.InvalidArgumentException;
import com.dd.exceptions.InventoryException;
import com.dd.exceptions.NoPlayerClassException;
import com.dd.levels.DungeonMap;
import com.dd.levels.Room;

public abstract class CommandHandler {
	
	protected GameState gameState;
	protected DungeonMap dungeonMap;
	protected Room room;
	
	protected Wizard wizard;
    protected Fighter fighter;
    protected PlayerType playerType = PlayerType.NONE;
    protected ItemType itemType = ItemType.NONE;
	
	public CommandHandler(GameState gameState) {
    	initGameState(gameState);
	}
	
	public abstract void handleCommand(String commandName, String[] args, CommandOutputLog outputLog) throws InvalidArgumentException, InventoryException;

	protected void initGameState(GameState activeState) {
		this.gameState = activeState;
    	this.dungeonMap = gameState.getMap();
    	this.playerType = gameState.getPlayerType();
    	updateState();
	}
	
	protected Player updateState() {
    	if(playerType == PlayerType.FIGHTER) {
    		fighter = gameState.getActiveFighter();
    		this.room = dungeonMap.getRoom(fighter.getPostion());
    		return fighter;
    	}
    	else if(playerType == PlayerType.WIZARD) {
    		wizard = gameState.getActiveWizard();
    		this.room = dungeonMap.getRoom(wizard.getPostion());
    		return wizard;
    	}
    	else {
    		throw new NoPlayerClassException("No player class in CommandHandler. ");
    	}
	}
	
	protected String getArgsString(String args[]){
        String argsStr = "";
        for(int i = 0; i < args.length - 1; i++) {
            argsStr += args[0] + " ";
        }
        argsStr += args[args.length - 1];
        return argsStr;
    }
}