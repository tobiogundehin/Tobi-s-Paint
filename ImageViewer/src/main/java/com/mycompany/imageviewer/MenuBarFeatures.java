package com.mycompany.imageviewer;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MenuBarFeatures {
    public void openButtonClick(FileChooser fileChooser, ImageView imageView, Stage stage, BorderPane layout, Pane root, Canvas canvas, GraphicsContext gc) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        fileChooser.setTitle("Open");
        File saveFile = fileChooser.showOpenDialog(stage);
        try {
            FileInputStream input = new FileInputStream(saveFile);
            Image image = new Image(input, 900, 500, false, false);
            imageView.setImage(image);
            imageView.setPreserveRatio(true);

            gc.drawImage(imageView.getImage(),0,0);
            root.setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.FULL)));
            layout.setCenter(root);

            stage.setTitle(saveFile.getName());
        } catch (FileNotFoundException f) {
            var fnferrormessage = new Label("File was not found");
            layout.setCenter(fnferrormessage);
        }
        //Set default directory to latest files directory
        String lastOpenedLocation = saveFile.getParent();
        fileChooser.setInitialDirectory(new File(lastOpenedLocation));

    }

    public void saveasButtonClick(FileChooser fileChooser, ImageView imageView, Stage stage, BorderPane layout, Pane root, Canvas canvas, GraphicsContext gc){
        fileChooser.setTitle("Save As");
        File saveFile = fileChooser.showSaveDialog(stage);
        Image snapshot = canvas.snapshot(null, null);
        BufferedImage bImage = SwingFXUtils.fromFXImage(snapshot, null);


        try {//from   w  ww  .  j  a v  a 2 s .  c  o  m
            if (bImage != null) {
                ImageIO.write(bImage, "png", saveFile);
            }
        } catch (IOException f) {
            throw new RuntimeException(f);
        }
        // Have it use same code in open function to open the new file that it just saved
        try {
            FileInputStream input = new FileInputStream(saveFile);
            Image image = new Image(input);
            imageView.setImage(image);
            gc.drawImage(image,0,0);
            root.setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.FULL)));
            layout.setCenter(root);
            stage.setTitle(saveFile.getName());
        } catch (FileNotFoundException f) {
            var fnferrormessage = new Label("File was not found");
            layout.setCenter(fnferrormessage);
        }
        //Set default directory to latest files directory
        String lastOpenedLocation = saveFile.getParent();
        fileChooser.setInitialDirectory(new File(lastOpenedLocation));
        }



    public void saveButtonClick(FileChooser fileChooser, ImageView imageView, Stage stage, BorderPane layout, Pane root, Canvas canvas, GraphicsContext gc){
        String saveFilePath = fileChooser.getInitialDirectory().getPath() + "\\" + stage.getTitle();
        File saveFile = new File(saveFilePath);
        Image saveImage = canvas.snapshot(null, null);
        BufferedImage bImage = SwingFXUtils.fromFXImage(saveImage, null);
        try {//from   w  ww  .  j  a v  a 2 s .  c  o  m
            if (bImage != null) {
                ImageIO.write(bImage, "png", saveFile);
            }
        } catch (IOException f) {
            throw new RuntimeException(f);
        }
        try {
            FileInputStream input = new FileInputStream(saveFile);
            Image image = new Image(input);
            imageView.setImage(image);
            gc.drawImage(image,0,0);
            root.setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.FULL)));
            layout.setCenter(root);
            stage.setTitle(saveFile.getName());
        } catch (FileNotFoundException f) {
            var fnferrormessage = new Label("File was not found");
            layout.setCenter(fnferrormessage);
        }
        //Set default directory to latest files directory
        String lastOpenedLocation = saveFile.getParent();
        fileChooser.setInitialDirectory(new File(lastOpenedLocation));
    }

}
