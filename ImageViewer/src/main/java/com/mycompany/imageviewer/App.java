package com.mycompany.imageviewer;

import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        MenuBarFeatures menuBarFeatures = new MenuBarFeatures();
        CreativeToolsFeatures creativeToolsFeatures = new CreativeToolsFeatures();
        HelpInfo helpInfo = new HelpInfo();
        FileChooser fileChooser = new FileChooser();
        ImageView imageView = new ImageView();

        //Menu Bar declaration and initialization
        BorderPane layout = new BorderPane();
        Menu filemenu = new Menu("File");
        Menu helpmenu = new Menu("Help");
        MenuItem infoButton = new MenuItem("Information");
        helpmenu.getItems().addAll(infoButton);
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(filemenu, helpmenu);
        MenuItem openButton = new MenuItem("Open");
        MenuItem saveButton = new MenuItem("Save");
        MenuItem saveAsButton = new MenuItem("Save As");
        filemenu.getItems().addAll(openButton, saveButton, saveAsButton);
        layout.setTop(menuBar);

        //Creative Tools on the left side of the canvas
        VBox creativeTools = new VBox();
        ToggleButton drawButton = new ToggleButton("Draw");
        Slider lineThickness = new Slider(1, 20, 1);
        lineThickness.setShowTickLabels(true);
        lineThickness.setShowTickMarks(true);
        ColorPicker colorPicker = new ColorPicker(Color.BLACK);
        creativeTools.getChildren().addAll(drawButton, lineThickness, colorPicker);
        layout.setLeft(creativeTools);

        //Border for canvas
        Pane root = new Pane();
        root.setMaxWidth(900);
        root.setMaxHeight(500);
        root.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        //Create canvas and add to pane
        Canvas canvas = new Canvas(900, 500);
        canvas.setCursor(Cursor.CROSSHAIR);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        layout.setCenter(root);
        //Open default scene
        Scene defaultScene = new Scene(layout, 1200, 800);
        stage.setScene(defaultScene);
        stage.setTitle("Paint");
        stage.show();

        //Adding filters for the supported image file types
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files (PNG,JPEG,BMP,GIF)", "*.png", "*.jpg", "*.bmp", "*.gif"),
            new FileChooser.ExtensionFilter("Image Files (.jpg)", "*.jpg"),
            new FileChooser.ExtensionFilter("Image Files (.png)", "*.png"),
            new FileChooser.ExtensionFilter("Image Files (.bmp)", "*.bmp"),
            new FileChooser.ExtensionFilter("Image Files (.gif)", "*.gif")
        );

        openButton.setOnAction(e -> {menuBarFeatures.openButtonClick(fileChooser, imageView, stage, layout, root, canvas, gc);});
        saveAsButton.setOnAction(e -> {menuBarFeatures.saveasButtonClick(fileChooser, imageView, stage, layout, root, canvas, gc);});
        saveButton.setOnAction(e -> {menuBarFeatures.saveButtonClick(fileChooser, imageView, stage, layout, root, canvas, gc);});
        drawButton.setOnAction(e -> {creativeToolsFeatures.drawButtonClick(drawButton, defaultScene, gc,lineThickness, colorPicker);});
        infoButton.setOnAction(e ->{helpInfo.helpInfoDisp();});
}
    public static void main(String[] args) {
        launch();
    }
}
