package com.paint.paint;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
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

public class Menu {
    public static void widthChoiceConfig(ChoiceBox widthChoice) {
        widthChoice.setValue("Width");
        widthChoice.setItems(FXCollections.observableArrayList("Width = 1px", "Width = 3px", "Width = 5px", "Width = 8px"));
    }

    public static void colorPickerConfig(ColorPicker colorPicker) {
        colorPicker.setValue(Color.BLACK);
    }

    public static void drawToggleConfig(ToggleButton drawToggleButton) {
        /*drawToggleButton.setSelected(false);
        ImageView drawIcon = new ImageView("https://www.google.com/url?sa=i&url=https%3A%2F%2Fimgbin.com%2Fpng%2FC31hN87E%2Fcomputer-icons-writing-implement-pen-png&psig=AOvVaw0CAycaN2pgfTLgRlsXfMhb&ust=1663880839361000&source=images&cd=vfe&ved=0CAwQjRxqFwoTCLC1ravlpvoCFQAAAAAdAAAAABAD");
        drawToggleButton.setGraphic(drawIcon);
        drawToggleButton.setContentDisplay(CENTER);*/
    }

}
