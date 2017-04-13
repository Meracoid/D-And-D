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
	private GameState gameState;
	private Player player;
	private DungeonMap dungeonMap;
	private Room room;

    public PickupCommand(GameState gameState) {
    	this.gameState = gameState;
    	this.player = gameState.getActivePlayer();
		this.dungeonMap = gameState.getMap();
		this.room = dungeonMap.getRoom(player.getPostion());
	}

    @Override
    public void handleCommand(String commandName, String[] args, CommandOutputLog outputLog) throws InvalidArgumentException {
    	this.player = gameState.getActivePlayer();
    	this.room = dungeonMap.getRoom(player.getPostion());
    	player.resetPickupSuccess();
    	if(args[0] == null) {
    		throw new InvalidArgumentException("Choose something to pickup. "
    				+ "Type \"help\" for help using the " + commandName +" command. ");
    	}
		Item item = null;
		switch(args[0]) {
		case "items":
			ArrayList<String> equippedItemNames = new ArrayList<String>();;
			for(String itemName : room.getItemList().keySet()) {
				player.resetPickupSuccess();
				try {
					item = room.getItem(itemName);
				}
				catch(UnknownItemException UIE) {
					outputLog.printToLog(UIE.getMessage());
				}
	    		try {
	    			if(item instanceof Artifact) {
						item = (Artifact) item;
						player.pickup((Artifact) item);
					}
					else if(item instanceof Magical) {
						item = (Magical) item;
						player.pickup((Magical) item);
					}
					else if(item instanceof OneHandedWeapon) {
						item = (OneHandedWeapon) item;
						player.pickup((OneHandedWeapon) item);
					}
					else if(item instanceof Potion) {
						item = (Potion) item;
						player.pickup((Potion) item);
					}
					else if(item instanceof Shield) {
						item = (Shield) item;
						player.pickup((Shield) item);
					}
					else if(item instanceof Suit) {
						item = (Suit) item;
						player.pickup((Suit) item);
					}
					else if(item instanceof TwoHandedWeapon) {
						item = (TwoHandedWeapon) item;
						player.pickup((TwoHandedWeapon) item);
					}
					else {
						outputLog.printToLog(item.getName() + " could not be equipped "
								+ "because it has not item type. ");
					}
	    			if(player.isPickupSuccess()) {
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
	    	player.resetPickupSuccess();
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
				if(item instanceof Artifact) {
					item = (Artifact) item;
					player.pickup((Artifact) item);
				}
				else if(item instanceof Magical) {
					item = (Magical) item;
					player.pickup((Magical) item);
				}
				else if(item instanceof OneHandedWeapon) {
					item = (OneHandedWeapon) item;
					player.pickup((OneHandedWeapon) item);
				}
				else if(item instanceof Potion) {
					item = (Potion) item;
					player.pickup((Potion) item);
				}
				else if(item instanceof Shield) {
					item = (Shield) item;
					player.pickup((Shield) item);
				}
				else if(item instanceof Suit) {
					item = (Suit) item;
					player.pickup((Suit) item);
				}
				else if(item instanceof TwoHandedWeapon) {
					item = (TwoHandedWeapon) item;
					player.pickup((TwoHandedWeapon) item);
				}
				else {
					outputLog.printToLog(item.getName() + " could not be equipped "
							+ "because it has not item type. ");
				}
    		}
			catch(EquipmentException | InventoryException E) {
    			outputLog.printToLog(E.getMessage());
    			return;
    		}
    		try {
    			if(player.isPickupSuccess()) {
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