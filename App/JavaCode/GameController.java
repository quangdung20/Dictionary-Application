package JavaCode;

//import Models.StudyRecord;
import Models.Ranking;
import Models.User;
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
    private Label emailtitle;

    @FXML
    private Label nametitle;

    @FXML
    private Label pointitle;

    @FXML
    private Label labelRanking, ranktitle;


    @FXML
    private Button learningBtn1, learningBtn2;


    @FXML
    private ListView<String> listViewRanking;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showInfoUser();
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
        showListRank(listUsers);
        setupImageRanking(String.valueOf(currentUser.getScore()));
        // set label user
        System.out.println("Username: " + currentUser.getUsername() + " Score: " + currentUser.getScore()+ listUsers.size());
    }

    private void showListRank(ArrayList<User> listUsers) {
        // Create a new ObservableList
        ObservableList<String> observableList = FXCollections.observableArrayList();

        // Convert each User to a String and add it to the ObservableList
        for (int i = 0; i < listUsers.size(); i++) {
            observableList.add(i + 1 + ". " + listUsers.get(i).getUsername() + ": " + listUsers.get(i).getScore() + " points");
        }

        // Set the items of the ListView
        listViewRanking.setItems(observableList);

        // Optionally, you can add a custom cell factory to change the way the items are displayed
        listViewRanking.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    // set backgpound color for user current
                    if (item.contains(currentUser.getUsername() + ": " + currentUser.getScore()) ){
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
        ranktitle.setText(Ranking.getRanking(Integer.parseInt(totalScore)).getValue());
    }
    private void showInfoUser() {
        nametitle.setText(currentUser.getUsername());
        emailtitle.setText(currentUser.getEmail());
        pointitle.setText(String.valueOf(currentUser.getScore()));
    }

}
