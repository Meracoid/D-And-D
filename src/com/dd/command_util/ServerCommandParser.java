package com.dd.command_util;

import com.dd.GameState;
import com.dd.exceptions.InvalidArgumentException;
import com.dd.network.GameServer;
import com.dd.network.ServerInstructionBuilder;

import java.util.ArrayList;

public class ServerCommandParser extends CommandParser {
    public ServerCommandParser(ServerCommandOutputLog outputLog, GameState gameState){
        super(outputLog, gameState);
    }

    @Override
    public void parse(String userInput){
        input = userInput;
        if(input == "") {
            output.print(ServerInstructionBuilder.buildPrintMessageInstruction("Please enter a command. "));
            return;
        }
        if(input.charAt(0) == ' ') {
            output.print(ServerInstructionBuilder.buildPrintMessageInstruction("You cannot start a command with a space. "));
            return;
        }

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
                    output.print(ServerInstructionBuilder.buildPrintMessageInstruction("Please check your usage of quotation marks. "));
                    return;
                }
            }
            args = new String[argumentList.size()];
            for(int i = 0; i < argumentList.size(); i++) {
                args[i] = argumentList.get(i);
            }
        }

        CommandHandler handler = commandMap.get(command);
        if(handler == null) {
            output.print(ServerInstructionBuilder.buildPrintMessageInstruction("The command \"" + command + "\" is not registered with the CommandParser."));
            return;
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
}
