package com.dd.command_util;

import com.dd.GameState;
import com.dd.entities.*;
import com.dd.exceptions.*;
import com.dd.levels.DungeonMap;
import com.dd.levels.Room;

public abstract class CommandHandler {
	
	protected GameState gameState;
	protected DungeonMap dungeonMap;
	protected static Room room;
	protected static Player player;
	protected static Monster monster;
	protected static CommandOutputLog globalOutput;
	protected static boolean examineMonster;
	protected static boolean monsterAttack = true;
	protected boolean dead = false;
	
	public CommandHandler(GameState gameState) {
    	initGameState(gameState);
	}
	
	public abstract void handleCommand(String commandName, String[] args, CommandOutputLog output) throws InvalidArgumentException;

	protected void initGameState(GameState activeState) {
		this.gameState = activeState;
    	this.dungeonMap = gameState.getMap();
    	updateState();
	}
	
	protected void updateState() {
		player = gameState.getActivePlayer();
		room = dungeonMap.getRoom(player.getPostion());
		try {
			monster = room.getMonster();
		}
		catch (NullMonsterException NME) {
			monster = null;
			//globalOutput.print(NME.getMessage());
		}
		if(player.isDead()){
			dead = true;
		}
	}

	public void setGlobalOutput(CommandOutputLog outputLog) {
		globalOutput = outputLog;
	}
	
	public void monsterAttack() {
		updateState();
		if(room.hasMonster() && monsterAttack && !isDead()) {
			try {
	    		monster = room.getMonster();
	    		monster.clearText();
				monster.attack(player);
				globalOutput.print(monster.getText() + "\n");
				if(examineMonster) {
					globalOutput.print(room.examineMonster());
				}
			}
			catch(NullMonsterException NME) {
				globalOutput.print(NME.getMessage());
			}
		}
		monsterAttack = true;
	}
	
	public void setPlayer(Player player){
		this.player = player;
	}

	public boolean isDead() {
		return dead;
	}
}
