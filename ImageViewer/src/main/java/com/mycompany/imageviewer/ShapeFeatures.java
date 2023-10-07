package com.mycompany.imageviewer;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

import java.util.Stack;
import java.util.concurrent.atomic.AtomicReference;

public class ShapeFeatures {
    public Button createShapeButton(String shapeName) {
        Button button = new Button(shapeName);
        button.setPrefWidth(100);
        return button;
    }
    public void drawSquare(GraphicsContext gc, Stack undostack, Canvas canvas) {
        AtomicReference<Double> startX = new AtomicReference<>((double) 0);
        AtomicReference<Double> startY = new AtomicReference<>((double) 0);
        AtomicReference<Double> currentX = new AtomicReference<>((double) 0);
        AtomicReference<Double> currentY = new AtomicReference<>((double) 0);
        canvas.setOnMousePressed(mouseEvent ->  {
            undostack.push(canvas.snapshot(null, null));
            startX.set(mouseEvent.getX());
            startY.set(mouseEvent.getY());
        });
        canvas.setOnMouseReleased(mouseEvent -> {
            currentX.set(mouseEvent.getX());
            currentY.set(mouseEvent.getY());

            double width = Math.abs(currentX.get() - startX.get());
            double height = Math.abs(currentY.get() - startY.get());

            double sideLength = Math.min(width, height); // Use the smaller dimension to make a square

            // Calculate the x and y coordinates to draw the square centered around the starting point
            double squareX = startX.get() < currentX.get() ? startX.get() : startX.get() - sideLength;
            double squareY = startY.get() < currentY.get() ? startY.get() : startY.get() - sideLength;

            gc.strokeRect(squareX, squareY, sideLength, sideLength);
            gc.fillRect(squareX, squareY, sideLength, sideLength);
        });
    }

    public void drawCircle(GraphicsContext gc, Stack undostack, Canvas canvas) {
        AtomicReference<Double> startX = new AtomicReference<>(0.0);
        AtomicReference<Double> startY = new AtomicReference<>(0.0);

        canvas.setOnMousePressed(mouseEvent ->  {
            undostack.push(canvas.snapshot(null, null));
            startX.set(mouseEvent.getX());
            startY.set(mouseEvent.getY());
        });

        canvas.setOnMouseReleased(mouseEvent -> {
            double endX = mouseEvent.getX();
            double endY = mouseEvent.getY();

            double radius = Math.sqrt(Math.pow(endX - startX.get(), 2) + Math.pow(endY - startY.get(), 2));
            double centerX = (startX.get() + endX) / 2;
            double centerY = (startY.get() + endY) / 2;

            // Draw the circle on the main canvas
            gc.strokeOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);
            gc.fillOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);
        });
    }

    public void drawRectangle(GraphicsContext gc, Stack undostack, Canvas canvas) {
        AtomicReference<Double> startX = new AtomicReference<>(0.0);
        AtomicReference<Double> startY = new AtomicReference<>(0.0);
        AtomicReference<Double> endX = new AtomicReference<>(0.0);
        AtomicReference<Double> endY = new AtomicReference<>(0.0);

        canvas.setOnMousePressed(mouseEvent ->  {
            undostack.push(canvas.snapshot(null, null));
            startX.set(mouseEvent.getX());
            startY.set(mouseEvent.getY());
        });

        canvas.setOnMouseReleased(mouseEvent -> {
            endX.set(mouseEvent.getX());
            endY.set(mouseEvent.getY());

            // Calculate the width and height of the rectangle
            double width = Math.abs(endX.get() - startX.get());
            double height = Math.abs(endY.get() - startY.get());

            // Calculate the x and y coordinates for drawing the rectangle
            double rectX = Math.min(startX.get(), endX.get());
            double rectY = Math.min(startY.get(), endY.get());

            gc.strokeRect(rectX, rectY, width, height);
            gc.fillRect(rectX, rectY, width, height);
        });
    }

    public void drawEllipse(GraphicsContext gc, Stack undostack, Canvas canvas) {
        AtomicReference<Double> startX = new AtomicReference<>(0.0);
        AtomicReference<Double> startY = new AtomicReference<>(0.0);
        AtomicReference<Double> endX = new AtomicReference<>(0.0);
        AtomicReference<Double> endY = new AtomicReference<>(0.0);

        canvas.setOnMousePressed(mouseEvent ->  {
            undostack.push(canvas.snapshot(null, null));
            startX.set(mouseEvent.getX());
            startY.set(mouseEvent.getY());
        });

        canvas.setOnMouseReleased(mouseEvent -> {
            endX.set(mouseEvent.getX());
            endY.set(mouseEvent.getY());

            // Calculate the semi-major and semi-minor axes for the ellipse
            double semiMajorAxis = Math.abs(endX.get() - startX.get()) / 2;
            double semiMinorAxis = Math.abs(endY.get() - startY.get()) / 2;

            // Calculate the center of the ellipse
            double centerX = startX.get() + semiMajorAxis;
            double centerY = startY.get() + semiMinorAxis;

            // Clear the canvas and draw the ellipse
            gc.strokeOval(centerX - semiMajorAxis, centerY - semiMinorAxis, semiMajorAxis * 2, semiMinorAxis * 2);
            gc.fillOval(centerX - semiMajorAxis, centerY - semiMinorAxis, semiMajorAxis * 2, semiMinorAxis * 2);
        });
    }


//    public void drawTriangle(GraphicsContext gc, Stack undostack, Canvas canvas) {
//        undostack.push(canvas.snapshot(null, null));
//        System.out.println("Snapshot " + (undostack.size() - 1) + " has been taken");
//        double[] xPoints = {700, 800, 650};
//        double[] yPoints = {50, 150, 150};
//        gc.fillPolygon(xPoints, yPoints, 3);
//        gc.strokePolygon(xPoints, yPoints, 3);
//    }
public void drawTriangle(GraphicsContext gc, double[] xPoints, double[] yPoints) {
    gc.setFill(Color.TRANSPARENT);
    gc.setStroke(Color.BLACK);
    gc.setLineWidth(2.0);

    gc.beginPath();
    gc.moveTo(xPoints[0], yPoints[0]);

    for (int i = 1; i < 3; i++) {
        gc.lineTo(xPoints[i], yPoints[i]);
    }

    gc.closePath();
    gc.stroke();
}
    public void drawPolygon(GraphicsContext gc, double[] xPoints, double[] yPoints, int sides) {
        gc.setFill(Color.TRANSPARENT);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2.0);

        gc.beginPath();
        gc.moveTo(xPoints[0], yPoints[0]);

        for (int i = 1; i < sides; i++) {
            gc.lineTo(xPoints[i], yPoints[i]);
        }

        gc.closePath();
        gc.stroke();
    }

}
