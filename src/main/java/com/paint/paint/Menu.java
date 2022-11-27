package com.paint.paint;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import static javafx.scene.control.ContentDisplay.CENTER;

/** <p>Menu.java contains methods configuring the various JavaFX and SceneBuilder menus,
 * boxes, and buttons. These methods should end up being called ONCE in the initialize()
 * method. Thus, NO OPERATIONAL CODE should be present in this class, only methods to
 * INITIALIZE components.</p>
 * @since 1.4.0
 * @author ianmc
 */

public class Menu extends Node {

    /**
     * <p> This method configures the width choice box so that it displays a starting value,
     * as well as the options for the width choices.</p>
     * @param widthChoice the ChoiceBox object to be configured as the width choice box.
     * @since 1.4.0
     */
    public static void widthChoiceConfig (ChoiceBox widthChoice) {
        widthChoice.setValue("Width");
        widthChoice.setItems(FXCollections.observableArrayList("Width = 1px", "Width = 3px", "Width = 5px", "Width = 8px", "Dashed Width = 1px", "Dashed Width = 3px", "Dashed Width = 5px", "Dashed Width = 8px"));
    }

    /**
     * <p> This method configures the color picker so that it can be used for selecting the
     * color being used by the cursor. It simply sets a default color, black. </p>
     * @param colorPicker the ColorPicker object to be configured.
     * @since 1.4.0
     */
    public static void colorPickerConfig (ColorPicker colorPicker) {colorPicker.setValue(Color.BLACK);}

    /**
     * <p> This method configures the timer display label so that its default state is invisible. </p>
     * @param timerDisplayBox the timer display label.
     * @since 3.6.0
     */
    public static void timerDisplayConfig (Label timerDisplayBox) {
        timerDisplayBox.setVisible(false);
    }

}
