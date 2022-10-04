package com.paint.paint;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;

/** fileIO.java implements basic file I/O methods that are then passed into Controller.java.
 * @since 0.1.1
*/

public class fileIO { //create public class fileIO which is static by default as top level class

    /** <p>lastSavedFile is a file object that stores what the last saved file was, so that save()
     * can function without having to specify file location each time.</p>
     */
    static File lastSavedFile;

    /**
     * <p> This method opens a file explorer window for the user to select an image to be opened.</p>
     * @return imgPath, on successful selection of a file, the path of that file, or null if a file was not selected
     * @throws IOException due to interfacing with file explorer i/o
     * @since 0.2.0
     */
    public static String open() throws IOException { //create an open() method
        Stage fileStage = new Stage(); //creates a JavaFX stage for the file explorer that the File Chooser object will be placed on later.
        FileChooser openFile = new FileChooser(); //creates a File Chooser object called openFile
        openFile.setTitle("Select an image file"); //gives the File Chooser a title
        openFile.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.bmp")); //sets extension filters so only valid file extensions are shown
        File selectedImg = openFile.showOpenDialog(fileStage); //creates a file object called selected file that is assigned to be whatever file is selected from the file chooser
        try { //try catch block. if user cancels the file explorer, selectedImg will be null, and throw an exception when we try to get its path
            String imgPath = selectedImg.getAbsolutePath(); //converts the selected image file into a string containing the filepath
            return imgPath; //returns the filepath
        } catch (Exception e) { //the catch block returns null if needed
            return null;
        }
    }

    /**
     * <p> This method opens a file explorer window for the user to specify where they would like to
     * save the contents of the active canvas, then takes a snapshot of that canvas and saves it at the specified
     * location. </p>
     * @param canvas the active canvas to be saved
     * @throws IOException due to interfacing with file explorer i/o
     * @since 1.0.1
     */
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

    /**
     * <p> This method will save the current canvas to the location of the last saved file,
     * bypassing the opening of a file explorer instance and selection of a location. Only
     * one previously saved file is tracked for the application, meaning this method can
     * overwrite an image saved in one tab with an image from another tab. </p>
     * @param canvas the active canvas to be saved.
     * @throws IOException due to interfacing with file explorer i/o
     * @since 1.0.1
     */
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