package com.dd.controller_util.controller;

import com.dd.DandD;
import com.dd.GameState;
import com.dd.GameType;
import com.dd.Stats;
import com.dd.controller_util.ControllerArgumentPackage;
import com.dd.controller_util.GameSceneController;

import com.dd.dd_util.BitSequence;
import com.dd.entities.Player;
import com.dd.entities.players.Fighter;
import com.dd.entities.players.Wizard;
import com.dd.levels.DungeonMap;
import com.dd.levels.MapPosition;
import com.dd.network.SocketCommChannel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import org.ietf.jgss.GSSManager;

import java.io.IOException;

public class CharacterCreationController extends GameSceneController{
    @FXML
    Label playerName;
    @FXML
    TextField playerNameField;
    @FXML
    RadioButton fighterRadio;
    @FXML
    RadioButton wizRadio;
    @FXML
    ToggleGroup characterClass;
    @FXML
    Button backButton;
    @FXML
    Button startGame;
    @FXML
    Label errorLable;
    String gameName;
    String addressNumber;

    private SocketCommChannel commChannel;

    @FXML
    private void backButtonAction(ActionEvent event){
        DandD.setActiveGameScene("JoinGameScene", null);
    }
    @FXML
    private void startGameAction(ActionEvent event){
        if(checkFields()){
            final int PREPARE_CLIENT_ID = 0x05;
            try {
                final byte FIGHTER_ID = 0x00;
                final byte WIZARD_ID = 0x01;
                String playerName = playerNameField.getText();
                int messageLen = 2 + (2*playerName.length());
                byte messageOut[] = new byte[messageLen];

                if(fighterRadio.isSelected()){
                    messageOut[0] = FIGHTER_ID;
                }
                else{
                    messageOut[0] = WIZARD_ID;
                }

                messageOut[1] = (byte)playerName.length();
                byte playerNameBytes[] = playerName.getBytes();
                for(int i = 2; i < messageLen; i++){
                    messageOut[i] = playerNameBytes[i-2];
                }

                commChannel.write(messageOut);

                byte messageBuffer[] = commChannel.read();
                BitSequence message = new BitSequence(8 * messageBuffer.length, messageBuffer);
                int instructionID = message.getNextBits(8).getAsInt();

                if(instructionID == PREPARE_CLIENT_ID) {
                    Player player = null;
                    int playerType = message.getNextBits(8).getAsInt();
                    int xPos = message.getNextBits(8).getAsInt();
                    int yPos = message.getNextBits(8).getAsInt();
                    int health = message.getNextBits(8).getAsInt();
                    int maxHealth = message.getNextBits(8).getAsInt();
                    int attack = message.getNextBits(8).getAsInt();
                    int defense = message.getNextBits(8).getAsInt();
                    int seed = message.getNextBits(32).getAsInt();
                    switch (playerType) {
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
                    ControllerArgumentPackage argumentPackage = new ControllerArgumentPackage();
                    argumentPackage.setArgument("GameState", gameState);
                    argumentPackage.setArgument("GameType", GameType.NET_CLIENT);
                    argumentPackage.setArgument("CommChannel", commChannel);
                    DandD.setActiveGameScene("RunningGameController", argumentPackage);
                }
                else{
                    DandD.setActiveGameScene("JoinGameScene", null);
                }
            }
            catch (Exception e){
                try {
                    commChannel.close();
                    DandD.setActiveGameScene("JoinGameScene", null);
                }
                catch (IOException ioE){
                    System.exit(1);
                }
            }
        }
    }
    private boolean checkFields(){
        if (playerNameField.getText().equals("")) {
            errorLable.setText("Please enter a character name.");
            return false;
        }
        else if (characterClass.getSelectedToggle() == null) {
            errorLable.setText("Please select a character class.");
            return false;
        }
        return true;
    }
    @Override
    public void setup(ControllerArgumentPackage args){
        commChannel = args.getArgument("CommChannel");
    }

    @Override
    public void teardown(){

    }
}