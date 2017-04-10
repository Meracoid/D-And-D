package com.dd.controller_util.controller;

import com.dd.DandD;
import com.dd.GameState;
import com.dd.controller_util.ControllerArgumentPackage;
import com.dd.controller_util.GameSceneController;
import com.dd.entities.Fighter;
import com.dd.entities.Wizard;
import com.dd.levels.*;

import java.util.Random;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

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
		GameState game = new GameState(saveName.getText(), map);
		if(fighterRadio.isSelected()) {
			Fighter fighter = new Fighter(characterName.getText(), map.getStartPosition());
			game.setActivePlayer(fighter);
		}
		else if(wizardRadio.isSelected()) {
			Wizard wizard = new Wizard(characterName.getText(), map.getStartPosition());
			game.setActivePlayer(wizard);
		}
		ControllerArgumentPackage args = new ControllerArgumentPackage();
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