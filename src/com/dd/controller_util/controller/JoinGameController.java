package com.dd.controller_util.controller;

import com.dd.controller_util.ControllerArgumentPackage;
import com.dd.controller_util.GameSceneController;

import java.io.IOException;

import com.dd.DandD;
import com.dd.SceneControllerTuple;

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
	@FXML
	private void nextButtonJoinAction(ActionEvent event) throws IOException{
		if(listItemIsClicked()){
			DandD.setActiveGameScene("CharacterCreationScene", null);
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
		else if(!netGameList.isPressed()){
			errorLable.setText("Please select a net game to join.");
			return false;
		}
		return true;
	}
	public void addNetGames(String gameName, String ipAddress){
		ObservableList<String> items =FXCollections.observableArrayList (
			    "Game Name: "+gameName+"\nIP Address: "+ipAddress);
		netGameList.setItems(items);
	}
    @Override
    public void setup(ControllerArgumentPackage args){
    	String gameName= args.getArgument("ServerName");
    	String ipAddress= args.getArgument("IPAddress");
    	addNetGames(gameName,ipAddress);
    }

    @Override
    public void teardown(){

    }
    
}