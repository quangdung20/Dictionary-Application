package JavaCode;
import Models.Question;
import Models.StudyRecord;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static Constants.Constant.*;

public class LearningEngComponent extends DatabaseConnection  implements Initializable {
    private static StudyRecord studyRecord;
    @FXML
    public AnchorPane bodyContainer, headerContainer;
    @FXML
    public Pane questionAndAnswerPane;
    @FXML
    private RadioButton answerBtnA, answerBtnB, answerBtnC, answerBtnD;
    @FXML
    private Button nextBtn, shuffleBtn, teamworkBtn, fiftyPercentBtn;
    @FXML
    private Circle circle1, circle2, circle3, circle4, circle5, circle6,
            circle7, circle8, circle9, circle10;
    @FXML
    private Label progressText1, progressText2, progressText3, progressText4,
            progressText5, progressText6, progressText7, progressText8, progressText9, progressText10,
            questionText, questionTitle, scoreLabel, countdownLabel;
    @FXML
    private VBox vboxListRanking;

    private Media correctAudioMedia;
    private MediaPlayer correctAudioMediaPlayer;
    private final Paint currentQuestion = Color.rgb(152, 206, 243),
            correctQuestion = Color.rgb(166, 232, 58),
            incorrectQuestion = Color.rgb(255, 0,0 );

    private ArrayList<Question> listQuestionData = new ArrayList<>();
    private ArrayList<Question> listQuestion = new ArrayList<>();
    private int currentQuestionIndex;

    private String answer = "";

    private boolean isCompleted = false;

    private boolean isHandledFinishAnimation = false;

    private Timeline mainTimeLine = new Timeline();

    private int score = 0;

    private final int correctScore = 10;

    private final int incorrectScore = -5;

    private boolean isUsedFiftyPercentHelp = false;
    private boolean isUsedShuffleHelp = false;
    private boolean isUsedTeamworkHelp = false;
    private final int countDownTime = 30;
    private AtomicInteger secondsRemaining;


    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        answerBtnA.setOnAction(event -> {
            answer = "A";
            answerBtnA.setSelected(true);
            answerBtnB.setSelected(false);
            answerBtnC.setSelected(false);
            answerBtnD.setSelected(false);
        });

        answerBtnB.setOnAction(event -> {
            answer = "B";
            answerBtnA.setSelected(false);
            answerBtnB.setSelected(true);
            answerBtnC.setSelected(false);
            answerBtnD.setSelected(false);
        });

        answerBtnC.setOnAction(event -> {
            answer = "C";
            answerBtnA.setSelected(false);
            answerBtnB.setSelected(false);
            answerBtnC.setSelected(true);
            answerBtnD.setSelected(false);
        });

        answerBtnD.setOnAction(event -> {
            answer = "D";
            answerBtnA.setSelected(false);
            answerBtnB.setSelected(false);
            answerBtnC.setSelected(false);
            answerBtnD.setSelected(true);
        });
        setQuestionProgress(1, currentQuestion);
        
    }

    private void setQuestionProgress(int i, Paint currentQuestion) {
    }

}

