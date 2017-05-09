package com.dd.command_util.command;

import com.dd.GameState;
import com.dd.command_util.CommandHandler;
import com.dd.exceptions.*;

public class AttackCommand extends CommandHandler {

    public AttackCommand(GameState gameState){
    	super(gameState);
    }

    @Override
    public void handleCommand(String commandName, String[] args) throws InvalidArgumentException{
    	if(isDead()){
    		print(player().getTitle() + " is dead. ");
    		return;
    	}
    	if(args[0] != null) {
    		throw new InvalidArgumentException(commandName + " command does not require an argument. ");
    	}
		player().attack(monster());
		if(monster().isDead()) {
			room().removeMonster(monster());
		}
		examineMonster = true;
    }
}