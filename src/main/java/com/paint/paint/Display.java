package com.paint.paint;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/*=======Display Class========*/
/*
    The purpose of this class is to handle
    displaying content in the application. This
    includes images, canvases, popup windows, etc.
 */

public class Display {

    static double baseHeight = 625;
    static double baseWidth = 1000;
    public static Canvas activeCanvas;
    public static boolean firstTab;

    public static boolean unsavedChanges = false;

    public static void setUnsavedChanges(boolean condition) {
        unsavedChanges = condition;
    }

    public static boolean getUnsavedChanges() {
        return unsavedChanges;
    }

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

    public static Tab newTab(TabPane tabPane, String name) { //creates a new tab and attaches it to the tabPane
        Tab newTab = new Tab();
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

    public static ScrollPane newScroll(Tab tab) {
        ScrollPane newScroll = new ScrollPane();
        tab.setContent(newScroll);
        tab.selectedProperty().addListener((observableValue, wasSelected, isSelected) -> {
            if (isSelected) {}
        });
        return newScroll;
    }

    public static StackPane newStack(ScrollPane scrollPane) {
        StackPane newStack = new StackPane();
        scrollPane.setContent(newStack);
        return newStack;
    }

    public static Canvas newCanvas(StackPane stackPane){ //closeLast method will close the image on the top layer of the canvas
        Canvas newCanvas = new Canvas();
        newCanvas.setHeight(baseHeight);
        newCanvas.setWidth(baseWidth);
        newCanvas.getGraphicsContext2D().setFill(Color.WHITE);
        newCanvas.getGraphicsContext2D().fillRect(0, 0, newCanvas.getWidth(), newCanvas.getHeight());
        newCanvas.setCursor(Cursor.CROSSHAIR);
        stackPane.getChildren().add(newCanvas);
        return newCanvas;
    }

    public static void createNewTab(TabPane tabPane, String tabName) {
        Canvas currCanvas = newCanvas(newStack(newScroll(newTab(tabPane, tabName))));
        if (firstTab = true) {
            activeCanvas = currCanvas;
            firstTab = false;
        }
    }

    public static Canvas getActiveCanvas() {
        return activeCanvas;
    }

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

}
