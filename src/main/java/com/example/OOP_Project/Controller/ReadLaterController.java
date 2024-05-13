package com.example.OOP_Project.Controller;

import java.net.URL;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

// Articles
import com.example.OOP_Project.Media.NewsArticle;
// Data 
import com.example.OOP_Project.Controller.DataController;
public class ReadLaterController {
    private static String[][] news_inputs = DataController.getInput();

    @FXML
    private DetailController detailController;
    @FXML
    private VBox laterContainer;
    public void initialize() {
        addreadlater();
    }    

    public void addreadlater() {
        
        for (int i = 0; i < news_inputs.length; i++) {

            if (DataController.debug(i) == "2") {
            Article article = new Article();
            AnchorPane art = article.createArticle(news_inputs[i][0], news_inputs[i][1], news_inputs[i][2],
                    news_inputs[i][3],
                    news_inputs[i][4]);

            Button moreButton = new Button();
            moreButton.setLayoutX(600);
            moreButton.setLayoutY(15);
            moreButton.setPrefSize(50, 40);
            moreButton.setStyle("-fx-background-color: #020023;");

            URL imageUrl = TodayController.class.getResource("/image/more.png");

            Image aterImage = new Image(imageUrl.toExternalForm());
            ImageView moreImageView = new ImageView(aterImage);

            moreImageView.setFitWidth(41);
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(1.0);
            colorAdjust.setContrast(-0.05);
            colorAdjust.setSaturation(0.04);
            moreImageView.setEffect(colorAdjust);

            moreImageView.setFitHeight(29);
            moreButton.setGraphic(moreImageView);

            int finalI = i; // Variable 'i' needs to be effectively final or be effectively used as a final
                            // variable
            moreButton.setOnAction(event -> {

                setPane(news_inputs[finalI][0], news_inputs[finalI][1], news_inputs[finalI][2],
                        news_inputs[finalI][3], news_inputs[finalI][4], news_inputs[finalI][5],
                        news_inputs[finalI][6], news_inputs[finalI][7], news_inputs[finalI][8]);
            });
        Pane checkReadLaterPane = new Pane();
        checkReadLaterPane.setLayoutX(0);
        checkReadLaterPane.setLayoutY(130);
        checkReadLaterPane.setPrefSize(200, 50);
        URL tick = getClass().getResource("/image/tick.png");
        Image laterImage = new Image(tick.toExternalForm());
        ImageView tickImageView = new ImageView(laterImage);
        tickImageView.setLayoutX(5);
        tickImageView.setLayoutY(10);
        tickImageView.setFitWidth(35);
        tickImageView.setFitHeight(35);

        Label viewInReadLaterLabel = new Label("View in Read Later");
        viewInReadLaterLabel.setLayoutX(44);
        viewInReadLaterLabel.setLayoutY(19);
        viewInReadLaterLabel.setTextFill(Color.rgb(242, 117, 117));
        viewInReadLaterLabel.setFont(Font.font("System Italic", 11));



        
        checkReadLaterPane.getChildren().addAll(tickImageView, viewInReadLaterLabel);
       
        



            art.getChildren().addAll(moreButton, checkReadLaterPane);
            laterContainer.getChildren().add(art);
           

        }
    }
    }



    public void setPane(String title, String type, String news, String summary, String category, String tag,
            String time, String facebookLink, String imageLink) {
        detailController.showDetail();
        detailController.setDetail(title, type, news, summary, category, tag, time, facebookLink, imageLink);
    }
}
