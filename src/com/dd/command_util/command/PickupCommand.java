package com.dd.command_util.command;

import java.util.ArrayList;

import com.dd.GameState;
import com.dd.command_util.CommandHandler;
import com.dd.command_util.CommandOutputLog;
import com.dd.command_util.CommandHandler.InvalidArgumentException;
import com.dd.entities.Fighter;
import com.dd.entities.Player;
import com.dd.entities.PlayerType;
import com.dd.entities.Wizard;
import com.dd.entities.Player.EquipmentException;
import com.dd.entities.Player.InventoryException;
import com.dd.items.*;
import com.dd.levels.DungeonMap;
import com.dd.levels.Room;
import com.dd.levels.Room.UnknownItemException;

public class PickupCommand extends CommandHandler {
	private GameState gameState;
	private Wizard wizard;
    private Fighter fighter;
    private PlayerType playerType = PlayerType.NONE;
	private DungeonMap dungeonMap;
	private Room room;

    public PickupCommand(GameState gameState) {
    	this.gameState = gameState;
    	if(gameState.getActivePlayer() instanceof Fighter) {
        	fighter = (Fighter) gameState.getActivePlayer();
        	playerType = playerType.FIGHTER;
        	this.dungeonMap = gameState.getMap();
    		this.room = dungeonMap.getRoom(fighter.getPostion());
        }
        else if(gameState.getActivePlayer() instanceof Wizard) {
        	wizard = (Wizard) gameState.getActivePlayer();
        	playerType = PlayerType.WIZARD;
        	this.dungeonMap = gameState.getMap();
    		this.room = dungeonMap.getRoom(wizard.getPostion());
        }
	}

    @Override
    public void handleCommand(String commandName, String[] args, CommandOutputLog outputLog) throws InvalidArgumentException {
    	if(playerType == PlayerType.FIGHTER) {
	    	this.fighter = (Fighter) gameState.getActivePlayer();
	    	this.room = dungeonMap.getRoom(fighter.getPostion());
	    	fighter.resetPickupSuccess();
	    	if(args[0] == null) {
	    		throw new InvalidArgumentException("Choose something to pickup. "
	    				+ "Type \"help\" for help using the " + commandName +" command. ");
	    	}
			Item item = null;
			switch(args[0]) {
			case "items":
				ArrayList<String> equippedItemNames = new ArrayList<String>();;
				for(String itemName : room.getItemList().keySet()) {
					fighter.resetPickupSuccess();
					try {
						item = room.getItem(itemName);
					}
					catch(UnknownItemException UIE) {
						outputLog.printToLog(UIE.getMessage());
					}
		    		try {
		    			if(item instanceof Artifact) {
							item = (Artifact) item;
							fighter.pickup((Artifact) item);
						}
						else if(item instanceof Magical) {
							item = (Magical) item;
							fighter.pickup((Magical) item);
						}
						else if(item instanceof OneHandedWeapon) {
							item = (OneHandedWeapon) item;
							fighter.pickup((OneHandedWeapon) item);
						}
						else if(item instanceof Potion) {
							item = (Potion) item;
							fighter.pickup((Potion) item);
						}
						else if(item instanceof Shield) {
							item = (Shield) item;
							fighter.pickup((Shield) item);
						}
						else if(item instanceof Suit) {
							item = (Suit) item;
							fighter.pickup((Suit) item);
						}
						else if(item instanceof TwoHandedWeapon) {
							item = (TwoHandedWeapon) item;
							fighter.pickup((TwoHandedWeapon) item);
						}
						else {
							outputLog.printToLog(item.getName() + " could not be equipped "
									+ "because it has not item type. ");
						}
		    			if(fighter.isPickupSuccess()) {
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
		    	equippedItemNames.forEach((k) -> outputLog.printToLog(fighter.titleToString() + " has equipped " + k + ". "));
		    	fighter.resetPickupSuccess();
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
						fighter.pickup((Artifact) item);
					}
					else if(item instanceof Magical) {
						item = (Magical) item;
						fighter.pickup((Magical) item);
					}
					else if(item instanceof OneHandedWeapon) {
						item = (OneHandedWeapon) item;
						fighter.pickup((OneHandedWeapon) item);
					}
					else if(item instanceof Potion) {
						item = (Potion) item;
						fighter.pickup((Potion) item);
					}
					else if(item instanceof Shield) {
						item = (Shield) item;
						fighter.pickup((Shield) item);
					}
					else if(item instanceof Suit) {
						item = (Suit) item;
						fighter.pickup((Suit) item);
					}
					else if(item instanceof TwoHandedWeapon) {
						item = (TwoHandedWeapon) item;
						fighter.pickup((TwoHandedWeapon) item);
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
	    			if(fighter.isPickupSuccess()) {
	    				room.removeItem(item.getName());
	    				outputLog.printToLog(fighter.titleToString() + " has equipped " + item.titleToString() + ". ");
	    			}
	    		}
	    		catch (UnknownItemException UIE) {
	    			outputLog.printToLog(UIE.getMessage());
	    			return;
				}
			}
    	} else if(playerType == PlayerType.WIZARD) {
    		this.wizard = (Wizard) gameState.getActivePlayer();
	    	this.room = dungeonMap.getRoom(wizard.getPostion());
	    	wizard.resetPickupSuccess();
	    	if(args[0] == null) {
	    		throw new InvalidArgumentException("Choose something to pickup. "
	    				+ "Type \"help\" for help using the " + commandName +" command. ");
	    	}
			Item item = null;
			switch(args[0]) {
			case "items":
				ArrayList<String> equippedItemNames = new ArrayList<String>();;
				for(String itemName : room.getItemList().keySet()) {
					wizard.resetPickupSuccess();
					try {
						item = room.getItem(itemName);
					}
					catch(UnknownItemException UIE) {
						outputLog.printToLog(UIE.getMessage());
					}
		    		try {
		    			if(item instanceof Artifact) {
							item = (Artifact) item;
							wizard.pickup((Artifact) item);
						}
						else if(item instanceof Magical) {
							item = (Magical) item;
							wizard.pickup((Magical) item);
						}
						else if(item instanceof OneHandedWeapon) {
							item = (OneHandedWeapon) item;
							wizard.pickup((OneHandedWeapon) item);
						}
						else if(item instanceof Potion) {
							item = (Potion) item;
							wizard.pickup((Potion) item);
						}
						else if(item instanceof Shield) {
							item = (Shield) item;
							wizard.pickup((Shield) item);
						}
						else if(item instanceof Suit) {
							item = (Suit) item;
							wizard.pickup((Suit) item);
						}
						else if(item instanceof TwoHandedWeapon) {
							item = (TwoHandedWeapon) item;
							wizard.pickup((TwoHandedWeapon) item);
						}
						else {
							outputLog.printToLog(item.getName() + " could not be equipped "
									+ "because it has not item type. ");
						}
		    			if(wizard.isPickupSuccess()) {
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
		    	equippedItemNames.forEach((k) -> outputLog.printToLog(wizard.titleToString() + " has equipped " + k + ". "));
		    	wizard.resetPickupSuccess();
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
						wizard.pickup((Artifact) item);
					}
					else if(item instanceof Magical) {
						item = (Magical) item;
						wizard.pickup((Magical) item);
					}
					else if(item instanceof OneHandedWeapon) {
						item = (OneHandedWeapon) item;
						wizard.pickup((OneHandedWeapon) item);
					}
					else if(item instanceof Potion) {
						item = (Potion) item;
						wizard.pickup((Potion) item);
					}
					else if(item instanceof Shield) {
						item = (Shield) item;
						wizard.pickup((Shield) item);
					}
					else if(item instanceof Suit) {
						item = (Suit) item;
						wizard.pickup((Suit) item);
					}
					else if(item instanceof TwoHandedWeapon) {
						item = (TwoHandedWeapon) item;
						wizard.pickup((TwoHandedWeapon) item);
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
	    			if(wizard.isPickupSuccess()) {
	    				room.removeItem(item.getName());
	    				outputLog.printToLog(wizard.titleToString() + " has equipped " + item.titleToString() + ". ");
	    			}
	    		}
	    		catch (UnknownItemException UIE) {
	    			outputLog.printToLog(UIE.getMessage());
	    			return;
				}
			}
    	}
		outputLog.printToLog("This room now contains the following items:\n" + room.examineItems());
    }
}