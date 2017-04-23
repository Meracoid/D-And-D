package com.dd.command_util;

import com.dd.GameState;
import com.dd.entities.*;
import com.dd.exceptions.*;
import com.dd.levels.DungeonMap;
import com.dd.levels.Room;

public abstract class CommandHandler {
	
	protected GameState gameState;
	protected DungeonMap dungeonMap;
	protected Room room;
	protected Player player;
	protected CommandOutputLog globalOutputLog;
	protected boolean examineMonster;
	protected boolean monsterAttack = true;
	protected boolean dead = false;
	
	public CommandHandler(GameState gameState) {
    	initGameState(gameState);
	}
	
	public abstract void handleCommand(String commandName, String[] args, CommandOutputLog outputLog) throws InvalidArgumentException;

	protected void initGameState(GameState activeState) {
		this.gameState = activeState;
    	this.dungeonMap = gameState.getMap();
    	updateState();
	}
	
	protected void updateState() {
		player = gameState.getActivePlayer();
		room = dungeonMap.getRoom(player.getPostion());
		if(player.died()){
			dead = true;
		}
	}
	
	public void setGlobalOutputLog(CommandOutputLog outputLog) {
		globalOutputLog = outputLog;
	}
	
	protected String getArgsString(String args[]){
        String argsStr = "";
        for(int i = 0; i < args.length - 1; i++) {
            argsStr += args[0] + " ";
        }
        argsStr += args[args.length - 1];
        return argsStr;
    }
	
	public void monsterAttack() {
		if(room.hasMonster() && monsterAttack) {
			try {
	    		Monster monster = room.getMonster();
	    		monster.clearText();
				monster.attack(player);
				globalOutputLog.printToLog(monster.getText());
				if(examineMonster) {
					examineMonster();
				}
			}
			catch(NullMonsterException NME) {
				globalOutputLog.printToLog(NME.getMessage());
			}
		}
		monsterAttack = true;
	}
	
	public void examineRoom() {
		globalOutputLog.printToLog(room.enterRoomText());
	}
	
	public void examineItems() {
		if(!room.hasItems()) {
			globalOutputLog.printToLog("There are no items in this room. ");
			return;
		}
		room.getItemMap().values().forEach((v) -> globalOutputLog.printToLog(
				v.titleToString() + " "
				+ v.examineToString() + "\n"));
	}
	
	public void examineMonster() {
		if(!room.hasMonster()) {
			globalOutputLog.printToLog("There are no monsters in this room. ");
			return;
		}
		room.getMonsterMap().values().forEach((v) -> globalOutputLog.printToLog(
				v.titleToString()
				+ "\nHealth: " + v.getStats().getHealth()
				+ "\nAttack/Defense: " + v.getStats().getAttack() + "/" + v.getStats().getDefense()
				+ "\n" + v.examineText()));
	}
	
	public boolean isDead() {
		return dead;
	}
}