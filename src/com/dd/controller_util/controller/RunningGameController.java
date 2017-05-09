package com.dd.controller_util.controller;

import com.dd.DandD;
import com.dd.GameState;
import com.dd.exceptions.InvalidCommandException;
import com.dd.command_util.CommandOutputLog;
import com.dd.command_util.CommandParser;
import com.dd.command_util.command.*;
import com.dd.controller_util.ControllerArgumentPackage;
import com.dd.controller_util.GameSceneController;
import com.dd.entities.Player;
import com.dd.levels.DungeonMap;
import com.dd.levels.MapPosition;
import com.dd.levels.Room;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class RunningGameController extends GameSceneController{
	@FXML private TextField input;
	@FXML private TextArea output;
	@FXML private TextArea map;
	@FXML private TextArea stats;
	@FXML private Button saveButton;
	@FXML private Button exitButton;

	private GameState gameState;
	private CommandParser commandParser;
	private CommandOutputLog log;
	
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
			catch(InvalidCommandException ICE){
				output.appendText(ICE.getMessage());
			}
	    }
	}
	
	/**
	 * Event handler for "Save" button.
	 */
	@FXML
	private void handleSaveButtonAction(ActionEvent event) {
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
                String filename = gameState.getName() + ".ser";
        		
                fos = new FileOutputStream(filename);
                out = new ObjectOutputStream(fos);
                out.writeObject(gameState);
                out.close();
                output.appendText("\nThe game \"" + gameState.getName() + "\" has been saved. ");
        } catch (IOException e) {
                e.printStackTrace();
                output.appendText("\nERROR - The game was not saved. ");
        }
	}
	
	/**
	 * Event handler for "Exit" button.
	 */
	@FXML
	private void handleExitButtonAction(ActionEvent event) {
		DandD.setActiveGameScene("MainMenuScene", null);
	}
	
	private void printIntro() {
		Player player = gameState.getActivePlayer();;
		DungeonMap dungeon = gameState.getMap();
		Room startRoom = dungeon.getRoom(player.getPostion());
		
		output.appendText(printLnTitle('~', " Dungeons and D&D ", 72));
		output.appendText("*Type \"help\" for a list of commands\n"
				+ printLnTitle('~', " Dungeon Master ", 72)
				+ "Hello " + player.getType() + " " + player.getName() + ". "
						+ "You have found yourself in a dark dungeon room. You see doors leading to other rooms. ");
		output.appendText(startRoom.examineRoom());
		
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
				if(x == playerPos.getX() && y == playerPos.getY()) {
					output.append("[X]");
				}
				else if(x == map.getEndPosition().getX() && y == map.getEndPosition().getY()) {
					output.append("[!]");
				}
				else if(map.isRoom(new MapPosition(x, y))) {
					output.append("[ ]");
				}
				else{
					output.append("   ");
				}
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

	/**
	 * Called when fxml document is loaded.
	 */
	public void initialize() {

	}

	@Override
	public void setup(ControllerArgumentPackage args){
		gameState = (GameState)args.getArgument("GameState");
		log = new CommandOutputLog(output);
		
		commandParser = new CommandParser(gameState);
		
		commandParser.registerCommand("move", new MoveCommand(gameState));
	    commandParser.registerCommand("examine", new ExamineCommand(gameState));
	    commandParser.registerCommand("drop", new DropCommand(gameState));
	    commandParser.registerCommand("attack", new AttackCommand(gameState));
	    commandParser.registerCommand("help", new HelpCommand(gameState));
	    commandParser.registerCommand("pickup", new PickupCommand(gameState));
	    commandParser.registerCommand("use", new UseCommand(gameState));

		input.requestFocus();
		input.clear();
		output.clear();
		map.clear();
		stats.clear();
		
		printIntro();
		updateMap();
		updateStatboard();
    }

	@Override
	public void teardown(){
		gameState = null;
		commandParser = null;

	}
}
