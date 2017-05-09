package com.dd.network;

import com.dd.Stats;
import com.dd.dd_util.BitPattern;
import com.dd.entities.Player;
import com.dd.entities.players.Fighter;
import com.dd.levels.Direction;
import com.dd.levels.DungeonMap;
import com.dd.levels.MapPosition;

import java.nio.ByteBuffer;

public class ServerInstructionBuilder {
    private static final byte MOVE_PLAYER_ID = (byte)0x00;
    private static final byte PRINT_MESSAGE_ID = (byte)0x01;
    private static final byte SET_STATS_ID = (byte)0x02;
    private static final byte SEND_PLAYER_INFO_ID = (byte)0x03;
    private static final byte SEND_ID_ID = (byte)0x04;
    private static final byte PREPARE_CLIENT_ID = (byte)0x05;
    private static final byte QUIT_ID = (byte)0xf0;
    private static final byte RELEASE_CONTROL_ID = (byte)0xff;

    /*
    public static byte[] buildGenerateGameStateInstruction(){

    }
    */

    public static byte[] buildMovePlayerInstruction(Direction dir){
        byte instruction[] = new byte[2];
        instruction[0] = MOVE_PLAYER_ID;
        final byte MOVE_NORTH_PATTERN = 0x00;
        final byte MOVE_SOUTH_PATTERN = 0x01;
        final byte MOVE_WEST_PATTERN = 0x02;
        final byte MOVE_EAST_PATTERN = 0x03;

        switch(dir){
            case NORTH:
                instruction[1] = MOVE_NORTH_PATTERN;
                break;
            case SOUTH:
                instruction[1] = MOVE_SOUTH_PATTERN;
                break;
            case WEST:
                instruction[1] = MOVE_WEST_PATTERN;
                break;
            case EAST:
                instruction[1] = MOVE_EAST_PATTERN;
                break;
        }

        return instruction;
    }

    public static byte[] buildPrintMessageInstruction(String message){
        short messageLen = (short)message.length();
        byte instruction[] = new byte[3 + (messageLen*2)];
        byte messageLenBytes[] = ByteBuffer.allocate(2).putShort(messageLen).array();
        instruction[0] = PRINT_MESSAGE_ID;
        instruction[1] = messageLenBytes[0];
        instruction[2] = messageLenBytes[1];
        byte messageBytes[] = message.getBytes();
        for(int i = 3; i < messageLen; i++){
            instruction[i + 2] = messageBytes[i];
        }
        return instruction;
    }

    public static byte[] buildSetStatsInstruction(Stats stats){
        byte instruction[] = new byte[5];
        instruction[0] = SET_STATS_ID;
        instruction[1] = (byte)stats.getHealth();
        instruction[2] = (byte)stats.getMaxHealth();
        instruction[3] = (byte)stats.getAttack();
        instruction[4] = (byte)stats.getDefense();
        return instruction;
    }

    public static byte[] buildReleaseInputInstruction(){
        byte instruction[] = new byte[1];
        instruction[0] = RELEASE_CONTROL_ID;
        return instruction;
    }

    public static byte[] buildSendPlayerInfoInstruction(){
        byte instruction[] = new byte[1];
        instruction[0] = SEND_PLAYER_INFO_ID;
        return instruction;
    }

    public static byte[] buildSendIDInstruction(){
        byte instruction[] = new byte[1];
        instruction[0] = SEND_ID_ID;
        return instruction;
    }

    public static byte[] buildPrepareClientInstruction(Player player, DungeonMap map){
        final byte FIGHTER_ID = 0x00;
        final byte WIZARD_ID = 0x01;

        byte instruction[] = new byte[12];
        MapPosition pos = player.getPostion();
        Stats stats = player.getStats();
        instruction[0] = PREPARE_CLIENT_ID;
        instruction[1] = (player instanceof Fighter) ? FIGHTER_ID : WIZARD_ID;
        instruction[2] = (byte)pos.getX();
        instruction[3] = (byte)pos.getY();
        instruction[4] = (byte)stats.getHealth();
        instruction[5] = (byte)stats.getMaxHealth();
        instruction[6] = (byte)stats.getAttack();
        instruction[7] = (byte)stats.getDefense();

        int seed = map.getSeed();
        byte seedBytes[] = ByteBuffer.allocate(4).putInt(seed).array();

        instruction[8] = seedBytes[0];
        instruction[9] = seedBytes[1];
        instruction[10] = seedBytes[2];
        instruction[11] = seedBytes[3];

        return instruction;
    }

    public static byte[] buildQuitInstruction(){
        byte instruction[] = new byte[1];
        instruction[0] = QUIT_ID;
        return instruction;
    }
}
