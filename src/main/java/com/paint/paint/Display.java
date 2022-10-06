package com.paint.paint;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> Display.java handles displaying content in the application, including images,
 * canvases, popup windows, etc. </p>
 * @since 0.2.0
 * @author ianmc
 */

public class Display {
    /** baseHeight double to store the starting height of canvases.*/
    static double baseHeight = 625;

    /** baseWidth double to store the starting width of canvases.*/
    static double baseWidth = 1000;

    /** activeCanvas object to store which canvas is currently active for editing.*/
    public static Canvas activeCanvas;

    /** activeStack object to store which stack is currently active. */
    public static StackPane activeStack;

    /** firstTab boolean stores if there has been a tab created or not.*/
    public static boolean firstTab;

    /** unsavedChanges boolean tracks whether changes have been made since the last save.*/
    public static boolean unsavedChanges = false;

    public static ArrayList<Image> undoStack = new ArrayList<Image>(29);

    public static ArrayList<Image> redoStack = new ArrayList<Image>(29);

    public static int undoIndex = 0;

    public static int redoIndex = 0;

    /**
     * <p>This method sets the unsaved changes boolean to true or false. This is set by the
     * Controller cursorUpdate() method and reset by the Controller save() and saveAs() methods </p>
     * @param condition the input argument, either true or false, that unsavedChanges will be set to.
     * @since 2.3.1
     */
    public static void setUnsavedChanges(boolean condition) {
        unsavedChanges = condition;
    }

    /**
     * <p> This method simply gets the status of the unsavedChanges boolean for use in
     * other methods. </p>
     * @return the status of the unsavedChanges boolean.
     * @since 2.3.1
     */
    public static boolean getUnsavedChanges() {
        return unsavedChanges;
    }

    /**
     * <p> This method is called to show a warning dialogue window when you try to close a tab with
     * unsaved changes. It takes in a parent tab pane and the active tab.</p>
     * @param tabPane Used for finding the parent stage for the window to be displayed in.
     * @param activeTab Used so that the user can override the message and close the tab.
     * @since 2.3.1
     */
    public static void showSaveWarning(TabPane tabPane, Tab activeTab) {
        Stage unsavedDiag = new Stage();
        unsavedDiag.setTitle("Paint");
        Stage ownerStage = (Stage) tabPane.getScene().getWindow();
        unsavedDiag.initModality(Modality.APPLICATION_MODAL);
        unsavedDiag.initOwner(ownerStage);

        StackPane basePane = new StackPane();

        VBox warnMsg = new VBox(20);
        warnMsg.setAlignment(Pos.CENTER);
        warnMsg.getChildren().add(new Text("You have unsaved changes!\n Are you sure you want to close this tab?"));
        warnMsg.setTranslateY(-25);

        Button yes = new Button("Yes");
        yes.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                unsavedDiag.close();
                tabPane.getTabs().remove(activeTab);
            }
        });
        yes.setTranslateX(-50);
        yes.setTranslateY(25);

        Button no = new Button("No");
        no.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                unsavedDiag.close();
            }
        });
        no.setTranslateX(50);
        no.setTranslateY(25);

        basePane.getChildren().addAll(warnMsg, yes, no);

        Scene warnScene = new Scene(basePane, 300, 100);
        unsavedDiag.setScene(warnScene);
        unsavedDiag.show();
    }

    /**
     * <p>This method is used to display an image on the active canvas. It takes in the canvas
     * for the image to be displayed on, the graphics context of that canvas, and the path of the
     * image to be opened.</p>
     * @param canvas the canvas that the image will be displayed on
     * @param graphContext the graphics context of that canvas, for drawing the image
     * @param path the path of the image file
     * @since 0.2.0
     */
    public static void showImage(Canvas canvas, GraphicsContext graphContext, String path) { //method to show an image
       try { //try block catches an empty path being passed in. if path is null, goes to an empty catch
           Image img = new Image(path); //creates a new Image object using the filepath passed in from the controller
           canvas.setHeight(img.getHeight()); //resize canvas height to size of image
           canvas.setWidth(img.getWidth()); //resize canvas width to size of image
           graphContext.drawImage(img, 0,0); //draw that image on the canvas
       }

       catch (Exception e) {
           //empty catch so that nothing happens when an empty path is received, or an improper filetype is received.
           //prevents console from throwing hella errors.
       }
    }

    /**
     * <p> This method is part of a group of several methods all used internally within the
     * createNewTab() method. This method creates a new tab, names it, sets closing behavior,
     * and adds it to the parent TabPane.</p>
     * @param tabPane the TabPane for the tab to be added to.
     * @param name the name that the tab will be given.
     * @return newTab, the newly created tab.
     * @since 2.1.1
     */
    public static imageTab newTab(TabPane tabPane, String name) { //creates a new tab and attaches it to the tabPane
        imageTab newTab = new imageTab();
        newTab.setText(name);
        newTab.setOnCloseRequest(e -> {
            if(getUnsavedChanges() == true) {
                e.consume();
                showSaveWarning(tabPane, newTab);
            }
            else;
        });
        tabPane.getTabs().add(newTab);
        return newTab;
    }

    /**
     * <p> This method is part of a group of several methods all used internally within the
     * createNewTab() method. This particular method creates a new ScrollPane within a given tab.
     * This method creates a new ScrollPane, and adds it to a pre-existing tab. It also adds a listener
     * to whether or not the tab is selected, for use in determining the active canvas.</p>
     * @param tab the tab for the ScrollPane to be added to
     * @return newScroll, the newly created ScrollPane
     * @since 2.1.1
     */
    public static ScrollPane newScroll(Tab tab) {
        ScrollPane newScroll = new ScrollPane();
        tab.setContent(newScroll);
        tab.selectedProperty().addListener((observableValue, wasSelected, isSelected) -> {
            if (isSelected) { setActives(tab); }
        });
        return newScroll;
    }

    /**
     * <p>This method is part of a group of several methods all used internally within the
     * createNewTab() method. This particular method creates a new StackPane and places it on a
     * pre-existing ScrollPane. </p>
     * @param scrollPane the ScrollPane for the StackPane to be added to.
     * @return newStack, the newly created StackPane.
     * @since 2.4.0
     */
    public static StackPane newStack(ScrollPane scrollPane) {
        StackPane newStack = new StackPane();
        scrollPane.setContent(newStack);
        return newStack;
    }

    /**
     * <p>This method is part of a group of several methods all used internally within the
     * createNewTab() method. This particular method creates a new Canvas to be added to a
     * pre-existing StackPane. This method defines the starting parameters for the Canvas,
     * as well as setting a new cursor for when the mouse cursor is over the Canvas. </p>
     * @param stackPane the StackPane for the Canvas to be added to.
     * @return newCanvas, the newly created canvas.
     * @since 2.1.1
     */
    public static Canvas newBaseCanvas(StackPane stackPane){ //closeLast method will close the image on the top layer of the canvas
        Canvas newCanvas = new Canvas();
        newCanvas.setHeight(baseHeight);
        newCanvas.setWidth(baseWidth);
        newCanvas.getGraphicsContext2D().setFill(Color.WHITE);
        newCanvas.getGraphicsContext2D().fillRect(0, 0, newCanvas.getWidth(), newCanvas.getHeight());
        newCanvas.setCursor(Cursor.CROSSHAIR);
        stackPane.getChildren().add(newCanvas);
        return newCanvas;
    }

    public static Canvas newStackCanvas(StackPane stackPane, double height, double width){ //closeLast method will close the image on the top layer of the canvas
        Canvas newCanvas = new Canvas();
        newCanvas.setHeight(height);
        newCanvas.setWidth(width);
        newCanvas.setCursor(Cursor.CROSSHAIR);
        stackPane.getChildren().add(newCanvas);
        return newCanvas;
    }

    /**
     * <p>This method uses the newTab, newScroll, newStack, and newCanvas methods sequentially to
     * create a new tab and add the appropriate components to it for correct functioning of the
     * application. </p>
     * @param tabPane the parent TabPane for the tab to be created.
     * @param tabName the name that will be given to the tab when it is created.
     * @since 2.1.1
     */
    public static void createNewTab(TabPane tabPane, String tabName) {
        imageTab currTab = newTab(tabPane, tabName); //makes a new tab and adds it to the tabpane
        ScrollPane currScroll = newScroll(currTab); //adds a scrollpane to that tab
        StackPane currStack = newStack(currScroll); //adds a stackpane to that scrollpane
        Canvas currCanvas = newBaseCanvas(currStack); //adds a base canvas to that stackpane (Ho-ro, the rattlin' bog, the bog down in the valley-o....look it up)
        final EventHandler<MouseEvent> mouseRel = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseRel) {
                WritableImage snap = new WritableImage((int) activeCanvas.getWidth(), (int) activeCanvas.getHeight());
                activeCanvas.snapshot(null, snap);
                undoStack.add(snap);
                undoIndex++;
                if (undoIndex == 30) undoIndex = 0;
                redoStack.clear();
                redoIndex = 0;
            }
        };
        currTab.setParentTabPane(tabPane);
        currTab.setScrollPane(currScroll);
        currTab.setStackPane(currStack);
        currTab.setCanvas(currCanvas);
        currStack.setOnMouseReleased(mouseRel);
        tabPane.getSelectionModel().select(currTab);
        if (firstTab = true) {
            activeCanvas = currCanvas;
            activeStack = currStack;
            firstTab = false;
        }
    }

    /**
     * <p>This method sets the active canvas to the one on the top of the StackPane, and the
     * active stackpane to the one belonging to the currently selected tab.</p>
     * @param tab the current selected tab.
     * @since 3.1.1
     */
    public static void setActives(Tab tab) {
        ScrollPane activeScrollPane = (ScrollPane) tab.getContent();
        StackPane activeStackPane = (StackPane) activeScrollPane.getContent();
        ObservableList stackList = activeStackPane.getChildren();
        activeCanvas = (Canvas) stackList.get(stackList.size()-1);
        activeStack = activeStackPane;
    }

    /**
     * <p> This method returns the canvas that is currently active for editing. </p>
     * @return activeCanvas, the current active canvas for editing.
     * @since 2.1.1
     */
    public static Canvas getActiveCanvas() {return activeCanvas;}

    /**
     * <p> This method returns the stackpane that is currently active for editing. </p>
     * @return activeStack, the current active StackPane for editing.
     * @since 3.1.1
     */
    public static StackPane getActiveStack() { return activeStack; }

    /**
     * <p> This method takes in the parent TabPane and the active canvas, and will clear the
     * active canvas of all images and drawn objects. It will display a warning message asking
     * the user if they are sure they want to clear the canvas.</p>
     * @param tabPane the parent TabPane for the warning message to be displayed in.
     * @param activeCanvas the canvas to be cleared.
     */
    public static void clearActiveCanvas(TabPane tabPane, Canvas activeCanvas) {
        Stage clearWarn = new Stage();
        clearWarn.setTitle("Paint");
        Stage ownerStage = (Stage) tabPane.getScene().getWindow();
        clearWarn.initModality(Modality.APPLICATION_MODAL);
        clearWarn.initOwner(ownerStage);

        StackPane basePane = new StackPane();

        VBox warnMsg = new VBox(20);
        warnMsg.setAlignment(Pos.CENTER);
        warnMsg.getChildren().add(new Text("Are you sure you want to clear the canvas?"));
        warnMsg.setTranslateY(-25);

        Button yes = new Button("Yes");
        yes.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                activeCanvas.getGraphicsContext2D().setFill(Color.WHITE);
                activeCanvas.getGraphicsContext2D().fillRect(0, 0, activeCanvas.getWidth(), activeCanvas.getHeight());
                clearWarn.close();
            }
        });
        yes.setTranslateX(-50);
        yes.setTranslateY(25);

        Button no = new Button("No");
        no.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                clearWarn.close();
            }
        });
        no.setTranslateX(50);
        no.setTranslateY(25);

        basePane.getChildren().addAll(warnMsg, yes, no);
        Scene warnScene = new Scene(basePane, 300, 100);
        clearWarn.setScene(warnScene);
        clearWarn.show();

    }

    /**
     * <p> This method is used to update the size of the active canvas based on the position of the
     * resizing slider. It normalizes the slider's range of -1 to +1, and uses it as a scaling factor
     * for the base width and height to be multiplied by.</p>
     * @param activeCanvas the active canvas to be resized.
     * @param graphContext the graphics context of the active canvas.
     * @param canvasSizeSlider the slider for resizing the canvas.
     * @since 2.3.1
     */
    public static void updateCanvasSize(Canvas activeCanvas, GraphicsContext graphContext, Slider canvasSizeSlider) {
        double scale = canvasSizeSlider.getValue() + 1;
        if (scale != 0) {
            activeCanvas.setWidth(scale*baseWidth);
            activeCanvas.setHeight(scale*baseHeight);
            activeCanvas.getGraphicsContext2D().setFill(Color.WHITE);
            activeCanvas.getGraphicsContext2D().fillRect(0, 0, activeCanvas.getWidth(), activeCanvas.getHeight());
        }

        else {
            activeCanvas.setWidth(baseWidth);
            activeCanvas.setHeight(baseHeight);
        }
    }

    /**
     * <p>This method is tied to the undo button, and provides undo functionality using a single shared stack.</p>
     * @param activeStack the current active StackPane
     * @param undoStack the shared undo stack (which is an array of images)
     * @since 3.2.0
     */
    public static void undo(StackPane activeStack, ArrayList undoStack) {
        if(undoIndex > 1) {
            Image undoImage = (Image) undoStack.get(undoIndex - 2);
            activeCanvas.getGraphicsContext2D().drawImage(undoImage, 0, 0);
            redoStack.add((Image) undoStack.get(undoIndex-1));
            redoIndex++;
            if (undoIndex > 0) undoIndex--;
        }

        else {
            activeCanvas = newBaseCanvas(activeStack);
        }
    }

    /**
     * <p> This method is tied to the redo button, and provides redo functionality using a single shared stack. </p>
     * @param activeStack the active StackPane
     * @param redoStack the shared redo stack, which is an array of images.
     * @since 3.2.0
     */
    public static void redo(StackPane activeStack, ArrayList redoStack) {
        try {
            Image redoImage = (Image) redoStack.get(redoIndex - 1);
            activeCanvas.getGraphicsContext2D().drawImage(redoImage, 0, 0);
            redoStack.remove(redoIndex - 1);
            if (redoIndex > 0) redoIndex--;
            undoIndex++;
        }
        catch(Exception e) {}

    }

    /**
     * <p> This method only returns the shared undo stack.</p>
     * @return undoStack, the shared undo stack.
     * @since 3.2.0
     */
    public static ArrayList getUndoStack() { return undoStack; }

    /**
     * <p> This method only returns the shared redo stack.</p>
     * @return redoStack, the shared undo stack.
     * @since 3.2.0
     */
    public static ArrayList getRedoStack() { return redoStack; }

    /**
     * <p> This method is simply used to display a help and about dialogue window when the user
     * requests it from the drop down menu. </p>
     * @param pane the TabPane for the dialogue window to be displayed within.
     */
    public static void helpShow(TabPane pane) {
        Stage helpWin = new Stage();
        helpWin.setTitle("Help");
        Stage ownerStage = (Stage) pane.getScene().getWindow();
        helpWin.initModality(Modality.APPLICATION_MODAL);
        helpWin.initOwner(ownerStage);
        VBox helpMsg = new VBox(20);
        helpMsg.setAlignment(Pos.CENTER);
        helpMsg.getChildren().add(new Text("About this application:\n" +
                                              "ISM Pain(t) v2.0.3\n" +
                                              "Author: Ian McMillan\n" +
                                              "Questions? Email the author:\n" +
                                              "ian.mcmillan1@valpo.edu\n"));
        Scene helpScene = new Scene(helpMsg, 200, 100);
        helpWin.setScene(helpScene);
        helpWin.show();
    }

    /**
     * <p> This method launches a popup window that prompts the user to enter a number of sides. </p>
     * @return numSides, the integer containing the user's inputted number of sides.
     * @since 3.2.0
     */
    public static int promptForSides() {
        final int[] numSides = {0};
        Stage promptStage = new Stage();
        VBox box = new VBox(20);
        TextField textBox = new TextField();
        Label commentLabel = new Label("Enter number of sides:");
        Button okButton = new Button("Enter");
        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                promptStage.close();
                numSides[0] = Integer.parseInt(textBox.getText());
            }
        });
        box.getChildren().addAll(commentLabel, textBox, okButton);
        promptStage.setScene(new Scene(box, 200, 150));
        promptStage.showAndWait();
        return numSides[0];
    }

}
