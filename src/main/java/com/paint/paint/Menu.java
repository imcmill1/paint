package com.paint.paint;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import static javafx.scene.control.ContentDisplay.CENTER;

/*=====Menu Class=====*/
/*
    The purpose of this class is to contain methods
    configuring the various JavaFX and SceneBuilder
    menus, boxes, and buttons. These methods should end
    up being called ONCE in the initialize() method. Thus,
    NO OPERATIONAL CODE should be present in this class, only
    methods to INITIALIZE components.
 */

public class Menu extends Node {
    public static void widthChoiceConfig (ChoiceBox widthChoice) {
        widthChoice.setValue("Width");
        widthChoice.setItems(FXCollections.observableArrayList("Width = 1px", "Width = 3px", "Width = 5px", "Width = 8px", "Dashed Width = 1px", "Dashed Width = 3px", "Dashed Width = 5px", "Dashed Width = 8px"));
    }

    public static void colorPickerConfig (ColorPicker colorPicker) {
        colorPicker.setValue(Color.BLACK);
    }

}
