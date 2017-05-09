package com.dd.command_util.command;

import java.util.ArrayList;

import com.dd.GameState;
import com.dd.command_util.CommandHandler;
import com.dd.command_util.CommandOutputLog;
import com.dd.command_util.LocalCommandOutputLog;
import com.dd.exceptions.*;
import com.dd.items.*;

public class PickupCommand extends CommandHandler {

    public PickupCommand(GameState gameState) {
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
    		throw new InvalidArgumentException("Choose something to " + commandName + ". "
    				+ "Type \"help\" for help using the " + commandName +" command. ");
    	}
		
		switch(args[0]) {
		case "items":
			ArrayList<String> equippedItemNames = new ArrayList<String>();
			for(Item equippedItem : room.getItemMap().values()) {
				try {
					Item item = room.getItem(equippedItem);
					player.pickup(item);
					equippedItemNames.add(equippedItem.getName());
				}
				catch(NullItemException | EquipmentException E) {
					output.print(E.getMessage());
				}
			}
			for(String itemName : equippedItemNames) {
	    		try {
	    			room.removeItem(itemName);
	    			output.print(player.getTitle() + " has equipped " + itemName + ". ");
	    		}
	    		catch (NullItemException UIE) {
	    			output.print(UIE.getMessage());
				}
			}
			break;
		default:
			try {
				Item item = room.getItem(args[0]);
				player.pickup(item);
				room.removeItem(item.getName());
				output.print(player.getTitle() + " has equipped " + item.getTitle() + ". ");
			}
			catch(NullItemException | EquipmentException E) {
				output.print(E.getMessage());
				return;
			}
		}
		if(!room.hasItems()) {
			output.print("This room now has no items. ");
			return;
		}
		output.print("This room now contains the following items:\n");
		output.print(room.examineItems());	
    }
}