package com.example.client;

import com.example.client.gui.util.StageManager;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
    //SocketManager socketManager;

    @Override
    public void start(Stage stage) throws Exception {
        StageManager.setStage(stage);
        StageManager.setPageTitle("Login");
        StageManager.replaceSceneContent("login.fxml");
        StageManager.getStage().show();
    }

    public static void main(String[] args) {
        launch();
    }
}