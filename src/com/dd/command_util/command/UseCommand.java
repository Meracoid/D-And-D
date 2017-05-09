package com.dd.command_util.command;

import com.dd.GameState;
import com.dd.command_util.CommandHandler;
import com.dd.exceptions.*;
import com.dd.items.Item;
import com.dd.items.Potion;

public class UseCommand extends CommandHandler {
    
	public UseCommand(GameState gameState) {
		super(gameState);
    }

    @Override
    public void handleCommand(String commandName, String[] args) throws InvalidArgumentException {
    	if(isDead()){
    		print(player().getTitle() + " is dead. ");
    		return;
    	}
    	if(args[0] == null) {
    		throw new InvalidArgumentException("Choose something to " + commandName + ". "
    				+ "Type \"help\" for help using the " + commandName +" command. ");
    	}
    	
    	Item item = null;
    	if(player().getInventory().getInventoryMap().containsKey(args[0])) {
    		item = player().getInventory().getInventoryMap().get(args[0]);
    	}
    	else {
    		if(room().getItemMap().containsKey(args[0])) {
        		item = room().getItemMap().get(args[0]);
        	}
        	else {
        		print("this room does not conatain \""
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
				player().usePotionFromInventory((Potion) item);
				print(player().getTitle() + " has used " + item.getTitle() + ". ");
			} catch (EquipmentException EE) {
				print(EE.getMessage());
			}
    	}
    	else {
    		print(item.getTitle() + " is not a Potion. ");
    		return;
    	}
    }
}
