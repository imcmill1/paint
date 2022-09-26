package com.paint.paint;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.Light.Point;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.List;

import static java.lang.Math.abs;

/*=====Edit Class=====*/
/*
    The purpose of this class is to contain methods
    for image editing, drawing, etc. Methods from
    this class will be wrapped in the Controller
    class.
 */
public class Edit {

    public static void removeHandlers(Canvas canvas) {
        canvas.setOnMousePressed(null);
        canvas.setOnMouseDragged(null);
        canvas.setOnMouseReleased(null);
    }
    public static void updateWidth(GraphicsContext graphContext, ChoiceBox widthChoice) {
        if (widthChoice.getValue() == null) { //runs if width has been selected
            graphContext.setLineWidth(1);
        }

        else {
            String choice = widthChoice.getValue().toString();
            switch (choice) {
                case "Width = 1px":
                    graphContext.setLineWidth(1);
                    break;
                case "Width = 3px":
                    graphContext.setLineWidth(3);
                    break;
                case "Width = 5px":
                    graphContext.setLineWidth(5);
                    break;
                case "Width = 8px":
                    graphContext.setLineWidth(8);
                    break;
                }
            }
        }

    public static void updateColor(GraphicsContext graphContext, ColorPicker colorPicker) {
        graphContext.setStroke(colorPicker.getValue());
    }

    public static void freeDraw (Canvas canvas, GraphicsContext graphContext) {
        final EventHandler<MouseEvent> mousePress = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mousePress) {
                graphContext.beginPath();
                graphContext.moveTo(mousePress.getX(), mousePress.getY());
                graphContext.stroke();
            }
        };

        final EventHandler<MouseEvent> mouseDrag = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseDrag) {
                graphContext.lineTo(mouseDrag.getX(), mouseDrag.getY());
                graphContext.stroke();
            }
        };

        final EventHandler<MouseEvent> mouseRel = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseRel) {
                //empty; no actions to be taken on released mouse
            }
        };

        canvas.setOnMousePressed(mousePress); //add the above created event handlers to the canvas
        canvas.setOnMouseDragged(mouseDrag);
        canvas.setOnMouseReleased(mouseRel);
    }

    public static void drawLine (Canvas canvas, GraphicsContext graphContext) {
        final EventHandler<MouseEvent> mousePress = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mousePress) {
                graphContext.beginPath();
                graphContext.moveTo(mousePress.getX(), mousePress.getY());
            }
        };

        final EventHandler<MouseEvent> mouseRel = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseRel) {
                graphContext.lineTo(mouseRel.getX(), mouseRel.getY());
                graphContext.stroke();
            }
        };

        canvas.setOnMousePressed(mousePress); //add the above created event handlers to the canvas
        canvas.setOnMouseReleased(mouseRel);
    }

    public static void drawDashedLine (Canvas canvas, GraphicsContext graphContext) {
        final EventHandler<MouseEvent> mousePress = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mousePress) {
                graphContext.beginPath();
                graphContext.moveTo(mousePress.getX(), mousePress.getY());
            }
        };

        final EventHandler<MouseEvent> mouseRel = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseRel) {
                graphContext.lineTo(mouseRel.getX(), mouseRel.getY());
                graphContext.setLineDashes(10);
                graphContext.stroke();
                graphContext.setLineDashes(0);
            }
        };

        canvas.setOnMousePressed(mousePress); //add the above created event handlers to the canvas
        canvas.setOnMouseReleased(mouseRel);
    }

    public static void drawRectangle (Canvas canvas, GraphicsContext graphContext) {
        final Point corner = new Point();
        //This block creates event handlers for when the mouse is pressed and released
        EventHandler<MouseEvent> mousePress = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mousePress) {
                graphContext.beginPath();
                graphContext.moveTo(mousePress.getX(), mousePress.getY());
                corner.setX(mousePress.getX());
                corner.setY(mousePress.getY());
            }
        };

        EventHandler<MouseEvent> mouseRel = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseRel) {
                double width = mouseRel.getX() - corner.getX();
                double height = mouseRel.getY() - corner.getY();
                graphContext.strokeRect(corner.getX(), corner.getY(), width, height);
            }
        };

        canvas.setOnMousePressed(mousePress); //add the above created event handlers to the canvas
        canvas.setOnMouseReleased(mouseRel);
    }

    public static void drawSquare (Canvas canvas, GraphicsContext graphContext) {
        final Point corner = new Point();
        //This block creates event handlers for when the mouse is pressed and released
        EventHandler<MouseEvent> mousePress = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mousePress) {
                graphContext.beginPath();
                graphContext.moveTo(mousePress.getX(), mousePress.getY());
                corner.setX(mousePress.getX());
                corner.setY(mousePress.getY());
            }
        };

        EventHandler<MouseEvent> mouseRel = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseRel) {
                double width = mouseRel.getX() - corner.getX();
                graphContext.strokeRect(corner.getX(), corner.getY(), width, width);
            }
        };

        canvas.setOnMousePressed(mousePress); //add the above created event handlers to the canvas
        canvas.setOnMouseReleased(mouseRel);
    }

    public static void drawEllipse (Canvas canvas, GraphicsContext graphContext) {
        final Point bound = new Point();
        //This block creates event handlers for when the mouse is pressed and released
        EventHandler<MouseEvent> mousePress = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mousePress) {
                graphContext.beginPath();
                graphContext.moveTo(mousePress.getX(), mousePress.getY());
                bound.setX(mousePress.getX());
                bound.setY(mousePress.getY());
            }
        };

        EventHandler<MouseEvent> mouseRel = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseRel) {
                double width = mouseRel.getX() - bound.getX();
                double height = mouseRel.getY() - bound.getY();
                graphContext.strokeOval(bound.getX(), bound.getY(), width, height);
            }
        };

        canvas.setOnMousePressed(mousePress); //add the above created event handlers to the canvas
        canvas.setOnMouseReleased(mouseRel);
    }

    public static void drawCircle (Canvas canvas, GraphicsContext graphContext) {
        final Point bound = new Point();
        //This block creates event handlers for when the mouse is pressed and released
        EventHandler<MouseEvent> mousePress = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mousePress) {
                graphContext.beginPath();
                graphContext.moveTo(mousePress.getX(), mousePress.getY());
                bound.setX(mousePress.getX());
                bound.setY(mousePress.getY());
            }
        };

        EventHandler<MouseEvent> mouseRel = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseRel) {
                double width = mouseRel.getX() - bound.getX();
                graphContext.strokeOval(bound.getX(), bound.getY(), width, width);
            }
        };

        canvas.setOnMousePressed(mousePress); //add the above created event handlers to the canvas
        canvas.setOnMouseReleased(mouseRel);
    }

    public static void colorGrabber (Canvas canvas, GraphicsContext graphContext, ToggleGroup editToggles, ColorPicker colorPicker) {
        //This block creates event handlers for when the mouse is pressed and released
        ObservableList<Toggle> toggleList = editToggles.getToggles(); //grabs an observable list of all toggles in the editToggles group
        EventHandler<MouseEvent> mousePress = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mousePress) {
                WritableImage snap = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
                canvas.snapshot(null, snap);
                Color grabbedColor = snap.getPixelReader().getColor((int) mousePress.getX(), (int) mousePress.getY());
                colorPicker.setValue(grabbedColor);
                //TODO set custom cursor
                graphContext.setStroke(grabbedColor);
                editToggles.selectToggle(toggleList.get(0));
            }
        };

        canvas.setOnMousePressed(mousePress); //add the above created event handlers to the canvas
    }

    public static void eraser(GraphicsContext graphContext) {
        graphContext.setStroke(Color.WHITE);
    }

    public static void cursorUpdate (Canvas canvas, GraphicsContext graphContext, ToggleGroup editToggles, ColorPicker colorPicker) {
        removeHandlers(canvas); //start by removing previous event handlers
        String selected; //creates a blank string
        List<Toggle> toggleList = (List) editToggles.getToggles(); //grabs an observable list of all toggles in the editToggles group
        try { //try to get the selected toggle, will fail if null
            selected = editToggles.getSelectedToggle().toString(); //get string for the selected toggle
            selected = selected.substring(53); //get just the end of the string with the button label
        }

        catch (Exception e) { //if null, caught here and event handlers are all removed.
            selected = ""; //if the selected toggle was null, set selected to an empty string
            removeHandlers(canvas); //remove handlers
        }

        switch (selected) {

            case "": //case also covered by the catch block, here for redundancy.
                removeHandlers(canvas); //just remove handlers
                break;

            case "'Draw'":
                freeDraw(canvas, graphContext);
                break;

            case "'Line'":
                drawLine(canvas, graphContext);
                break;

            case "'Dashed Line'":
                drawDashedLine(canvas, graphContext);
                break;

            case "'Rectangle'":
                drawRectangle(canvas, graphContext);
                break;

            case "'Square'":
                drawSquare(canvas, graphContext);
                break;

            case "'Circle'":
                drawCircle(canvas, graphContext);
                break;

            case "'Ellipse'":
                drawEllipse(canvas, graphContext);
                break;

            case "'Color Grabber'":
                colorGrabber(canvas, graphContext, editToggles, colorPicker);
                break;

            case "'Eraser'":
                eraser(graphContext);
                break;

        }


    }

}
