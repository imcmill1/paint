package com.paint.paint;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;

/*==========fileIO Class==========*/
/* The purpose of this class is to
*  implement basic file I/O methods
*  that can be passed into Controller.java. */

public class fileIO { //create public class fileIO which is static by default as top level class
    static File lastSavedFile;

    public static String open() throws IOException { //create an open() method
        Stage fileStage = new Stage(); //creates a JavaFX stage for the file explorer that the File Chooser object will be placed on later.
        FileChooser openFile = new FileChooser(); //creates a File Chooser object called openFile
        openFile.setTitle("Select an image file"); //gives the File Chooser a title
        openFile.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png")); //sets extension filters so only valid file extensions are shown
        File selectedImg = openFile.showOpenDialog(fileStage); //creates a file object called selected file that is assigned to be whatever file is selected from the file chooser
        try { //try catch block. if user cancels the file explorer, selectedImg will be null, and throw an exception when we try to get its path
            String imgPath = selectedImg.getAbsolutePath(); //converts the selected image file into a string containing the filepath
            return imgPath; //returns the filepath
        } catch (Exception e) { //the catch block returns null if needed
            return null;
        }
    }

    public static void saveAs(Canvas canvas) throws IOException { //placeholder saveAs method
        Stage fileStage = new Stage();
        FileChooser saveFile = new FileChooser();
        saveFile.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG Files", "*.png"));
        saveFile.setTitle("Save As...");
        File file = saveFile.showSaveDialog(fileStage);
        if (file != null) {
            WritableImage saveImg = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight()); //type cast for now; fix later
            canvas.snapshot(null, saveImg);
            ImageIO.write(SwingFXUtils.fromFXImage(saveImg, null), "png", file);
            lastSavedFile = file;
        }
    }

    public static void save(Canvas canvas) throws IOException { // placeholder save method
        if (lastSavedFile != null) {//check if lastSaved file not blank, if true, take snapshot and save at lastSavedFile
            WritableImage saveImg = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight()); //type cast for now; fix later
            canvas.snapshot(null, saveImg);
            ImageIO.write(SwingFXUtils.fromFXImage(saveImg, null), "png", lastSavedFile);
        }

        else {
            fileIO.saveAs(canvas);
        } //if it was null, call saveAs instead
    }

}