package com.dd.controller_util.controller;

import com.dd.DandD;
import com.dd.GameState;
import com.dd.controller_util.ControllerArgumentPackage;
import com.dd.controller_util.GameSceneController;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class LoadGameController extends GameSceneController{
	
	@FXML private ListView<String> fileList;
	@FXML private Button loadButton;
	@FXML private Button backButton;
	@FXML private Label errorLabel;
	
	private List<GameState> gamestates;
	
	/**
	 * Event handler for "Load Game" button.
	 */
	@FXML
	private void handleLoadButtonAction(ActionEvent event) {
		int selectedIndex = fileList.getSelectionModel().getSelectedIndex();
		
		if (selectedIndex < 0) {
			errorLabel.setText("Please select a file.");
			return;
		}
		
		GameState game = gamestates.get(selectedIndex);
		
		ControllerArgumentPackage args = new ControllerArgumentPackage();
		args.setArgument("GameState", game);

		DandD.setActiveGameScene("RunningGameScene", args);
	}
	
	/**
	 * Event handler for "Back" button.
	 */
	@FXML
	private void handleBackButtonAction(ActionEvent event) {
		DandD.setActiveGameScene("MainMenuScene", null);
	}
	
	/**
	 * Adds the list of GameStates to the ListView.
	 */
	private void populateListView() {
		// Get all of the .json files
		File[] files = getFiles(System.getProperty("user.dir"), ".json");
		
		// Load up all the save files and stores them in the GameState ArrayList
		this.gamestates = new ArrayList<>();
		for (File file : files) {
			
			try {
				FileReader filereader = new FileReader(file);
				gamestates.add(new Gson().fromJson(filereader, GameState.class));
			}
			catch (FileNotFoundException e) {
				errorLabel.setText("Error opening a file.");
				return;
			}
		}
		
		// Now populate the ListView
		for (GameState gamestate : gamestates) {
			String description = gamestate.getName() + ": "
							   + gamestate.getActivePlayer(gamestate.getPlayerType()).getName() + " "
							   + gamestate.getMap().getSeed();
			
			fileList.getItems().add(description);
		}
	}
	
	/**
	 * Gets the list of files with a given extension.
	 */
	private File[] getFiles(String filepath, String extension) {
		// Get the folder path
		File folder = new File(filepath);
		
		// Get all files with the given extension
		File[] files = folder.listFiles(new FilenameFilter() {
			public boolean accept(File folder, String name) {
				return name.toLowerCase().endsWith(extension);
			}
		});
		
		return files;
	}
	
	/**
	 * Called when fxml document is loaded.
	 */
	public void initialize() {
		
	}

	@Override
	public void setup(ControllerArgumentPackage args){
		gamestates = null;
		fileList.getItems().clear();
		errorLabel.setText("");
		populateListView();
	}

	@Override
	public void teardown(){

	}
}
