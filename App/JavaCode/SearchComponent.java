package JavaCode;

import Interface.*;
import javafx.application.Platform;
import javafx.scene.control.*;
import retrofit2.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static Constants.Constant.URL_API;

public class SearchComponent implements Initializable{

    @FXML
    private Button cancelBtn;

    @FXML
    private ListView<String> list_word_relative, listviewMeaning;

    @FXML
    private TextField inputWord;
    @FXML
    private Label phonetic,  switchLangBtn;
    @FXML
    private Label showWord;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // switch language button
        switchLangBtn.setOnMouseClicked(event -> switchLanguage(event));
        // search word button
        inputWord.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!inputWord.getText().isBlank()){
//                    getMeaning(inputWord.getText());
//                    setShowWord(inputWord.getText());
//                    searchBtn();
                }

            }
        });
        // cancel button
        cancelBtn.setOnMouseClicked(event -> {
            inputWord.clear();
            phonetic.setText("");
            showWord.setText("");
            listviewMeaning.getItems().clear();
            list_word_relative.getItems().clear();
        });
    }


    public void switchLanguage(MouseEvent event) {
        Object source = event.getSource();
        if (source == switchLangBtn){
            String text = switchLangBtn.getText();
            if (text.equals("ENGLISH")) {
                switchLangBtn.setText("VIET NAM");
            } else {
                switchLangBtn.setText("ENGLISH");
            }
        }
    }



}