package com.dd.controller_util.controller;

import com.dd.controller_util.ControllerArgumentPackage;
import com.dd.controller_util.GameSceneController;

import java.io.IOException;

import com.dd.DandD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class JoinGameController extends GameSceneController{
	@FXML
	Button nextButtonJoin;
	@FXML
	RadioButton joinRadioButton;
	@FXML
	TextField gameNameDisplay;
	@FXML
	TextField ipAddressDisplay;
	@FXML
	Button addButton;
	@FXML
	Button backToMenu;
	@FXML
	ToggleGroup netGames;
	@FXML
	Label errorLable;
	@FXML
	private void nextButtonJoinAction(ActionEvent event) throws IOException{
		if(checkRadioButton()){
			DandD.setActiveGameScene("NextMenuScene", null);
		}
	}
	@FXML
	private void addButtonAction(ActionEvent event) throws IOException{
		DandD.setActiveGameScene("AddMenuScene", null);
	}
	@FXML
	private void backToMenuAction(ActionEvent event) throws IOException{
		DandD.setActiveGameScene("MainMenuScene", null);
	}
	private boolean checkRadioButton(){
		if(netGames.hasProperties()){
			if(netGames.getSelectedToggle()==null){
				errorLable.setText("Please Select a Game");
				return false;
			}
		}
		else if(!netGames.hasProperties()){
			errorLable.setText("No Games are available. Please try again later");
			return false;
		}
		return true;
	}
    @Override
    public void setup(ControllerArgumentPackage args){

    }

    @Override
    public void teardown(){

    }
    
}