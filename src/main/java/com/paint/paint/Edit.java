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
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Edit {

    public static void drawUpdate(Canvas canvas, GraphicsContext GraphContext, ToggleButton drawToggleButton) {
        GraphContext.setStroke(Color.BLACK); //placeholder width and color setting
        GraphContext.setLineWidth(4); //will be changed to reflect picker later

        if (drawToggleButton.isSelected() == true) {
            System.out.println(drawToggleButton.isSelected());
            canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent press) -> {
                GraphContext.beginPath();
                GraphContext.moveTo(press.getX(), press.getY());
                GraphContext.stroke();
            });

            canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, (MouseEvent drag) -> {
                GraphContext.lineTo(drag.getX(), drag.getY());
                GraphContext.stroke();
            });

            canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, (MouseEvent released) -> {
            });
        }

        else if (drawToggleButton.isSelected() == false) { //can't get these fucking handlers to go away
            canvas.setOnMousePressed(null);
            canvas.setOnMouseDragged(null);
            canvas.setOnMouseReleased(null);
        }
    }
}
