package com.paint.paint;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.awt.*;

/*=====Edit Class=====*/
/*
    The purpose of this class is to contain methods
    for image editing, drawing, etc. Methods from
    this class will be wrapped in the Controller
    class.
 */
public class Edit {
    public static void updateWidth(GraphicsContext GraphContext, ChoiceBox widthChoice) {
        String choice = widthChoice.getValue().toString();
        switch (choice) {
            case "Width = 1px":
                GraphContext.setLineWidth(1);
                break;
            case "Width = 3px":
                GraphContext.setLineWidth(3);
                break;
            case "Width = 5px":
                GraphContext.setLineWidth(5);
                break;
            case "Width = 8px":
                GraphContext.setLineWidth(8);
                break;
        }
    }

    public static void updateColor(GraphicsContext GraphContext, ColorPicker colorPicker) {
        GraphContext.setStroke(colorPicker.getValue());
    }
    public static void drawUpdate(Canvas canvas, GraphicsContext GraphContext, ToggleButton drawToggleButton) {
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
