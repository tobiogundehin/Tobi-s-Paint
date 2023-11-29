package com.mycompany.imageviewer;

import javafx.stage.FileChooser;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogMessage {
    public void logMessage(String buttonName){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now) + ": " + buttonName + " button was selected");
    }
    public void logMessage(File file, FileChooser fileChooser, String buttonName){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        if (buttonName.equals("Open")){
            System.out.println(dtf.format(now) + ": " + file.getName() + " was opened");
        }
        if (buttonName.equals("Save")){
            System.out.println(dtf.format(now) + ": " + file.getName() + " was saved");
        }
        if (buttonName.equals("Save As")){
            System.out.println(dtf.format(now) + ": " + file.getName() + " was saved at " + fileChooser.getInitialDirectory());
        }
    }
}
