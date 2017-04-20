package com.dd.command_util.command;

import com.dd.GameState;
import com.dd.command_util.CommandHandler;
import com.dd.command_util.CommandOutputLog;
import com.dd.entities.Monster;
import com.dd.entities.Player;
import com.dd.exceptions.*;
import com.dd.items.Item;
import com.dd.items.Potion;

public class UseCommand extends CommandHandler {
    
	public UseCommand(GameState gameState) {
		super(gameState);
    }

    @Override
    public void handleCommand(String commandName, String[] args, CommandOutputLog outputLog) throws InvalidArgumentException {
    	if(args[0] == null) {
    		throw new InvalidArgumentException("Choose something to " + commandName + ". "
    				+ "Type \"help\" for help using the " + commandName +" command. ");
    	}
		Player player = updateState();
    	Item item = null;
    	if(player.getInventory().getInventoryMap().containsKey(args[0])) {
    		item = player.getInventory().getInventoryMap().get(args[0]);
    	}
    	else {
    		if(room.getItemMap().containsKey(args[0])) {
        		item = room.getItemMap().get(args[0]);
        	}
        	else {
        		outputLog.printToLog("this room does not conatain \""
        				+ args[0] + "\". ");
        		return;
        	}
    	}
    	if(item instanceof Potion) {
			try {
				player.usePotionFromInventory((Potion) item);
				outputLog.printToLog(player.titleToString() + " has used " + item.titleToString() + ". ");
			} catch (EquipmentException EE) {
				outputLog.printToLog(EE.getMessage());
			}
    	}
    	else {
    		outputLog.printToLog(item.titleToString() + " is not a Potion. ");
    		return;
    	}
    	if(room.hasMonster()) {
    		Monster monster = room.getMonster();
			room.getMonsterList().values().forEach((v) -> outputLog.printToLog(
					v.titleToString()
					+ "\nHealth: " + v.getStats().getHealth()
					+ "\nAttack/Defense: " + v.getStats().getAttack() + "/" + v.getStats().getDefense()
					+ "\n" + v.examineText()));
			monster.attack(player);
			outputLog.printToLog(player.getText());
		}
		else {
			outputLog.printToLog("There are no monsters in this room. ");
		}
    }
}