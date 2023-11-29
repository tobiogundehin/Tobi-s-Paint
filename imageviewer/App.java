package com.mycompany.imageviewer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicReference;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        MenuBarFeatures menuBarFeatures = new MenuBarFeatures();
        CreativeToolsFeatures creativeToolsFeatures = new CreativeToolsFeatures();
        HelpInfo helpInfo = new HelpInfo();
        FileChooser fileChooser = new FileChooser();
        ImageView imageView = new ImageView();
        ConfirmationDialog confirmationDialog = new ConfirmationDialog();
        ShapeFeatures shapeFeatures = new ShapeFeatures();
        TabFeatures tabFeatures = new TabFeatures();
        JavaPuns javaPuns = new JavaPuns();
        ButtonGraphics buttonGraphics = new ButtonGraphics();
        LogMessage logMessage = new LogMessage();

        //Use BorderPane for Layout
        BorderPane borderPane = new BorderPane();

        //Menu Bar declaration and initialization
        //Help Menu
        Menu helpmenu = new Menu("Help");
        MenuItem infoButton = new MenuItem("Information");
        MenuItem shortCutButton = new MenuItem("Shortcuts");
        helpmenu.getItems().addAll(infoButton, shortCutButton);

        //File Menu
        Menu filemenu = new Menu("File");
        MenuItem openButton = new MenuItem("Open");
        MenuItem saveButton = new MenuItem("Save");
        MenuItem saveAsButton = new MenuItem("Save As");
        MenuItem newTabButton = new MenuItem("New Tab");
        MenuItem clearTabButton = new MenuItem("Clear Tab");
        filemenu.getItems().addAll(newTabButton, openButton, saveButton, saveAsButton, clearTabButton);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(filemenu, helpmenu);
        borderPane.setTop(menuBar);

        //Shortcuts
        KeyCombination newTabShortCut = new KeyCodeCombination(KeyCode.T, KeyCombination.CONTROL_DOWN) ; //Ctrl T
        KeyCombination openShortCut = new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN) ; //Ctrl O
        KeyCombination saveAsShortCut = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN) ; //Ctrl S
        KeyCombination undoShortcut = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN) ; //Ctrl Z

        //Creative Tools on the left side of the canvas
        VBox creativeTools = new VBox(20);
        Button drawButton = new Button("Draw");
        Tooltip drawButtonTooltip = new Tooltip("Use this for Freehand Drawing");
        Tooltip.install(drawButton, drawButtonTooltip);
        buttonGraphics.setButtonGraphic(drawButton);

        Button lineButton = new Button("Line");
        Tooltip lineButtonTooltip = new Tooltip("Drag the cursor from point to point and draw a straight line connecting them");
        Tooltip.install(lineButton, lineButtonTooltip);
        buttonGraphics.setButtonGraphic(lineButton);

        Button dashButton = new Button("Dashes");
        Tooltip dashButtonTooltip = new Tooltip("Use this for Freehand Drawing(With Dashed Lines)");
        Tooltip.install(dashButton, dashButtonTooltip);
        buttonGraphics.setButtonGraphic(dashButton);

        Button selectButton = new Button("Select");
        Tooltip selectButtonTooltip = new Tooltip("Use this to reset the cursor");
        Tooltip.install(selectButton, selectButtonTooltip);
        buttonGraphics.setButtonGraphic(selectButton);

        Button eyeDropperButton = new Button("EyeDropper");
        Tooltip eyeDropperButtonTooltip = new Tooltip("Use this to get the color of a specific point");
        Tooltip.install(eyeDropperButton, eyeDropperButtonTooltip);
        buttonGraphics.setButtonGraphic(eyeDropperButton);

        Button eraserButton = new Button("Eraser");
        Tooltip eraserButtonTooltip = new Tooltip("Use this to clear an area of the canvas");
        Tooltip.install(eraserButton, eraserButtonTooltip);
        buttonGraphics.setButtonGraphic(eraserButton);

        Button textButton = new Button("Add Text");
        TextField textField = new TextField("Enter Text");

        Button copyButton = new Button("Copy");
        Button moveButton = new Button("Move");
        Button pasteButton = new Button("Paste");
        Button rotateButton = new Button("Rotate");
        Button vFlipButton = new Button("Vertical Flip");
        Button hFlipButton = new Button("Horizontal Flip");
        Button rotateSelectionButton = new Button("Rotate Selection");
        Button toStrokeButton = new Button("To Stroke");
        Button toFillButton = new Button("To Fill");
        Button undoButton = new Button("Undo");
        Button redoButton = new Button("Redo");
        Button squareButton = shapeFeatures.createShapeButton("Square");
        Button circleButton = shapeFeatures.createShapeButton("Circle");
        Button rectangleButton = shapeFeatures.createShapeButton("Rectangle");
        Button ellipseButton = shapeFeatures.createShapeButton("Ellipse");
        Button triangleButton = shapeFeatures.createShapeButton("Triangle");
        Button polygonButton = shapeFeatures.createShapeButton("Polygon");

        ChoiceBox<String> fontChoiceBox = new ChoiceBox<>();
        fontChoiceBox.getItems().addAll(Font.getFamilies());
        fontChoiceBox.setValue("Calibri");

        Slider dashLength = new Slider(1, 50, 3);
        dashLength.setShowTickMarks(true);
        dashLength.setShowTickLabels(true);

        Slider lineThicknessSlider = new Slider(1, 30, 1);
        ColorPicker strokeColorPicker = new ColorPicker(Color.BLACK);
        ColorPicker fillColorPicker = new ColorPicker(Color.BLACK);
        ColorPicker eyeDropperColorPicker = new ColorPicker(Color.TRANSPARENT);
        eyeDropperColorPicker.setDisable(true);
        Slider polygonSidesSlider = new Slider(3, 15, 3);
        polygonSidesSlider.setShowTickLabels(true);
        polygonSidesSlider.setSnapToTicks(true);
        polygonSidesSlider.setMajorTickUnit(1);

        VBox controlsVBox = new VBox(10);
        controlsVBox.setPadding(new Insets(10));
        HBox toStrokeorFillBox = new HBox(toFillButton, toStrokeButton);
        HBox undoredobox = new HBox(undoButton, redoButton);
        toStrokeorFillBox.setSpacing(10);
        undoredobox.setSpacing(10);
        controlsVBox.getChildren().addAll(
                new Label("Dash Length:"), dashLength,
                new Label("Line Thickness:"), lineThicknessSlider,
                new Label("Stroke Color:"), strokeColorPicker,
                new Label("Fill Color:"), fillColorPicker,
                new Label("EyeDropper Color"), eyeDropperColorPicker,
                new Label("Number of Sides for Polygon"),polygonSidesSlider
        );

        GridPane drawModesGrid = new GridPane();
//        drawModesGrid.setHgap(5);
        drawModesGrid.setVgap(10);
        drawModesGrid.add(drawButton, 0, 0);
        drawModesGrid.add(lineButton, 1, 0);
        drawModesGrid.add(dashButton, 0, 1);
        drawModesGrid.add(selectButton, 1, 1);
        drawModesGrid.add(eyeDropperButton, 0, 2);
        drawModesGrid.add(eraserButton, 1, 2);
        drawModesGrid.add(textButton, 0, 3);
        drawModesGrid.add(textField, 1, 3);
        drawModesGrid.add(fontChoiceBox,0,4);
        drawModesGrid.add(copyButton, 0, 5);
        drawModesGrid.add(moveButton, 1, 5);
        drawModesGrid.add(pasteButton, 0, 6);
        drawModesGrid.add(rotateButton,1,6);
        drawModesGrid.add(vFlipButton, 0, 7);
        drawModesGrid.add(hFlipButton,1,7);
        drawModesGrid.add(rotateSelectionButton, 0, 8);

        GridPane shapeButtonsGrid = new GridPane();
        shapeButtonsGrid.setHgap(10);
        shapeButtonsGrid.setVgap(10);
        shapeButtonsGrid.add(squareButton, 0, 0);
        shapeButtonsGrid.add(circleButton, 1, 0);
        shapeButtonsGrid.add(rectangleButton, 0, 1);
        shapeButtonsGrid.add(ellipseButton, 1, 1);
        shapeButtonsGrid.add(triangleButton, 0, 2);
        shapeButtonsGrid.add(polygonButton, 1,2);


        creativeTools.getChildren().addAll(drawModesGrid,shapeButtonsGrid, controlsVBox, toStrokeorFillBox, undoredobox);
        borderPane.setLeft(creativeTools);

        Label javaPun = new Label(javaPuns.returnPun());
        borderPane.setBottom(javaPun);

        //Tab Format
        TabPane tabPane = new TabPane();
        borderPane.setCenter(tabPane);

        TabFeatures.DrawingTab newTab = new TabFeatures.DrawingTab("New Tab", 900, 500);
        tabPane.getTabs().add(newTab.getTab());

        //Stacks for undo and redo
        Stack<Object> undostack = new Stack<>();
        Stack<Object> redostack = new Stack<>();

        //Timers and Time-related functionalities
        Timer autoSaveTimer = new Timer();
        Label timeLeft = new Label();
        TimerTask autoSaveTask = new TimerTask() {
            int tick = 10;
            @Override
            public void run() {
                Platform.runLater(() -> {
                    timeLeft.setText(Integer.toString(tick));
                    borderPane.setBottom(timeLeft);
                    tick--;
                    if (tick == 0) {
                        tick = 10;
                        saveButton.fire();
                    }
                });
            }

            ;
        };

        //Open default scene
        Scene defaultScene = new Scene(borderPane, 1500, 1000);
        stage.setScene(defaultScene);
        stage.setTitle("Paint");
        stage.show();

        //Adding filters for the supported image file types
        menuBarFeatures.setFileChooserFilters(fileChooser);
        //Tab Close Alert
        confirmationDialog.TabCloseAlert(newTab.getTab(), menuBarFeatures, fileChooser, imageView, stage, borderPane);

        //Shortcuts
        tabFeatures.activateShortcuts(defaultScene,newTabShortCut,newTabButton,openShortCut, openButton, saveAsShortCut, saveAsButton,undoShortcut, undoButton);

        //File Menu MenuItems
        newTabButton.setOnAction(e -> {
            menuBarFeatures.newTabButtonClick(tabPane, confirmationDialog, menuBarFeatures, fileChooser, imageView, stage, borderPane);
            logMessage.logMessage(newTabButton.getText());
                });

        openButton.setOnAction(e -> {
            menuBarFeatures.openButtonClickAdd(tabPane, confirmationDialog,menuBarFeatures,fileChooser,imageView,stage,borderPane,newTab);
            });


        saveAsButton.setOnAction(e -> {
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            menuBarFeatures.saveasButtonClick(fileChooser, imageView, stage, borderPane, selectedTab);
            autoSaveTimer.scheduleAtFixedRate(autoSaveTask, 0, 10000);
            });

        saveButton.setOnAction(e -> {
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            menuBarFeatures.saveButtonClick(fileChooser, imageView, stage, borderPane, selectedTab);
            autoSaveTimer.scheduleAtFixedRate(autoSaveTask, 0, 10000);
            });

        clearTabButton.setOnAction(e -> {
            confirmationDialog.ClearTabAlert(tabPane, clearTabButton);
            logMessage.logMessage(clearTabButton.getText());
        });

        //Creative Tools
        drawButton.setOnAction(e -> {
            creativeToolsFeatures.selectButtonClick(tabPane);
            creativeToolsFeatures.drawButtonClick(tabPane, strokeColorPicker, lineThicknessSlider, undostack);
            logMessage.logMessage(drawButton.getText());
        });

        selectButton.setOnAction(e -> {
            creativeToolsFeatures.selectButtonClick(tabPane);
            logMessage.logMessage(selectButton.getText());
        });

        dashButton.setOnAction(e -> {
            creativeToolsFeatures.selectButtonClick(tabPane);
            creativeToolsFeatures.dashButtonClick(dashLength, tabPane, lineThicknessSlider, strokeColorPicker, undostack);
            logMessage.logMessage(dashButton.getText());
        });

        lineButton.setOnAction(e -> {
            creativeToolsFeatures.selectButtonClick(tabPane);
            creativeToolsFeatures.lineButtonClick(tabPane, strokeColorPicker, lineThicknessSlider, undostack);
            logMessage.logMessage(lineButton.getText());
        });

        eyeDropperButton.setOnAction(e -> {
            creativeToolsFeatures.selectButtonClick(tabPane);
            creativeToolsFeatures.eyeDropperButtonClick(tabPane, eyeDropperColorPicker);
            logMessage.logMessage(eyeDropperButton.getText());
        });

        eraserButton.setOnAction(e ->{
            creativeToolsFeatures.eraserButtonClick(tabPane, lineThicknessSlider, undostack);
            logMessage.logMessage(eraserButton.getText());
        });

        textButton.setOnAction(e ->{
            creativeToolsFeatures.selectButtonClick(tabPane);
            creativeToolsFeatures.textButtonClick(tabPane, textField, strokeColorPicker, fillColorPicker, lineThicknessSlider);
        });

        copyButton.setOnAction(e ->{
            creativeToolsFeatures.selectButtonClick(tabPane);
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            Canvas canvas = (Canvas) selectedTab.getContent();
            creativeToolsFeatures.captureRectangleAreaCopy(canvas,undostack);
            logMessage.logMessage(copyButton.getText());
        });
        moveButton.setOnAction(e ->{
            creativeToolsFeatures.selectButtonClick(tabPane);
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            Canvas canvas = (Canvas) selectedTab.getContent();
            GraphicsContext gc = canvas.getGraphicsContext2D();
            creativeToolsFeatures.captureRectangleAreaMove(canvas,undostack);
            logMessage.logMessage(moveButton.getText());
        });

        pasteButton.setOnAction(e -> {
            creativeToolsFeatures.selectButtonClick(tabPane);
            AtomicReference<Double> startX = new AtomicReference<>(0.0);
            AtomicReference<Double> startY = new AtomicReference<>(0.0);
            AtomicReference<Double> endX = new AtomicReference<>(0.0);
            AtomicReference<Double> endY = new AtomicReference<>(0.0);
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            Canvas canvas = (Canvas) selectedTab.getContent();
            GraphicsContext gc = canvas.getGraphicsContext2D();
            Image capturedImage = creativeToolsFeatures.getCapturedImage();
            double width = capturedImage.getWidth();
            double height = capturedImage.getHeight();
            canvas.setOnMousePressed(mouseEvent -> {
                undostack.push(canvas.snapshot(null, null));
                startX.set(mouseEvent.getX());
                startY.set(mouseEvent.getY());
            });
            canvas.setOnMouseReleased(mouseEvent -> {
                endX.set(mouseEvent.getX());
                endY.set(mouseEvent.getY());

                // Calculate the x and y coordinates for drawing the rectangle
                double rectX = Math.min(startX.get(), endX.get());
                double rectY = Math.min(startY.get(), endY.get());
                gc.drawImage(capturedImage, rectX, rectY, width, height);
            });
            logMessage.logMessage(pasteButton.getText());
                });

        rotateButton.setOnAction(e ->{
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            Canvas canvas = (Canvas) selectedTab.getContent();
            undostack.push(canvas.snapshot(null, null));
            tabFeatures.rotateCanvas(90, canvas);
            logMessage.logMessage(rotateButton.getText());
        });

        vFlipButton.setOnAction(e ->{
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            Canvas canvas = (Canvas) selectedTab.getContent();
            undostack.push(canvas.snapshot(null, null));
            tabFeatures.flipCanvas(vFlipButton.getText(), canvas);
            logMessage.logMessage(vFlipButton.getText());
        });
        hFlipButton.setOnAction(e ->{
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            Canvas canvas = (Canvas) selectedTab.getContent();
            undostack.push(canvas.snapshot(null, null));
            tabFeatures.flipCanvas(hFlipButton.getText(), canvas);
            logMessage.logMessage(hFlipButton.getText());
        });

        rotateSelectionButton.setOnAction(e ->{
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            Canvas canvas = (Canvas) selectedTab.getContent();
            tabFeatures.rotateSelection(canvas, 90);
            logMessage.logMessage(rotateSelectionButton.getText());
        });

        toFillButton.setOnAction(e -> fillColorPicker.setValue(eyeDropperColorPicker.getValue()));

        toStrokeButton.setOnAction(e -> strokeColorPicker.setValue(eyeDropperColorPicker.getValue()));

        infoButton.setOnAction(e -> helpInfo.helpInfoDisp());

        shortCutButton.setOnAction(e -> helpInfo.shortCutDisp());

        tabPane.getSelectionModel().getSelectedItem().setOnCloseRequest(e -> confirmationDialog.TabCloseAlert(newTab.getTab(),menuBarFeatures,fileChooser,imageView,stage,borderPane));

        squareButton.setOnAction(e ->
                {
                    Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
                    creativeToolsFeatures.selectButtonClick(tabPane);
                    Canvas canvas = (Canvas) selectedTab.getContent();
                    GraphicsContext gc = canvas.getGraphicsContext2D();
                    gc.setFill(fillColorPicker.getValue());
                    gc.setStroke(strokeColorPicker.getValue());
                    shapeFeatures.drawSquare(gc,undostack, canvas);
                }
        );
        circleButton.setOnAction(e -> {
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            creativeToolsFeatures.selectButtonClick(tabPane);
            if(selectedTab.getContent() instanceof Canvas){
                Canvas canvas = (Canvas) selectedTab.getContent();
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.setFill(fillColorPicker.getValue());
                gc.setStroke(strokeColorPicker.getValue());
                shapeFeatures.drawCircle(gc,undostack, canvas);
            }
        });
        rectangleButton.setOnAction(e -> {
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            creativeToolsFeatures.selectButtonClick(tabPane);
            if(selectedTab.getContent() instanceof Canvas){
                Canvas canvas = (Canvas) selectedTab.getContent();
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.setFill(fillColorPicker.getValue());
                gc.setStroke(strokeColorPicker.getValue());
                shapeFeatures.drawRectangle(gc,undostack, canvas);
            }
        });
        ellipseButton.setOnAction(e -> {
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            creativeToolsFeatures.selectButtonClick(tabPane);
            if(selectedTab.getContent() instanceof Canvas){
                Canvas canvas = (Canvas) selectedTab.getContent();
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.setFill(fillColorPicker.getValue());
                gc.setStroke(strokeColorPicker.getValue());
                shapeFeatures.drawEllipse(gc,undostack, canvas);
            }
        });
        triangleButton.setOnAction(e -> {
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            creativeToolsFeatures.selectButtonClick(tabPane);
            Canvas canvas = (Canvas) selectedTab.getContent();
            GraphicsContext gc = canvas.getGraphicsContext2D();
            Polygon triangle = new Polygon();
            triangle.setFill(fillColorPicker.getValue());
            triangle.setStroke(strokeColorPicker.getValue());
            undostack.push(canvas.snapshot(null, null));
            canvas.setOnMousePressed(mouseEvent -> {
                int sides = 3;
                double centerX = mouseEvent.getX();
                double centerY = mouseEvent.getY();
                double radius = Math.min(centerX, centerY) - 20;
                double angleIncrement = 2.0 * Math.PI / sides;

                gc.setStroke(strokeColorPicker.getValue());
                gc.setFill(fillColorPicker.getValue());
                gc.beginPath();

                double x = centerX + radius;
                double y = centerY;

                gc.moveTo(x, y);

                for (int i = 1; i <= sides; i++) {
                    x = centerX + radius * Math.cos(i * angleIncrement);
                    y = centerY + radius * Math.sin(i * angleIncrement);
                    gc.lineTo(x, y);
                }

                gc.closePath();
                gc.stroke();
            });
        });

        polygonButton.setOnAction(e ->{
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            creativeToolsFeatures.selectButtonClick(tabPane);
            Canvas canvas = (Canvas) selectedTab.getContent();
            GraphicsContext gc = canvas.getGraphicsContext2D();
            Polygon polygon = new Polygon();
            polygon.setFill(fillColorPicker.getValue());
            polygon.setStroke(strokeColorPicker.getValue());
            undostack.push(canvas.snapshot(null, null));
            canvas.setOnMousePressed(mouseEvent -> {
                int sides = (int) polygonSidesSlider.getValue();
                double centerX = mouseEvent.getX();
                double centerY = mouseEvent.getY();
                double radius = Math.min(centerX, centerY) - 20;
                double angleIncrement = 2.0 * Math.PI / sides;

                gc.setStroke(strokeColorPicker.getValue());
                gc.setFill(fillColorPicker.getValue());
                gc.beginPath();

                double x = centerX + radius;
                double y = centerY;

                gc.moveTo(x, y);

                for (int i = 1; i <= sides; i++) {
                    x = centerX + radius * Math.cos(i * angleIncrement);
                    y = centerY + radius * Math.sin(i * angleIncrement);
                    gc.lineTo(x, y);
                }

                gc.closePath();
                gc.stroke();
            });

        });

        // Set up action event handlers for line thickness and colors
        lineThicknessSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            if(selectedTab.getContent() instanceof Canvas) {
                Canvas canvas = (Canvas) selectedTab.getContent();
                GraphicsContext gc = canvas.getGraphicsContext2D();
                double thickness = newValue.doubleValue();
                gc.setLineWidth(thickness);
            }
        });

        strokeColorPicker.setOnAction(e -> {
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            if(selectedTab.getContent() instanceof Canvas) {
                Canvas canvas = (Canvas) selectedTab.getContent();
                GraphicsContext gc = canvas.getGraphicsContext2D();
                Color strokeColor = strokeColorPicker.getValue();
                gc.setStroke(strokeColor);
            }
        });

        fillColorPicker.setOnAction(e -> {
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            if(selectedTab.getContent() instanceof Canvas) {
                Canvas canvas = (Canvas) selectedTab.getContent();
                GraphicsContext gc = canvas.getGraphicsContext2D();
                Color fillColor = fillColorPicker.getValue();
                gc.setFill(fillColor);
            }
        });



        undoButton.setOnAction(e ->{
            logMessage.logMessage(undoButton.getText());
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            Canvas canvas = (Canvas) selectedTab.getContent();
            GraphicsContext gc = canvas.getGraphicsContext2D();
            redostack.push(canvas.snapshot(null, null));
            if (!undostack.isEmpty()) {
                Image snapshot = (Image) undostack.pop();
                redostack.push(snapshot);
                gc.drawImage(snapshot,0, 0); // Implement this method to restore the canvas
            }

        });

        redoButton.setOnAction(e ->{
            logMessage.logMessage(redoButton.getText());
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            Canvas canvas = (Canvas) selectedTab.getContent();
            GraphicsContext gc = canvas.getGraphicsContext2D();
            undostack.push(canvas.snapshot(null, null));
            if (!redostack.isEmpty()) {
                redostack.pop();
                Image snapshot = (Image) redostack.pop();
                undostack.push(snapshot);
                gc.drawImage(snapshot, 0, 0);
            }
        });


}
    public static void main(String[] args) {
        launch();
    }
}
