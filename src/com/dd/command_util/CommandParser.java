package com.dd.command_util;

import com.dd.command_util.CommandHandler;
import java.lang.IllegalArgumentException;
import java.util.HashMap;
import java.util.Map;

public class CommandParser {
    private Map<String, CommandHandler> commandMap;

    public CommandParser() {
        commandMap = new HashMap<String, CommandHandler>();
    };

    public void registerCommand(String commandName, CommandHandler commandHandler) {
        if(commandMap.containsKey(commandName))
            throw new IllegalArgumentException("The command \""
                                                + commandName
                                                + "\" has already been registerd with this CommandParser. Registration failed.");
        if(commandHandler == null)
            throw new IllegalArgumentException("The command handler passed for \""
                                                    + commandName
                                                    + "\" is null. Registration failed.");
        commandMap.put(commandName, commandHandler);
    }

    public void unregisterCommand(String commandName, CommandHandler commandHandler) {
        if(!commandMap.containsKey(commandName))
            throw new IllegalArgumentException("The command \""
                                                + commandName
                                                + "\" has not been registered with this CommandParser. Unregistration failed.");
    }

    public void parseCommand(String command, String[] args) {
        CommandHandler handler = commandMap.get(command);
        if(handler == null)
            throw new IllegalArgumentException("The command \""
                                                    + command
                                                    + "\" is invalid.");
        handler.handleCommand(args);
    }
}