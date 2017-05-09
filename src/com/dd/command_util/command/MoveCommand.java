package com.dd.command_util.command;

import com.dd.GameState;
import com.dd.command_util.CommandHandler;
import com.dd.exceptions.*;
import com.dd.levels.Direction;
import com.dd.levels.MapPosition;

public class MoveCommand extends CommandHandler {

    public MoveCommand(GameState gameState) {
    	super(gameState);
	}

    @Override
    public void handleCommand(String commandName, String[] args) throws InvalidArgumentException {
    	if(isDead()){
    		print(player().getTitle() + " is dead. ");
    		return;
    	}
    	if(args[0] == null) {
    		throw new InvalidArgumentException("Choose a direction to move. "
    				+ "Type \"help\" for help using the " + commandName +" command. ");
    	}
    	monsterAttack = false;
    	
		MapPosition position = player().getPostion();
    	switch(args[0].toLowerCase()) {
    	case "north": 
			if(map().isRoomInDir(position, Direction.NORTH)){
				position.moveNorth();
				print("The " + player().getTitle() + " has moved through the North door. ");
				print(map().getRoom(player().getPostion()).examineRoom());
			}
			else{
				print("No North Door! ");
			}
			break;
		
    	case "south": 
			if(map().isRoomInDir(position, Direction.SOUTH)){
				position.moveSouth();
				print(player().getTitle() + " has moved through the South door. ");
				print(map().getRoom(position).examineRoom());
			}
			else{
				print("No South Door! ");
			}
			break;
		
    	case "east": 
			if(map().isRoomInDir(position, Direction.EAST)){
				position.moveEast();
				print(player().getTitle() + " has moved through the East door. ");
				print(map().getRoom(position).examineRoom());
			}
			else{
				print("No East Door! ");
			}
			break;
		
    	case "west": 
			if(map().isRoomInDir(position, Direction.WEST)){
				position.moveWest();
				print(player().getTitle() + " has moved through the West door. ");
				print(map().getRoom(position).examineRoom());
			}
			else{
				print("No West Door! ");
			}
			break;
		
    	default:
    		throw new InvalidArgumentException(args[0] + "is not a direction. "
    				+ "Type \"help\" for help using the move command. ");
		}
    }
}