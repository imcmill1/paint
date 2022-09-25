package com.paint.paint;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

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

/**@TO-DO-LIST
/*
 * resize canvas (for larger drawing)
 * smart/aware save ("you're about to close without saving...")
 * KNOWN ISSUES TO FIX:
 *  - Open and createNewTab should both make the newest tab the active tab
 *  - Add custom icons for the shapes and tools toolbar
*/

public class Controller { //static controller class
    //=====FXML LOADS=====//
    @FXML private BorderPane rootPane;
    @FXML private TabPane menuTabs;
    @FXML private TabPane imageTabs;
    @FXML private MenuButton fileButton;
    @FXML private ToggleGroup editToggles;
    @FXML private ChoiceBox widthChoice;
    @FXML private ColorPicker colorPicker;

    private int tabsOpened = 1;

    //=====FILE IO METHODS=====//
    //These are the methods from the fileIO static class. They have to be wrapped in a method here in the controller for them to be used
    //in FXML. Most are one line commands.
    @FXML
    protected void open() throws IOException { //wrapped open() function
        Display.createNewTab(imageTabs, "untitled" + tabsOpened);
        String selectedImg = fileIO.open(); //creates string selectedImg and assigns it to a call of fileIO.open()
        Display.showImage(Display.getActiveCanvas(), Display.getActiveCanvas().getGraphicsContext2D(), selectedImg); //passes that string as the path into showImage
        tabsOpened++;
    }

    @FXML
    protected void save() throws IOException { fileIO.save(Display.getActiveCanvas());}

    @FXML
    protected void saveAs() throws IOException { fileIO.saveAs(Display.getActiveCanvas());}

    //=====DISPLAY METHODS=====//
    //Methods from the Display class. Similar to above, these methods must be wrapped in a method inside the controller class.
    //Note: one exception is that currently the showImage() method is not wrapped. Instead, it is called directly from the Controller.open() method.

    @FXML
    protected void createNewTab() {
        Display.createNewTab(imageTabs, "untitled" + tabsOpened);
        try {Edit.cursorUpdate(Display.getActiveCanvas(), Display.getActiveCanvas().getGraphicsContext2D(), editToggles, colorPicker);}

        catch (Exception e) {}
        tabsOpened++;
    }

    @FXML
    protected void clearActiveCanvas() {
        Display.clearActiveCanvas(Display.getActiveCanvas());
    }

    @FXML
    protected void helpShow() {Display.helpShow(imageTabs);}

    //=====EDIT METHODS=====//
    //Methods from the Edit class. As with above, these methods must be wrapped in a controller method

    @FXML
    protected void updateWidth() {
        try {Edit.updateWidth(Display.getActiveCanvas().getGraphicsContext2D(), widthChoice);}

        catch (Exception e) {}
    }

    @FXML
    protected void updateColor() {
        try {Edit.updateColor(Display.getActiveCanvas().getGraphicsContext2D(), colorPicker);}

        catch (Exception e) {}
    }

    @FXML
    protected void cursorUpdate() {
        try {Edit.cursorUpdate(Display.getActiveCanvas(), Display.getActiveCanvas().getGraphicsContext2D(), editToggles, colorPicker);}

        catch (Exception e) {}
    }

    //=====INITIALIZE=====//
    //placeholder initialize method for things not directly injected into FXML.
    //Direct injection is preferred.
    public void initialize() {
        Menu.widthChoiceConfig(widthChoice);
        Menu.colorPickerConfig(colorPicker);
        Display.firstTab = true;
        Display.createNewTab(imageTabs, "untitled");
    }
}