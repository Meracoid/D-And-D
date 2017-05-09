package com.dd.controller_util.controller;

import com.dd.DandD;
import com.dd.controller_util.ControllerArgumentPackage;
import com.dd.controller_util.GameSceneController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

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
    Button backButton;
    @FXML
    Label errorLable;
    @FXML
    private void addButtonAction(ActionEvent event) throws IOException{
        if(checkFields()){
            ControllerArgumentPackage serverGame= new ControllerArgumentPackage();
            String name=serverNameField.getText();
            String address=ipAddressField.getText();
            if(!addressChecker(address)){
                return;
            }
            serverGame.setArgument("ServerName", name);
            serverGame.setArgument("IPAddress", address);
            DandD.setActiveGameScene("JoinGameScene", serverGame);
        }
    }
    @FXML
    private void backButtonAction(ActionEvent event) throws IOException{
        DandD.setActiveGameScene("JoinGameScene", null);
    }
    private boolean addressChecker(String address){
        if(!address.contains(".")){
            errorLable.setText("Please re-enter the IP Address Field with \".\"");
            ipAddressField.setText("");
            return false;
        }
        else if (address.endsWith(".")){
            errorLable.setText("Please remove the period at the end of IP Address Field");
            return false;
        }
        else if(address.matches("[A-Za-z].*")||address.matches(".*[A-Za-z].*")||address.matches(".*[A-Za-z]")) {
            errorLable.setText("Please re-enter the IP Address Field without letters");
            ipAddressField.setText("");
            return false;
        }
        String[] checkAddress=address.split("\\.");
        if(checkAddress.length>4) {
            errorLable.setText("Please re-enter a valid IP address in the IP Address Field");
            ipAddressField.setText("");
            return false;
        }
        for(int i=0; i<checkAddress.length; i++){
            if(checkAddress[i].matches("\\p{Punct}.*")||checkAddress[i].matches(".*\\p{Punct}.*")||checkAddress[i].matches(".*\\p{Punct}")){
                errorLable.setText("Please re-enter an IP address without punctuation");
                ipAddressField.setText("");
                return false;
            }
            int numCheck=Integer.parseInt(checkAddress[i]);
            if(numCheck>=256 || numCheck<0){
                errorLable.setText("Please re-enter a valid IP address in the IP Address Field");
                ipAddressField.setText("");
                return false;
            }
        }
        return true;
    }
    @FXML
    private boolean checkFields(){
        if(serverNameField.getText().equals("")){
            errorLable.setText("Please enter the server name");
            return false;
        }
        if(ipAddressField.getText().equals("")){
            errorLable.setText("Please enter the ip address");
            return false;
        }
        return true;
    }
    @Override
    public void setup(ControllerArgumentPackage args){
        serverNameField.setText("");
        ipAddressField.setText("");
        errorLable.setText("");
    }

    @Override
    public void teardown(){

    }
}