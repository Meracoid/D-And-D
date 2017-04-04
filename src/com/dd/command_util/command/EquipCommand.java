package com.dd.command_util.command;

import com.dd.GameState;
import com.dd.command_util.CommandHandler;
import com.dd.command_util.CommandOutputLog;
import com.dd.entities.Player;
import com.dd.entities.Player.EquipmentException;
import com.dd.entities.Player.InventoryException;
import com.dd.items.*;
import com.dd.levels.DungeonMap;
import com.dd.levels.Room;
import com.dd.levels.Room.UnknownItemException;

public class EquipCommand extends CommandHandler {
	private Player player;
	private DungeonMap dungeonMap;

    public EquipCommand(GameState gameState) {
    	this.player = gameState.getActivePlayer();
		this.dungeonMap = gameState.getMap();
	}

    @Override
    public void handleCommand(String commandName, String[] args, CommandOutputLog outputLog) {
		if(args[0] != null) {
	    	Room room = dungeonMap.getRoom(player.getPostion());
	    	Item item = room.getItem(args[0]);
	    	if(item != null){
	    		try{
	    			player.equip(item);
	    			room.removeItem(item.getName());
	    			outputLog.printToLog(player.getName() + " has equipped " + item.getName() + "\n");
	    		}
	    		catch(EquipmentException ee) {
	    			outputLog.printToLog(ee.toString() + "\n");
	    		}
	    		catch(InventoryException ie) {
	    			outputLog.printToLog(ie.toString() + "\n");
	    		} catch (UnknownItemException UIE) {
					outputLog.printToLog(UIE.toString() + "\n");
				}
	    	}
	    	else {
	    		outputLog.printToLog("The item \"" + args[0] + "\" is not in this room.\n");
	    	}
	    	outputLog.printToLog(room.examineString());
		}
		else {
			outputLog.printToLog("Type \"help\" for help using the equip command.\n");
		}
    }
}