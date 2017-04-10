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
    private String playerClass;

    public CommandParser(){}
    
    public CommandParser(CommandOutputLog outputLog, String playerName, String playerClass) {
        this.outputLog = outputLog;
        this.playerName = playerName;
        this.playerClass = playerClass;
    }
    
    public void parse(String userInput) throws InvalidCommandException, FileNotFoundException{
    	if(userInput == null) {
            throw new IllegalArgumentException();
        }
    	outputLog.printToLog("\n" + RunningGameController.printLnTitle('~', "", 80));
    	outputLog.printToLog(playerClass + " " + playerName + ">> " + userInput + "\n");
    	outputLog.printToLog(RunningGameController.printLnTitle('~', " Dungeon Master ", 80));
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
    
/* 
 * Kyle, there are two versions. you'll probably just start from scratch, but here they are:
 * 
 * newer version: hardly works:
 
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
    	int quoteBegIndex = 0;
    	int quoteEndIndex = 0;
    	//int argNum=0;
    	int cmdIndex = 0;
    	if(commandStr.length > 1) { //if there are arguments
    		String argStr = userInput.substring(commandStr[cmdIndex].length() + 1); //this string is everything after command
	    	for(int i = 0; i < argStr.length() - 1; i++) {
	    		if(argStr.charAt(i) == '\"') {
	    			quoteBegIndex = i + 1;
	    			quoteEndIndex = quoteBegIndex + 1;
	    			while(quoteEndIndex < argStr.length() && argStr.charAt(quoteEndIndex) != '\"') {
	    				quoteEndIndex++;
	    				i++;
	    			}
	    			arguments.add(argStr.substring(quoteBegIndex, quoteEndIndex));
	    			//argNum++;
	    			argStr = argStr.substring(quoteEndIndex + 1);
	    			commandStr = argStr.split(" ");
	    			cmdIndex = 0;
	    			i = 0;
		    	}else {
	    			if(commandStr.length > 1 && argStr.length() > 0) {
	    				arguments.add(commandStr[cmdIndex]);
	    				argStr = argStr.substring(commandStr[cmdIndex].length() + 1);
	    				cmdIndex++;
	    			}
	    		}
	    	}
    		for(int i = 0; i < commandStr.length; i++) {
    			arguments.add(commandStr[i]);
    		}
    	}
	    String[] args = new String[arguments.size()];
	    for(int i=0; i < arguments.size(); i++) {
	    	args[i] = arguments.get(i);
	    }
	    
	    //TEST STUFF DELETE ME!!!
	    System.out.println("command: " + command);
	    for(int i = 0; i < args.length; i++){
	    	System.out.println("args[" + i + "]: " + args[i]);
	    }
	    //DELETE ME!!!
    	CommandHandler handler = commandMap.get(command);
    	if(handler == null) {
            throw new InvalidCommandException("The command \""
                                                + command
                                                + "\" is not registered with the CommandParser.");
        }
    	handler.handleCommand(command, args, outputLog);
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
    	int quoteBegIndex = 0;
    	int quoteEndIndex = 0;
    	if(commandStr.length > 1) { //if there are arguments
    		String argStr = userInput.substring(command.length() + 1);
    		for(int i = 0;i<argStr.length(); i++) {
    			if(argStr.charAt(quoteBegIndex) == '\"') {
    				while(quoteEndIndex < argStr.length() && argStr.charAt(quoteEndIndex) != '\"') {
    					quoteEndIndex++;
    				}
    				arguments.add(argStr.substring(i + 1, quoteEndIndex));
    			}
    			argStr = argStr.substring(quoteEndIndex + 1);
    			commandStr = argStr.split(" ");
    		}
    		for(int i = 0; i < commandStr.length; i++) {
    			arguments.add(commandStr[i]);
    		}
    	}
	    String[] args = new String[arguments.size()];
	    for(int i=0; i < arguments.size(); i++) {
	    	args[i] = arguments.get(i);
	    }
	    
	    //TEST STUFF DELETE ME!!!
	    System.out.println("command: " + command);
	    for(int i = 0; i < args.length; i++){
	    	System.out.println("args[" + i + "]: " + args[i]);
	    }
	    //DELETE ME!!!
    	CommandHandler handler = commandMap.get(command);
    	if(handler == null) {
            throw new InvalidCommandException("The command \""
                                                + command
                                                + "\" is not registered with the CommandParser.");
        }
    	handler.handleCommand(command, args, outputLog);
	}
	*/

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