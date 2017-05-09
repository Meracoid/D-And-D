package com.dd.command_util.command.network;

import com.dd.GameState;
import com.dd.command_util.CommandOutputLog;
import com.dd.command_util.command.AttackCommand;
import com.dd.exceptions.InvalidArgumentException;
import com.dd.network.ServerInstructionBuilder;

public class ServerAttackCommand extends AttackCommand{
    public ServerAttackCommand(GameState gameState){
        super(gameState);
    }

    @Override
    public void handleCommand(String commandName, String commandAgrs[], CommandOutputLog outputLog) throws InvalidArgumentException{
        super.handleCommand(commandName, commandAgrs, outputLog);
        outputLog.print(ServerInstructionBuilder.buildSetStatsInstruction(gameState.getActivePlayer().getStats()));
        outputLog.print(ServerInstructionBuilder.buildReleaseInputInstruction());
    }
}
