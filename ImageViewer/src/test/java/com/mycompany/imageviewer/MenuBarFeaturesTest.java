package com.mycompany.imageviewer;

import javafx.scene.control.Tab;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MenuBarFeaturesTest {
    /**
     * Unit Test for fileextensionfromfile
     */
    public void testGetFileExtensionFromFile() {
        File fileWithExtension = new File("example.txt");
        File fileWithoutExtension = new File("fileWithoutExtension");

        String extension1 = getFileExtensionFromFile(fileWithExtension);
        String extension2 = getFileExtensionFromFile(fileWithoutExtension);

        assertEquals("txt", extension1);
        assertEquals("", extension2);
    }

    private String getFileExtensionFromFile(File file) {
        String fileName = file.getName();
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex != -1) {
            return fileName.substring(lastDotIndex + 1);
        }
        return "";
    }

    /**
     * Unit Test for fileextensionfromText
     */
    public void testGetFileExtensionFromTab() {
        Tab tabWithExtension = createTab("example.txt");
        Tab tabWithoutExtension = createTab("fileWithoutExtension");

        String extension1 = getFileExtensionFromTab(tabWithExtension);
        String extension2 = getFileExtensionFromTab(tabWithoutExtension);

        assertEquals("txt", extension1);
        assertEquals("", extension2);
    }

    private Tab createTab(String text) {
        Tab tab = new Tab();
        tab.setText(text);
        return tab;
    }

    private String getFileExtensionFromTab(Tab selectedTab) {
        String fileName = selectedTab.getText();
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex != -1) {
            return fileName.substring(lastDotIndex + 1);
        }
        return "";
    }

}