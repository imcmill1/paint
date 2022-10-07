package com.paint.paint;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
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
    public Timer autoSave;
    public imageTab() {
        imageTab newTab = this;
        autoSave = new Timer();
        autoSave.scheduleAtFixedRate( new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    if (lastSavedFile != null) {
                        try { fileIO.save(stackPane, newTab); }
                        catch (IOException e) { throw new RuntimeException(e); }
                    }
                });
            }
        },0,30000);  // start immediately, run every 30 seconds
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
    public File getLastSavedFile() { return lastSavedFile; }
}
