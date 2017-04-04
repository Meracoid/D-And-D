package com.dd.command_util.command;

import com.dd.GameState;
import com.dd.command_util.CommandHandler;
import com.dd.command_util.CommandOutputLog;
import com.dd.entities.Monster;
import com.dd.entities.Player;
import com.dd.items.Item;
import com.dd.levels.DungeonMap;
import com.dd.levels.MapPosition;
import com.dd.levels.Room;

public class EnterCommand extends CommandHandler {
	private Player player;
	private DungeonMap map;

    public EnterCommand(GameState gameState) {
    	player = gameState.getActivePlayer();
    	map = gameState.getMap();
	}

    @Override
    public void handleCommand(String commandName, String[] args, CommandOutputLog outputLog){
    	if(!player.isinDungeon()) {
	    	player.enterDungeon();
	    	outputLog.printToLog(player.getName() + " has entered the Dungeon\n");
	    	outputLog.printToLog(map.getRoom(player.getPostion()).examineString() + "\n");
    	}
    	else {
    		outputLog.printToLog(player.getName() + " is already inside the Dungeon. "
    				+ "Now you need to gather items, defeat monsters, and escape!\n");
    	}
    }
}