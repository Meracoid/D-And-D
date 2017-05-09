package com.dd.command_util.command.network;

import com.dd.GameState;
import com.dd.command_util.CommandOutputLog;
import com.dd.command_util.command.MoveCommand;
import com.dd.exceptions.InvalidArgumentException;
import com.dd.levels.Direction;
import com.dd.levels.MapPosition;
import com.dd.network.ServerInstructionBuilder;

public class ServerMoveCommand extends MoveCommand{
    public ServerMoveCommand(GameState gameState){
        super(gameState);
    }

    @Override
    public void handleCommand(String commandName, String commandAgrs[], CommandOutputLog outputLog) throws InvalidArgumentException {
        MapPosition pos = gameState.getActivePlayer().getPostion();
        pos = new MapPosition(pos.getX(), pos.getY());
        super.handleCommand(commandName, commandAgrs, outputLog);
        MapPosition newPos = gameState.getActivePlayer().getPostion();
        if(pos.getX() != newPos.getX() || pos.getY() != newPos.getY()){
            Direction dir = null;
            switch(commandAgrs[1]){
                case "north":
                    dir = Direction.NORTH;
                    break;
                case "south":
                    dir = Direction.SOUTH;
                    break;
                case "east":
                    dir = Direction.EAST;
                    break;
                case "west":
                    dir = Direction.WEST;
                    break;
            }
            outputLog.print(ServerInstructionBuilder.buildMovePlayerInstruction(dir));
        }
        outputLog.print(ServerInstructionBuilder.buildReleaseInputInstruction());
    }
}
