package com.dd.command_util.command;

import com.dd.GameState;
import com.dd.command_util.CommandHandler;
import com.dd.command_util.CommandOutputLog;
import com.dd.entities.Monster;
import com.dd.exceptions.*;
import com.dd.items.Item;

public class ExamineCommand extends CommandHandler {

    public ExamineCommand(GameState gameState) {
    	super(gameState);
	}

    @Override
    public void handleCommand(String commandName, String[] args, CommandOutputLog outputLog) throws InvalidArgumentException {
    	if(args[0] == null) {
    		throw new InvalidArgumentException("Choose something to " + commandName + ". "
    				+ "Type \"help\" for help using the " + commandName +" command. ");
    	}
		updateState();
    	switch(args[0].toLowerCase()) {
    	case "room":
			outputLog.printToLog(room.enterRoomText());
			break;
    	case "monsters":
		case "monster":
			if(room.hasMonster()) {
				room.getMonsterList().values().forEach((v) -> outputLog.printToLog(
						v.titleToString()
						+ "\nHealth: " + v.getStats().getHealth()
						+ "\nAttack/Defense: " + v.getStats().getAttack() + "/" + v.getStats().getDefense()
						+ "\n" + v.examineText()));
			}
			else {
				outputLog.printToLog("There are no monsters in this room. ");
			}
			break;
		case "item":
		case "items":
			if(room.hasItems()) {
				room.getItemList().values().forEach((v) -> outputLog.printToLog(
						v.titleToString() + " "
						+ v.examineToString() + "\n"));
			}
			else {
				outputLog.printToLog("There are no items in this room. ");
			}
			break;
		
		default:
			if(room.getMonster(args[0]) != null) {
				Monster monster = room.getMonster(args[0]);
				outputLog.printToLog(
						monster.titleToString() +". "
						+ "\nHealth: " + monster.getStats().getHealth()
						+ "\nAttack/Defense: " + monster.getStats().getAttack() + "/" + monster.getStats().getDefense()
						+ monster.examineText());
			}
			try{
				Item item = room.getItem(args[0]);
				outputLog.printToLog(item.titleToString() + " "
						+ item.examineToString() + "\n");
				break;
			}
			catch(UnknownItemException UIE) {
				outputLog.printToLog(UIE.toString());
    			return;
			}
    	}
	}
}