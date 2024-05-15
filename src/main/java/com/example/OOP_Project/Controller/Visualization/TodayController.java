package com.example.OOP_Project.Controller.Visualization;

import com.example.OOP_Project.Controller.DataManagement.DataController;
import com.example.OOP_Project.Controller.DataManagement.DetailController;
import com.example.OOP_Project.Controller.DataManagement.Display;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.Socket;

// Client socket
import com.example.OOP_Project.ClientHandler.SocketClient;
// Articles
import com.example.OOP_Project.Media.NewsArticle;

public class TodayController extends Display implements DataController{
    private static String[][] news_inputs = DataController.getInput();
    public void addArticles() {
        addArticles(news_inputs, articleContainer,new String[]{"2", "1"});
    }
    
    @FXML
    private TextField input;

    public void handleSearch() {
        String searchText = input.getText();
        System.out.println("Từ khóa tìm kiếm: " + searchText);
        articleContainer.getChildren().clear();
        client.sendMessageToServer(searchText);
        addArticles();
    }
    public void handleSearch_1() {
        String searchText = "Blockchain";
        System.out.println("Từ khóa tìm kiếm: " + searchText);
        articleContainer.getChildren().clear();
        client.sendMessageToServer(searchText);
        addArticles();
    }
    public void handleSearch_2() {
        String searchText = "Jeff Bezos";
        System.out.println("Từ khóa tìm kiếm: " + searchText);
        articleContainer.getChildren().clear();
        client.sendMessageToServer(searchText);
        addArticles();
    }
    public void handleSearch_3() {
        String searchText = "Elon Musk";
        System.out.println("Từ khóa tìm kiếm: " + searchText);
        articleContainer.getChildren().clear();
        client.sendMessageToServer(searchText);
        addArticles();
    }



    @FXML
    private VBox articleContainer;

    // Client
    private SocketClient client;

    public void initialize() {
      
            addArticles();

        try {
            client = new SocketClient(new Socket("127.0.0.1", 8888));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setNewsInputs(int size) {
        news_inputs = new String[9][size];
        System.out.println("Inputs reseted");
    }

    public static void addSearchResult(int pos, String jsonString) {
        NewsArticle article = new NewsArticle(jsonString);
        
        DataController.setInput(article.toArray(), pos);
        System.out.println(article.toArray()[0]);    
        
        System.out.println("Article added at 111111111 " + pos);
    }

    public void refreshArticles() {
        articleContainer.getChildren().clear();
        addArticles();
    }

}