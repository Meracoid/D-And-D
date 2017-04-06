package com.dd.controller_util.controller;

import java.io.IOException;

import com.dd.DandD;
import com.dd.controller_util.ControllerArgumentPackage;
import com.dd.controller_util.GameSceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenuController extends GameSceneController{
	@FXML private Button newGame;
	@FXML private Button joinGame;
	@FXML private Button loadGame;
	
	/**
	 * Event handler for "New Game" button.
	 */
	@FXML
	private void handleNewGameAction(ActionEvent event) throws IOException {
		DandD.setActiveGameScene("NewGameScene", null);
	}
	
	/**
	 * Event handler for "Join Game" button.
	 */
	@FXML
	private void handleJoinGameAction(ActionEvent event) {
		DandD.setActiveGameScene("JoinGameScene", null);
	}
	
	/**
	 * Event handler for "Load Game" button.
	 */
	@FXML
	private void handleLoadGameAction(ActionEvent event) throws IOException {
		DandD.setActiveGameScene("LoadGameScene", null);
	}
	
	/**
	 * Called when fxml document is loaded.
	 */
	public void initialize() {

	}

	@Override
	public void setup(ControllerArgumentPackage args){

	}

	@Override
	public void teardown(){

	}
}