package com.dd.command_util.command;

import java.util.ArrayList;

import com.dd.GameState;
import com.dd.command_util.CommandHandler;
import com.dd.command_util.CommandOutputLog;
import com.dd.command_util.CommandHandler.InvalidArgumentException;
import com.dd.entities.Player;
import com.dd.entities.Player.EquipmentException;
import com.dd.entities.Player.InventoryException;
import com.dd.items.*;
import com.dd.levels.DungeonMap;
import com.dd.levels.Room;
import com.dd.levels.Room.UnknownItemException;

public class PickupCommand extends CommandHandler {
	private Player player;
	private DungeonMap dungeonMap;
	private Room room;

    public PickupCommand(GameState gameState) {
    	this.player = gameState.getActivePlayer();
		this.dungeonMap = gameState.getMap();
		this.room = dungeonMap.getRoom(player.getPostion());
	}

    @Override
    public void handleCommand(String commandName, String[] args, CommandOutputLog outputLog) throws InvalidArgumentException {
    	player.resetEquipSuccess();
    	this.room = dungeonMap.getRoom(player.getPostion());
    	if(args[0] == null) {
    		throw new InvalidArgumentException("Choose something to pickup. "
    				+ "Type \"help\" for help using the " + commandName +" command. ");
    	}
		Item item = null;
		switch(args[0]) {
		case "items":
			ArrayList<String> equippedItemNames = new ArrayList<String>();;
			for(String itemName : room.getItemList().keySet()) {
				player.resetEquipSuccess();
				try {
					item = room.getItem(itemName);
				}
				catch(UnknownItemException UIE) {
					outputLog.printToLog(UIE.getMessage());
				}
	    		try {
	    			player.equip(item);
	    			if(player.isEquipSuccess()) {
	    				equippedItemNames.add(itemName);
	    			}
	    		}
	    		catch(EquipmentException | InventoryException E) {
	    			outputLog.printToLog(E.toString());
	    		}
			}
			for(String itemName : equippedItemNames) {
	    		try {
	    			room.removeItem(itemName);
	    		}
	    		catch (UnknownItemException UIE) {
	    			outputLog.printToLog(UIE.getMessage());
				}
			}
	    	equippedItemNames.forEach((k) -> outputLog.printToLog(player.titleToString() + " has equipped " + k + ". "));
	    	player.resetEquipSuccess();
			break;
		default:
			try {
				item = room.getItem(args[0]);
			}
			catch(UnknownItemException UIE) {
				outputLog.printToLog(UIE.getMessage());
				return;
			}
			if(item == null) {
				throw new InvalidArgumentException("The item \"" + args[0] + "\" is not in this room. ");
			}
			try {
				player.equip(item);
    		}
			catch(EquipmentException | InventoryException E) {
    			outputLog.printToLog(E.getMessage());
    			return;
    		}
    		try {
    			if(player.isEquipSuccess()) {
    				room.removeItem(item.getName());
    				outputLog.printToLog(player.titleToString() + " has equipped " + item.titleToString() + ". ");
    			}
    		}
    		catch (UnknownItemException UIE) {
    			outputLog.printToLog(UIE.getMessage());
    			return;
			}
		}
		outputLog.printToLog("This room now contains the following items:\n" + room.examineItems());
    }
}