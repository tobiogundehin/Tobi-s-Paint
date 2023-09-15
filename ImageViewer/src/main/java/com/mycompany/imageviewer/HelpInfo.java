package com.mycompany.imageviewer;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HelpInfo {
    //TODO:Import HelpInfo File and display that
    //TODO:Add Release Notes(Maybe Display on release)
    public void helpInfoDisp(){
        Stage helpWindow = new Stage();
        BorderPane helpLayout = new BorderPane();
        String helpText ="1. Open:\n" +
                "Feature Description:\n" +
                "The \"Open\" feature allows you to import existing images into the Paint Application.\n " +
                "You can access your own works or incorporate reference images to assist your creative process.\n " +
                "How to Use:\n" +
                "\n" +
                "Click on the \"Open\" button in the application menu.\n" +
                "Navigate to the location of the image or project you want to open.\n" +
                "Select the file and click \"Open.\"\n" +
                "The chosen file will now appear in your workspace, ready for editing.\n" ;

        Label helpLabel = new Label();
        helpLabel.setText(helpText);
        helpLayout.setCenter(helpLabel);
        Scene helpScene = new Scene(helpLayout, 600, 800);
        helpWindow.setTitle("Help");
        helpWindow.setScene(helpScene);
        helpWindow.show();
    }
}
