package com.dd.network;

import com.dd.GameState;
import com.dd.Stats;
import com.dd.command_util.CommandOutputLog;
import com.dd.dd_util.BitSequence;
import com.dd.entities.Player;
import com.dd.levels.DungeonMap;
import com.dd.levels.MapPosition;

import java.io.IOException;
import java.util.concurrent.Semaphore;

public class ClientInstructionHandler extends Thread{
    private NetworkCommChannel commChannel;
    private ClientInputController clientInputController;
    private GameState gameState;
    private Player player;
    private CommandOutputLog outputLog;

    private static final int MOVE_PLAYER_ID = 0x00;
    private static final int PRINT_MESSAGE_ID = 0x01;
    private static final int SET_STATS_ID = 0x02;
    private static final int RELEASE_CONTROL_ID = 0x0f;

    private static final int NORTH_ID = 0x00;
    private static final int SOUTH_ID = 0x01;
    private static final int WEST_ID = 0x02;
    private static final int EAST_ID = 0x03;

    public ClientInstructionHandler(NetworkCommChannel commChannel, GameState gameState, ClientInputController clientInputController, CommandOutputLog outputLog){
        this.commChannel = commChannel;
        this.gameState = gameState;
        this.clientInputController = clientInputController;
        this.outputLog = outputLog;
        player = gameState.getActivePlayer();
    }

    public void run(){
        byte message[] = null;
        BitSequence messageSequence = null;

        while(true) {
            clientInputController.listenerTakeControl();
            try {
                message = commChannel.read();
                messageSequence = new BitSequence(message.length * 8, message);
            } catch (IOException e) {
                System.exit(1);
            }
            while(true) {
                int instrCode = 0;
                try {
                    instrCode = messageSequence.getNextBits(8).getAsInt();
                }
                catch(Exception e){
                    clientInputController.listenerReleaseControl();
                    break;
                }
                if(instrCode == MOVE_PLAYER_ID) {
                    int direction = messageSequence.getNextBits(8).getAsInt();
                    MapPosition pos = player.getPostion();
                    switch (direction) {
                        case NORTH_ID:
                            pos.moveNorth();
                            break;
                        case SOUTH_ID:
                            pos.moveSouth();
                            break;
                        case WEST_ID:
                            pos.moveWest();
                            break;
                        case EAST_ID:
                            pos.moveEast();
                            break;
                    }
                }
                else if(instrCode == PRINT_MESSAGE_ID) {
                    int numChar = messageSequence.getNextBits(16).getAsInt();
                    String outputStr = messageSequence.getNextBits(numChar * 16).getAsString();
                    outputLog.print(outputStr);
                }
                else if(instrCode == SET_STATS_ID) {
                    int health = messageSequence.getNextBits(8).getAsInt();
                    int maxHealth = messageSequence.getNextBits(8).getAsInt();
                    int attack = messageSequence.getNextBits(8).getAsInt();
                    int defense = messageSequence.getNextBits(8).getAsInt();
                    player.setStats(new Stats(health, maxHealth, attack, defense));
                }
                else if(instrCode == RELEASE_CONTROL_ID){
                    clientInputController.listenerReleaseControl();
                    break;
                }
            }
        }
    }
}
