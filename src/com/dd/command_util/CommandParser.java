package com.dd.command_util;

import com.dd.GameState;
import com.dd.controller_util.controller.RunningGameController;
import com.dd.entities.*;
import com.dd.exceptions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CommandParser {
    protected Map<String, CommandHandler> commandMap = new HashMap<String, CommandHandler>();
    protected CommandOutputLog output;
    protected String input;
    protected Player player;

    public CommandParser(){}
    
    public CommandParser(CommandOutputLog output, GameState game) {
        this.output = output;
        this.player = game.getActivePlayer();
    }
    
    public void parse(String userInput) throws InvalidCommandException{
    	input = userInput;
    	if(input == "") {
            throw new InvalidCommandException("Please enter a command. ");
        }
    	if(input.charAt(0) == ' ') {
    		throw new InvalidCommandException("You cannot start a command with a space. ");
    	}
    	
    	output.print("\n" + RunningGameController.printLnTitle('~', "", 72));
    	output.print(player.getTitle() + ">> " + input + "\n");
    	output.print(RunningGameController.printLnTitle('~', " Dungeon Master ", 72));
    	
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
    		handler.handleCommand(command, args, output);
    		if(!player.isDead()) {
    			handler.monsterAttack();
    		}
    	}
    	catch (InvalidArgumentException E) {    		
    		output.print(E.getMessage());
    	}
    }
    
    protected boolean hasArg() {
    	String[] inputArray = input.split(" ");
    	if(inputArray.length <= 1){
    		return false;
    	}
    	return true;
    }
    
    protected int argStartIndex() {
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
    
    protected int quoteNum(String arg) {
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
        this.output = outputLog;
    }

    public void setPlayer(Player player){
    	for(CommandHandler cmdHandler : commandMap.values()){
    		cmdHandler.setPlayer(player);
		}
	}
}
