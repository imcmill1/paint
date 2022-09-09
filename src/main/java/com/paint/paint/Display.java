package com.paint.paint;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/*=======Display Class========*/
/* The purpose of this class is to handle
    displaying canvases, images, layering
    canvases, and canvas selection.
 */

public class Display {

    static List imgList = new ArrayList(); //create a list object to store the images that have been opened sequentially
    public static void showImage(StackPane pane, Canvas canvas, String path) { //method to show an image
       try { //try block catches an empty path being passed in. if path is null, goes to an empty catch
           ImageView img = new ImageView(path); //creates a new ImageView object using the filepath passed in from the controller
           imgList.add(img); //adds that image to the list
           img.setPreserveRatio(true); //turns on preserve ration to make sure the aspect ratio is retained when image is resized to fit
           img.setFitHeight(canvas.getHeight()); //sets a height limit for the image to be fit to
           img.setFitWidth(canvas.getWidth()); //same as above but for width
           pane.getChildren().addAll(img, canvas); //adds the image to the StackPane passed in from the controller.
       }
       catch (Exception e) {
           //empty catch so that nothing happens when an empty path is received, or an improper filetype is received.
           //prevents console from throwing hella errors.
       }
    }

    public static void closeLast(StackPane pane){ //closeLast method will close the image on the top layer of the canvas
        pane.getChildren().remove(imgList.get(imgList.size()-1)); //uses remove() method on the newest image in imgList
        imgList.remove(imgList.size()-1); //pops that image off the list after it has been removed.
    }

    public static void closeAll(StackPane pane){ //closeAll method just clears the pane entirely. This will likely cause issues later.
        pane.getChildren().clear();
    }

}
