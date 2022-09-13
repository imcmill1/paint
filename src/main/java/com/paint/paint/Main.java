package com.paint.paint;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application { //creates main class extending Application
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("paint.fxml")); //creates an FXML loader object

        Scene scene = new Scene(fxmlLoader.load(), 1000, 500); //creates a new scene and loads to FXML file into it

        stage.setTitle("Paint"); //titles the window
        stage.setScene(scene); //sets the stage with the scene
        stage.show(); //shows the stage.
    }

    public static void main(String[] args) {
        launch();
    } //main only launches the application.
}