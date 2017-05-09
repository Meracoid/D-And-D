package com.dd.command_util;

import com.dd.GameState;
import com.dd.network.GameServer;
import com.dd.network.ServerInstructionBuilder;

public class ServerCommandOutputLog extends CommandOutputLog {
    private GameServer gameServer;
    private GameState gameState;

    public ServerCommandOutputLog(GameServer gameServer, GameState gameState){
        this.gameServer = gameServer;
        this.gameState = gameState;
    }

    @Override
    public void print(String string){
        GameServer.PlayerConnection playerConn = gameServer.getActivePlayerConn();
        System.out.println(string);
        playerConn.appendToOutBuffer(ServerInstructionBuilder.buildPrintMessageInstruction(string));
    }

    @Override
    public void print(byte output[]){
        GameServer.PlayerConnection playerConn = gameServer.getActivePlayerConn();
        playerConn.appendToOutBuffer(output);
    }
}
