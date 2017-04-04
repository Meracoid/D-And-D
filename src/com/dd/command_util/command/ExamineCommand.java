package com.dd.command_util.command;

import com.dd.GameState;
import com.dd.command_util.CommandHandler;
import com.dd.command_util.CommandOutputLog;
import com.dd.entities.Monster;
import com.dd.entities.Player;
import com.dd.items.Item;
import com.dd.levels.DungeonMap;
import com.dd.levels.Room;

public class ExamineCommand extends CommandHandler {
	private Player player;
	private DungeonMap map;

    public ExamineCommand(GameState gameState) {
    	player = gameState.getActivePlayer();
    	map = gameState.getMap();
	}

    @Override
    public void handleCommand(String commandName, String[] args, CommandOutputLog outputLog){
    	if(player.isinDungeon()) {
	    	Room room = map.getRoom(player.getPostion());
	    	switch(args[0]) {
	    	case "room":
				outputLog.printToLog(room.examineString());
				break;
	    	case "monsters":
			case "monster":
				if(room.hasMonster()) {
					for(String monsterName : room.getMonsterList()) {
						Monster monster = room.getMonster(monsterName);
						outputLog.printToLog("~" + monsterName
								+ "\nHealth: " + monster.getStats().getHealth()
								+ "\nAttack/Defense: " + monster.getStats().getAttack() + "/" + monster.getStats().getDefense()
								+ "\n\n" + monster.getDescription() + "\n");
					}
				}
				else {
					outputLog.printToLog("There are no monsters in this room.");
				}
				break;
			case "item":
			case "items":
				if(room.hasItems()) {
					for(String itemName : room.getItemList()) {
						Item item = room.getItem(itemName);
						outputLog.printToLog("~" + itemName + " "
								+ item.examineToString() + "\n");
					}
				}
				else {
					outputLog.printToLog("There are no items in this room");
				}
				break;
			
			default:
				if(room.getMonster(args[0]) != null) {
					Monster monster = room.getMonster(args[0]);
					String monsterName = monster.getName();
					outputLog.printToLog("~" + monsterName
							+ "\nHealth: " + monster.getStats().getHealth()
							+ "\nAttack/Defense: " + monster.getStats().getAttack() + "/" + monster.getStats().getDefense()
							+ "\n\n" + monster.getDescription() + "\n");
					break;
				}
				if(room.getItem(args[0]) != null) {
					Item item = room.getItem(args[0]);
					String itemName = item.getName();
					outputLog.printToLog("~" + itemName + " "
							+ item.examineToString() + "\n");
					break;
				}
				else{
					outputLog.printToLog("The argument \"" + args[0] + "\" is invalid.\n"
	        			+ "Type \"help\" for help using the examine command.\n");
				}
				break;
	    	}
    	}
    	else {
    		outputLog.printToLog("Please enter the Dungeon first.\n");
    	}
    }
}