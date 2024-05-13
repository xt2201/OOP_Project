package com.example.OOP_Project.Controller;
// Articles
import com.example.OOP_Project.Media.NewsArticle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
public class DataController {
    private static String[][] inputs = {
        { "Title 1", "Type 11111111111111111111111 1", "News 11111111111111111111111", "Summary 1", "Category 1", "Tag 1", "Time 1", "https://www.facebook.com/",
                "https://www.w3schools.com/images/w3schools_logo_436_2.png", "1" },
        { "Title 2", "Type 2", "News 2", "Summary 2", "Category 2", "Tag 2", "Time 2", "https://www.facebook.com/",
                "https://www.w3schools.com/images/w3schools_logo_436_2.png", "1" },
        { "Title 3", "Type 3", "News 3", "Summary 3", "Category 3", "Tag 3", "Time 3", "https://www.facebook.com/",
                "https://www.w3schools.com/images/w3schools_logo_436_2.png", "1" },
        { "Title 4", "Type 4", "News 4", "Summary 4", "Category 4", "Tag 4", "Time 4", "https://www.facebook.com/",
                "https://www.w3schools.com/images/w3schools_logo_436_2.png" , "1"},
        { "Title 5", "Type 5", "News 5", "Summary 5", "Category 5", "Tag 5", "Time 5", "https://www.facebook.com/",
                "https://www.w3schools.com/images/w3schools_logo_436_2.png", "1" },
        { "Title 6", "Type 6", "News 6", "Summary 6", "Category 6", "Tag 6", "Time 6", "https://www.facebook.com/",
                "https://www.w3schools.com/images/w3schools_logo_436_2.png", "1" },
        { "Title 7", "Type 7", "News 7", "Summary 7", "Category 7", "Tag 7", "Time 7", "https://www.facebook.com/",
                "https://www.w3schools.com/images/w3schools_logo_436_2.png" , "1"},
        { "Title 8", "Type 8", "News 8", "Summary 8", "Category 8", "Tag 8", "Time 8", "https://www.facebook.com/",
                "https://www.w3schools.com/images/w3schools_logo_436_2.png" , "1"},
        { "Title 9", "Type 9", "News 9", "Summary 9", "Category 9", "Tag 9", "Time 9", "https://www.facebook.com/",
                "https://www.w3schools.com/images/w3schools_logo_436_2.png" , "1"},
        { "Title 10", "Type 10", "News 10", "Summary 10", "Category 10", "Tag 10", "Time 10",
                "https://www.facebook.com/", "https://www.w3schools.com/images/w3schools_logo_436_2.png" , "1"}
};
public static String[][] getInput() {
    return DataController.inputs;
}
public static void setInput(String[] new_inputs,int pos) {
        DataController.inputs[pos] = new_inputs;
}
public static void setReadLater(int pos, String num) {
    DataController.inputs[pos][9] = num;
}
public static String debug(int pos) {
    return DataController.inputs[pos][9];
}


private static Boolean[] later = {false,false,false,false,false,false,false,false,false,false};

public static Boolean[] getlater() {
    return DataController.later;
}
public static void setlater(int pos) {
    DataController.later[pos] = !DataController.later[pos];
}


}
