package JavaCode;

//import Models.StudyRecord;
import Models.Ranking;
import Models.User;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

import static Constants.Constant.*;

public class GameController extends DatabaseConnection implements Initializable {

    @FXML
    private ImageView imageRanking;

    @FXML
    private Label emailTitle;

    @FXML
    private Label nameTitle;

    @FXML
    private Label pointTitle;

    @FXML
    private Label labelRanking, rankTitle;

    @FXML
    private Label timesLeaning;
    @FXML
    private Label correctRate;

    @FXML
    private Button learningBtn1, learningBtn2;

    @FXML
    private ListView<String> listViewRanking;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Timeline timeline = new Timeline(new KeyFrame(javafx.util.Duration.millis(500), event -> updateAfterGame()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        learningBtn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showComponent(GAME_TESTING);
            }
        });

        learningBtn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showComponent(GAME_UNSCRAMBLE);
            }
        });

        // set label user
        System.out.println("Username: " + currentUser.getUsername() + " Score: " + currentUser.getStudyRecord().getScore()+ "\t number user: " + listUsers.size());
    }

    public void updateAfterGame() {
        // Cập nhật thông tin người dùng
        showInfoUser();
        // Cập nhật xếp hạng
        showListRank(listUsers);
        // Cập nhật hình ảnh xếp hạng
        setupImageRanking(String.valueOf(currentUser.getStudyRecord().getScore()));
    }
    private void showListRank(ArrayList<User> listUsers) {
        // Create a new ObservableList
        ObservableList<String> observableList = FXCollections.observableArrayList();
        for (int i = 0; i < listUsers.size(); i++) {
            observableList.add(i + 1 + ". " + listUsers.get(i).getUsername() + ": " + listUsers.get(i).getStudyRecord().getScore() + " points");
        }
        // Set the items of the ListView
        listViewRanking.setItems(observableList);

//         Optionally, you can add a custom cell factory to change the way the items are displayed
        listViewRanking.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    // set backgpound color for user current
                    if (item.contains(currentUser.getUsername() + ": " + currentUser.getStudyRecord().getScore()) ){
                        setText(item);
                        setStyle("-fx-background-color: #ffde23; -fx-text-fill: #000000;");
                    }
                    else {
                        setText(item);
                        setStyle("-fx-font-size: 13px;");
                    }
                }
            }
        });
    }

    private void setupImageRanking(String totalScore) {

        switch (Ranking.getRanking(Integer.parseInt(totalScore))) {
            case BRONZE -> {
                Image image = new Image(IMAGE_BRONZE);
                imageRanking.setImage(image);
            }
            case SILVER -> {
                Image image = new Image(IMAGE_SILVER);
                imageRanking.setImage(image);
            }
            case GOLD -> {
                Image image = new Image(IMAGE_GOLD);
                imageRanking.setImage(image);
            }
            case PLATINUM -> {
                Image image = new Image(IMAGE_PLATINUM);
                imageRanking.setImage(image);
            }
            case DIAMOND -> {
                Image image = new Image(IMAGE_DIAMOND);
                imageRanking.setImage(image);
            }
            case MASTER -> {
                Image image = new Image(IMAGE_MASTER);
                imageRanking.setImage(image);
            }
            case GRANDMASTER -> {
                Image image = new Image(IMAGE_GRANDMASTER);
                imageRanking.setImage(image);
            }
            case CHALLENGER -> {
                Image image = new Image(IMAGE_CHALLENGER);
                imageRanking.setImage(image);
            }
        }
        labelRanking.setText(Ranking.getRanking(Integer.parseInt(totalScore)).getValue());
        rankTitle.setText(Ranking.getRanking(Integer.parseInt(totalScore)).getValue());
    }
    private void showInfoUser() {
        double correctQuestionsRate = (double) currentUser.getStudyRecord().getCorrectQuestions() / currentUser.getStudyRecord().getScore() * 100;
        nameTitle.setText(currentUser.getUsername());
        emailTitle.setText(currentUser.getEmail());
        pointTitle.setText(String.valueOf(currentUser.getStudyRecord().getScore()));
        timesLeaning.setText(String.valueOf(currentUser.getStudyRecord().getTimesAttend()));
        correctRate.setText(String.format("%.2f", correctQuestionsRate) + "%");
    }

}
