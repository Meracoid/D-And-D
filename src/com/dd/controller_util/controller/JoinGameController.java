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
	@FXML
	private void nextButtonJoinAction(ActionEvent event) throws IOException{
		if(listItemIsClicked()){
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
	private boolean listItemIsClicked(){
		if(netGameList.equals("")){
			errorLable.setText("No net games at this point. Please try again later.");
		}
		else if(!netGameList.isPressed()){
			errorLable.setText("Please select a net game to join.");
		}
		return true;
	}
	public void addNetGames(){
		
	}
    @Override
    public void setup(ControllerArgumentPackage args){

    }

    @Override
    public void teardown(){

    }
    
}