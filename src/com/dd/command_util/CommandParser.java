package com.dd.command_util;

import com.dd.GameState;
import com.dd.command_util.CommandHandler;
import com.dd.command_util.CommandHandler.InvalidArgumentException;
import com.dd.controller_util.controller.RunningGameController;
import com.dd.entities.*;
import com.dd.entities.Player.InventoryException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CommandParser {
    private Map<String, CommandHandler> commandMap = new HashMap<String, CommandHandler>();
    private CommandOutputLog outputLog;
    private String input;
    private Wizard wizard;
    private Fighter fighter;
    private PlayerType playerType = PlayerType.NONE; 

    public CommandParser(){}
    
    public CommandParser(CommandOutputLog outputLog, GameState game) {
        this.outputLog = outputLog;
        if(game.getActivePlayer() instanceof Fighter) {
        	fighter = (Fighter) game.getActivePlayer();
        	playerType = playerType.FIGHTER;
        }
        else if(game.getActivePlayer() instanceof Wizard) {
        	wizard = (Wizard) game.getActivePlayer();
        	playerType = PlayerType.WIZARD;
        }
    }
    
    public void parse(String userInput) throws InvalidCommandException{
    	input = userInput;
    	if(input == "") {
            throw new InvalidCommandException("Please enter a command. ");
        }
    	if(input.charAt(0) == ' ') {
    		throw new InvalidCommandException("You cannot start a command with a space. ");
    	}
    	outputLog.printToLog("\n" + RunningGameController.printLnTitle('~', "", 72));
    	if(playerType == PlayerType.FIGHTER) {
    		outputLog.printToLog(fighter.titleToString() + ">> " + input + "\n");
    	}
    	else if(playerType == PlayerType.WIZARD) {
    		outputLog.printToLog(wizard.titleToString() + ">> " + input + "\n");
    	}
    	outputLog.printToLog(RunningGameController.printLnTitle('~', " Dungeon Master ", 72));
    	
    	String command = "";
    	String[] args = {null};
    	if(!hasArg()){
    		command = input;
    	}
    	else {
    		int argStartIndex = argStartIndex(); 
    		command = input.toLowerCase().substring(0, argStartIndex - 1);
    		String argStr = input.substring(argStartIndex);
    		String[] argArray = argStr.split(" ");
    		ArrayList<String> argumentList = new ArrayList<String>();
    		argumentList.add(argStr);
    		for(int i = 0; i < argArray.length; i++) {
    			//no quote token
    			if(quoteNum(argArray[i]) == 0) {
    				argumentList.add(argArray[i]);
    			}
    			//one quote token
    			else if(quoteNum(argArray[i]) == 1) {
    				String arg = argArray[i];
    				for(int j = i + 1; j < argArray.length; j++) {
    					arg = arg + " " + argArray[j];
    					if(quoteNum(argArray[j]) > 0) {
    						i = j;
    						break;
    					}
    				}
    				argumentList.add(arg.substring(1, arg.length() - 1));
    			}
    			//two quote token
    			else if(quoteNum(argArray[i]) == 2) {
    				argumentList.add(argArray[i].substring(1, argArray[i].length() - 1));
    			}
    			else {
    				throw new InvalidCommandException("Please check your useage of quotation marks. ");
    			}
    		}
    		args = new String[argumentList.size()];
    		for(int i = 0; i < argumentList.size(); i++) {
    			args[i] = argumentList.get(i);
    		}
    	}
    	//this code prints parsing to console
//    	System.out.println("Command: " + command);
//    	for(int i = 0; i < args.length; i++) {
//    		System.out.println("args[" + i + "]: " + args[i]);
//    	}
    	//this code prints parsing to console
    	
    	CommandHandler handler = commandMap.get(command);
    	if(handler == null) {
            throw new InvalidCommandException("The command \""
                                                + command
                                                + "\" is not registered with the CommandParser.");
        }
    	try {
    		handler.handleCommand(command, args, outputLog);
    	}
    	catch (InvalidArgumentException | InventoryException E) {    		
    		outputLog.printToLog(E.toString());
    	}
    }
    
    private boolean hasArg() {
    	String[] inputArray = input.split(" ");
    	if(inputArray.length <= 1){
    		return false;
    	}
    	return true;
    }
    
    private int argStartIndex() {
    	for(int i = 0; i < input.length(); i++) {
    		if(input.charAt(i) == ' ') {
    			return i + 1;
    		}
    		if(i == input.length() - 1) {
    			return i;
    		}
    	}
    	return -1; //error occurred
    }
    
    private int quoteNum(String arg) {
    	int quoteNum = 0;
    	for(int i = 0; i <arg.length(); i++) {
    		if(arg.charAt(i) == '"') {
    			quoteNum++;
    		}
    	}
    	return quoteNum;
    }

    public void registerCommand(String commandName, CommandHandler commandHandler) {
        if(commandMap.containsKey(commandName))
            throw new IllegalArgumentException("The command \""
                                                + commandName
                                                + "\" has already been registered with this CommandParser. Registration failed.");
        if(commandHandler == null)
            throw new IllegalArgumentException("The command handler passed for \""
                                                    + commandName
                                                    + "\" is null. Registration failed.");
        //Set game state for handler?
        commandMap.put(commandName, commandHandler);
    }

    public void unregisterCommand(String commandName, CommandHandler commandHandler) {
        if(!commandMap.containsKey(commandName))
            throw new IllegalArgumentException("The command \""
                                                + commandName
                                                + "\" has not been registered with this CommandParser. Un-registration failed.");
    }

    public void setOutputLog(CommandOutputLog outputLog){
        this.outputLog = outputLog;
    }

    public class InvalidCommandException extends Exception {
    	public InvalidCommandException(String message){
    		super(message);
		}
    	
    	@Override
		public String toString() {
			return super.toString().substring(59);
		}
	}
}