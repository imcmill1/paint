package com.paint.paint;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import java.io.IOException;

//TO DO LIST:
/*
- Fix display so images can't overlap the toolbar
- Pin image to top left corner of stackpane to mimic paint display
- Remove close, closeAll, reimplement close as 'new' function that clears the canvas
- Implement sprint 2 features:
    - Draw a line
    - Save image with that line on it
    - DONE: Canvas that resizes to image being opened
    - Add scroll bars to navigate left/right and up/down
    - Control width of line drawn (line weight)
    - Have a color chooser, does not need to change color of line yet
    - Have a help menu button with "Help and about" options
*/

public class Controller { //static controller class
    //=====FXML LOADS=====//
    @FXML private StackPane displayPane; //inject the stackpane for the display
    @FXML
    private Canvas baseCanvas; //injects canvas and creates graphics context variable.
    private GraphicsContext GraphContext;

    //=====FILE IO METHODS=====//
    //These are the methods from the fileIO static class. They have to be wrapped in a method here in the controller for them to be used
    //in FXML. Most are one line commands.
    @FXML
    protected void open() throws IOException { //wrapped open() function
        String selectedImg = fileIO.open(); //creates string selectedImg and assigns it to a call of fileIO.open()
        Display.showImage(baseCanvas, GraphContext, selectedImg); //passes that string as the path into showImage
    }

    @FXML
    protected void save() throws IOException { fileIO.save(baseCanvas);}

    @FXML
    protected void saveAs() throws IOException { fileIO.saveAs(baseCanvas);}

    //=====DISPLAY METHODS=====//
    //Methods from the Display class. Similar to above, these methods must be wrapped in a method inside the controller class.
    //Note: one exception is that currently the showImage() method is not wrapped. Instead, it is called directly from the Controller.open() method.
    @FXML
    protected void newCanvas() { Display.newCanvas(displayPane);}


    //=====INITIALIZE=====//
    //placeholder initialize method for things not directly injected into FXML.
    //Direct injection is preferred.
    public void initialize() {
        GraphContext = baseCanvas.getGraphicsContext2D();
    }
}