/**
 * <p> The main package for this paint project. Contains all relevant class files. </p>
 * @since 0.0.1
 */
package com.paint.paint;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

//VERSION NUMBERING
/* With minor exception, version numbers will be assigned as follows:
   X.x.x - assigned to updates after completing a sprint
   x.X.x - assigned to feature additions within each sprint
   x.x.X - assigned to incremental updates to features
 */

//TO-DO LIST
/* SPRINT 6
    - Allow rotation of an image, as well as mirror it horizontally and vertically
    - Allow rotation of a selection

 * KNOWN ISSUES TO FIX:
    - Make Undo/Redo less janky. It works, but sometimes does unpredictable things.
    - Somehow fix copy/paste to use the system clipboard.
    - Fix timer display so that it displays only the current tab's autosave timer
*/

/**
 * <p>Controller.java is the class that the FXML file reads methods from, and contains wrappers
 * for all necessary methods. This class takes methods from other classes and "wraps" them in a controller
 * method, allowing them to be used with the FXML file. Besides this "wrapping" and the
 * initialization of FXML objects and running startup methods, no functional code should
 * be present in this file.</p>
 * @since 0.0.1
 * @author ianmc
 */

public class Controller {
    //=====FXML LOADS=====//
    /** imageTabs is the TabPane that contains each canvas for image editing*/
    @FXML public TabPane imageTabs;

    /** editToggles is the toggle group containing all toggle buttons on the menubar*/
    @FXML private ToggleGroup editToggles;

    /** widthChoice is a choice box that allows for line width selection*/
    @FXML private ChoiceBox widthChoice;

    /** colorPicker is the color picker that allows for line color selection*/
    @FXML private ColorPicker colorPicker;

    /** canvasSizeSlider is the slider allowing for resizing of the active canvas*/
    @FXML private Slider canvasSizeSlider;

    @FXML private CheckBox timerDispToggle;

    @FXML private Label timerDispBox;

    /** tabsOpened is an internal variable that ticks up every time a new canvas is opened*/
    private int tabsOpened = 1;

    public static String lastEvent;

    //=====FILE IO METHODS=====//
    //These are the methods from the fileIO static class. They have to be wrapped in a method here in the controller for them to be used
    //in FXML. Most are one line commands.

    /**
     * <p> This method is used to open an image in a new tab in the application. It calls
     * the createNewTab() method from the Display class, the open() method from the fileIO
     * class, and the showImage method from the Display class.</p>
     * @since 0.2.0
     * @throws IOException due to interfacing with file explorer i/o
     */
    @FXML
    protected void open() throws IOException { //wrapped open() function
        Display.createNewTab(imageTabs, "untitled" + tabsOpened, timerDispBox);
        String selectedImg = fileIO.open(imageTabs); //creates string selectedImg and assigns it to a call of fileIO.open()
        Display.showImage(Display.getActiveCanvas(), Display.getActiveCanvas().getGraphicsContext2D(), selectedImg); //passes that string as the path into showImage
        tabsOpened++;
        lastEvent = "Image Opened";
    }

    /**
     * <p> This method is used to save the image on the active canvas. It calls the fileIO
     * save() method using the Display getActiveCanvas() method as the input argument. It also
     * toggles a boolean within the Display class that tracks whether or not there are unsaved
     * changes.
     * </p>
     * @since 1.0.1
     * @throws IOException due to interfacing with file explorer i/o
     */
    @FXML
    protected void save() throws IOException {
        fileIO.save(Display.getActiveStack(), (imageTab) imageTabs.getSelectionModel().getSelectedItem());
        Display.setUnsavedChanges(false);
        lastEvent = "Save";
    }

    /**
     * <p> This method functions similarly to the save() method, the difference being that
     * saveAs() will open a file explorer instance asking the user to specify the directory they wish
     * to save in. This method calls the fileIO saveAs() method and also toggles the unsaved
     * changes boolean within Display.
     * </p>
     * @since 1.0.1
     * @throws IOException due to interfacing with file explorer i/o
     */
    @FXML
    protected void saveAs() throws IOException {
        fileIO.saveAs(Display.getActiveStack(), (imageTab) imageTabs.getSelectionModel().getSelectedItem());
        Display.setUnsavedChanges(false);
        lastEvent = "Save As";
    }

    //=====DISPLAY METHODS=====//
    //Methods from the Display class. Similar to above, these methods must be wrapped in a method inside the controller class.
    //Note: one exception is that currently the showImage() method is not wrapped. Instead, it is called directly from the Controller.open() method.

    /**
     * <p> This this method is called to create a new tab. It runs the Display method
     * createNewTab(), and then tries to run the Edit method cursorUpdate to update the cursor
     * to the active canvas. Finally, it increments the tabsOpened counter.
     * </p>
     * @since 2.1.0
     */
    @FXML
    protected void createNewTab() {
        Display.createNewTab(imageTabs, "untitled" + tabsOpened, timerDispBox);
        try {Edit.cursorUpdate(Display.getActiveCanvas(), Display.getActiveCanvas().getGraphicsContext2D(), editToggles, colorPicker);}

        catch (Exception e) {}
        tabsOpened++;
        lastEvent = "New Tab Created";
    }

    /**
     * <p> This method is attached to the clear button on the menubar, and calls the
     * clearActiveCanvas() from the Display class. </p>
     * @since 2.4.0
     */
    @FXML
    protected void clearActiveCanvas() {
        Display.clearActiveCanvas(imageTabs, Display.getActiveCanvas());
        lastEvent = "Canvas Clear";
    }

    /**
     * <p>This method is used to show a help dialogue window. It calls the Display method
     * helpShow using the imageTabs TabPane as input. </p>
     * @since 1.2.0
     */
    @FXML
    protected void helpShow() {Display.helpShow(imageTabs);}

    /**
     * <p>This method updates the size of the canvas after a resize from the slider. It calls
     * the Display method updateCanvasSize().</p>
     * @since 2.3.1
     */
    @FXML
    protected void canvasSizeUpdate() {
        Display.updateCanvasSize(Display.getActiveCanvas(), Display.getActiveCanvas().getGraphicsContext2D(), canvasSizeSlider.getValue());
        lastEvent = "Canvas Resize";
    }

    /**
     * <p> This method performs an undo action on the active stack. It calls the Display method undo(). </p>
     * @implNote <p>Currently, the undo/redo functionality relies on a single undo/redo stack. This means that all image tabs
     * SHARE one stack, and an action can be "undone" on one tab, and then "redone" on a different tab. </p>
     * @since 3.2.0
     */
    @FXML
    protected void undo() {
        Display.undo(Display.getActiveStack(), Display.getUndoStack());
        lastEvent = "Undo";
    }

    /**
     * <p> This method performs a redo action on the active stack. It calls the Display method redo(). </p>
     * @implNote <p>Currently, the undo/redo functionality relies on a single undo/redo stack. This means that all image tabs
     * SHARE one stack, and an action can be "undone" on one tab, and then "redone" on a different tab. </p>
     * @since 3.2.0
     */
    @FXML
    protected void redo() {
        Display.redo(Display.getActiveStack(), Display.getRedoStack());
        lastEvent = "Redo";
    }

    /**
     * <p> This method wraps the Display timerDisplayUpdate() method, and implements the functionality of the
     * timer display checkbox. </p>
     * @since 3.6.0
     */
    @FXML
    protected void timerDisplayUpdate() { Display.timerDisplayUpdate(timerDispToggle, timerDispBox); }

    //=====EDIT METHODS=====//
    //Methods from the Edit class. As with above, these methods must be wrapped in a controller method

    /**
     * <p> This method calls the Edit method updateWidth to update the line width of the graphics
     * context 'cursor'. Applies to freeDraw, shapes, and tools. </p>
     * @since 1.4.0
     */
    @FXML
    protected void updateWidth() {
        try {
            Edit.updateWidth(Display.getActiveCanvas().getGraphicsContext2D(), widthChoice);
            lastEvent = "Width Update";
        }

        catch (Exception e) {}
    }

    /**
     * <p> This method calls the Edit method updateColor() to change the color of the graphics
     * context 'cursor'. Applies to freeDraw, shapes, and tools. </p>
     */
    @FXML
    protected void updateColor() {
        try {
            Edit.updateColor(Display.getActiveCanvas().getGraphicsContext2D(), colorPicker);
            lastEvent = "Color Update";
        }

        catch (Exception e) {}
    }

    /**
     * <p>This method updates the cursor so that the selected tool from the menubar is applied to the
     * cursor. This also sets the unsavedChanges boolean so that aware save functions correctly. It calls
     * the Display method setUnsavedChanges and the Edit method cursorUpdate./p>
     * @since 2.1.1
     */
    @FXML
    protected void cursorUpdate() {
        try {
            Display.setUnsavedChanges(true);
            Edit.updateWidth(Display.getActiveCanvas().getGraphicsContext2D(), widthChoice);
            Edit.updateColor(Display.getActiveCanvas().getGraphicsContext2D(), colorPicker);
            lastEvent = Edit.cursorUpdate(Display.getActiveCanvas(), Display.getActiveCanvas().getGraphicsContext2D(), editToggles, colorPicker);
        }

        catch (Exception e) {}
    }

    /**
     * <p> This method wraps the now deprecated rotateClockwise90() method in the Edit class. Its functionality
     * is bugged and is not slated to be fixed, so this method is also considered deprecated. </p>
     * @deprecated
     * @since 4.1.0
     */
    @FXML
    protected void rotateClockwise90() {
        Edit.rotateClockwise90(Display.getActiveCanvas());
    }

    /**
     * <p> This method wraps the now deprecated rotateCounterClockwise90() method in the Edit class. Its functionality
     * is bugged and is not slated to be fixed, so this method is also considered deprecated. </p>
     * @deprecated
     * @since 4.1.0
     */
    @FXML
    protected void rotateCounterClockwise90() {
        Edit.rotateCounterClockwise90(Display.getActiveCanvas());
    }

    //=====INITIALIZE=====//
    //Initialize method for things not directly injected into FXML.
    //Direct injection is preferred.

    /**
     * <p> This method is used for one-time setup methods called at startup. It calls
     * the Menu methods widthChoiceConfig and colorPickerConfig to set up the color picker
     * and width picker. It also sets the firstTab boolean, and the Display method
     * createNewTab so that the application opens a blank tab on startup.</p>
     * @since 1.4.0
     */
    public void initialize() throws IOException {
        Menu.widthChoiceConfig(widthChoice);
        Menu.colorPickerConfig(colorPicker);
        Display.firstTab = true;
        Display.createNewTab(imageTabs, "untitled", timerDispBox);
        Menu.timerDisplayConfig(timerDispBox);
        File logFile = new File("C:\\Users\\ianmc\\IdeaProjects\\paint\\src\\logfiles\\eventLog.txt");
        logFile.createNewFile();
        FileWriter wiper = new FileWriter("C:\\Users\\ianmc\\IdeaProjects\\paint\\src\\logfiles\\eventLog.txt", false);
        wiper.flush();
        Timer logger = new Timer(true);
        logger.scheduleAtFixedRate( new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    try {
                        FileWriter fw = new FileWriter("C:\\Users\\ianmc\\IdeaProjects\\paint\\src\\logfiles\\eventLog.txt", true);
                        BufferedWriter bw = new BufferedWriter(fw);
                        PrintWriter pw = new PrintWriter(bw);
                        pw.println("Most recent event: " + lastEvent + " as of " + LocalDateTime.now());
                        pw.flush();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        },0,5000);  // start immediately, run every second
        /*
        paintTest.getPolygonXSidesTest();
        paintTest.getPolygonYSidesTest();
        paintTest.canvasRescaleTest();
        */
    }
}