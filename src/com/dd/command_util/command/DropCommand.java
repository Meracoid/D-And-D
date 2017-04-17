package com.dd.command_util.command;

import com.dd.GameState;
import com.dd.command_util.CommandHandler;
import com.dd.command_util.CommandOutputLog;
import com.dd.entities.*;
import com.dd.exceptions.*;
import com.dd.items.*;

public class DropCommand extends CommandHandler {
	
	private Item dropItem;

    public DropCommand(GameState gameState) {
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
		player.resetDropSuccess();
		switch (args[0]) {
		case "left hand":
		case "lefthand":
			try {
				dropItem = player.getLeftHand();
				player.drop(Equip.LEFTHAND);
				outputLog.printToLog(player.titleToString() + " has dropped their left hand. ");
			} catch (EquipmentException EE) {
				outputLog.printToLog(EE.getMessage());
			}
			break;
		case "right hand":
		case "righthand":
			try {
				dropItem = player.getRightHand();
				player.drop(Equip.RIGHTHAND);
				outputLog.printToLog(player.titleToString() + " has dropped their right hand. ");
			} catch (EquipmentException EE) {
				outputLog.printToLog(EE.getMessage());
			}
			break;
		case "hands":
			try {
				dropItem = (TwoHandedWeapon) player.getTwoHands();
				player.drop(Equip.HANDS);
				outputLog.printToLog(player.titleToString() + " has dropped both hands. ");
			}
			catch (EquipmentException ee) {
				outputLog.printToLog(ee.getMessage() + "\n");
			}
			break;
		case "suit":
			try {
				dropItem = (Suit) player.getSuitArea();
				player.drop(Equip.SUIT);
				outputLog.printToLog(player.titleToString() + " has dropped their suit. ");
			}
			catch (EquipmentException ee) {
				outputLog.printToLog(ee.getMessage() + "\n");
			}
			break;
		default:
			if(args[1].equals("inventory")) {
				int inventoryNum = Integer.parseInt(args[2]);
				int i = 1;
				for(Item item : player.getInventory().getInventoryMap().values()) {
					if(i == inventoryNum) {
						try {
							dropItem = item;
							if(dropItem instanceof Artifact) {
								dropItem = (Artifact) dropItem;
								//player.discardfromInventory(inventoryName);
								player.removeFromInventory(dropItem);
								outputLog.printToLog(player.titleToString() + " has dropped " + dropItem.titleToString() + " "
										+ "from their inventory. ");
							}
							else if(dropItem instanceof Potion) {
								dropItem = (Potion) dropItem;
								//player.discardfromInventory(inventoryName);
								player.removeFromInventory(dropItem);
								outputLog.printToLog(player.titleToString() + " has dropped " + dropItem.titleToString() + " "
										+ "from their inventory. ");
							}
							else if(dropItem instanceof Magical) {
								dropItem = (Magical) dropItem;
								//player.discardfromInventory(inventoryName);
								player.removeFromInventory(dropItem);
								outputLog.printToLog(player.titleToString() + " has dropped " + dropItem.titleToString() + " "
										+ "from their inventory. ");
							}
							else {
								outputLog.printToLog(dropItem.titleToString() + " is the incorrect type. ");
							}
						}
						catch (InventoryException IE) {
							outputLog.printToLog(IE.getMessage());
						}
					}
					i++;
				}
				break;
			}
			else {
				outputLog.printToLog("The body area \"" + args[0] + "\" is not a valid entry. "
					+ "Type \"help\" for help using the examine command. ");
				break;
			}
		}
		if(player.isDropSuccess()) {
			this.room.addItem(dropItem);
		}
	
	outputLog.printToLog("This room now contains the following items:\n" + this.room.examineItems());
	}
}