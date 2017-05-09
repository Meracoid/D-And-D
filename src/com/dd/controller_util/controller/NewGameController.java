package com.dd.controller_util.controller;

import com.dd.DandD;
import com.dd.GameState;
import com.dd.GameType;
import com.dd.controller_util.ControllerArgumentPackage;
import com.dd.controller_util.GameSceneController;
import com.dd.entities.*;
import com.dd.entities.players.Fighter;
import com.dd.entities.players.Wizard;
import com.dd.levels.*;

import java.nio.channels.Pipe;
import java.util.Random;

import com.dd.network.GameServer;
import com.dd.network.PipeCommChannel;
import com.dd.network.ServerGameState;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class NewGameController extends GameSceneController{
	@FXML private TextField saveName;
	@FXML private TextField seedNumber;
	@FXML private TextField characterName;
	@FXML private Button startButton;
	@FXML private Button backButton;
	@FXML private RadioButton fighterRadio;
	@FXML private RadioButton wizardRadio;
	@FXML private ToggleGroup characterClass;
	@FXML private Label errorLabel;
	@FXML private CheckBox networkPlay;

	/**
	 * Event handler for "Start Game" button.
	 */
	@FXML
	private void handleStartButtonAction(ActionEvent event) {
		if (!checkFields()) {
			return;
		}
		Integer seed = Integer.parseInt(seedNumber.getText());
		DungeonMap map = new DungeonMap(seed);
		Player player = null;
		if(fighterRadio.isSelected()) {
			player = new Fighter(characterName.getText(), map.getStartPosition());
		}
		else if(wizardRadio.isSelected()) {
			player = new Wizard(characterName.getText(), map.getStartPosition());
		}
		GameState game = null;
		ControllerArgumentPackage args = new ControllerArgumentPackage();

		if(networkPlay.isSelected()) {
			try {
				game = new ServerGameState(saveName.getText(), player, map);
				Pipe commPipe = Pipe.open();
				PipeCommChannel commChannel = new PipeCommChannel(commPipe.source(), commPipe.sink());
				GameServer server = new GameServer((ServerGameState)game, player, commChannel);
				server.start();
				args.setArgument("GameType", GameType.NET_SERVER);
				args.setArgument("CommChannel", commChannel);
			}
			catch(Exception e){
				System.exit(1);
			}
		}
		else{
			game = new GameState(saveName.getText(), player, map);
			args.setArgument("GameType", GameType.LOCAL);
		}
		args.setArgument("GameState", game);

		DandD.setActiveGameScene("RunningGameScene", args);
	}
	
	/**
	 * Event handler for "Back" button.
	 */
	@FXML
	private void handleBackButtonAction(ActionEvent event) {
		DandD.setActiveGameScene("MainMenuScene", null);
	}
	
	/**
	 * Checks the form to make sure nothing is empty or invalid.
	 */
	private boolean checkFields() {
		
		// check for empty fields
		if(saveName.getText().equals("")){
			errorLabel.setText("Please enter a name for the save file.");
			return false;
		}
		else if (seedNumber.getText().equals("")) {
			errorLabel.setText("Please enter a seed number.");
			return false;
		}
		else if (characterName.getText().equals("")) {
			errorLabel.setText("Please enter a character name.");
			return false;
		}
		else if (characterClass.getSelectedToggle() == null) {
			errorLabel.setText("Please select a character class.");
			return false;
		}
		
		// make sure the seed number is an integer
		try {
			Integer.parseInt(seedNumber.getText());
		}
		catch (NumberFormatException e) {
			errorLabel.setText("Please enter a valid seed number.");
			return false;
		}	
		
		return true;
	}
	
	/**
	 * Returns a string representation of the selected radio button.
	 */
	private String getRadioButtonText() {
		if (characterClass.getSelectedToggle() == null) {
			return "";
		}
		
		RadioButton rbutton = (RadioButton) characterClass.getSelectedToggle();
		return rbutton.getText();
	}
	
	private void displaySeedNumber() {
		Random rand = new Random();
		String result = Integer.toString(rand.nextInt(Integer.MAX_VALUE));
		seedNumber.setText(result);
	}

	/**
	 * Called when fxml document is loaded.
	 */
	public void initialize() {
		
	}

	@Override
	public void setup(ControllerArgumentPackage args){
		saveName.requestFocus();
		saveName.clear();
		seedNumber.clear();
		characterName.clear();
		fighterRadio.setSelected(false);
		wizardRadio.setSelected(false);
		errorLabel.setText("");
		displaySeedNumber();
	}

	@Override
	public void teardown(){

	}
}
