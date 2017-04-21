package com.dd.controller_util.controller;

import com.dd.DandD;
import com.dd.GameState;
import com.dd.controller_util.ControllerArgumentPackage;
import com.dd.controller_util.GameSceneController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

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
	@FXML
	private void backButtonAction(ActionEvent event){
		DandD.setActiveGameScene("JoinGameScene", null);
	}
	@FXML
	private void startGameAction(ActionEvent event){
		if(checkFields()){
			//Implement when you try to start to game.
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
		gameName=args.getArgument("GameName");
		addressNumber=args.getArgument("GameAddress");
    }

    @Override
    public void teardown(){

    }
}