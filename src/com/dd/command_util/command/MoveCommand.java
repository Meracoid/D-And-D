package com.dd.command_util.command;

import com.dd.GameState;
import com.dd.command_util.CommandHandler;
import com.dd.command_util.CommandOutputLog;
import com.dd.entities.Fighter;
import com.dd.entities.Player;
import com.dd.entities.PlayerType;
import com.dd.entities.Wizard;
import com.dd.exceptions.*;
import com.dd.levels.Direction;
import com.dd.levels.MapPosition;

public class MoveCommand extends CommandHandler {

    public MoveCommand(GameState gameState) {
    	super(gameState);
	}

    @Override
    public void handleCommand(String commandName, String[] args, CommandOutputLog outputLog) throws InvalidArgumentException {
    	if(args[0] == null) {
    		throw new InvalidArgumentException("Choose a direction to move. "
    				+ "Type \"help\" for help using the " + commandName +" command. ");
    	}
    	Player player = updateState();
		if(playerType == PlayerType.FIGHTER) {
    		player = (Fighter) player;
    	}
    	else if(playerType == PlayerType.WIZARD) {
    		player = (Wizard) player; 
    	}
    	else {
    		throw new NoPlayerClassException("No player class in CommandHandler. ");
    	}
		MapPosition position = player.getPostion();
    	switch(args[0].toLowerCase()) {
    	case "north": 
			if(dungeonMap.isRoomInDir(position, Direction.NORTH)){
					position.moveNorth();
					outputLog.printToLog("The " + player.titleToString() + " has moved through the North door. ");
					outputLog.printToLog(dungeonMap.getRoom(player.getPostion()).enterRoomText());
			}
			else{
				outputLog.printToLog("No North Door! ");
			}
			break;
		
    	case "south": 
			if(dungeonMap.isRoomInDir(position, Direction.SOUTH)){
				position.moveSouth();
				outputLog.printToLog(player.titleToString() + " has moved through the South door. ");
				outputLog.printToLog(dungeonMap.getRoom(player.getPostion()).enterRoomText());
			}
			else{
				outputLog.printToLog("No South Door! ");
			}
			break;
		
    	case "east": 
			if(dungeonMap.isRoomInDir(position, Direction.EAST)){
				position.moveEast();
				outputLog.printToLog(player.titleToString() + " has moved through the East door. ");
				outputLog.printToLog(dungeonMap.getRoom(player.getPostion()).enterRoomText());
			}
			else{
				outputLog.printToLog("No East Door! ");
			}
			break;
		
    	case "west": 
			if(dungeonMap.isRoomInDir(position, Direction.WEST)){
				position.moveWest();
				outputLog.printToLog(player.titleToString() + " has moved through the West door. ");
				outputLog.printToLog(dungeonMap.getRoom(player.getPostion()).enterRoomText());
			}
			else{
				outputLog.printToLog("No West Door! ");
			}
			break;
		
    	default:
    		throw new InvalidArgumentException(args[0] + "is not a direction. "
    				+ "Type \"help\" for help using the move command. ");
		}
    }
}