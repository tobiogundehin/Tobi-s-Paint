package com.mycompany.imageviewer;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

import java.util.Stack;

public class UndoRedo {

    public void setredoStack(Stack undostack, Stack redostack){
        Object toredostackobj = undostack.get(undostack.size() - 1);
        undostack.pop();
        redostack.push(toredostackobj);
    }
    public Image canvasSnapshot(Canvas canvas){
        Image snapshot = canvas.snapshot(null, null);
        return snapshot;
    }

}
