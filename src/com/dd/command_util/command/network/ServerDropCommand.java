package com.dd.command_util.command.network;

import com.dd.GameState;
import com.dd.command_util.CommandOutputLog;
import com.dd.command_util.command.DropCommand;
import com.dd.exceptions.InvalidArgumentException;
import com.dd.network.ServerInstructionBuilder;

public class ServerDropCommand extends DropCommand {
    public ServerDropCommand(GameState gameState){
        super(gameState);
    }

    @Override
    public void handleCommand(String commandName, String commandAgrs[], CommandOutputLog outputLog) throws InvalidArgumentException {
        super.handleCommand(commandName, commandAgrs, outputLog);
        outputLog.print(ServerInstructionBuilder.buildSetStatsInstruction(gameState.getActivePlayer().getStats()));
        outputLog.print(ServerInstructionBuilder.buildReleaseInputInstruction());
    }
}
