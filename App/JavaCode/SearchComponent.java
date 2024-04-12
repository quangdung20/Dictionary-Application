package JavaCode;

import API_Dictionary.DataLoadedInterface;
import API_Dictionary.VoiceRequest;
import Models.Request_ListWord;
import Models.Word;
import com.voicerss.tts.Languages;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static Constants.Constant.*;
import static Models.Request_ListWord.*;

public class SearchComponent implements Initializable{

    @FXML
    private Button cancelBtn;

    @FXML
    private Button deleteWordBtn;

    @FXML
    private Button editDefinitionBtn;

    @FXML
    private ListView<String> history_search, suggestWord;

    @FXML
    private TextField inputWord;

    @FXML
    private Button saveBtn;

    @FXML
    private WebView meaningArea;

    @FXML
    private Label selectedWord;

    @FXML
    private Button speakBtn;

    @FXML
    private Label switchLangBtn;

    private String speckLang = "en-us";

    private WebEngine definitionWebEngine;

    private Word currentSelectedWord;

    public static final String engLangCode = "en-US";
    public static final String vieLangCode = "vi-VN";

    public static String currentLang = "en-US";
    public static boolean isEngVie = true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        suggestWord.setVisible(false);
        suggestWord.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                suggestWord.setVisible(false);
            }
        });
        selectedWord.setText("");
        switchLangBtn.setText("ENGLISH");
        // switch language button
        switchLangBtn.setOnMouseClicked(event -> switchLanguage(event));
        // search word button
        if (dataEngVie.isEmpty()) {
            Request_ListWord.addDataLoadedListener(new DataLoadedInterface() {
                @Override
                public void onDataLoaded() {
                    loadWordList();
                }
            });
        } else {
            loadWordList();
        }
        inputWord.setOnKeyTyped(new EventHandler<KeyEvent>() {
            private final Timeline searchTimeline = new Timeline();
            {
                // Set the delay
                searchTimeline.getKeyFrames().add(
                        new KeyFrame(Duration.millis(500), this::executeSearch)
                );
                searchTimeline.setCycleCount(1);
            }

            @Override
            public void handle(KeyEvent keyEvent) {
                // Reset the timeline to cancel any pending execution
                searchTimeline.stop();

                String searchKey = inputWord.getText();
                if (searchKey.isEmpty()) {
                    showMeaningWord();
                    cancelBtn.setVisible(false);
                } else {
                    cancelBtn.setVisible(true);
                    // Schedule the search operation after the delay
                    searchTimeline.playFromStart();
                }
            }

            private void executeSearch(ActionEvent event) {
                // This method will be called after the delay
                String searchKey =inputWord.getText();
                handleSearchOnKeyTyped(searchKey);
            }
        });

        inputWord.setOnKeyReleased(event -> {
            String searchKey = inputWord.getText();
            handleSearchOnKeyTyped(searchKey);
            if (!inputWord.getText().isEmpty()) {
                suggestWord.setVisible(true);
            }
            else {
                suggestWord.setVisible(false);
            }
            setShowWord(inputWord.getText());
        });
        // cancel button
        cancelBtn.setOnMouseClicked(event -> {
            inputWord.clear();
            meaningArea.getEngine().loadContent("");
            suggestWord.setVisible(false);
            selectedWord.setText("");

        });
    }

    private void setShowWord(String text) {
        selectedWord.setText(inputWord.getText());
    }


    public void switchLanguage(MouseEvent event) {
        Object source = event.getSource();
        if (source == switchLangBtn){
            if (isEngVie) {
                currentLang = vieLangCode;
                switchLangBtn.setText("VIET NAM");
                speckLang = "vi-vn";
                currentData = dataVieEng;
            } else {
                currentLang = engLangCode;
                switchLangBtn.setText("ENGLISH");
                speckLang = "en-us";
                currentData = dataEngVie;
            }
            isEngVie = !isEngVie;

           showMeaningWord();
        }
    }

    @FXML
    public void setSpeakBtn(ActionEvent event) throws Exception {
        if (selectedWord == null || selectedWord.getText().isEmpty()) return;
        VoiceRequest wordListening = new VoiceRequest(selectedWord.getText(), speckLang);
            wordListening.valueProperty().addListener(
                    (observable, oldValue, newValue) -> newValue.start());
            Thread thread = new Thread(wordListening);
            thread.setDaemon(true);
            thread.start();
    }

    public void loadWordList() {
        this.suggestWord.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Đảm bảo rằng newValue không null
                Word selectedWord = currentData.get(newValue.trim());
                currentSelectedWord = selectedWord;
                saveBtn.setVisible(false);
                String definition = selectedWord.getMeaning();
                this.selectedWord.setText(selectedWord.getWord());

                definitionWebEngine = meaningArea.getEngine();
                definitionWebEngine.loadContent(definition, "text/html");
            }
        });

        showMeaningWord();
        suggestWord.setVisible(false);
    }

    private void showMeaningWord() {
        // Chuyển danh sách từ Map thành danh sách có thứ tự
        List<String> sortedWords = new ArrayList<>(currentData.keySet());

        // Sắp xếp danh sách các từ theo thứ tự bảng chữ cái (alpha)
        Collections.sort(sortedWords);

        // Đặt danh sách đã sắp xếp vào wordListView
        this.history_search.getItems().setAll(sortedWords);
    }

    private void showResultSuggest(List<String> searchResultList) {
        // Sắp xếp danh sách các từ theo thứ tự bảng chữ cái (alpha)
        Collections.sort(searchResultList);
          suggestWord.setPrefHeight(suggestWord.getItems().size() * 25); // 25 is the height of each item
        // Đặt danh sách gợi ý cho suggestWord
        this.suggestWord.getItems().setAll(searchResultList);
    }

    @FXML
    private void handleSearchOnKeyTyped(String searchKey) {
        List<String> searchResultList = new ArrayList<>();
        // Chuyển sang chữ thường để tìm kiếm không phân biệt chữ hoa/chữ thường
        searchKey = searchKey.trim().toLowerCase();

        for (String key : currentData.keySet()) {
            if (key.toLowerCase().startsWith(searchKey)) {
                searchResultList.add(key);
            }
        }

        if (searchResultList.isEmpty()) {
            showMeaningWord();
            suggestWord.setVisible(false);
            suggestWord.getItems().clear();
        } else {
            showResultSuggest(searchResultList);
        }
    }
}