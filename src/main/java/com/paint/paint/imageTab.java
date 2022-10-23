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

public class imageTab extends Tab {
    private File lastSavedFile;
    private TabPane parentTabPane;
    private ScrollPane scrollPane;
    private StackPane stackPane;
    private Canvas canvas;
    private Timer autoSave;
    private int timerVal;

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
    public void setParentTabPane (TabPane tabPane) { parentTabPane = tabPane; }
    public void setScrollPane (ScrollPane inputScroll) { scrollPane = inputScroll; }
    public void setStackPane (StackPane inputStack) { stackPane = inputStack; }

    public void setCanvas (Canvas inputCanvas) { canvas = inputCanvas; }
    public void setLastSavedFile (File inputFile) { lastSavedFile = inputFile; }
    public TabPane getParentTabPane() { return parentTabPane; }

    public ScrollPane getScrollPane() { return scrollPane; }

    public StackPane getStackPane() { return stackPane; }

    public Canvas getCanvas() { return canvas; }

    public Timer getTimer() { return autoSave; }

    public int getTimerVal() { return timerVal; }
    public File getLastSavedFile() { return lastSavedFile; }
}
