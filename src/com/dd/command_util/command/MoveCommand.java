package com.dd.command_util.command;

import com.dd.GameState;
import com.dd.command_util.CommandHandler;
import com.dd.command_util.CommandOutputLog;
import com.dd.entities.Player;
import com.dd.items.Potion;
import com.dd.levels.Direction;
import com.dd.levels.DungeonMap;
import com.dd.levels.MapPosition;

public class MoveCommand extends CommandHandler {
	private Player player;
	private DungeonMap map;

    public MoveCommand(GameState gameState) {
    	player = gameState.getActivePlayer();
    	map = gameState.getMap();
	}

    @Override
    public void handleCommand(String commandName, String[] args, CommandOutputLog outputLog) {
    	if(args[0] != null) {
			MapPosition position = player.getPostion();
	    	switch(args[0]) {
	    	case "north": 
				if(map.isRoomInDir(position, Direction.NORTH)){
						position.moveNorth();
						outputLog.printToLog("The " + player.titleToString() + " has moved through the North door. ");
				}
				else{
					outputLog.printToLog("No North Door! ");
				}
				break;
			
	    	case "south": 
				if(map.isRoomInDir(position, Direction.SOUTH)){
					position.moveSouth();
					outputLog.printToLog(player.titleToString() + " has moved through the South door. ");
				}
				else{
					outputLog.printToLog("No South Door! ");
				}
				break;
			
	    	case "east": 
				if(map.isRoomInDir(position, Direction.EAST)){
					position.moveEast();
					outputLog.printToLog(player.titleToString() + " has moved through the East door. ");
				}
				else{
					outputLog.printToLog("No East Door! ");
				}
				break;
			
	    	case "west": 
				if(map.isRoomInDir(position, Direction.WEST)){
					position.moveWest();
					outputLog.printToLog(player.titleToString() + " has moved through the West door. ");
				}
				else{
					outputLog.printToLog("No West Door! ");
				}
				break;
			
	    	default:
	    		outputLog.printToLog("The argument \"" + args[0] + "\" is invalid.\n"
	    				+ "Type \"help\" for help using the move command. ");
				return;	
			}
	    	outputLog.printToLog(map.getRoom(player.getPostion()).examineString());
    	}
    	else {
    		outputLog.printToLog("Type \"help\" for help using the move command. ");
    	}
    }
}