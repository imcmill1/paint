package com.paint.paint;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * <p>Main.java is solely used for launching the application window. It creates a scene,
 * loaded from the FXML file, sets the application tile, then shows the created stage. </p>
 * @since 0.0.1
 * @author ianmc
 */

public class Main extends Application { //creates main class extending Application

    /**
     * <p> The starting method for the application containing the tasks needed to open the
     * application window.</p>
     * @param stage the stage for the application
     * @throws IOException due to interfacing with file explorer i/o
     * @since 0.0.1
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("paint.fxml")); //creates an FXML loader object

        Scene scene = new Scene(fxmlLoader.load(), 1100, 925); //creates a new scene and loads to FXML file into it

        stage.setTitle("Paint"); //titles the window
        stage.setScene(scene); //sets the stage with the scene
        stage.show(); //shows the stage.
    }

    /**
     * <p> The main for the project. This single line main is run one time, and launches the application.</p>
     * @param args default string argument parameters for main()
     */
    public static void main(String[] args) {
        launch();
    } //main only launches the application.
}