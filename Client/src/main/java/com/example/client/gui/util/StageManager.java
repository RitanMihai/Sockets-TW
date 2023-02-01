package com.example.client.gui.util;

import com.example.client.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class StageManager {
    private static Stage stage;

    public static void setPageTitle(String title){
        if(Objects.isNull(stage))
            return;
        stage.setTitle(title);
    }

    public static void setPageIcon(String iconName) {
        stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("images/" + iconName))));
    }

    public static Parent replaceSceneContent(String fxml) throws Exception {
        Parent page = new  FXMLLoader(Main.class.getResource(fxml)).load();
        Scene scene = stage.getScene();
        if (scene == null) {
            scene = new Scene(page/*, 700, 450 */);
            stage.setScene(scene);
        } else {
            stage.getScene().setRoot(page);
        }
        stage.sizeToScene();
        return page;
    }

    public static void setStage(Stage stage){
        StageManager.stage = stage;
    }

    public static Stage getStage(){
        return StageManager.stage;
    }
}
