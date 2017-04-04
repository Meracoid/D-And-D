package com.dd.command_util;

import com.dd.command_util.CommandHandler;
import com.dd.controller_util.controller.RunningGameController;
import com.dd.entities.Player;

import java.io.FileNotFoundException;
import java.lang.IllegalArgumentException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CommandParser {
    private Map<String, CommandHandler> commandMap = new HashMap<String, CommandHandler>();
    private CommandOutputLog outputLog;
    private String playerName;

    public CommandParser(){}
    
    public CommandParser(CommandOutputLog outputLog, String playerName) {
        this.outputLog = outputLog;
        this.playerName = playerName;
    }
    
    public void parse(String userInput) throws InvalidCommandException, FileNotFoundException{
    	if(userInput == null) {
            throw new IllegalArgumentException();
        }
    	outputLog.printToLog(RunningGameController.printLnTitle('~', "", 80));
    	outputLog.printToLog(playerName + ">> " + userInput + "\n");
    	outputLog.printToLog(RunningGameController.printLnTitle('~', "Dungeon Master", 80));
    	String commandStr[] = userInput.split(" ");
    	String command = commandStr[0];
    	ArrayList<String> arguments = new ArrayList<String>();
    	if(commandStr.length > 1) {
    		arguments.add(userInput.substring(command.length() + 1));
    		for(int i = 0; i < commandStr.length - 1; i++) {
    			arguments.add(commandStr[i + 1]);
    		}
    	}
	    String[] args = new String[arguments.size()];
	    for(int i=0; i < arguments.size(); i++) {
	    	args[i] = arguments.get(i);
	    }
    	CommandHandler handler = commandMap.get(command);
    	if(handler == null) {
            throw new InvalidCommandException("The command \""
                                                + command
                                                + "\" is not registered with the CommandParser.");
        }
    	handler.handleCommand(command, args, outputLog);
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
    	InvalidCommandException(String message){
    		super(message);
		}
	}
}