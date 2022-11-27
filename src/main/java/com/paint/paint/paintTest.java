package com.paint.paint;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 * <p> paintTest.java is a class file containing my three unit tests, which test the polygon math calculations and
 * the canvas rescaling factor calculations. </p>
 * @author ianmc
 * @since 3.5.0
 */
public class paintTest {

    /**
     * <p> This method contains an array of predefined x-values for the 5 vertices of a polygon at 0,0 with a radius of 100.
     * It then creates a testX array that contains the values returned from the Edit.getPolygonSides() method, and compares them
     * to the expected values. It returns true if the values match, and false if they do not.</p>
     * @since 3.5.0
     * @return the result of the test comparison
     */
    public static boolean getPolygonXSidesTest() {
        double[] expectedX = new double[5]; //define expected points for given input
        expectedX[0] = 0;
        expectedX[1] = -95.10565162951535;
        expectedX[2] = -58.77852522924732;
        expectedX[3] = 58.7785252292473;
        expectedX[4] = 95.10565162951536;
        double[] testX = Edit.getPolygonSides(0,0, 100, 5, true);
        for (int i = 0; i <= 4; i++) {
            if (expectedX[i] != testX[i]) {
                System.out.println(false);
                return false;
            }
        }
        System.out.println(true);
        return true;
    }

    /**
     * <p> This method contains an array of predefined y-values for the 5 vertices of a polygon at 0,0 with a radius of 100.
     * It then creates a testY array that contains the values returned from the Edit.getPolygonSides() method, and compares them
     * to the expected values. It returns true if the values match, and false if they do not.</p>
     * @since 3.5.0
     * @return the result of the test comparison
     */
    public static boolean getPolygonYSidesTest() {
        double[] expectedY = new double[5]; //define expected points for given input
        expectedY[0] = -100;
        expectedY[1] = -30.901699437494745;
        expectedY[2] = 80.90169943749473;
        expectedY[3] = 80.90169943749476;
        expectedY[4] = -30.901699437494724;
        double[] testY = Edit.getPolygonSides(0, 0, 100, 5, false);
        for (int i = 0; i <= 4; i++) {
            if (expectedY[i] != testY[i]) {
                System.out.println(false);
                return false;
            }
        }
        System.out.println(true);
        return true;
    }

    /**
     * <p> This method creates a dummy canvas object with a test graphics context, and then an expected scalar value. It then
     * creates a testScale value which is assigned to the return value of Display.updateCanvasSize with an input value of 0. If the test scale matches
     * the expected scale (both 1) it returns true, otherwise, returns false. </p>
     * @since 3.5.0
     * @return the result of the test comparison
     */
    public static boolean canvasRescaleTest() {
        Canvas testCanvas = new Canvas();
        GraphicsContext testGC = testCanvas.getGraphicsContext2D();
        double expectedScale = 1;
        double testScale = Display.updateCanvasSize(testCanvas, testGC, 0);
        if (expectedScale == testScale) {
            System.out.println(true);
            return true;
        }
        else {
            System.out.println(false);
            return false;
        }
    }
}
