package com.dd.command_util.command;

import com.dd.GameState;
import com.dd.command_util.CommandHandler;
import com.dd.command_util.CommandOutputLog;
import com.dd.entities.Player;
import com.dd.entities.Equip;
import com.dd.entities.Player.EquipmentException;
import com.dd.levels.DungeonMap;
import com.dd.levels.Room;

public class DropCommand extends CommandHandler {
	private Player player;
	private DungeonMap map;

    public DropCommand(GameState gameState) {
    	player = gameState.getActivePlayer();
    	map = gameState.getMap();
	}

	@Override
	public void handleCommand(String commandName, String[] args, CommandOutputLog outputLog){
		Room room = map.getRoom(player.getPostion());
		switch (args[0]) {
		case "left hand":
		case "lefthand":
			try {
				room.addItem(player.getLeftHand());
				player.discardEquipment(Equip.LEFTHAND);
				outputLog.printToLog(player.titleToString() + " has dropped their left hand. ");
			} catch (EquipmentException ee) {
				outputLog.printToLog(ee.toString());
			}
			break;
		case "right hand":
		case "righthand":
			try {
				room.addItem(player.getRightHand());
				player.discardEquipment(Equip.RIGHTHAND);
				outputLog.printToLog(player.titleToString() + " has dropped their right hand. ");
			} catch (EquipmentException ee) {
				outputLog.printToLog(ee.toString());
			}
			break;
		case "hands":
			try {
				room.addItem(player.getLeftHand());
				player.discardEquipment(Equip.HANDS);
				outputLog.printToLog(player.getName() + " has dropped both hands. ");
			} catch (EquipmentException ee) {
				outputLog.printToLog(ee.toString() + "\n");
			}
			break;
		case "suit":
			try {
				room.addItem(player.getSuit());
				player.discardEquipment(Equip.SUIT);
				outputLog.printToLog(player.getName() + " has dropped their suit. ");
			} catch (EquipmentException ee) {
				outputLog.printToLog(ee.toString() + "\n");
			}
			break;
		default:
			outputLog.printToLog("The body area \"" + args[0] + "\" is not a valid entry. "
					+ "Type \"help\" for help using the examine command. ");
		}
		outputLog.printToLog(room.examineString());
	}
}