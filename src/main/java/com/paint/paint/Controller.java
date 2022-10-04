/**
 * <p> The main package for this paint project. Contains all relevant class files. </p>
 * @since 0.0.1
 */
package com.paint.paint;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

//VERSION NUMBERING
/* With minor exception, version numbers will be assigned as follows:
   X.x.x - assigned to updates after completing a sprint
   x.X.x - assigned to feature additions within each sprint
   x.x.X - assigned to incremental updates to features
 */

/*TO-DO-LIST
    SPRINT 4:
    - Undo/Redo using at least one Stack
    - Be able to draw a regular sided polygon with any number of sides

    SPRINT 5:
    - Add at least 3 unit tests (trivial is OK!)
    - Timer that allows for autosave
    - Timer's countdown shall be visible to the user, with view toggleable on/off
    - There shall be SOME Javadoc commenting
    - Ability to save in an alternate file format than the file was originally
        - There will be a warning when converting file types that data may be lost


 * KNOWN ISSUES TO FIX:
 *  - Open and createNewTab should both make the newest tab the active tab
 *  - Add custom icons for the shapes and tools toolbar
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
    @FXML private TabPane imageTabs;

    /** editToggles is the toggle group containing all toggle buttons on the menubar*/
    @FXML private ToggleGroup editToggles;

    /** widthChoice is a choice box that allows for line width selection*/
    @FXML private ChoiceBox widthChoice;

    /** colorPicker is the color picker that allows for line color selection*/
    @FXML private ColorPicker colorPicker;

    /** canvasSizeSlider is the slider allowing for resizing of the active canvas*/
    @FXML private Slider canvasSizeSlider;

    /** tabsOpened is an internal variable that ticks up every time a new canvas is opened*/
    private int tabsOpened = 1;


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
        Display.createNewTab(imageTabs, "untitled" + tabsOpened);
        String selectedImg = fileIO.open(); //creates string selectedImg and assigns it to a call of fileIO.open()
        Display.showImage(Display.getActiveCanvas(), Display.getActiveCanvas().getGraphicsContext2D(), selectedImg); //passes that string as the path into showImage
        tabsOpened++;
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
        fileIO.save(Display.getActiveCanvas());
        Display.setUnsavedChanges(false);
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
        fileIO.saveAs(Display.getActiveCanvas());
        Display.setUnsavedChanges(false);
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
        Display.createNewTab(imageTabs, "untitled" + tabsOpened);
        try {Edit.cursorUpdate(Display.getActiveCanvas(), Display.getActiveCanvas().getGraphicsContext2D(), editToggles, colorPicker);}

        catch (Exception e) {}
        tabsOpened++;
    }

    /**
     * <p> This method is attached to the clear button on the menubar, and calls the
     * clearActiveCanvas() from the Display class. </p>
     * @since 2.4.0
     */
    @FXML
    protected void clearActiveCanvas() {
        Display.clearActiveCanvas(imageTabs, Display.getActiveCanvas());
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
    protected void canvasSizeUpdate() {Display.updateCanvasSize(Display.getActiveCanvas(), Display.getActiveCanvas().getGraphicsContext2D(), canvasSizeSlider);}

    //=====EDIT METHODS=====//
    //Methods from the Edit class. As with above, these methods must be wrapped in a controller method

    /**
     * <p> This method calls the Edit method updateWidth to update the line width of the graphics
     * context 'cursor'. Applies to freeDraw, shapes, and tools. </p>
     * @since 1.4.0
     */
    @FXML
    protected void updateWidth() {
        try {Edit.updateWidth(Display.getActiveCanvas().getGraphicsContext2D(), widthChoice);}

        catch (Exception e) {}
    }

    /**
     * <p> This method calls the Edit method updateColor() to change the color of the graphics
     * context 'cursor'. Applies to freeDraw, shapes, and tools. </p>
     */
    @FXML
    protected void updateColor() {
        try {Edit.updateColor(Display.getActiveCanvas().getGraphicsContext2D(), colorPicker);}

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
            Edit.cursorUpdate(Display.getActiveCanvas(), Display.getActiveCanvas().getGraphicsContext2D(), editToggles, colorPicker);
        }

        catch (Exception e) {}
    }

    //=====INITIALIZE=====//
    //placeholder initialize method for things not directly injected into FXML.
    //Direct injection is preferred.

    /**
     * <p> This method is used for one-time setup methods called at startup. It calls
     * the Menu methods widthChoiceConfig and colorPickerConfig to set up the color picker
     * and width picker. It also sets the firstTab boolean, and the Display method
     * createNewTab so that the application opens a blank tab on startup.</p>
     * @since 1.4.0
     */
    public void initialize() {
        Menu.widthChoiceConfig(widthChoice);
        Menu.colorPickerConfig(colorPicker);
        Display.firstTab = true;
        Display.createNewTab(imageTabs, "untitled");
    }
}