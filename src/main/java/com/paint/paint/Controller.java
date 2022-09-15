package com.paint.paint;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.StackPane;
import java.io.IOException;

//VERSION NUMBERING
/* With minor exception, version numbers will be assigned as follows:
   X.x.x - assigned to updates after completing a sprint
   x.X.x - assigned to feature additions within each sprint
   x.x.X - assigned to incremental updates to features
 */

/*======Controller Class======*/
/*
    The purpose of this class is to wrap methods from other classes
    and pass them to FXML. Beyond initialization of objects and other
    overhead that cannot be completed in Main(), there should be no
    backend code in this class.
 */

//TO DO LIST:
/*
- Implement sprint 2 features:
    - Control width of line drawn (line weight)
*/

public class Controller { //static controller class
    //=====FXML LOADS=====//
    @FXML private StackPane displayPane; //inject the stackpane for the display
    @FXML private ScrollPane scrollPane;
    @FXML private Canvas baseCanvas; //injects canvas and creates graphics context variable.
    @FXML private ToggleButton drawToggleButton;

    @FXML private ChoiceBox widthChoice;
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
    protected void newCanvas() { Display.newCanvas(baseCanvas, GraphContext);}

    @FXML
    protected void helpShow() {Display.helpShow(displayPane);}

    //=====EDIT METHODS=====//
    //Methods from the Edit class. As with above, these methods must be wrapped in a controller method

    @FXML
    protected void drawUpdate() {
        //Edit.drawToggle(drawToggleButton);
        Edit.drawUpdate(baseCanvas, GraphContext, drawToggleButton);
    }

    //=====INITIALIZE=====//
    //placeholder initialize method for things not directly injected into FXML.
    //Direct injection is preferred.
    public void initialize() {
        GraphContext = baseCanvas.getGraphicsContext2D();
        scrollPane.setContent(baseCanvas);
        drawToggleButton.setSelected(false);
    }
}