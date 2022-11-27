package com.paint.paint;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.effect.Light.Point;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import java.util.List;
import static java.lang.Math.abs;

/**
 * <p>Edit.java contains the methods for image editing, drawing, etc.
 *  Methods from this class will be wrapped in the Controller class.</p>
 * @since 1.1.1
 * @author ianmc
 */

public class Edit {

    /** selection is a Rectangle2D object that stores the current selection.*/
    public static Rectangle2D selection = new Rectangle2D(0, 0, 0, 0);

    /** copiedImage is an Image object storing the current object on the "clipboard"*/
    public static Image copiedImage;

    /**
     * <p> This method is used as a concise, one-line, abstract method to remove the event handlers
     * from the active canvas.</p>
     * @param canvas the canvas for event handlers to be removed from.
     * @since 2.0.0
     */
    public static void removeHandlers(Canvas canvas) {
        canvas.setOnMousePressed(null);
        canvas.setOnMouseDragged(null);
        canvas.setOnMouseReleased(null);
    }

    /**
     * <p> This method is used in the Controller to update the width of lines drawn
     * by the cursor and by tools. </p>
     * @param graphContext the graphics context to be updated.
     * @param widthChoice the ChoiceBox containing the width options.
     * @since 1.4.0
     */
    public static void updateWidth(GraphicsContext graphContext, ChoiceBox widthChoice) {
        graphContext.setLineDashes(0);
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

                case "Dashed Width = 1px":
                    graphContext.setLineWidth(1);
                    graphContext.setLineDashes(10);
                    break;

                case "Dashed Width = 3px":
                    graphContext.setLineWidth(3);
                    graphContext.setLineDashes(10);
                    break;

                case "Dashed Width = 5px":
                    graphContext.setLineWidth(5);
                    graphContext.setLineDashes(10);
                    break;

                case "Dashed Width = 8px":
                    graphContext.setLineWidth(8);
                    graphContext.setLineDashes(10);
                    break;
                }
            }
        }

    /**
     * <p> This method is used in the Controller to update the color of lines drawn
     * by the cursor and tools. </p>
     * @param graphContext the graphics context to be updated.
     * @param colorPicker the ColorPicker object for the selected color to be retrieved from.
     * @since 1.4.0
     */
    public static void updateColor(GraphicsContext graphContext, ColorPicker colorPicker) {
        graphContext.setStroke(colorPicker.getValue());
    }

    /**
     * <p> This is one of many methods used internally within the cursorUpdate method.
     * This particular method configures and adds the event handlers necessary for free
     * drawing on the canvas. </p>
     * @param canvas the active canvas.
     * @param graphContext the active graphics context.
     * @since 2.2.0
     */
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

    /**
     * <p> This is one of many methods used internally within the cursorUpdate method.
     * This particular method configures and adds the event handlers necessary for
     * drawing a straight line on the canvas. </p>
     * @param canvas the active canvas.
     * @param graphContext the active graphics context.
     * @since 2.2.0
     */
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

    /**
     * <p> This is one of many methods used internally within the cursorUpdate method.
     * This particular method configures and adds the event handlers necessary for
     * drawing a right triangle on the canvas. </p>
     * @param canvas the active canvas.
     * @param graphContext the active graphics context.
     * @since 2.2.0
     */
    public static void drawRightTri (Canvas canvas, GraphicsContext graphContext) {
        final Point corner = new Point();
        final EventHandler<MouseEvent> mousePress = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mousePress) {
                graphContext.beginPath();
                graphContext.moveTo(mousePress.getX(), mousePress.getY());
                corner.setX(mousePress.getX());
                corner.setY(mousePress.getY());
            }
        };

        final EventHandler<MouseEvent> mouseRel = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseRel) {
                graphContext.lineTo(mouseRel.getX(), mouseRel.getY());
                graphContext.lineTo(corner.getX(), mouseRel.getY());
                graphContext.lineTo(corner.getX(), corner.getY());
                graphContext.stroke();
            }
        };

        canvas.setOnMousePressed(mousePress); //add the above created event handlers to the canvas
        canvas.setOnMouseReleased(mouseRel);
    }

    /**
     * <p> This is one of many methods used internally within the cursorUpdate method.
     * This particular method configures and adds the event handlers necessary for
     * drawing a rectangle on the canvas. </p>
     * @param canvas the active canvas.
     * @param graphContext the active graphics context.
     * @since 2.2.0
     */
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

    /**
     * <p> This is one of many methods used internally within the cursorUpdate method.
     * This particular method configures and adds the event handlers necessary for
     * drawing a square on the canvas. </p>
     * @param canvas the active canvas.
     * @param graphContext the active graphics context.
     * @since 2.2.0
     */
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

    /**
     * <p> This method is used to do the math required to compute the locations of all points of a polygon
     * given the desired number of sides. </p>
     * @param centerX the x-coordinate of the centerpoint of the polygon whose points are to be computed.
     * @param centerY the y-coordinate of the centerpoint of the polygon whose points are to be computed.
     * @param radius the "radius" of the polygon, ie the radius of a circle that would contain the polygon's points
     * @param sides the number of sides of the desired polygon
     * @param x a boolean input that tells the method whether to return the x-coordinates of the polygon's points, or the y-coordinates.
     * @return returnX, a double array of x-coordinates, or returnY, a double array of y-coordinates
     * @since 3.2.0
     */
    public static double[] getPolygonSides(double centerX, double centerY, double radius, int sides, boolean x) {
        double[] returnX = new double[sides];
        double[] returnY = new double[sides];
        final double angleStep = Math.PI * 2 / sides;
        // assumes one point is located directly beneath the center point
        double angle = 0;
        for (int i = 0; i < sides; i++, angle += angleStep) {
            //draws rightside-up; to change, change multiple of angle
            // x coordinate of the corner
            returnX[i] = -1 * Math.sin(angle) * radius + centerX;
            // y coordinate of the corner
            returnY[i] = -1 * Math.cos(angle) * radius + centerY;
        }
        if(x)
            return returnX;
        else
            return returnY;
    }

    /**
     * <p> This is one of many methods used internally within the cursorUpdate method.
     * This particular method configures and adds the event handlers necessary for
     * drawing a straight line on the canvas. </p>
     * @param canvas the active canvas.
     * @param graphContext the active graphics context.
     * @since 3.2.0
     */
    public static void drawPolygon (Canvas canvas, GraphicsContext graphContext) {
        final Point center = new Point();
        final EventHandler<MouseEvent> mousePress = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mousePress) {
                center.setX(mousePress.getX());
                center.setY(mousePress.getY());
            }
        };

        final EventHandler<MouseEvent> mouseRel = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseRel) {
                double radius = Math.sqrt(Math.pow(abs(center.getX() - mouseRel.getX()), 2) + Math.pow(abs(center.getY() - mouseRel.getY()), 2));
                int sides = Display.promptForSides();
                double[] xPoints = getPolygonSides(center.getX(), center.getY(), radius, sides, true);
                double[] yPoints = getPolygonSides(center.getX(), center.getY(), radius, sides, false);

                graphContext.strokePolygon(xPoints, yPoints, sides);
            }
        };

        canvas.setOnMousePressed(mousePress); //add the above created event handlers to the canvas
        canvas.setOnMouseReleased(mouseRel);
    }

    /**
     * <p> This is one of many methods used internally within the cursorUpdate method.
     * This particular method configures and adds the event handlers necessary for
     * drawing an ellipse on the canvas. </p>
     * @param canvas the active canvas.
     * @param graphContext the active graphics context.
     * @since 2.2.0
     */
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

    /**
     * <p> This is one of many methods used internally within the cursorUpdate method.
     * This particular method configures and adds the event handlers necessary for
     * drawing a circle on the canvas. </p>
     * @param canvas the active canvas.
     * @param graphContext the active graphics context.
     * @since 2.2.0
     */
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

    /**
     * <p> This is one of many methods used internally within the cursorUpdate method.
     * This particular method configures and adds the event handlers necessary for
     * a color grabber to select a color off of the canvas and write that color into the
     * color picker.</p>
     * @param canvas the active canvas.
     * @param graphContext the active graphics context.
     * @param editToggles the ToggleGroup that all tools belong to, for selecting free draw after a color is grabbed.
     * @param colorPicker the ColorPicker that the grabbed color will be written to.
     * @since 2.2.0
     */
    public static void colorGrabber (Canvas canvas, GraphicsContext graphContext, ToggleGroup editToggles, ColorPicker colorPicker) {
        //This block creates event handlers for when the mouse is pressed and released
        ObservableList<Toggle> toggleList = editToggles.getToggles(); //grabs an observable list of all toggles in the editToggles group
        EventHandler<MouseEvent> mousePress = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mousePress) {
                WritableImage snap = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
                canvas.snapshot(null, snap);
                Color grabbedColor = snap.getPixelReader().getColor((int) mousePress.getX(), (int) mousePress.getY());
                colorPicker.setValue(grabbedColor);
                graphContext.setStroke(grabbedColor);
                editToggles.selectToggle(toggleList.get(0));
            }
        };

        canvas.setOnMousePressed(mousePress); //add the above created event handlers to the canvas
    }

    /**
     * <p> This is one of many methods used internally within the cursorUpdate method.
     * This particular method configures and adds the event handlers necessary for
     * an eraser tool. </p>
     * @param canvas the active canvas.
     * @param graphContext the active graphics context.
     * @since 2.2.0
     */
    public static void eraser(Canvas canvas, GraphicsContext graphContext) {
        graphContext.setStroke(Color.WHITE);
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

    /**
     * <p> This is one of many methods used internally within the cursorUpdate method.
     * This particular method configures and adds the event handlers necessary for
     * a selection tool. It creates a 2D rectangle drawn by the cursor and stores it
     * in the selection field.</p>
     * @param canvas the active canvas.
     * @param graphContext the active graphics context.
     * @since 3.0.0
     */
    public static void select(Canvas canvas, GraphicsContext graphContext) {
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
                Rectangle2D internalSelect = new Rectangle2D(corner.getX(), corner.getY(), width, height);
                selection = internalSelect;
            }
        };

        canvas.setOnMousePressed(mousePress); //add the above created event handlers to the canvas
        canvas.setOnMouseReleased(mouseRel);
    }

    /**
     * <p> This is one of many methods used internally within the cursorUpdate method.
     * This particular method configures and adds the event handlers necessary for
     * copying the selection area. It configures snapshot parameters to take a snapshot
     * of the entire StackPane within the bounds of the selection rectangle, then takes a
     * snapshot and stores it to the copiedImage field.</p>
     * @param stack the StackPane that the active canvas is stacked on.
     * @param selection the 2D rectangle to be used as snapshot parameters.
     * @since 3.0.0
     */
    public static void copy(StackPane stack, Rectangle2D selection) {
        SnapshotParameters sp = new SnapshotParameters();
        sp.setViewport(selection);
        WritableImage img = new WritableImage((int)selection.getWidth(), (int)selection.getHeight());
        stack.snapshot(sp, img);
        /*Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putImage(img);
        clipboard.setContent(content);*/
        copiedImage = img;
    }

    /**
     * <p> This is one of many methods used internally within the cursorUpdate method.
     * This particular method configures and adds the event handlers necessary for
     * a paste tool. It draws the copied image on the canvas at the location of the cursor
     * when pressed, adjusted so that the image is drawn with the cursor at the center.</p>
     * @param canvas the active canvas.
     * @param graphContext the active graphics context.
     * @since 3.0.0
     */
    public static void paste(Canvas canvas, GraphicsContext graphContext) {
        final Point center = new Point();
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        EventHandler<MouseEvent> mousePress = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mousePress) {
                graphContext.beginPath();
                graphContext.moveTo(mousePress.getX(), mousePress.getY());
                center.setX(mousePress.getX() - (copiedImage.getWidth()/2));
                center.setY(mousePress.getY() - (copiedImage.getHeight()/2));
            }
        };

        EventHandler<MouseEvent> mouseRel = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseRel) {
                graphContext.drawImage(copiedImage, center.getX(), center.getY());
            }
        };

        canvas.setOnMousePressed(mousePress);
        canvas.setOnMouseReleased(mouseRel);
    }

    /**
     * <p> This is one of many methods used internally within the cursorUpdate method.
     * This particular method configures and adds the event handlers necessary for
     * a move tool. When the mouse is pressed, it first draws a filled rectangle at the
     * location of the selection rectangle. It then draws the copied image on the canvas
     * at the location of the cursor when the mouse is released, giving the illusion that
     * the selection has been picked up and moved.</p>
     * @param stack the current StackPane that the active canvas is stacked on.
     * @param canvas the active canvas.
     * @param graphContext the active graphics context.
     * @param selection a 2D rectangle object containing the selection area.
     * @since 3.0.0
     */
    public static void move(StackPane stack, Canvas canvas, GraphicsContext graphContext, Rectangle2D selection) {
        final Point center = new Point();
        SnapshotParameters sp = new SnapshotParameters();
        try { sp.setViewport(selection); }
        catch (Exception e) {}
        WritableImage img = new WritableImage((int)selection.getWidth(), (int)selection.getHeight());
        stack.snapshot(sp, img);
        /*Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putImage(img);
        clipboard.setContent(content);*/
        copiedImage = img;
        EventHandler<MouseEvent> mousePress = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mousePress) {
                graphContext.beginPath();
                graphContext.moveTo(mousePress.getX(), mousePress.getY());
                center.setX(mousePress.getX() - (copiedImage.getWidth()/2));
                center.setY(mousePress.getY() - (copiedImage.getHeight()/2));
                graphContext.setFill(Color.WHITE);
                graphContext.fillRect(selection.getMinX(), selection.getMinY(), selection.getWidth(), selection.getHeight());
            }
        };

        EventHandler<MouseEvent> mouseRel = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseRel) {
                graphContext.drawImage(copiedImage, center.getX(), center.getY());
            }
        };

        canvas.setOnMousePressed(mousePress);
        canvas.setOnMouseReleased(mouseRel);
    }

    /**
     * <p> This method is called by the Controller to update the cursor so that the selected tool
     * is being implemented by the cursor. It calls most of the above functions in a large switch
     * statement to implement this functionality. </p>
     * @param canvas the active canvas.
     * @param graphContext the active graphics context.
     * @param editToggles the ToggleGroup containing all the tool toggle buttons.
     * @param colorPicker the ColorPicker used for selecting the color to be used by the tools.
     */
    public static String cursorUpdate (Canvas canvas, GraphicsContext graphContext, ToggleGroup editToggles, ColorPicker colorPicker) {
        removeHandlers(canvas); //start by removing previous event handlers
        String selected; //creates a blank string
        try { //try to get the selected toggle, will fail if null
            selected = editToggles.getSelectedToggle().toString(); //get string for the selected toggle
            selected = selected.substring(53); //get just the end of the string with the button label
        }

        catch (Exception e) { //if null, caught here and event handlers are all removed.
            selected = ""; //if the selected toggle was null, set selected to an empty string
            removeHandlers(canvas); //remove handlers
        }
        String lastTool = null;
        switch (selected) {

            case "": //case also covered by the catch block, here for redundancy.
                removeHandlers(canvas); //just remove handlers
                selection = null;
                lastTool =  "";
                break;

            case "'Draw'":
                selection = null;
                graphContext.setStroke(colorPicker.getValue());
                freeDraw(canvas, graphContext);
                lastTool =  "Free draw";
                break;

            case "'Line'":
                selection = null;
                drawLine(canvas, graphContext);
                lastTool =  "Straight line";
                break;

            case "'Right Triangle'":
                selection = null;
                drawRightTri(canvas, graphContext);
                lastTool =  "Right Triangle";
                break;

            case "'Rectangle'":
                selection = null;
                drawRectangle(canvas, graphContext);
                lastTool =  "Rectangle";
                break;

            case "'Square'":
                selection = null;
                drawSquare(canvas, graphContext);
                lastTool =  "Square";
                break;

            case "'Polygon'":
                selection = null;
                drawPolygon(canvas, graphContext);
                lastTool =  "Polygon";
                break;

            case "'Circle'":
                selection = null;
                drawCircle(canvas, graphContext);
                lastTool =  "Circle";
                break;

            case "'Ellipse'":
                selection = null;
                drawEllipse(canvas, graphContext);
                lastTool =  "Ellipse";
                break;

            case "'Color Grabber'":
                selection = null;
                colorGrabber(canvas, graphContext, editToggles, colorPicker);
                lastTool =  "Color Grabber";
                break;

            case "'Eraser'":
                selection = null;
                eraser(canvas, graphContext);
                lastTool =  "Eraser";
                break;

            case "'Select'":
                select(canvas, graphContext);
                lastTool =  "Select";
                break;

            case "'Copy'":
                copy((StackPane)canvas.getParent(), selection);
                lastTool =  "Select";
                break;

            case "'Paste'":
                selection = null;
                paste(canvas, graphContext);
                lastTool =  "Paste";
                break;

            case "'Move'":
                move((StackPane)canvas.getParent(), canvas, graphContext, selection);
                lastTool =  "Move";
                break;

        }
        return lastTool;
    }

    /**
     * <p> This method was to be used to rotate the canvas clockwise. It currently works, but is bugged.
     * As such, this method is to be considered deprecated, and its functionality is no longer tied to a controller method.</p>
     *
     * @param canvas the active canvas
     * @deprecated
     */
    public static void rotateClockwise90(Canvas canvas) {
        if (selection == null) { //if selection is null, rotate the whole canvas
            canvas.setRotate(90);
        }
    }

    /**
     * <p> This method was to be used to rotate the canvas counter clockwise. It currently works, but is bugged.
     * As such, this method is to be considered deprecated, and its functionality is no longer tied to a controller method.</p>
     *
     * @param canvas the active canvas
     * @deprecated
     */
    public static void rotateCounterClockwise90(Canvas canvas) {
        if (selection == null) { //if selection is null, rotate the whole canvas
            canvas.setRotate(-90);
        }
    }

}
