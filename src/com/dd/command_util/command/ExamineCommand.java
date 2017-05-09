package com.dd.command_util.command;

import com.dd.GameState;
import com.dd.command_util.CommandHandler;
import com.dd.command_util.CommandOutputLog;
import com.dd.command_util.LocalCommandOutputLog;
import com.dd.exceptions.*;
import com.dd.items.Item;

public class ExamineCommand extends CommandHandler {

    public ExamineCommand(GameState gameState) {
    	super(gameState);
	}

    @Override
    public void handleCommand(String commandName, String[] args, CommandOutputLog output) throws InvalidArgumentException {
    	setGlobalOutput(output);
    	updateState();
    	if(dead){
    		output.print(player.getTitle() + " is dead. ");
    		return;
    	}
    	if(args[0] == null) {
    		throw new InvalidArgumentException("Choose something to " + commandName + ". "
    				+ "Type \"help\" for help using the " + commandName +" command. ");
    	}
    	
    	switch(args[0].toLowerCase()) {
    	case "room":
    		output.print(room.examineRoom());
    		examineMonster = false;
			break;
    	case "monsters":
		case "monster":
			output.print(room.examineMonster());
			examineMonster = false;
			break;
		case "item":
		case "items":
			output.print(room.examineItems());
			examineMonster = false;
			break;
		default:
			try{
				Item item = room.getItem(args[0]);
				output.print(item.getTitle() + " "
						+ item.examineToString() + "\n");
				examineMonster = false;
			}
			catch(NullItemException UIE) {
				output.print(UIE.getMessage());
			}
			/*
			 * examine <monster name> command - not needed if one monster.
			 */
//			try{
//				Monster monster = room.getMonster(args[0]);
//				outputLog.printToLog(
//						monster.titleToString() +". "
//						+ "\nHealth: " + monster.getStats().getHealth()
//						+ "\nAttack/Defense: " + monster.getStats().getAttack() + "/" + monster.getStats().getDefense()
//						+ monster.examineText());
//			}
//			catch(NullMonsterException NME) {
//				outputLog.printToLog(NME.getMessage());
//			}
    	}
	}
}