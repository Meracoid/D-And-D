package com.dd.command_util.command.network;

import com.dd.GameState;
import com.dd.Stats;
import com.dd.command_util.CommandHandler;
import com.dd.command_util.CommandOutputLog;
import com.dd.command_util.LocalCommandOutputLog;
import com.dd.controller_util.controller.RunningGameController;
import com.dd.dd_util.BitSequence;
import com.dd.exceptions.InvalidArgumentException;
import com.dd.levels.MapPosition;
import com.dd.network.ClientInputController;
import com.dd.network.ClientInstructionHandler;
import com.dd.network.NetworkCommChannel;

import java.io.IOException;

public class NetworkHandledCommand extends CommandHandler{
    private NetworkCommChannel commChannel;
    private ClientInputController clientInputController;

    private static final int COMMAND_REQUEST_ID = 0x00;
    private static final int MOVE_PLAYER_ID = 0x01;
    private static final int PRINT_MESSAGE_ID = 0x02;
    private static final int SET_STATS_ID = 0x03;

    private static final int NORTH_ID = 0x00;
    private static final int SOUTH_ID = 0x01;
    private static final int WEST_ID = 0x02;
    private static final int EAST_ID = 0x03;

    public NetworkHandledCommand(NetworkCommChannel commChannel, GameState gameState, ClientInputController clientInputController){
        super(gameState);
        this.commChannel = commChannel;
        this.clientInputController = clientInputController;
    }

    @Override
    public void handleCommand(String commandName, String[] args, CommandOutputLog outputLog) throws InvalidArgumentException{
    	if(dead){
    		outputLog.print(player.getTitle() + " is dead. ");
    		return;
    	}
    	setGlobalOutput(outputLog);

    	if(commandName.equals("help")){
            if(args[0] != null){
                throw new InvalidArgumentException("The " + commandName + " command should not be followed by any arguments. ");
            }
            monsterAttack = false;

            outputLog.print(RunningGameController.printLnTitle('~', "AVAILABLE COMMANDS", 72)
                    + "\"attack <name>\"\n"
                    + "Initiate an attack against a monster or player.\n"
                    + "\n"
                    + "\"move <direction>\"\n"
                    + "Move the player to the room in the specified direction of the room the "
                    + "player is currently in. If no room exists in the specified direction this "
                    + "will fail. The valid directions are: north, south, east, and west.\n"
                    + "\n"
                    + "\"examine room | monsters | items | <name>\"\n"
                    + "Get a description of the either the room, a monster by name, an item by name, or the room "
                    + "the player is currently in, or a list of monsters or items.\n"
                    + "\n"
                    + "\"use <item name>\"\n"
                    + "Use an item to receive its effects. If the item specified is not a "
                    + "usable item this will fail.\n"
                    + "\n"
                    + "\"pickup <item name> | items\"\n"
                    + "pickup an item with the name and attempt to equip it to the player or add it to "
                    + "their inventory. Use argument \"items\" to attempt to equip all the items in the room\n"
                    + "\n"
                    + "\"drop <body location>\"\n"
                    + "Remove an item from the given body location. For items in the inventory, please "
                    + "provide an number to indicate which inventory item. The dropped item will be "
                    + "placed in the room the player is currently in. ");
            return;
        }

    	String cmdString = commandName;
    	for(String argStr : args){
    	    commandName += " " + argStr;
        }

        try {
            commChannel.write(cmdString.getBytes());
            clientInputController.clientReleaseControl();
            updateState();
            InputControlRegainThread controlRegainThread = new InputControlRegainThread();
            controlRegainThread.start();
        }
        catch (IOException e){
    	    System.exit(1);
        }
    }

    public class InputControlRegainThread extends Thread{
        public InputControlRegainThread(){}

        @Override
        public void run(){
            clientInputController.clientTakeControl();
        }
    }
}
