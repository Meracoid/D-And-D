package com.dd.command_util.command;

import com.dd.GameState;
import com.dd.command_util.CommandHandler;
import com.dd.command_util.CommandOutputLog;
import com.dd.network.NetworkCommChannel;
import com.dd.network.NetworkCommInterpreter;

public class NetworkHandledCommand extends CommandHandler{
    private NetworkCommChannel channel;
    private NetworkCommInterpreter interpreter;

    public NetworkHandledCommand(NetworkCommChannel channel, NetworkCommInterpreter interpreter, GameState gameState){
        super(gameState);
        this.channel = channel;
        this.interpreter = interpreter;
    }

    @Override
    public void handleCommand(String commandName, String[] args, CommandOutputLog outputLog){

    }
}
