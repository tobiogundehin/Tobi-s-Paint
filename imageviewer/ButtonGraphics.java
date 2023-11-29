package com.mycompany.imageviewer;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class ButtonGraphics {
    public void setButtonGraphic(Button button){
        ImageView buttonGraphicIV = new ImageView();
        buttonGraphicIV.setFitWidth(27);
        buttonGraphicIV.setFitHeight(27);
        String imagePath = "";
        if (button.getText().equals("Draw")) {
            imagePath = "C:\\Users\\iyanu\\Desktop\\Fall 23\\CS-250-A\\ImageViewer\\src\\main\\java\\pencil_logo.jpg";
            File imageFile = new File(imagePath);
            Image buttonLogo = new Image(imageFile.toURI().toString());
            buttonGraphicIV.setImage(buttonLogo);
            button.setGraphic(buttonGraphicIV);;
        }
        if (button.getText().equals("Line")){
            imagePath = "C:\\Users\\iyanu\\Desktop\\Fall 23\\CS-250-A\\ImageViewer\\src\\main\\java\\line_logo.jpg";
            File imageFile = new File(imagePath);
            Image buttonLogo = new Image(imageFile.toURI().toString());
            buttonGraphicIV.setImage(buttonLogo);
            button.setGraphic(buttonGraphicIV);
        }
        if (button.getText().equals("Dashes")){
            imagePath = "C:\\Users\\iyanu\\Desktop\\Fall 23\\CS-250-A\\ImageViewer\\src\\main\\java\\dashes_logo.jpg";
            File imageFile = new File(imagePath);
            Image buttonLogo = new Image(imageFile.toURI().toString());
            buttonGraphicIV.setImage(buttonLogo);
            button.setGraphic(buttonGraphicIV);
        }
        if (button.getText().equals("Select")){
            imagePath = "C:\\Users\\iyanu\\Desktop\\Fall 23\\CS-250-A\\ImageViewer\\src\\main\\java\\select_logo.jpg";
            File imageFile = new File(imagePath);
            Image buttonLogo = new Image(imageFile.toURI().toString());
            buttonGraphicIV.setImage(buttonLogo);
            button.setGraphic(buttonGraphicIV);
        }
        if (button.getText().equals("EyeDropper")){
            imagePath = "C:\\Users\\iyanu\\Desktop\\Fall 23\\CS-250-A\\ImageViewer\\src\\main\\java\\eyedropper_logo.jpg";
            File imageFile = new File(imagePath);
            Image buttonLogo = new Image(imageFile.toURI().toString());
            buttonGraphicIV.setImage(buttonLogo);
            button.setGraphic(buttonGraphicIV);
        }
        if (button.getText().equals("Eraser")){
            imagePath = "C:\\Users\\iyanu\\Desktop\\Fall 23\\CS-250-A\\ImageViewer\\src\\main\\java\\eraser_logo.jpg";
            File imageFile = new File(imagePath);
            Image buttonLogo = new Image(imageFile.toURI().toString());
            buttonGraphicIV.setImage(buttonLogo);
            button.setGraphic(buttonGraphicIV);
        }
    }
}
