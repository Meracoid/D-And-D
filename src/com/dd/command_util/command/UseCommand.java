package com.dd.command_util.command;

import com.dd.GameState;
import com.dd.command_util.CommandHandler;
import com.dd.command_util.CommandOutputLog;
import com.dd.entities.Fighter;
import com.dd.entities.Player;
import com.dd.entities.PlayerType;
import com.dd.entities.Wizard;
import com.dd.exceptions.*;
import com.dd.items.Item;
import com.dd.items.Potion;

public class UseCommand extends CommandHandler {
    
	public UseCommand(GameState gameState) {
		super(gameState);
    }

    @Override
    public void handleCommand(String commandName, String[] args, CommandOutputLog outputLog) throws InvalidArgumentException {
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
    	Item item = null;
    	if(player.getInventory().containsKey(args[0])) {
    		item = player.getInventory().get(args[0]);
    	}
    	else {
    		if(room.getItemList().containsKey(args[0])) {
        		item = room.getItemList().get(args[0]);
        	}
        	else {
        		outputLog.printToLog("this room does not conatain \""
        				+ args[0] + "\". ");
        		return;
        	}
    	}
    	if(item instanceof Potion) {
			try {
				player.usePotionFromInventory(item.getName());
				outputLog.printToLog(player.titleToString() + " has used " + item.titleToString() + ". ");
			} catch (InventoryException IE) {
				outputLog.printToLog(IE.getMessage());
			}
    	}
    	else {
    		outputLog.printToLog(item.titleToString() + " is not a Potion. ");
    		return;
    	}
    }
}