package com.dd.command_util.command;

import com.dd.GameState;
import com.dd.command_util.CommandHandler;
import com.dd.command_util.CommandOutputLog;
import com.dd.command_util.LocalCommandOutputLog;
import com.dd.exceptions.*;
import com.dd.levels.Direction;
import com.dd.levels.MapPosition;

public class MoveCommand extends CommandHandler {

    public MoveCommand(GameState gameState) {
    	super(gameState);
	}

    @Override
    public void handleCommand(String commandName, String[] args, CommandOutputLog output) throws InvalidArgumentException {
    	setGlobalOutput(output);
    	updateState();
    	if(dead){
    		output.print(player.getTitle() + " is dead. ");
    		return;
    	}
    	if(args[0] == null) {
    		throw new InvalidArgumentException("Choose a direction to move. "
    				+ "Type \"help\" for help using the " + commandName +" command. ");
    	}
    	monsterAttack = false;
    	
		MapPosition position = player.getPostion();
    	switch(args[0].toLowerCase()) {
    	case "north": 
			if(dungeonMap.isRoomInDir(position, Direction.NORTH)){
				position.moveNorth();
				output.print("The " + player.getTitle() + " has moved through the North door. ");
				output.print(dungeonMap.getRoom(player.getPostion()).examineRoom());
			}
			else{
				output.print("No North Door! ");
			}
			break;
		
    	case "south": 
			if(dungeonMap.isRoomInDir(position, Direction.SOUTH)){
				position.moveSouth();
				output.print(player.getTitle() + " has moved through the South door. ");
				output.print(dungeonMap.getRoom(player.getPostion()).examineRoom());
			}
			else{
				output.print("No South Door! ");
			}
			break;
		
    	case "east": 
			if(dungeonMap.isRoomInDir(position, Direction.EAST)){
				position.moveEast();
				output.print(player.getTitle() + " has moved through the East door. ");
				output.print(dungeonMap.getRoom(player.getPostion()).examineRoom());
			}
			else{
				output.print("No East Door! ");
			}
			break;
		
    	case "west": 
			if(dungeonMap.isRoomInDir(position, Direction.WEST)){
				position.moveWest();
				output.print(player.getTitle() + " has moved through the West door. ");
				output.print(dungeonMap.getRoom(player.getPostion()).examineRoom());
			}
			else{
				output.print("No West Door! ");
			}
			break;
		
    	default:
    		throw new InvalidArgumentException(args[0] + "is not a direction. "
    				+ "Type \"help\" for help using the move command. ");
		}
    }
}