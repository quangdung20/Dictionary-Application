package JavaCode;

import API_Dictionary.VoiceRequest;
import com.voicerss.tts.Languages;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.*;

import static com.voicerss.tts.Languages.Vietnamese;

public class SearchComponent implements Initializable{

    @FXML
    private Button cancelBtn;

    @FXML
    private Button deleteWordBtn;

    @FXML
    private Button editDefinitionBtn;

    @FXML
    private ListView<String> history_search;

    @FXML
    private TextField inputWord;

    @FXML
    private Button saveBtn;

    @FXML
    private TextArea meaningArea;

    @FXML
    private Label selectedWord;

    @FXML
    private Button speakBtn;

    @FXML
    private Label switchLangBtn;

    private String speckLang = "en-us";

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        switchLangBtn.setText("ENGLISH");
        // switch language button
        switchLangBtn.setOnMouseClicked(event -> switchLanguage(event));
        // search word button
        inputWord.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!inputWord.getText().isBlank()){
//                    getMeaning(inputWord.getText());
                    setShowWord(inputWord.getText());
//                    searchBtn();
                }

            }
        });
        // cancel button
        cancelBtn.setOnMouseClicked(event -> {
            inputWord.clear();
        });
    }

    private void setShowWord(String text) {
        selectedWord.setText(inputWord.getText());
    }


    public void switchLanguage(MouseEvent event) {
        Object source = event.getSource();
        if (source == switchLangBtn){
            String text = switchLangBtn.getText();
            if (text.equals("ENGLISH")) {
                switchLangBtn.setText("VIET NAM");
                speckLang = "vi-vn";

            } else {
                switchLangBtn.setText("ENGLISH");
                speckLang = "en-us";
            }
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


}