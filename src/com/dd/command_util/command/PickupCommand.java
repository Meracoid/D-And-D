package com.dd.command_util.command;

import java.util.ArrayList;

import com.dd.GameState;
import com.dd.command_util.CommandHandler;
import com.dd.command_util.CommandOutputLog;
import com.dd.exceptions.*;
import com.dd.items.*;

public class PickupCommand extends CommandHandler {

    public PickupCommand(GameState gameState) {
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
		
		switch(args[0]) {
		case "items":
			ArrayList<String> equippedItemNames = new ArrayList<String>();
			for(Item equippedItem : room().getItemMap().values()) {
				try {
					Item item = room().getItem(equippedItem);
					player().pickup(item);
					equippedItemNames.add(equippedItem.getName());
				}
				catch(NullItemException | EquipmentException E) {
					print(E.getMessage());
				}
			}
			for(String itemName : equippedItemNames) {
	    		try {
	    			room().removeItem(itemName);
	    			print(player().getTitle() + " has equipped " + itemName + ". ");
	    		}
	    		catch (NullItemException UIE) {
	    			print(UIE.getMessage());
				}
			}
			break;
		default:
			try {
				Item item = room().getItem(args[0]);
				player().pickup(item);
				room().removeItem(item.getName());
				print(player().getTitle() + " has equipped " + item.getTitle() + ". ");
			}
			catch(NullItemException | EquipmentException E) {
				print(E.getMessage());
				return;
			}
		}
		if(!room().hasItems()) {
			print("This room now has no items. ");
			return;
		}
		print("This room now contains the following items:\n");
		print(room().examineItems());	
    }
}