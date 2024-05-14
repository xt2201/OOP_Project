package com.example.OOP_Project.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;

// Client socket
import com.example.OOP_Project.ClientHandler.SocketClient;
// Articles
import com.example.OOP_Project.Media.NewsArticle;
// Data 
import com.example.OOP_Project.Controller.DataController;
public class TodayController {
    private static String[][] news_inputs = DataController.getInput();
    
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
    private DetailController detailController;

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

    // Handle search
    // public static void newInputs

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

    

    public String debug() {
        return "Hello";
    }

    

    public void refreshArticles() {
        articleContainer.getChildren().clear();
        addArticles();
    }
    public void addArticles() {
        Display.addArticles(news_inputs, articleContainer,new String[]{"2", "1"});
    }


    // public void addArticles() {
        
    //     for (int i = 0; i < news_inputs.length; i++) {
    //         int num = i;
    //         Article article = new Article();
    //         AnchorPane art = article.createArticle(news_inputs[i][0], news_inputs[i][1], news_inputs[i][2],
    //                 news_inputs[i][3],
    //                 news_inputs[i][4]);

    //         Button moreButton = new Button();
    //         moreButton.setLayoutX(600);
    //         moreButton.setLayoutY(15);
    //         moreButton.setPrefSize(50, 40);
    //         moreButton.setStyle("-fx-background-color: #020023;");

    //         URL imageUrl = TodayController.class.getResource("/image/more.png");

    //         Image aterImage = new Image(imageUrl.toExternalForm());
    //         ImageView moreImageView = new ImageView(aterImage);

    //         moreImageView.setFitWidth(41);
    //         ColorAdjust colorAdjust = new ColorAdjust();
    //         colorAdjust.setBrightness(1.0);
    //         colorAdjust.setContrast(-0.05);
    //         colorAdjust.setSaturation(0.04);
    //         moreImageView.setEffect(colorAdjust);

    //         moreImageView.setFitHeight(29);
    //         moreButton.setGraphic(moreImageView);

    //         int finalI = i; // Variable 'i' needs to be effectively final or be effectively used as a final
    //                         // variable
    //         moreButton.setOnAction(event -> {

    //             setPane(news_inputs[finalI][0], news_inputs[finalI][1], news_inputs[finalI][2],
    //                     news_inputs[finalI][3], news_inputs[finalI][4], news_inputs[finalI][5],
    //                     news_inputs[finalI][6], news_inputs[finalI][7], news_inputs[finalI][8]);
    //         });
    //     Pane checkReadLaterPane = new Pane();
    //     checkReadLaterPane.setLayoutX(0);
    //     checkReadLaterPane.setLayoutY(130);
    //     checkReadLaterPane.setPrefSize(200, 50);
    //     URL tick = getClass().getResource("/image/tick.png");
    //     Image laterImage = new Image(tick.toExternalForm());
    //     ImageView tickImageView = new ImageView(laterImage);
    //     tickImageView.setLayoutX(5);
    //     tickImageView.setLayoutY(10);
    //     tickImageView.setFitWidth(35);
    //     tickImageView.setFitHeight(35);

    //     Label viewInReadLaterLabel = new Label("View in Read Later");
    //     viewInReadLaterLabel.setLayoutX(44);
    //     viewInReadLaterLabel.setLayoutY(19);
    //     viewInReadLaterLabel.setTextFill(Color.rgb(242, 117, 117));
    //     viewInReadLaterLabel.setFont(Font.font("System Italic", 11));


    //     Button laterButton = new Button();
    //     laterButton.setLayoutX(539);
    //     laterButton.setLayoutY(13);
    //     laterButton.setPrefSize(40, 50);
    //     laterButton.setStyle("-fx-background-color: #020023;");
    //     URL imageUr = getClass().getResource("/image/later.png");

    //         Image aterImag = new Image(imageUr.toExternalForm());
    //         ImageView laterImageView = new ImageView(aterImag);
    //     laterImageView.setFitWidth(40);
    //     laterImageView.setFitHeight(50);
    //     laterButton.setGraphic(laterImageView);
    //     ColorAdjust colorAdjus = new ColorAdjust();
    //     colorAdjus.setBrightness(1.0);
    //     colorAdjus.setContrast(0.06);
    //     laterImageView.setEffect(colorAdjus);        
    //     laterButton.setOnAction(event -> {
            
    //         if (DataController.debug(num) == "1") {
    //             DataController.setReadLater(num, "2");
    //             System.out.println(DataController.debug(num));
    //         } else {
    //             DataController.setReadLater(num, "1");
    //             System.out.println(DataController.debug(num));
    //         };
    //         DataController.setlater(num);
    //         checkReadLaterPane.setVisible(DataController.getlater()[num]);

    //     });
        
    //     checkReadLaterPane.getChildren().addAll(tickImageView, viewInReadLaterLabel);
    //     checkReadLaterPane.setVisible(DataController.getlater()[num]);
        



    //         art.getChildren().addAll(moreButton, checkReadLaterPane, laterButton);
    //         articleContainer.getChildren().add(art);
            

    //     }
        
    // }

    // public static Boolean transpose(Boolean init) {
    //     return !init; // Trả về giá trị mới của biến init (readlater)
    // }





    // public void setPane(String title, String type, String news, String summary, String category, String tag,
    //         String time, String facebookLink, String imageLink) {
    //     detailController.showDetail();
    //     detailController.setDetail(title, type, news, summary, category, tag, time, facebookLink, imageLink);
    // }

}