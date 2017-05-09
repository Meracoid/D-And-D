package com.dd;

import com.dd.SceneControllerTuple;
import com.dd.controller_util.ControllerArgumentPackage;

import java.io.*;
import java.lang.IllegalArgumentException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DandD extends Application {
    private static Stage stage;
    private static Map<String, SceneControllerTuple> gameSceneControllerMap = new HashMap<String, SceneControllerTuple>();
    private static UUID gameUUID;

    public static void main(String[] args) {
    	launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            File uuidFile = new File("GameUUID.ser.uuid");
            FileInputStream fis = new FileInputStream(uuidFile);
            ObjectInputStream in = new ObjectInputStream(fis);
            gameUUID = (UUID)in.readObject();
            in.close();
        }
        catch (IOException | ClassNotFoundException e) {
            gameUUID = UUID.randomUUID();
            FileOutputStream fos = null;
            ObjectOutputStream out = null;
            try {
                String filename = "GameUUID.ser.uuid";
                fos = new FileOutputStream(filename);
                out = new ObjectOutputStream(fos);
                out.writeObject(gameUUID);
                out.close();
             }
            catch (IOException ioE) {
                System.exit(1);
            }
        }

        try {
            stage = primaryStage;
            stage.setWidth(1280);
            stage.setHeight(720);

            addGameSceneControllerTuple("AddServerScene",
                                        generateSceneControllerTuple(getClass().getResource("/com/dd/fxml/AddServer.fxml")));
            addGameSceneControllerTuple("CharacterCreationScene",
                                        generateSceneControllerTuple(getClass().getResource("/com/dd/fxml/CharacterCreation.fxml")));
            addGameSceneControllerTuple("JoinGameScene",
                                        generateSceneControllerTuple(getClass().getResource("/com/dd/fxml/JoinMenu.fxml")));
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

    public static UUID getGameUUID(){
        return gameUUID;
    }
}