package com.mycompany.imageviewer;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MenuBarFeatures {
    public void openButtonClick(FileChooser fileChooser, ImageView imageView, Stage stage, BorderPane borderPane, Tab selectedTab, TabPane tabPane) {
        Canvas canvas = new Canvas(900, 500);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        canvas.setCursor(Cursor.CROSSHAIR);
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        fileChooser.setTitle("Open");
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            try {
                FileInputStream input = new FileInputStream(selectedFile);
                Image image = new Image(input, canvas.getWidth(), canvas.getHeight(), false, false);
                imageView.setImage(image);
                imageView.setPreserveRatio(true);
                gc.drawImage(imageView.getImage(), 0, 0);
                selectedTab.setContent(canvas);
                selectedTab.setText(selectedFile.getName());
            } catch (FileNotFoundException e) {
                var fnfErrorMessage = new Label("File was not found");
                borderPane.setCenter(fnfErrorMessage);
            }
            // Set default directory to the directory of the opened file
            String lastOpenedLocation = selectedFile.getParent();
            fileChooser.setInitialDirectory(new File(lastOpenedLocation));
            ;
        }
    }

    public void saveasButtonClick(FileChooser fileChooser, ImageView imageView, Stage stage, BorderPane layout, Tab selectedTab){
        Node content = selectedTab.getContent();
        fileChooser.setTitle("Save As");
        File saveFile = fileChooser.showSaveDialog(stage);
        Image snapshot = content.snapshot(null, null);
        BufferedImage bImage = SwingFXUtils.fromFXImage(snapshot, null);
        selectedTab.setText(saveFile.getName());

        try {//from   w  ww  .  j  a v  a 2 s .  c  o  m
            if (bImage != null) {
                ImageIO.write(bImage, "png", saveFile);
            }
        } catch (IOException f) {
            throw new RuntimeException(f);
        }
        //Set default directory to latest files directory
        String lastOpenedLocation = saveFile.getParent();
        fileChooser.setInitialDirectory(new File(lastOpenedLocation));
        }

    public void saveButtonClick(FileChooser fileChooser, ImageView imageView, Stage stage, BorderPane layout, Tab selectedTab){
        Node content = selectedTab.getContent();
        String saveFilePath = fileChooser.getInitialDirectory().getPath() + "\\" + selectedTab.getText();
        File saveFile = new File(saveFilePath);
        Image saveImage = content.snapshot(null, null);
        BufferedImage bImage = SwingFXUtils.fromFXImage(saveImage, null);
        try {//from   w  ww  .  j  a v  a 2 s .  c  o  m
            if (bImage != null) {
                ImageIO.write(bImage, "png", saveFile);
            }
        } catch (IOException f) {
            throw new RuntimeException(f);
        }
        //Set default directory to latest files directory
        String lastOpenedLocation = saveFile.getParent();
        fileChooser.setInitialDirectory(new File(lastOpenedLocation));
    }

    public void setFileChooserFilters(FileChooser fileChooser){
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files (PNG,JPEG,BMP,GIF)", "*.png", "*.jpg", "*.bmp", "*.gif"),
                new FileChooser.ExtensionFilter("Image Files (.jpg)", "*.jpg"),
                new FileChooser.ExtensionFilter("Image Files (.png)", "*.png"),
                new FileChooser.ExtensionFilter("Image Files (.bmp)", "*.bmp"),
                new FileChooser.ExtensionFilter("Image Files (.gif)", "*.gif")
        );
    }

    public void newTabButtonClick(TabPane tabPane, ConfirmationDialog confirmationDialog, MenuBarFeatures menuBarFeatures, FileChooser fileChooser, ImageView imageView, Stage stage, BorderPane borderPane){
        TabFeatures.DrawingTab addTab;
        addTab = new TabFeatures.DrawingTab("New Tab", 900, 500);
        addTab.getCanvas().heightProperty().bind(tabPane.heightProperty());
        addTab.getCanvas().widthProperty().bind(tabPane.widthProperty());
        tabPane.getTabs().add(addTab.getTab());
        confirmationDialog.TabCloseAlert(addTab.getTab(),menuBarFeatures,fileChooser,imageView,stage,borderPane);
    }

    public void openButtonClickAdd(TabPane tabPane, ConfirmationDialog confirmationDialog, MenuBarFeatures menuBarFeatures, FileChooser fileChooser, ImageView imageView, Stage stage, BorderPane borderPane, TabFeatures.DrawingTab newTab){
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        if (selectedTab == null) {
            TabFeatures.DrawingTab addTab;
            addTab = new TabFeatures.DrawingTab("New Tab", 900, 500);
            tabPane.getTabs().add(addTab.getTab());
            confirmationDialog.TabCloseAlert(addTab.getTab(),menuBarFeatures,fileChooser,imageView,stage,borderPane);
        }
        selectedTab = tabPane.getSelectionModel().getSelectedItem();
        menuBarFeatures.openButtonClick(fileChooser, imageView, stage, borderPane, selectedTab, tabPane);
        confirmationDialog.TabCloseAlert(newTab.getTab(),menuBarFeatures,fileChooser,imageView,stage,borderPane);
    }

}
