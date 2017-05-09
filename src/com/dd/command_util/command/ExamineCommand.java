package com.dd.command_util.command;

import com.dd.GameState;
import com.dd.command_util.CommandHandler;
import com.dd.exceptions.*;
import com.dd.items.Item;

public class ExamineCommand extends CommandHandler {

    public ExamineCommand(GameState gameState) {
    	super(gameState);
	}

    @Override
    public void handleCommand(String commandName, String[] args) throws InvalidArgumentException {
    	if(isDead()){
    		print(player().getTitle() + " is dead. ");
    		return;
    	}
    	if(args[0] == null) {
    		throw new InvalidArgumentException("Choose something to " + commandName + ". "
    				+ "Type \"help\" for help using the " + commandName +" command. ");
    	}
    	
    	switch(args[0].toLowerCase()) {
    	case "room":
    		print(room().examineRoom());
			break;
    	case "monsters":
		case "monster":
			print(room().examineMonster());
			examineMonster = false;
			break;
		case "item":
		case "items":
			print(room().examineItems());
			break;
		default:
			try{
				Item item = room().getItem(args[0]);
				print(item.getTitle() + " "
						+ item.examineToString() + "\n");
			}
			catch(NullItemException UIE) {
				print(UIE.getMessage());
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