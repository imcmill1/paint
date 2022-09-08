package com.paint.paint;

import java.io.File;
import java.io.IOException;
import java.awt.Desktop;

/*==========fileIO Class==========*/
/* The purpose of this class is to
*  implement basic file I/O methods
*  that can be passed into Controller.java. */

public class fileIO { //create public class fileIO which is static by default as top level class
    public static void open() throws IOException { //create an open() method
        File file = new File ("C://Users//ianmc//OneDrive//Pictures");
        Desktop desktop = Desktop.getDesktop();
        desktop.open(file);
    }

    // placeholder save method
    public static void save() {
        System.out.println("save");
    }

    //placeholder saveAs method
    public static void saveAs() {
        System.out.println("saveAs");
    }

}

