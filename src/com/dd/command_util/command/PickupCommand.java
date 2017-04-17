package com.dd.command_util.command;

import java.util.ArrayList;

import com.dd.GameState;
import com.dd.command_util.CommandHandler;
import com.dd.command_util.CommandOutputLog;
import com.dd.entities.*;
import com.dd.exceptions.*;
import com.dd.items.*;

public class PickupCommand extends CommandHandler {

    public PickupCommand(GameState gameState) {
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
    	player.resetPickupSuccess();
		Item item = null;
		switch(args[0]) {
		case "items":
			ArrayList<String> equippedItemNames = new ArrayList<String>();;
			for(Item equippedItem : room.getItemMap().values()) {
				player.resetPickupSuccess();
				try {
					item = room.getItem(equippedItem);
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
	    				equippedItemNames.add(equippedItem.getName());
	    			}
	    		}
	    		catch(EquipmentException | InventoryException E) {
	    			outputLog.printToLog(E.getMessage());
	    		}
			}
			for(String itemName : equippedItemNames) {
	    		try {
	    			room.removeItem(itemName);
	    			outputLog.printToLog(player.titleToString() + " has equipped " + itemName + ". ");
	    		}
	    		catch (UnknownItemException UIE) {
	    			outputLog.printToLog(UIE.getMessage());
				}
			}
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