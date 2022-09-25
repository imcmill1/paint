package com.paint.paint;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
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

    public static Canvas activeCanvas;
    public static boolean firstTab;
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
        tabPane.getTabs().add(newTab);
        return newTab;
    }

    public static ScrollPane newScroll(Tab tab) {
        ScrollPane newScroll = new ScrollPane();
        tab.setContent(newScroll);
        tab.selectedProperty().addListener((observableValue, wasSelected, isSelected) -> {
            if (isSelected) {
                setActiveCanvas(tab);
                ScrollPane activeScrollPane = (ScrollPane) tab.getContent();
                activeCanvas = (Canvas) activeScrollPane.getContent();
            }
        });
        return newScroll;
    }

    public static Canvas newCanvas(ScrollPane scrollPane){ //closeLast method will close the image on the top layer of the canvas
        Canvas newCanvas = new Canvas();
        newCanvas.setHeight(250);
        newCanvas.setWidth(600);
        newCanvas.getGraphicsContext2D().setFill(Color.WHITE);
        newCanvas.getGraphicsContext2D().fillRect(0, 0, newCanvas.getWidth(), newCanvas.getHeight());
        newCanvas.setCursor(Cursor.CROSSHAIR);
        scrollPane.setContent(newCanvas);
        return newCanvas;
    }

    public static void createNewTab(TabPane tabPane, String tabName) {
        Canvas currCanvas = newCanvas(newScroll(newTab(tabPane, tabName)));
        if (firstTab = true) {
            activeCanvas = currCanvas;
            firstTab = false;
        }
    }

    public static void setActiveCanvas(Tab tab) {
        ScrollPane activeScrollPane = (ScrollPane) tab.getContent();
        activeCanvas = (Canvas) activeScrollPane.getContent();
    }

    public static Canvas getActiveCanvas() {
        return activeCanvas;
    }

    public static void clearActiveCanvas(Canvas activeCanvas) {
        activeCanvas.getGraphicsContext2D().setFill(Color.WHITE);
        activeCanvas.getGraphicsContext2D().fillRect(0, 0, activeCanvas.getWidth(), activeCanvas.getHeight());
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
