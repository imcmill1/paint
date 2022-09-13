package com.paint.paint;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import java.util.ArrayList;
import java.util.List;

/*=======Display Class========*/
/* The purpose of this class is to handle
    displaying canvases, images, layering
    canvases, and canvas selection.
 */

public class Display {

    public static void showImage(Canvas canvas, GraphicsContext gc, String path) { //method to show an image
       try { //try block catches an empty path being passed in. if path is null, goes to an empty catch
           Image img = new Image(path); //creates a new Image object using the filepath passed in from the controller
           canvas.setHeight(img.getHeight()); //resize canvas height to size of image
           canvas.setWidth(img.getWidth()); //resize canvas width to size of image
           gc.drawImage(img, 0,0); //draw that image on the canvas
       }

       catch (Exception e) {
           //empty catch so that nothing happens when an empty path is received, or an improper filetype is received.
           //prevents console from throwing hella errors.
       }
    }

    public static void newCanvas(Canvas canvas, GraphicsContext gc){ //closeLast method will close the image on the top layer of the canvas
        gc.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
    }

}
