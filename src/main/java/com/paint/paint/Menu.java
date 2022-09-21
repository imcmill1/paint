package com.paint.paint;

import javafx.collections.FXCollections;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

import java.awt.*;

/*=====Menu Class=====*/
/*
    The purpose of this class is to contain methods
    configuring the various JavaFX and SceneBuilder
    menus, boxes, and buttons. These methods should end
    up being called ONCE in the initialize() method. Thus,
    NO OPERATIONAL CODE should be present in this class, only
    methods to INITIALIZE components.
 */

public class Menu {
    public static void widthChoiceConfig(ChoiceBox widthChoice) {
        widthChoice.setValue("Width");
        widthChoice.setItems(FXCollections.observableArrayList("Width = 1px", "Width = 3px", "Width = 5px", "Width = 8px"));
    }

    public static void colorPickerConfig(ColorPicker colorPicker) {
        colorPicker.setValue(Color.BLACK);
    }
}
