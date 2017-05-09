package com.dd.command_util.command.network;

import com.dd.GameState;
import com.dd.command_util.CommandOutputLog;
import com.dd.command_util.command.ExamineCommand;
import com.dd.exceptions.InvalidArgumentException;
import com.dd.network.ServerInstructionBuilder;

public class ServerExamineCommand extends ExamineCommand{
    public ServerExamineCommand(GameState gameState){
        super(gameState);
    }

    @Override
    public void handleCommand(String commandName, String commandAgrs[], CommandOutputLog outputLog) throws InvalidArgumentException {
        super.handleCommand(commandName, commandAgrs, outputLog);
        outputLog.print(ServerInstructionBuilder.buildReleaseInputInstruction());
    }
}
