package com.example.OOP_Project.Controller.Visualization;

import com.example.OOP_Project.Controller.DataManagement.DataController;
import com.example.OOP_Project.Controller.DataManagement.Display;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

// Client socket
import com.example.OOP_Project.ClientHandler.SocketClient;
// Articles
import com.example.OOP_Project.Media.NewsArticle;

public class TodayController extends Display {
    // Client scoket
    private static final SocketClient client = initializeClient();

    private static SocketClient initializeClient() {
        SocketClient client;
        try {
            client = new SocketClient("127.0.0.1", 8888);
        } catch (Exception e) {
            throw new Error("Failed to initialize socket client");
        }
        return client;
    }

    // Inputs
    private static String[][] news_inputs = DataController.getInput();

    public void addArticles() {
        addArticles(news_inputs, articleContainer);
    }

    @FXML
    private TextField input;

    public void handleInputTextSearch() {
        String searchText = input.getText();
        System.out.println("Từ khóa tìm kiếm: " + searchText);
        getSearchResult(searchText);
    }

    public void handlePresetButtonSearch(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        // Lấy text của Button
        String buttonText = clickedButton.getText();
        System.out.println("Từ khóa tìm kiếm: " + buttonText);
        getSearchResult(buttonText);

    }

    public void getSearchResult(String Text) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                while (!client.getRefreshStatus())
                    System.out.print("Loading\r");
                refreshArticles();
                client.setRefreshStatus(false);
            }
        });
        client.sendMessageToServer("2-" + Text);
    }

    @FXML
    private VBox articleContainer;

    public void initialize() {
        addArticles();
    }

    @FXML
    public void close() {
        System.out.println("Close");
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