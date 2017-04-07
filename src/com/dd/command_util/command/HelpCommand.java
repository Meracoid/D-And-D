package com.dd.command_util.command;

import com.dd.command_util.CommandHandler;
import com.dd.command_util.CommandOutputLog;
import com.dd.controller_util.controller.RunningGameController;

public class HelpCommand extends CommandHandler {
    public HelpCommand() {}
	
	@Override
	public void handleCommand(String commandName, String[] args, CommandOutputLog outputLog){
    	if(args.length != 0){
			outputLog.printToLog("Invalid arguments \""
					+ getArgsString(args)
					+ "\" passed to help command.");
		}

		outputLog.printToLog(RunningGameController.printLnTitle('~', "AVAILABLE COMMANDS", 80)
				+ "\"attack <entity_name>\"\n"
				+ "Initiate an attack against the entity with name entity_name.\n"
				+ "\n"
				+ "\"move <direction>\"\n"
				+ "\n"
				+ "Move the player to the room in the specified direction of the room the "
				+ "player is currently in. If no room exists in the specified direction this "
				+ "will fail. The valid directions are: north, south, east, and west.\n"
				+ "\n"
				+ "\"examine room | monsters | items | <name>\"\n"
				+ "Get a description of either an entity by name, and item by name, or the room "
				+ "the player is currently in, or a list of monsters or items.\n"
				+ "\n"
				+ "\"use <item name>\"\n"
				+ "Use an item with the name item_name to receive its effects. If the item specified "
				+ "is not a usable item this will fail.\n"
				+ "\n"
				+ "\"pickup <item name> | items\"\n"
				+ "pickup an item with the name and attempt to equip it to the player or add it to "
				+ "their inventory. use argument \"items\" to attempt to equip all the items in room\n"
				+ "\n"
				+ "\"drop <item name>\"\n"
				+ "Remove an item with the name item name from the players inventory. The item will "
				+ "be placed in the room the player is currently in.");
	}
}