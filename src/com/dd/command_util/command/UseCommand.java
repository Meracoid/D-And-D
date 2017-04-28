package com.dd.command_util.command;

import com.dd.GameState;
import com.dd.command_util.CommandHandler;
import com.dd.command_util.CommandOutputLog;
import com.dd.exceptions.*;
import com.dd.items.Item;
import com.dd.items.Potion;

public class UseCommand extends CommandHandler {
    
	public UseCommand(GameState gameState) {
		super(gameState);
    }

    @Override
    public void handleCommand(String commandName, String[] args, CommandOutputLog outputLog) throws InvalidArgumentException {
    	setGlobalOutputLog(outputLog);
		updateState();
    	if(dead){
    		outputLog.printToLog(player.getTitle() + " is dead. ");
    		return;
    	}
    	if(args[0] == null) {
    		throw new InvalidArgumentException("Choose something to " + commandName + ". "
    				+ "Type \"help\" for help using the " + commandName +" command. ");
    	}
    	
    	Item item = null;
    	if(player.getInventory().getInventoryMap().containsKey(args[0])) {
    		item = player.getInventory().getInventoryMap().get(args[0]);
    	}
    	else {
    		if(room.getItemMap().containsKey(args[0])) {
        		item = room.getItemMap().get(args[0]);
        	}
        	else {
        		outputLog.printToLog("this room does not conatain \""
        				+ args[0] + "\". ");
        		return;
        	}
    	}
		
//    	Item item = room.hasPotion(args[0]);
//    	if(item != null) {
//    		try {
//				player.usePotion((Potion) item);
//			} catch (EquipmentException EE) {
//				outputLog.printToLog(EE.getMessage());
//			}
//    	}
//    	else {
//    		try {
//				item = player.getInventory().get(args[0]);
//			} catch (InventoryException IE) {
//				outputLog.printToLog(IE.getMessage());
//			}
//    	}
    	if(item instanceof Potion) {
			try {
				player.usePotionFromInventory((Potion) item);
				outputLog.printToLog(player.getTitle() + " has used " + item.titleToString() + ". ");
			} catch (EquipmentException EE) {
				outputLog.printToLog(EE.getMessage());
			}
    	}
    	else {
    		outputLog.printToLog(item.titleToString() + " is not a Potion. ");
    		return;
    	}
    }
}