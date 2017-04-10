package com.dd;

import com.dd.SceneControllerTuple;
import com.dd.controller_util.ControllerArgumentPackage;
import java.io.IOException;
import java.lang.IllegalArgumentException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DandD extends Application {
    private static Stage stage;
    private static Map<String, SceneControllerTuple> gameSceneControllerMap = new HashMap<String, SceneControllerTuple>();

    public static void main(String[] args) {
    	launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            stage = primaryStage;
            stage.setWidth(1280);
            stage.setHeight(720);

            addGameSceneControllerTuple("AddServerScene",
                                       generateSceneControllerTuple(new URL("/com/dd/fxml/AddScene.fxml")));
            addGameSceneControllerTuple("CharacterCreationScene",
                                        generateSceneControllerTuple(new URL("/com/dd/fxml/CharacterCreation.fxml")));
            addGameSceneControllerTuple("JoinGameScene",
                                        generateSceneControllerTuple(new URL("/com/dd/fxml/JoinMenu.fxml")));
            addGameSceneControllerTuple("LoadGameScene",
                                        generateSceneControllerTuple(getClass().getResource("/com/dd/fxml/LoadGame.fxml")));
            addGameSceneControllerTuple("MainMenuScene",
                                        generateSceneControllerTuple(getClass().getResource("/com/dd/fxml/MainMenu.fxml")));
            addGameSceneControllerTuple("NewGameScene",
                                        generateSceneControllerTuple(getClass().getResource("/com/dd/fxml/NewGame.fxml")));
            addGameSceneControllerTuple("RunningGameScene",
                                        generateSceneControllerTuple(getClass().getResource("/com/dd/fxml/RunningGame.fxml")));
            
            primaryStage.setTitle("D&D");
            setActiveGameScene("MainMenuScene", null);
            primaryStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        catch (IllegalStateException e) {
            e.printStackTrace();
            System.exit(2);
        }
    }

    private static SceneControllerTuple generateSceneControllerTuple(URL fxml) throws IOException{
        FXMLLoader loader = new FXMLLoader(fxml);
        SceneControllerTuple tuple = new SceneControllerTuple(new Scene(loader.load()), loader.getController());
        return tuple;
    }

    private static void addGameSceneControllerTuple(String name, SceneControllerTuple tuple) {
        if(gameSceneControllerMap.containsKey(name))
            throw new IllegalArgumentException("GameScene \""
                    + name
                    + "\" is already registered with the GameRunner. Registration failed.");
        gameSceneControllerMap.put(name, tuple);
    }

    public static void setActiveGameScene(String name, ControllerArgumentPackage args) {
        SceneControllerTuple tuple = gameSceneControllerMap.get(name);
        if(tuple == null)
            throw new IllegalArgumentException("GameScene \""
                    + name
                    + "\" is not registered with the GameRunner. GameScene unchanged.");
        tuple.getController().setup(args);
        stage.setScene(tuple.getScene());
    }
}