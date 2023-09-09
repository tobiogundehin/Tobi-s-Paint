package com.mycompany.imageviewer;

import java.awt.image.BufferedImage;
import java.io.*;

import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;


/**
 * JavaFX App
 */
public class App extends Application {



    @Override
    public void start(Stage stage) {
//      Creating MenuBar with Open, Save and Save as buttons under File Menu, 

        BorderPane layout = new BorderPane();

        Menu filemenu = new Menu("File");
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(filemenu);
        MenuItem openbutton = new MenuItem("Open");
        MenuItem savebutton = new MenuItem("Save");
        MenuItem saveasbutton = new MenuItem("Save As");

        filemenu.getItems().add(openbutton);
        filemenu.getItems().add(savebutton);
        filemenu.getItems().add(saveasbutton);

        layout.setTop(menuBar);

        Scene scene = new Scene(layout, 1000, 800);
        stage.setScene(scene);
        stage.setTitle("Image Viewer");
        stage.show();

//         Adding open button functionality with use of Filechooser
        FileChooser fileChooser = new FileChooser();

        //Imageview declaration
        ImageView imageView = new ImageView();

        //Adding filters for the most popular image file types  
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files (PNG,JPEG,BMP,GIF)", "*.png", "*.jpg", "*.bmp", "*.gif"),
                new FileChooser.ExtensionFilter("Image Files (.jpg)", "*.jpg"),
                new FileChooser.ExtensionFilter("Image Files (.png)", "*.png"),
                new FileChooser.ExtensionFilter("Image Files (.bmp)", "*.bmp"),
                new FileChooser.ExtensionFilter("Image Files (.gif)", "*.gif")
        );

        //Creating the imageview functionality
        openbutton.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(stage);
            try {
                FileInputStream input = new FileInputStream(selectedFile);
                Image image = new Image(input);
                //Setting image to the image view
                imageView.setImage(image);
                //Setting the image view parameters
                imageView.fitHeightProperty();
                imageView.fitWidthProperty();
                imageView.preserveRatioProperty();
                layout.setCenter(imageView);
                stage.setTitle(selectedFile.getName() + "- Image Viewer");
            } catch (FileNotFoundException f) {
                var fnferrormessage = new Label("File was not found");
                layout.setCenter(fnferrormessage);
            }
        });

//        Adding Save As Button functionality
        saveasbutton.setOnAction(e -> {
            fileChooser.setTitle("Save As");
            File saveFile = fileChooser.showSaveDialog(stage);
            Image saveImage = imageView.getImage();
            BufferedImage bImage = SwingFXUtils.fromFXImage(saveImage, null);

            try {//from   w  ww  .  j  a v  a 2 s .  c  o  m
                ImageIO.write(bImage, "png", saveFile);
            } catch (IOException f) {
                throw new RuntimeException(f);
            }


        }
        );

    }

    public static void main(String[] args) {
        launch();
    }

}
