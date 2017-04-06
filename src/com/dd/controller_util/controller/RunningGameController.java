package com.dd.controller_util.controller;

import com.google.gson.Gson;
import com.dd.DandD;
import com.dd.GameState;
import com.dd.GameType;
import com.dd.command_util.CommandOutputLog;
import com.dd.command_util.CommandParser;
import com.dd.command_util.command.*;
import com.dd.controller_util.ControllerArgumentPackage;
import com.dd.controller_util.GameSceneController;
import com.dd.entities.Player;
import com.dd.levels.DungeonMap;
import com.dd.levels.MapPosition;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Node;

public class RunningGameController extends GameSceneController{
	@FXML private TextField input;
	@FXML private TextArea output;
	@FXML private TextArea map;
	@FXML private TextArea stats;
	@FXML private Button saveButton;
	@FXML private Button exitButton;

	private GameState gameState;
	private CommandParser commandParser;
	
	/**
	 * Event handler for "Enter" key.
	 */
	@FXML
	private void handleEnterPressed(KeyEvent event) {
		// if enter pressed and input is not empty
		if (event.getCode() == KeyCode.ENTER && !input.getText().equals("")) {
			// append to output and clear the textfield
			String commandStr = input.getText();
			input.clear();
			try{
				commandParser.parse(commandStr);
				updateMap();
				updateStatboard();
			}
			catch(CommandParser.InvalidCommandException e){
				output.appendText("The command string \""
									+ commandStr
									+ "\" is invalid. Type \"help\" for a list of commands.\n");
			}
			catch(FileNotFoundException e){
				output.appendText("ERROR: File issue!\n");
			}
	    }
	}
	
	/**
	 * Event handler for "Save" button.
	 */
	@FXML
	private void handleSaveButtonAction(ActionEvent event) throws FileNotFoundException {
		File gsonFile = new File(gameState.getName() + ".json");
		PrintStream toGsonFile = new PrintStream(gsonFile);
		toGsonFile.println(new Gson().toJson(gameState));
		toGsonFile.close();
		output.appendText("The game has been saved as \"" + gameState.getName() + "\".\n");
	}
	
	/**
	 * Event handler for "Exit" button.
	 */
	@FXML
	private void handleExitButtonAction(ActionEvent event) {
		DandD.setActiveGameScene("MainMenuScene", null);
	}
	
	public void updateMap() {
		map.setText(printMap());
	}
	
	public void updateStatboard() {
		stats.setText(printStatboard());
	}
	
	public String printMap() {
		Player player = gameState.getActivePlayer();
		DungeonMap map = gameState.getMap();
		StringBuilder output = new StringBuilder();
		output.append(printLnTitle('~', "MAP", 40));
		MapPosition playerPos = player.getPostion();
		for(int y = 0; y < map.getMaxRow(); y++){
			for(int x = 0; x < map.getMaxCol(); x++){
				if(playerPos.getX() == x && playerPos.getY() == y)
					output.append(" X ");
				else if(map.isRoom(new MapPosition(x, y)))
					output.append("[ ]");
				else
					output.append("   ");
			}
			output.append("\n");
		}
		return output.toString();
	}
	
	public String printStatboard() {
		Player player = gameState.getActivePlayer();
		StringBuilder output = new StringBuilder();
		output.append(printLnTitle('~', player.getName().toUpperCase() + "'S STATS BOARD", 40));
		output.append(player.statboardToString());
		return output.toString();
	}
	
	public static String printLnTitle(char c, String str, int width) {
    	StringBuilder output = new StringBuilder();
		int strLength = str.length();
		int startIndex = (width / 2) - (strLength / 2);
		for(int i = 0; i <= width; i++){
			if(i == startIndex){
				output.append(str);
				i += strLength;
			}else{
				output.append(c);
			}
		}
		output.append("\n");
		return output.toString();
	}

	public void setGameState(GameState gameState){
		this.gameState = gameState;
	}

	/**
	 * Called when fxml document is loaded.
	 */
	public void initialize() {

	}

	@Override
	public void setup(ControllerArgumentPackage args){
		GameState gameState = args.getArgument("GameState");
		this.gameState = gameState;
		
		updateMap();
		updateStatboard();
		output.clear();
		output.appendText(printLnTitle('~', " Welcome to Dungeons and D&D ", 80));
		output.appendText("Type \"help\" for a list of commands\n");
		
		String characterName = gameState.getActivePlayer().getName();
		String characterClass = gameState.getActivePlayer().getClass().toString().substring(22);
		commandParser = new CommandParser(new CommandOutputLog(output), characterName, characterClass);
		commandParser.registerCommand("move", new MoveCommand(gameState));
		commandParser.registerCommand("examine", new ExamineCommand(gameState));
		commandParser.registerCommand("drop", new DropCommand(gameState));
		commandParser.registerCommand("attack", new AttackCommand(gameState));
		commandParser.registerCommand("equip", new EquipCommand(gameState));
		commandParser.registerCommand("help", new HelpCommand());
		commandParser.registerCommand("pickup", new PickupCommand(gameState));
		commandParser.registerCommand("use", new UseCommand());
	}

	@Override
	public void teardown(){
		gameState = null;
		commandParser = null;
	}
}