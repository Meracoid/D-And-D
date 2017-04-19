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
	private void backButtonAction(ActionEvent event){
		DandD.setActiveGameScene("JoinGameScene", null);
	}
	@FXML
	private void startGameAction(ActionEvent event){
		
		ControllerArgumentPackage args = new ControllerArgumentPackage();
		String gameName=args.getArgument("GameName");
		String addressNumber=args.getArgument("GameAddress");
		args.setArgument("GameState", gameName);

		DandD.setActiveGameScene("RunningGameScene", args);
	}
    @Override
    public void setup(ControllerArgumentPackage args){
    	
    }

    @Override
    public void teardown(){

    }
}