package com.paint.paint;

/*=====Edit Class=====*/
/*
    The purpose of this class is to contain methods
    for image editing, drawing, etc. Methods from
    this class will be wrapped in the Controller
    class.
 */

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Edit {

    public static void updateWidth(ChoiceBox widthChoice) {

    }
    public static void drawUpdate(Canvas canvas, GraphicsContext GraphContext, ToggleButton drawToggleButton) {
        GraphContext.setStroke(Color.BLACK); //placeholder width and color setting
        GraphContext.setLineWidth(4); //will be changed to reflect picker later

        //This block creates event handlers for when the mouse is pressed, dragged, and released
        final EventHandler<MouseEvent> mousePress = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mousePress) {
                GraphContext.beginPath();
                GraphContext.moveTo(mousePress.getX(), mousePress.getY());
                GraphContext.stroke();
            }
        };

        final EventHandler<MouseEvent> mouseDrag = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseDrag) {
                GraphContext.lineTo(mouseDrag.getX(), mouseDrag.getY());
                GraphContext.stroke();
            }
        };

        final EventHandler<MouseEvent> mouseRel = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseRel) {
                //empty; no actions to be taken on released mouse
            }
        };

        if (drawToggleButton.isSelected() == true) { //if the draw button is selected
            canvas.setOnMousePressed(mousePress); //add the above created event handlers to the canvas
            canvas.setOnMouseDragged(mouseDrag);
            canvas.setOnMouseReleased(mouseRel);
        }

        else {
            canvas.setOnMousePressed(null); //else, make sure they're removed.
            canvas.setOnMouseDragged(null);
            canvas.setOnMouseReleased(null);
        }
    }
}
