package com.paint.paint;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class paintTest {

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
