package com.dd.controller_util.controller;

import com.dd.DandD;
import com.dd.controller_util.ControllerArgumentPackage;
import com.dd.controller_util.GameSceneController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddServerController extends GameSceneController{
	@FXML
	Label serverName;
	@FXML
	Label ipAddress;
	@FXML
	TextField serverNameField;
	@FXML
	TextField ipAddressField;
	@FXML
	Button addButton;
	@FXML
	Label errorLabel;
	@FXML
	private void addButtonAction(ActionEvent event){
		if(checkFields()){
			ControllerArgumentPackage serverGame= new ControllerArgumentPackage();
			serverGame.setArgument("ServerName",serverNameField.getText());
			serverGame.setArgument("IPAddress",  ipAddressField.getText());
			DandD.setActiveGameScene("JoinGameScene", serverGame);
		}
	}
	private boolean checkFields(){
		if(serverNameField.getText()==""){
			errorLabel.setText("Please enter the server name");
			return false;
		}
		if(ipAddressField.getText()==""){
			errorLabel.setText("Please enter the ip address");
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