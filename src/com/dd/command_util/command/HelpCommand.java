package com.dd.command_util.command;

import com.dd.command_util.CommandHandler;
import com.dd.command_util.CommandOutputLog;
import com.dd.controller_util.controller.RunningGameController;

public class HelpCommand extends CommandHandler {
    public HelpCommand() {}
	
	@Override
	public void handleCommand(String commandName, String[] args, CommandOutputLog outputLog) throws InvalidArgumentException{
    	if(args[0] != null){
			throw new InvalidArgumentException("The " + commandName + " command should not be followed by any arguments. ");
		}

		outputLog.printToLog(RunningGameController.printLnTitle('~', "AVAILABLE COMMANDS", 72)
				+ "\"attack <name>\"\n"
				+ "Initiate an attack against a monster or player.\n"
				+ "\n"
				+ "\"move <direction>\"\n"
				+ "Move the player to the room in the specified direction of the room the "
				+ "player is currently in. If no room exists in the specified direction this "
				+ "will fail. The valid directions are: north, south, east, and west.\n"
				+ "\n"
				+ "\"examine room | monsters | items | <name>\"\n"
				+ "Get a description of the either the room, a monster by name, an item by name, or the room "
				+ "the player is currently in, or a list of monsters or items.\n"
				+ "\n"
				+ "\"use <item name>\"\n"
				+ "Use an item to receive its effects. If the item specified is not a "
				+ "usable item this will fail.\n"
				+ "\n"
				+ "\"pickup <item name> | items\"\n"
				+ "pickup an item with the name and attempt to equip it to the player or add it to "
				+ "their inventory. Use argument \"items\" to attempt to equip all the items in the room\n"
				+ "\n"
				+ "\"drop <body location>\"\n"
				+ "Remove an item from the given body location. For items in the inventory, please "
				+ "provide an number to indicate which inventory item. The dropped item will be "
				+ "placed in the room the player is currently in. ");
	}
}