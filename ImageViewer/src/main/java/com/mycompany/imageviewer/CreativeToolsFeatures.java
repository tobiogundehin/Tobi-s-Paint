package com.mycompany.imageviewer;

import javafx.scene.Cursor;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.Stack;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class CreativeToolsFeatures {
    UndoRedo undoRedo = new UndoRedo();
    //Draw button to activate sketch functionality
    public void drawButtonClick(TabPane tabPane, ColorPicker colorPicker, Slider lineThickness, Stack undostack) {
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
                Canvas canvas = (Canvas) selectedTab.getContent();
                canvas.setCursor(Cursor.CROSSHAIR);
                GraphicsContext gc = canvas.getGraphicsContext2D();

                // Set up the mouse pressed event handler
                canvas.setOnMousePressed(event -> {
                    undostack.push(canvas.snapshot(null, null));
                    System.out.println("Snapshot " + (undostack.size() - 1) + " has been taken");
                    gc.beginPath();
                    gc.setLineWidth(lineThickness.getValue()); // Adjust line width as needed
                    gc.setStroke(colorPicker.getValue()); // Adjust line color as needed
                    gc.lineTo(event.getX(), event.getY());
                    gc.setLineDashes(0);
                    gc.stroke();
                });

                // Set up the mouse dragged event handler
                canvas.setOnMouseDragged(event -> {
                    gc.lineTo(event.getX(), event.getY());
                    gc.stroke();
                });
                canvas.setOnMouseReleased(event -> {

                });
        }


    public void lineButtonClick(TabPane tabPane, ColorPicker colorPicker, Slider lineThickness, Stack undostack){
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            if (selectedTab.getContent() instanceof Canvas) {
                Canvas canvas = (Canvas) selectedTab.getContent();
                canvas.setCursor(Cursor.CROSSHAIR);
                GraphicsContext gc = canvas.getGraphicsContext2D();

                // Variables to capture the starting point and track drawing state
                final AtomicReference<Double> startX = new AtomicReference<>(0.0);
                final AtomicReference<Double> startY = new AtomicReference<>(0.0);
                AtomicBoolean isDrawing = new AtomicBoolean(false);

                // Set up the mouse pressed event handler
                canvas.setOnMousePressed(event -> {
                    // Capture the starting point and set drawing state to true
                    undostack.push(canvas.snapshot(null, null));
                    System.out.println("Snapshot " + (undostack.size() - 1) + " has been taken");
                    startX.set(event.getX());
                    startY.set(event.getY());
                    isDrawing.set(true);
                });

                // Set up the mouse released event handler
                canvas.setOnMouseReleased(event -> {
                    if (isDrawing.get()) {
                        double endX = event.getX();
                        double endY = event.getY();

                        gc.setLineDashes(0);
                        gc.setStroke(colorPicker.getValue());
                        gc.setLineWidth(lineThickness.getValue());
                        gc.strokeLine(startX.get(), startY.get(), endX, endY);

                        isDrawing.set(false); // Set drawing state to false
                    }
                });
                };
            }

    public void dashButtonClick(Slider dashLength, TabPane tabPane, Slider lineThickness, ColorPicker colorPicker, Stack undostack){
        dashLength.setVisible(true);
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            if (selectedTab.getContent() instanceof Canvas) {
                Canvas canvas = (Canvas) selectedTab.getContent();
                canvas.setCursor(Cursor.CROSSHAIR);
                GraphicsContext gc = canvas.getGraphicsContext2D();
                // Set up the mouse pressed event handler
                canvas.setOnMousePressed(event -> {
                    undostack.push(canvas.snapshot(null, null));
                    System.out.println("Snapshot " + (undostack.size() - 1) + " has been taken");
                    gc.beginPath();
                    gc.setLineWidth(lineThickness.getValue()); // Adjust line width as needed
                    gc.setStroke(colorPicker.getValue()); // Adjust line color as needed
                    gc.setLineDashes(dashLength.getValue()); // Set the dash length

                    // Start drawing from the initial point
                    gc.lineTo(event.getX(), event.getY());
                    gc.stroke();
                });

                // Set up the mouse dragged event handler
                canvas.setOnMouseDragged(event -> {
                    // Continue drawing with dashes
                    gc.lineTo(event.getX(), event.getY());
                    gc.stroke();
                });

            }
        }

    public void selectButtonClick(TabPane tabPane){
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        if (selectedTab.getContent() instanceof Canvas) {
            Canvas canvas = (Canvas) selectedTab.getContent();
            canvas.setCursor(Cursor.DEFAULT);
            canvas.setOnMousePressed(null);
            canvas.setOnMouseDragged(null);
        }
    }

    public void eyeDropperButtonClick(TabPane tabPane, ColorPicker eyeDropperColorPicker) {
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        if (selectedTab.getContent() instanceof Canvas) {
            Canvas canvas = (Canvas) selectedTab.getContent();
            GraphicsContext gc = canvas.getGraphicsContext2D();
            Image image = canvas.snapshot(null, null);
            canvas.setOnMouseClicked((MouseEvent event) -> {
                double x = event.getX();
                double y = event.getY();

                Color color = image.getPixelReader().getColor((int) x, (int) y);

                // Print the color information to the console
                eyeDropperColorPicker.setValue(color);
            });
        }
    }

    public void eraserButtonClick(TabPane tabPane, Slider lineThickness, Stack undostack){
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        Canvas canvas = (Canvas) selectedTab.getContent();
        canvas.setCursor(Cursor.CROSSHAIR);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Set up the mouse pressed event handler
        canvas.setOnMousePressed(event -> {
            undostack.push(canvas.snapshot(null, null));
            System.out.println("Snapshot " + (undostack.size() - 1) + " has been taken");
            gc.beginPath();
            gc.setLineWidth(lineThickness.getValue()); // Adjust line width as needed
            gc.setStroke(Color.WHITE); // Adjust line color as needed
            gc.lineTo(event.getX(), event.getY());
            gc.setLineDashes(0);
            gc.stroke();
        });

        // Set up the mouse dragged event handler
        canvas.setOnMouseDragged(event -> {
            gc.lineTo(event.getX(), event.getY());
            gc.stroke();
        });
        canvas.setOnMouseReleased(event -> {

        });
        };

    public void textButtonClick(TabPane tabPane, TextField textField, ColorPicker strokeColorPicker, ColorPicker fillColorPicker, Slider lineThicknessSlider) {
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        Canvas canvas = (Canvas) selectedTab.getContent();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        String text = textField.getText();
        canvas.setOnMousePressed(event -> {
            double x = event.getX();
            double y = event.getY();
            gc.setStroke(strokeColorPicker.getValue());
            gc.setFill(fillColorPicker.getValue());
            gc.setLineWidth(lineThicknessSlider.getValue());
            gc.strokeText(text, x, y);
        });
    }
    private WritableImage capturedImage;
    public void captureRectangleAreaCopy(Canvas canvas, Stack undostack) {
        // Create a snapshot parameters object to specify the capture region

        GraphicsContext gc = canvas.getGraphicsContext2D();
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

        SnapshotParameters params = new SnapshotParameters();
        params.setViewport(new javafx.geometry.Rectangle2D(rectX, rectY, width, height));

        // Capture the specified region as a WritableImage
        capturedImage = canvas.snapshot(params, null);

    });

}
    public void captureRectangleAreaMove(Canvas canvas, Stack undostack) {
        // Create a snapshot parameters object to specify the capture region

        GraphicsContext gc = canvas.getGraphicsContext2D();
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


            SnapshotParameters params = new SnapshotParameters();
            params.setViewport(new javafx.geometry.Rectangle2D(rectX, rectY, width, height));

            // Capture the specified region as a WritableImage
            capturedImage = canvas.snapshot(params, null);
            gc.clearRect(rectX, rectY, width, height);
        });

    }
    public Image getCapturedImage() {
        return capturedImage;
    }
}
