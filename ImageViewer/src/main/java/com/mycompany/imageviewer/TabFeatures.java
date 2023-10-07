package com.mycompany.imageviewer;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.input.KeyCombination;

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
        public void activateShortcuts(Scene scene, KeyCombination newTabShortCut, MenuItem newTabButton, KeyCombination openShortCut, MenuItem openButton, KeyCombination saveAsShortCut, MenuItem saveAsButton, KeyCombination undoShortCut, Button undoButton){
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
                if (undoShortCut.match(event)){
                    undoButton.fire();
                }
            });
        }
    }

