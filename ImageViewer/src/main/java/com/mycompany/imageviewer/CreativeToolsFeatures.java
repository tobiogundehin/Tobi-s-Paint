package com.mycompany.imageviewer;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;

public class CreativeToolsFeatures {
    //Draw button to activate sketch functionality
    public void drawButtonClick(ToggleButton drawButton, Scene defaultScene, GraphicsContext gc, Slider lineThickness, ColorPicker colorPicker){
        if(drawButton.isSelected()) {
            defaultScene.setOnMousePressed(f -> {
                gc.beginPath();
                gc.setLineWidth(lineThickness.getValue());
                gc.setStroke(colorPicker.getValue());
                gc.lineTo(f.getScreenX() - 580, f.getScreenY() - 260);
                gc.stroke();
            });
            defaultScene.setOnMouseDragged(f -> {
                gc.setLineWidth(lineThickness.getValue());
                gc.setStroke(colorPicker.getValue());
                gc.lineTo(f.getScreenX() - 580, f.getScreenY() - 260);
                gc.stroke();
            });
        }
}
}
