package com.example.OOP_Project.Controller.Visualization;

import java.util.ArrayList;

import com.example.OOP_Project.Controller.DataManagement.DataController;
import com.example.OOP_Project.Controller.DataManagement.DetailController;
import com.example.OOP_Project.Controller.DataManagement.Display;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class ReadLaterController extends Display {
    public static ArrayList<String[]> news_inputs = DataController.getRead();
    static String[] init =  {"Welcome to our ReadLater Page", "This is the instruction", "", "Instructionssssss", "Category 1", "Tag 1", "Time 1", "Link 1", "Image Link 1", "1"};
    @FXML
    private DetailController detailController;
    @FXML
    private VBox laterContainer;
    public void initialize() {
        for (String[] arr : news_inputs) {
            for (String str : arr) {
                System.out.print(str + " ");
            }
            System.out.println();
        }
        System.out.println(news_inputs);
        if (news_inputs.isEmpty()) {
            DataController.initialRead();
        }
        else {
            DataController.removeFromLater(init[0]);
        }
        addArticles(news_inputs, laterContainer,new String[]{"2"});
    }    
    public void addreadlater() {
       
        addArticles(news_inputs, laterContainer,new String[]{"2"});
        
    }
}
