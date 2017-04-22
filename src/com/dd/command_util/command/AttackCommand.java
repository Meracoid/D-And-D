package com.dd.command_util.command;

import com.dd.GameState;
import com.dd.command_util.CommandHandler;
import com.dd.command_util.CommandOutputLog;
import com.dd.entities.*;
import com.dd.exceptions.*;

public class AttackCommand extends CommandHandler {

    public AttackCommand(GameState gameState){
    	super(gameState);
    }

    @Override
    public void handleCommand(String commandName, String[] args, CommandOutputLog outputLog) throws InvalidArgumentException{
    	if(args[0] != null) {
    		throw new InvalidArgumentException(commandName + " command does not require an argument. ");
    	}		
		Player player = updateState();
		Monster monster = null;
		try{
			monster = room.getMonster();
			player.clearText();
			player.attack(monster);
			outputLog.printToLog(player.getText());
			player.clearText();
		}
		catch(NullMonsterException UME) {
			outputLog.printToLog(UME.getMessage());
		}
		if(room.hasMonster()) {
			room.getMonsterMap().values().forEach((v) -> outputLog.printToLog(
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