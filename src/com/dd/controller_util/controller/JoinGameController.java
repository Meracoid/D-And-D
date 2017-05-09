package com.dd.controller_util.controller;

import com.dd.GameState;
import com.dd.Stats;
import com.dd.controller_util.ControllerArgumentPackage;
import com.dd.controller_util.GameSceneController;

import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;

import com.dd.DandD;
import com.dd.SceneControllerTuple;

import com.dd.dd_util.BitSequence;
import com.dd.entities.Player;
import com.dd.entities.players.Fighter;
import com.dd.entities.players.Wizard;
import com.dd.levels.DungeonMap;
import com.dd.levels.MapPosition;
import com.dd.network.GameServer;
import com.dd.network.SocketCommChannel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class JoinGameController extends GameSceneController{
    @FXML
    Button nextButtonJoin;
    @FXML
    Button addButton;
    @FXML
    Button backToMenu;
    @FXML
    ListView netGameList;
    @FXML
    Label errorLable;
    private ObservableList<String> items=FXCollections.observableArrayList();
    @FXML
    Button deleteItem;
    @FXML
    private void deleteItemAction(ActionEvent event) throws IOException{
        if(listItemIsClicked()){
            int index = netGameList.getSelectionModel().getSelectedIndex();
            items.remove(index);
            netGameList.setItems(items);
            errorLable.setText("");
        }
    }
    @FXML
    private void nextButtonJoinAction(ActionEvent event) throws IOException{
        if(listItemIsClicked()){
            ControllerArgumentPackage serverGame= new ControllerArgumentPackage();
            String listName= (String)netGameList.getSelectionModel().getSelectedItem();
            int x0= listName.lastIndexOf(" ");
            int x1= listName.lastIndexOf("\n");
            String gameName= listName.substring(11, x1);
            String ipAddressNumber=listName.substring(x0+1);
            //Establish connection to server
            try{
                final int SEND_PLAYER_INFO_ID = 0x03;
                final int SEND_ID_ID = 0x04;
                final int PREPARE_CLIENT_ID = 0x05;
                final int QUIT_ID = 0xf0;
                Socket sock = new Socket(ipAddressNumber, GameServer.getServerPort());
                SocketCommChannel commChannel = new SocketCommChannel(sock);
                while(true){
                    byte messageBuffer[] = commChannel.read();
                    BitSequence message = new BitSequence(8 * messageBuffer.length, messageBuffer);
                    int instructionID = message.getNextBits(8).getAsInt();
                    switch(instructionID){
                        case SEND_ID_ID:
                            ByteBuffer response = ByteBuffer.allocate(16).putLong(DandD.getGameUUID().getLeastSignificantBits());
                            response.putLong(DandD.getGameUUID().getMostSignificantBits());
                            commChannel.write(response.array());
                            break;
                        case SEND_PLAYER_INFO_ID:
                            serverGame.setArgument("CommChannel", commChannel);
                            DandD.setActiveGameScene("CharacterCreationScene", serverGame);
                            break;
                        case PREPARE_CLIENT_ID:
                            final byte FIGHTER_ID = 0x00;
                            final byte WIZARD_ID = 0x01;
                            Player player = null;
                            int playerType = message.getNextBits(8).getAsInt();
                            int xPos = message.getNextBits(8).getAsInt();
                            int yPos = message.getNextBits(8).getAsInt();
                            int health = message.getNextBits(8).getAsInt();
                            int maxHealth = message.getNextBits(8).getAsInt();
                            int attack = message.getNextBits(8).getAsInt();
                            int defense = message.getNextBits(8).getAsInt();
                            int seed = message.getNextBits(32).getAsInt();
                            switch (playerType){
                                case FIGHTER_ID:
                                    player = new Fighter("",
                                                            new MapPosition(xPos, yPos),
                                                            new Stats(health, maxHealth, attack, defense));
                                    break;
                                case WIZARD_ID:
                                    player = new Wizard("",
                                                            new MapPosition(xPos, yPos),
                                                            new Stats(health, maxHealth, attack, defense));
                                    break;

                            }
                            GameState gameState = new GameState("", player, new DungeonMap(seed));
                            serverGame.setArgument("GameState", gameState);
                            DandD.setActiveGameScene("RunningGameScene", serverGame);
                            break;
                        case QUIT_ID:
                            break;
                    }
                }
            }
            catch(Exception e){
                return;
            }
        }
    }
    @FXML
    private void addButtonAction(ActionEvent event) throws IOException{
        DandD.setActiveGameScene("AddServerScene", null);
    }
    @FXML
    private void backToMenuAction(ActionEvent event) throws IOException{
        DandD.setActiveGameScene("MainMenuScene", null);
    }
    private boolean listItemIsClicked(){
        if(netGameList.getItems().isEmpty()){
            errorLable.setText("No net games at this point. Please try again later.");
            return false;
        }
        else if(netGameList.getSelectionModel().getSelectedIndex()==-1) {
            errorLable.setText("Please select a net game to join or delete.");
            return false;
        }
        return true;
    }
    public void addNetGames(String gameName, String ipAddress){
        items.add("Game Name: "+gameName+"\nIP Address: "+ipAddress);
        netGameList.setItems(items);
    }
    @Override
    public void setup(ControllerArgumentPackage args){
        if(args!=null) {
            String gameName = args.getArgument("ServerName");
            String ipAddress = args.getArgument("IPAddress");
            addNetGames(gameName, ipAddress);
        }
        errorLable.setText("");
        netGameList.getSelectionModel().select(-1);
    }

    @Override
    public void teardown(){

    }
}