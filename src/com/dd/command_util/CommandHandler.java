package com.dd.command_util;

import com.dd.GameState;
import com.dd.entities.*;
import com.dd.exceptions.*;
import com.dd.levels.DungeonMap;
import com.dd.levels.Room;

public abstract class CommandHandler {
	
	protected GameState gameState;
	public static boolean examineMonster;
	public static boolean monsterAttack = true;
	
	public CommandHandler(GameState gameState) {
    	initGameState(gameState);
	}
	
	public abstract void handleCommand(String commandName, String[] args) throws InvalidArgumentException;

	protected void initGameState(GameState activeState) {
		this.gameState = activeState;
	}
	
	public void monsterAttack() {
		monster().attack(player());
	}
	
	protected Player player() {
		return gameState.getActivePlayer();
	}
	
	protected DungeonMap map() {
		return gameState.getMap();
	}
	
	protected Room room() {
		return map().getRoom(player().getPostion());
	}
	
	protected Monster monster() {
		return room().getMonster();
	}

	protected boolean isDead() {
		return player().isDead();
	}
	
	protected static void print(String text) {
    	CommandOutputLog.print(text);
    }
}
