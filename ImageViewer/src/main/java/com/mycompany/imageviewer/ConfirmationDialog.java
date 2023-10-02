package com.mycompany.imageviewer;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.util.Optional;

public class ConfirmationDialog {
    public void TabCloseAlert(Tab tab, MenuBarFeatures menuBarFeatures, FileChooser fileChooser, ImageView imageView, Stage stage, BorderPane borderPane) {
        tab.setOnCloseRequest(e -> {
            // Create a confirmation alert
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Save or Close");
            alert.setHeaderText(null);
            alert.setContentText("Do you want to save changes before closing the tab?");

            // Create custom buttons
            ButtonType alertsaveButton = new ButtonType("Save");
            ButtonType closeButton = new ButtonType("Close");
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            // Add custom buttons to the alert
            alert.getButtonTypes().setAll(alertsaveButton, closeButton, cancelButton);
            Optional<ButtonType> result = alert.showAndWait();

            // Handle the user's response
            if (result.isPresent()) {
                if (result.get() == alertsaveButton) {
                    // Implement save logic here
                    // For example, call a method to save the tab's content
                    if (tab.getText() == "New Tab") {
                        menuBarFeatures.saveasButtonClick(fileChooser, imageView, stage, borderPane, tab);
                    }
                    menuBarFeatures.saveButtonClick(fileChooser, imageView, stage, borderPane, tab);
                } else if (result.get() == closeButton) {
                    // Close the tab without saving
                    tab.getTabPane().getTabs().remove(tab);
                } else {
                    // Cancel the close action
                    e.consume();
                }
            }
        });
    }

    public void ClearTabAlert(TabPane tabPane, MenuItem clearTabButton) {
        clearTabButton.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Clear Tab");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to clear this tab?");
            ButtonType alertclearTabButton = new ButtonType("Clear Tab");
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(alertclearTabButton, cancelButton);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent()) {
                if (result.get() == alertclearTabButton) {
                    Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
                    Canvas canvas = (Canvas) selectedTab.getContent();
                    GraphicsContext gc = canvas.getGraphicsContext2D();
                    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                } else {
                    e.consume();
                }
            }
        });

    }
}

