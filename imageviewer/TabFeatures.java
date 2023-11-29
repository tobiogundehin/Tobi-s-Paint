package com.mycompany.imageviewer;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCombination;

import java.util.concurrent.atomic.AtomicReference;

public class TabFeatures {
    public static class DrawingTab {
        private Tab tab;
        private Canvas canvas;
        private GraphicsContext gc;

        public DrawingTab(String tabName, double width, double height) {
            tab = new Tab(tabName);
            canvas = new Canvas(width, height);
            canvas.setCursor(Cursor.CROSSHAIR);
            gc = canvas.getGraphicsContext2D();
            tab.setContent(canvas);
        }

        public Tab getTab() {
            return tab;
        }

        public Canvas getCanvas() {
            return canvas;
        }

        public GraphicsContext getGC() {
            return gc;
        }

    }

    public void activateShortcuts(Scene scene, KeyCombination newTabShortCut, MenuItem newTabButton, KeyCombination openShortCut, MenuItem openButton, KeyCombination saveAsShortCut, MenuItem saveAsButton, KeyCombination undoShortCut, Button undoButton) {
        scene.setOnKeyPressed(event -> {
            if (newTabShortCut.match(event)) {
                // Simulate a button click
                newTabButton.fire();
            }
            if (openShortCut.match(event)) {
                // Simulate a button click
                openButton.fire();
            }
            if (saveAsShortCut.match(event)) {
                // Simulate a button click
                saveAsButton.fire();
            }
            if (undoShortCut.match(event)) {
                undoButton.fire();
            }
        });
    }

    public void rotateCanvas(double angle, Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
        canvas.snapshot(null, writableImage);
        ImageView iv = new ImageView(writableImage);
        iv.setRotate(angle);
        WritableImage writableImage2 = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
        iv.snapshot(null, writableImage2);
        gc.drawImage(writableImage2, 0, 0);
    }

    public void flipCanvas(String flipName, Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        if (flipName == "Vertical Flip") {
            WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
            canvas.snapshot(null, writableImage);
            ImageView iv = new ImageView(writableImage);
            iv.setScaleX(-1);
            WritableImage writableImage2 = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
            iv.snapshot(null, writableImage2);
            gc.drawImage(writableImage2, 0, 0);
        }
        if (flipName == "Horizontal Flip") {
            WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
            canvas.snapshot(null, writableImage);
            ImageView iv = new ImageView(writableImage);
            iv.setScaleY(-1);
            WritableImage writableImage2 = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
            iv.snapshot(null, writableImage2);
            gc.drawImage(writableImage2, 0, 0);
        }
    }
    private WritableImage capturedImage;
    public void rotateSelection(Canvas canvas, double angle) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        AtomicReference<Double> startX = new AtomicReference<>(0.0);
        AtomicReference<Double> startY = new AtomicReference<>(0.0);
        AtomicReference<Double> endX = new AtomicReference<>(0.0);
        AtomicReference<Double> endY = new AtomicReference<>(0.0);

        canvas.setOnMousePressed(mouseEvent ->  {
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

            SnapshotParameters params = new SnapshotParameters();
            params.setViewport(new javafx.geometry.Rectangle2D(rectX, rectY, width, height));

            // Capture the specified region as a WritableImage
            capturedImage = canvas.snapshot(params, null);
            canvas.snapshot(null, capturedImage);
            ImageView iv = new ImageView(capturedImage);
            iv.setRotate(angle);
            WritableImage writableImage2 = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
            iv.snapshot(null, writableImage2);
            gc.drawImage(writableImage2, 0, 0);
                });
        }
}


