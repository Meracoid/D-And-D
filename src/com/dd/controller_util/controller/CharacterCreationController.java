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
		JoinGameController joinGame=new JoinGameController();
		String item=joinGame.netGameList.getSelectionModel().getSelectedItem().toString();
		String gameName= item.substring(11, item.indexOf("\n"));
		
		ControllerArgumentPackage args = new ControllerArgumentPackage();
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