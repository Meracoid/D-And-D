package com.dd.command_util.command;

import com.dd.Console;
import com.dd.command_util.CommandHandler;
import com.dd.command_util.CommandOutputLog;
import com.dd.tester.Tester;

public class QuitCommand extends CommandHandler {
    public QuitCommand(){}

    @Override
    public void handleCommand(String[] args, CommandOutputLog outputLog){
    	Console.updateScreen("Thank you for playing! Goodbye.");
    	System.exit(0);
    }
}