package com.dd.command_util.command;

import com.dd.GameState;
import com.dd.command_util.CommandHandler;
import com.dd.command_util.CommandOutputLog;
import com.dd.entities.*;
import com.dd.exceptions.*;

public class AttackCommand extends CommandHandler {

    public AttackCommand(GameState gameState){
    	super(gameState);
    }

    @Override
    public void handleCommand(String commandName, String[] args, CommandOutputLog outputLog) throws InvalidArgumentException{
    	if(args[0] == null) {
    		throw new InvalidArgumentException("Choose something to " + commandName + ". "
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
		Monster monster = null;
		try{
			monster = room.getMonster(args[0]);
		}
		catch(UnknownMonsterException UME) {
			outputLog.printToLog(UME.getMessage());
		}
		player.clearText();
		player.attack(monster);
		outputLog.printToLog(player.getText());
		player.clearText();
		if(room.hasMonster()) {
			monster.attack(player);
			outputLog.printToLog(player.getText());
		}
    }
}