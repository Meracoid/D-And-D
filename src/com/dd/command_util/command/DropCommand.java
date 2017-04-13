package com.dd.command_util.command;

import com.dd.GameState;
import com.dd.command_util.CommandHandler;
import com.dd.command_util.CommandOutputLog;
import com.dd.command_util.CommandHandler.InvalidArgumentException;
import com.dd.entities.Player;
import com.dd.entities.Equip;
import com.dd.entities.ItemType;
import com.dd.entities.Player.EquipmentException;
import com.dd.entities.Player.InventoryException;
import com.dd.items.Artifact;
import com.dd.items.Item;
import com.dd.items.Magical;
import com.dd.items.OneHandedWeapon;
import com.dd.items.Potion;
import com.dd.items.Shield;
import com.dd.items.Suit;
import com.dd.items.TwoHandedWeapon;
import com.dd.levels.DungeonMap;
import com.dd.levels.Room;

public class DropCommand extends CommandHandler {
	private GameState gameState;
	private Player player;
	private DungeonMap map;
	private Room currRoom;
	private Item dropItem;

    public DropCommand(GameState gameState) {
    	this.gameState = gameState;
    	this.player = gameState.getActivePlayer();
    	this.map = gameState.getMap();
	}

	@Override
	public void handleCommand(String commandName, String[] args, CommandOutputLog outputLog) throws InvalidArgumentException {
		this.player = gameState.getActivePlayer();
		this.currRoom = map.getRoom(player.getPostion());
		player.resetDropSuccess();
		if(args[0] == null) {
    		throw new InvalidArgumentException("Choose something to pickup. "
    				+ "Type \"help\" for help using the " + commandName +" command. ");
    	}
		switch (args[0]) {
		case "left hand":
		case "lefthand":
			try {
				if(player.getLeftHandType() == ItemType.ONEHANDEDWEAPON) {
					dropItem = (OneHandedWeapon) player.getLeftHand();
					player.drop(Equip.LEFTHAND);
					outputLog.printToLog(player.titleToString() + " has dropped their left hand. ");
				}
				else if(player.getLeftHandType() == ItemType.SHIELD) {
					dropItem = (Shield) player.getLeftHand();
					player.drop(Equip.LEFTHAND);
					outputLog.printToLog(player.titleToString() + " has dropped their left hand. ");
				}
				else if(player.getLeftHandType() == ItemType.MAGICAL) {
					dropItem = (Magical) player.getLeftHand();
					player.drop(Equip.LEFTHAND);
					outputLog.printToLog(player.titleToString() + " has dropped their left hand. ");
				}
				else {
					outputLog.printToLog(dropItem.titleToString() + " is the incorrect type. ");
				}
			} catch (EquipmentException ee) {
				outputLog.printToLog(ee.getMessage());
			}
			break;
		case "right hand":
		case "righthand":
			try {
				if(player.getRightHandType() == ItemType.ONEHANDEDWEAPON) {
					dropItem = (OneHandedWeapon) player.getRightHand();
					player.drop(Equip.RIGHTHAND);
					outputLog.printToLog(player.titleToString() + " has dropped their right hand. ");
				}
				else if(player.getRightHandType() == ItemType.SHIELD) {
					dropItem = (Shield) player.getRightHand();
					player.drop(Equip.RIGHTHAND);
					outputLog.printToLog(player.titleToString() + " has dropped their right hand. ");
				}
				else if(player.getRightHandType() == ItemType.MAGICAL) {
					dropItem = (Magical) player.getRightHand();
					player.drop(Equip.RIGHTHAND);
					outputLog.printToLog(player.titleToString() + " has dropped their right hand. ");
				}
				else {
					outputLog.printToLog(dropItem.titleToString() + " is the incorrect type. ");
				}
			} catch (EquipmentException ee) {
				outputLog.printToLog(ee.getMessage());
			}
			break;
		case "hands":
			try {
				if(player.getHandsType() == ItemType.TWOHANDEDWEAPON) {
					dropItem = (TwoHandedWeapon) player.getHands();
					player.drop(Equip.HANDS);
					outputLog.printToLog(player.titleToString() + " has dropped both hands. ");
				}
				else {
					outputLog.printToLog(dropItem.titleToString() + " is the incorrect type. ");
				}
			}
			catch (EquipmentException ee) {
				outputLog.printToLog(ee.getMessage() + "\n");
			}
			break;
		case "suit":
			try {
				if(player.getSuitType() == ItemType.SUIT) {
					dropItem = (Suit) player.getSuit();
					player.drop(Equip.SUIT);
					outputLog.printToLog(player.titleToString() + " has dropped their suit. ");
				}
				else {
					outputLog.printToLog(dropItem.titleToString() + " is the incorrect type. ");
				}
			}
			catch (EquipmentException ee) {
				outputLog.printToLog(ee.getMessage() + "\n");
			}
			break;
		default:
			if(args[1].equals("inventory")) {
				int inventoryNum = Integer.parseInt(args[2]);
				int i = 1;
				for(String inventoryName : player.getInventory().keySet()) {
					if(i == inventoryNum) {
						try {
							dropItem = player.getInventory().get(inventoryName);
							if(dropItem instanceof Artifact) {
								dropItem = (Artifact) dropItem;
								//player.discardfromInventory(inventoryName);
								player.discardfromInventory(dropItem);
								outputLog.printToLog(player.titleToString() + " has dropped " + dropItem.titleToString() + " "
										+ "from their inventory. ");
							}
							else if(dropItem instanceof Potion) {
								dropItem = (Potion) dropItem;
								//player.discardfromInventory(inventoryName);
								player.discardfromInventory(dropItem);
								outputLog.printToLog(player.titleToString() + " has dropped " + dropItem.titleToString() + " "
										+ "from their inventory. ");
							}
							else if(dropItem instanceof Magical) {
								dropItem = (Magical) dropItem;
								//player.discardfromInventory(inventoryName);
								player.discardfromInventory(dropItem);
								outputLog.printToLog(player.titleToString() + " has dropped " + dropItem.titleToString() + " "
										+ "from their inventory. ");
							}
							else {
								outputLog.printToLog(dropItem.titleToString() + " is the incorrect type. ");
							}
						}
						catch (InventoryException ie) {
							outputLog.printToLog(ie.getMessage());
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
			currRoom.addItem(dropItem);
			player.changeStats(dropItem.getNegStatChange());
		}
		outputLog.printToLog("This room now contains the following items:\n" + this.currRoom.examineItems());
	}
}