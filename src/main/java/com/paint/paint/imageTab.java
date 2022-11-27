package com.paint.paint;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * <p>imageTab.java is a custom class of tabs that extends the JavaFx Tab class. This class allows objects to contain
 * several data fields that make interacting with multiple tabs in the application much easier. These fields are, of course,
 * set when a new tab is created, and have no default value provided by a constructor. In addition, this class implements
 * a timer thread for each open tab, so that autosave can work across multiple tabs. </p>
 * @author ianmc
 * @since 3.3.0
 */

public class imageTab extends Tab {

    /** An field containing the tab's last saved file. */
    private File lastSavedFile;

    /** The parent tabpane that the tab belongs to. */
    private TabPane parentTabPane;

    /** The scrollpane child of the tab. */
    private ScrollPane scrollPane;

    /** The stackpane child of the tab. */
    private StackPane stackPane;

    /** The canvas child of the tab. */
    private Canvas canvas;

    /** The tab's unique autosave timer object. */
    private Timer autoSave;

    /** The private value of the timer, incremented and reset inside the timer task.*/
    private int timerVal;

    /**
     * <p> This constructor creates a new timer thread using a fixed rate scheduler, the timer member field, and the timerVal
     * member field. It runs every second and updates the value, and if the value reaches zero, it performs an autosave using the fileIO.save()
     * method, provided that the tab has been saved at least once and the lastSavedFile field is not null. It will then update the value of the timerLabel object
     * with the new current value of the timer. </p>
     * @param timerLabel the label displaying the timer value.
     * @since 3.3.0
     */
    public imageTab(Label timerLabel) {
        imageTab newTab = this;
        autoSave = new Timer(true);
        timerVal = 30;
        autoSave.scheduleAtFixedRate( new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    if (lastSavedFile != null) {
                        if(timerVal == 0) {
                            try {
                                fileIO.save(stackPane, newTab);
                                timerVal = 30;
                                timerLabel.setText(Integer.toString(timerVal));
                            }
                            catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        else {
                            timerVal--;
                            timerLabel.setText(Integer.toString(timerVal));
                        }
                    }
                });
            }
        },0,1000);  // start immediately, run every second
    }

    /**
     * <p> The setter method for the parent tab pane field. </p>
     * @param tabPane
     * @since 3.3.0
     */
    public void setParentTabPane (TabPane tabPane) { parentTabPane = tabPane; }

    /**
     * <p> The setter method for the child scroll pane field. </p>
     * @param inputScroll
     * @since 3.3.0
     */
    public void setScrollPane (ScrollPane inputScroll) { scrollPane = inputScroll; }

    /**
     * <p> The setter method for the child stack pane field. </p>
     * @param inputStack
     * @since 3.3.0
     */
    public void setStackPane (StackPane inputStack) { stackPane = inputStack; }

    /**
     * <p> The setter method for the child canvas field. </p>
     * @param inputCanvas
     * @since 3.3.0
     */
    public void setCanvas (Canvas inputCanvas) { canvas = inputCanvas; }

    /**
     * <p> The setter method for the last saved file field. </p>
     * @param inputFile
     * @since 3.3.0
     */
    public void setLastSavedFile (File inputFile) { lastSavedFile = inputFile; }

    /**
     * <p> The getter method for the parent tab pane field. </p>
     * @since 3.3.0
     * @return the parent tab pane
     */
    public TabPane getParentTabPane() { return parentTabPane; }

    /**
     * <p> The getter method for the child scroll pane field. Currently unused. </p>
     * @since 3.3.0
     * @return the child scroll pane
     */
    public ScrollPane getScrollPane() { return scrollPane; }

    /**
     * <p> The getter method for the child stack pane field. Currently unused. </p>
     * @since 3.3.0
     * @return the child stack pane
     */
    public StackPane getStackPane() { return stackPane; }

    /**
     * <p> The getter method for the child canvas field. </p>
     * @since 3.3.0
     * @return the child canvas
     */
    public Canvas getCanvas() { return canvas; }

    /**
     * <p> The getter method for the child timer object field. Currently unused. </p>
     * @since 3.3.0
     * @return the timer object
     */
    public Timer getTimer() { return autoSave; }

    /**
     * <p> The getter method for the child timer value field. Currently unused. </p>
     * @since 3.3.0
     * @return the value of the timer
     */
    public int getTimerVal() { return timerVal; }

    /**
     * <p> The getter method for the last saved file field. </p>
     * @since 3.3.0
     * @return the last saved file
     */
    public File getLastSavedFile() { return lastSavedFile; }
}
