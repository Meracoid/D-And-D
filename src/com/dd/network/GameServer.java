package com.dd.network;

import com.dd.GameState;
import com.dd.command_util.CommandHandler;
import com.dd.command_util.ServerCommandOutputLog;
import com.dd.command_util.ServerCommandParser;
import com.dd.command_util.command.network.ServerAttackCommand;
import com.dd.command_util.command.network.ServerPickupCommand;
import com.dd.command_util.command.network.ServerUseCommand;
import com.dd.dd_util.BitPattern;
import com.dd.dd_util.BitSequence;
import com.dd.dd_util.ByteUtility;
import com.dd.entities.Player;
import com.dd.entities.players.Fighter;
import com.dd.entities.players.Wizard;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.*;

public class GameServer extends Thread{
    private ServerGameState gameState;
    private PlayerConnection localClientConn;
    private Player localClientPlayer;
    private PipeCommChannel localClientPipe;
    private Selector selector;
    private List<SelectionKey> connectedPlayerList = new ArrayList<SelectionKey>();
    private List<SelectionKey> connectionsToTerminate = new ArrayList<SelectionKey>();
    private SelectionKey serverKey;
    private SelectionKey localClientInKey, localClientOutKey;
    private PlayerConnection activePlayerConn;
    private ServerCommandOutputLog outputLog;
    private ServerCommandParser cmdParser;
    private ServerSocket serverSocket;
    private ServerSocketChannel serverSocketChannel;
    private static final int port = 55555;

    public GameServer(ServerGameState gameState, Player localClientPlayer, PipeCommChannel localClientPipe){
        this.gameState = gameState;
        this.localClientPlayer = gameState.getActivePlayer();
        this.localClientPlayer = localClientPlayer;
        this.localClientPipe = localClientPipe;
        localClientConn = new PlayerConnection(localClientPlayer, localClientPipe);
        outputLog = new ServerCommandOutputLog(this, gameState);
        cmdParser = new ServerCommandParser(outputLog, gameState);
        cmdParser.registerCommand("attack", new ServerAttackCommand(gameState));
        cmdParser.registerCommand("drop", new ServerAttackCommand(gameState));
        cmdParser.registerCommand("examine", new ServerAttackCommand(gameState));
        cmdParser.registerCommand("move", new ServerAttackCommand(gameState));
        cmdParser.registerCommand("pickup", new ServerPickupCommand(gameState));
        cmdParser.registerCommand("use", new ServerUseCommand(gameState));
    }

    @Override
    public void run(){
        try{
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress("localhost", port));
            serverKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            localClientPipe.getInputChannel().configureBlocking(false);
            localClientPipe.getOutputChannel().configureBlocking(false);
            localClientInKey = localClientPipe.getInputChannel().register(selector, SelectionKey.OP_READ);
            localClientInKey.attach(localClientConn);
            localClientOutKey = localClientPipe.getOutputChannel().register(selector, SelectionKey.OP_WRITE);
            localClientOutKey.attach(localClientConn);
        }
        catch(IOException e){
            System.out.println(e.getMessage());
            System.exit(1);
        }

        while(true){
            try{
                for(SelectionKey key : connectedPlayerList){
                    if(!key.isValid()){
                        //remove player from activePlayerList in GameState
                        //and send out new gamestate
                    }
                }
                int numKeys = selector.select();
                if(!localClientInKey.isValid()
                        || !localClientOutKey.isValid()
                        || !serverKey.isValid()) {
                    //Server teardown
                }
                if(numKeys == 0)
                    continue;

                //Receive all incoming message and new connections
                for(SelectionKey key : selector.selectedKeys()){
                    if(key == serverKey && key.isAcceptable()){
                        //Accept new incoming connection
                        SocketCommChannel commChannel = new SocketCommChannel(((ServerSocketChannel)serverKey.channel()).socket().accept());
                        commChannel.getSocket().getChannel().configureBlocking(false);
                        PlayerConnection playerConn = new PlayerConnection(null, commChannel);
                        SelectionKey newKey = commChannel.getSocket().getChannel().register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                        newKey.attach(playerConn);
                        connectedPlayerList.add(newKey);
                        //Player must now be validated
                        playerConn.appendToOutBuffer(ServerInstructionBuilder.buildSendIDInstruction());
                    }
                    else{
                        PlayerConnection playerConn = (PlayerConnection)key.attachment();
                        activePlayerConn = playerConn;

                        if(key.isReadable()){
                            if(playerConn != localClientConn) {
                                if (playerConn.getPlayerID() == null) {
                                    //Validation
                                    NetworkCommChannel commChannel = playerConn.getCommChannel();
                                    byte data[] = commChannel.read();
                                    if (data.length == 128 / 8) {
                                        BitSequence playerIDBits = new BitSequence(128, data);
                                        //Get UUID
                                        UUID playerID = new UUID(playerIDBits.getNextBits(128).getAsLong(), playerIDBits.getNextBits(128).getAsLong());
                                        Player player = gameState.getRegisteredPlayer(playerID);
                                        if (player != null) {
                                            gameState.addActivePlayer(playerID, player);
                                            playerConn.setPlayerID(playerID);
                                            playerConn.appendToOutBuffer(ServerInstructionBuilder.buildPrepareClientInstruction(player, gameState.getMap()));
                                        }
                                        else {
                                            playerConn.appendToOutBuffer(ServerInstructionBuilder.buildSendPlayerInfoInstruction());
                                        }
                                    }
                                    else {
                                        commChannel.write(ServerInstructionBuilder.buildQuitInstruction());
                                        commChannel.close();
                                        connectedPlayerList.remove(key);
                                    }
                                }
                                else if (playerConn.getPlayer() == null) {
                                    final int FIGHTER_ID = 0x00;
                                    final int WIZARD_ID = 0x01;
                                    NetworkCommChannel commChannel = playerConn.getCommChannel();
                                    byte data[] = commChannel.read();
                                    BitSequence playerInfoBits = new BitSequence(data.length * 8, data);
                                    int playerClass = playerInfoBits.getNextBits(8).getAsInt();
                                    int playerNameLen = playerInfoBits.getNextBits(8).getAsInt();
                                    String playerName = playerInfoBits.getNextBits(16 * playerNameLen).getAsString();
                                    Player newPlayer = null;
                                    switch (playerClass) {
                                        case FIGHTER_ID:
                                            newPlayer = new Fighter(playerName, gameState.getMap().getStartPosition());
                                            break;
                                        case WIZARD_ID:
                                            newPlayer = new Wizard(playerName, gameState.getMap().getStartPosition());
                                            break;
                                        default:
                                            commChannel.write(ServerInstructionBuilder.buildQuitInstruction());
                                            commChannel.close();
                                            connectedPlayerList.remove(key);
                                    }
                                    gameState.registerPlayer(playerConn.getPlayerID(), newPlayer);
                                    gameState.addActivePlayer(playerConn.getPlayerID(), newPlayer);
                                    playerConn.setPlayer(newPlayer);
                                    playerConn.appendToOutBuffer(ServerInstructionBuilder.buildPrepareClientInstruction(newPlayer, gameState.getMap()));
                                }
                                else {
                                    NetworkCommChannel commChannel = playerConn.getCommChannel();
                                    byte data[] = commChannel.read();
                                    BitSequence message = new BitSequence(data.length, data);
                                    String cmd = message.getNextBits(data.length).getAsString();
                                    cmdParser.parse(cmd);
                                }
                            }
                            else{
                                NetworkCommChannel commChannel = playerConn.getCommChannel();
                                byte data[] = commChannel.read();
                                BitSequence message = new BitSequence(data.length, data);
                                String cmd = message.getNextBits(data.length).getAsString();
                                cmdParser.parse(cmd);
                            }
                        }
                    }
                }

                //Send out all outgoing messages
                for(SelectionKey key : selector.selectedKeys()){
                    if(key.isWritable()){
                        PlayerConnection playerConn = (PlayerConnection)key.attachment();
                        byte outBuffer[] = playerConn.flushOutBuffer();
                        if(outBuffer != null)
                            playerConn.commChannel.write(playerConn.flushOutBuffer());
                    }
                }

                for(SelectionKey key : connectionsToTerminate){
                    PlayerConnection playerConn = (PlayerConnection)key.attachment();
                    playerConn.commChannel.close();
                }
                connectionsToTerminate.clear();
            }
            catch (IOException e){

            }
        }
    }

    public static int getServerPort(){
        return port;
    }

    public PlayerConnection getActivePlayerConn(){
        return activePlayerConn;
    }

    public class PlayerConnection{
        private Player player;
        private UUID playerID;
        private NetworkCommChannel commChannel;
        private byte[] inHolder = null;
        private List<Byte> outBuffer = new ArrayList<Byte>();

        public PlayerConnection(Player player, NetworkCommChannel commChannel){
            this.player = player;
            this.commChannel = commChannel;
        }

        public Player getPlayer(){
            return player;
        }

        public void setPlayer(Player player){
            this.player = player;
        }

        public void setPlayerID(UUID playerID){
            this.playerID = playerID;
        }

        public UUID getPlayerID(){
            return playerID;
        }

        public NetworkCommChannel getCommChannel(){
            return commChannel;
        }

        public void setCommChannel(NetworkCommChannel commChannel){
            this.commChannel = commChannel;
        }

        public void appendToOutBuffer(byte[] outData){
            for(byte b : outData){
                outBuffer.add(b);
            }
        }

        public byte[] flushOutBuffer(){
            byte[] outArr = ByteUtility.byteArrayFromByteList(outBuffer);
            outBuffer.clear();
            return outArr;
        }
    }
}
