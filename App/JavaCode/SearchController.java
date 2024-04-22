package JavaCode;

import API_Dictionary.VoiceRequest;
import Models.AlertMessage;
import Models.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

import static Constants.Constant.*;
import static java.sql.DriverManager.getConnection;

public class SearchController extends DatabaseConnection implements Initializable {

    @FXML
    private Button cancelBtn;

    @FXML
    private Button deleteWordBtn;

    @FXML
    private Button editDefinitionBtn;

    @FXML
    private ListView<String> listWordView, suggestListWord;

    @FXML
    private TextField inputWord;

    @FXML
    private Button saveBtn;

    @FXML
    private WebView meaningArea;

    @FXML
    private TextField setWord;

    @FXML
    private Button speakBtn;

    @FXML
    private Label switchLangBtn;

    private String speckLang = "en-us";

    private WebEngine definitionWebEngine;

    private Word currentSelectedWord;

    HashMap<String, Word> currentData = new HashMap<>();

    public static final String engLangCode = "en-US";
    public static final String vieLangCode = "vi-VN";
    public static String currentLang = "en-US";

    public static boolean isEngVie = true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateListAddWord();
        handleListAddedWord();
        showListAddedWords(listAddWords);
        handleListWordDic();
        saveBtn.setDisable(true);
        deleteWordBtn.setDisable(true);
        editDefinitionBtn.setDisable(true);
        setWord.setText("");
        setWord.setEditable(false);
        switchLangBtn.setText("ENGLISH");
        suggestListWord.setVisible(false);
        switchLangBtn.setOnMouseClicked(event -> switchLanguage(event));
        suggestListWord.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                suggestListWord.setVisible(false);
            }
        });
        cancelBtn.setOnMouseClicked(event -> {
            inputWord.clear();
            meaningArea.getEngine().loadContent("");
            suggestListWord.setVisible(false);
            setWord.setText("");
        });

        // lắng nghe sự kiện khi người dùng nhập từ cần tìm kiếm tuy nhiên muốn sau 0,5s mới thực hiện nghe
        inputWord.setOnKeyReleased(event -> {
            handleSearchOnKeyTyped(inputWord.getText());
        });
    }

    public void updateListAddWord() {
        listAddWords.clear();
        pullAddedWords();
        showListAddedWords(listAddWords);
    }

    public void switchLanguage(MouseEvent event) {
        Object source = event.getSource();
        if (source == switchLangBtn) {
            if (isEngVie) {
                currentLang = vieLangCode;
                switchLangBtn.setText("VIET NAM");
                speckLang = "vi-vn";
            } else {
                currentLang = engLangCode;
                switchLangBtn.setText("ENGLISH");
                speckLang = "en-us";
            }
            isEngVie = !isEngVie;
        }
    }

    @FXML
    public void setSpeakBtn(ActionEvent event) throws Exception {
        if (setWord == null || setWord.getText().isEmpty()) return;
        VoiceRequest wordListening = new VoiceRequest(setWord.getText(), speckLang);
        wordListening.valueProperty().addListener(
                (observable, oldValue, newValue) -> newValue.start());
        Thread thread = new Thread(wordListening);
        thread.setDaemon(true);
        thread.start();
    }

    public void handleSearchOnKeyTyped(String searchKey) {
        if (searchKey.isEmpty()) {
            suggestListWord.setVisible(false);
            return;
        }
        suggestListWord.setVisible(true);

        if (isEngVie) {
            currentData = searchWord(searchKey, TABLE_ENG_VIE);
        } else {
            currentData = searchWord(searchKey, TABLE_VIE_ENG);
        }
        // nếu không có kết quả trong 2 bảng thì tìm kiếm trong bảnh addword của user current
        if (currentData.isEmpty()) {
            currentData = searchWord(searchKey, TABLE_ADD_WORD);
        }
        ObservableList<String> list = FXCollections.observableArrayList();
        for (String key : currentData.keySet()) {
            list.add(key);
        }
        suggestListWord.setItems(list);
    }


    private void showListAddedWords(ArrayList<Word> listAddWords) {
        ObservableList<String> list = FXCollections.observableArrayList();
        int i = 0;
        for (Word word : listAddWords) {
            i++;
            list.add(i +". "+ word.getWord());
        }
        listWordView.setItems(list);
        listWordView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item);
                }
            }
        });
    }

    private void handleListAddedWord() {
        this.listWordView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Đảm bảo rằng newValue không null
                Word selectedWord = listAddWords.get(Integer.parseInt(newValue.split("\\.")[0].trim()) - 1);
                currentSelectedWord = selectedWord;
                saveBtn.setDisable(false);
                deleteWordBtn.setDisable(false);
                editDefinitionBtn.setDisable(false);
                String definition = selectedWord.getMeaning();
                this.setWord.setText(selectedWord.getWord());
                definitionWebEngine = meaningArea.getEngine();
                definitionWebEngine.loadContent(definition, "text/html");
            }
        });
    }

    public void handleListWordDic() {
        this.suggestListWord.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Đảm bảo rằng newValue không null
                Word selectedWord = currentData.get(newValue);
                currentSelectedWord = selectedWord;
                String definition = selectedWord.getMeaning();
                this.setWord.setText(selectedWord.getWord());
                definitionWebEngine = meaningArea.getEngine();
                definitionWebEngine.loadContent(definition, "text/html");
            }
        });
    }

    // cho lưu chỉnh sửa từ đã thêm

    @FXML
    public void saveWord(ActionEvent event){
        AlertMessage alert = new AlertMessage();
        if (currentSelectedWord != null) {
            String definition = definitionWebEngine.getDocument().getDocumentElement().getTextContent();
            currentSelectedWord.setMeaning(definition);
            listAddWords.clear();
            // update từ đã chỉnh sửa vào bảng add_word
            updateWordInAddWordTable(currentSelectedWord.getWord(), setWord.getText(), definition);
            // thông bảo lưu thành công
            alert.successMessage("Lưu từ mới thành công!");
            saveBtn.setDisable(true);
            deleteWordBtn.setDisable(true);
            editDefinitionBtn.setDisable(true);
            updateListAddWord();
        }
    }

    // cho phép người dùng xóa từ đã thêm vào database từ đã thêm
    @FXML
    public void deleteWord(ActionEvent event) {
        AlertMessage alert = new AlertMessage();
        if (currentSelectedWord != null) {
            listAddWords.remove(currentSelectedWord);
            // xóa từ đó trong bảng add_word
            Connection connection = getConnection();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM add_word WHERE word = ? AND userID = ?");
                preparedStatement.setString(1, currentSelectedWord.getWord());
                preparedStatement.setInt(2, currentUser.getUserID());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            saveBtn.setDisable(true);
            deleteWordBtn.setDisable(true);
            editDefinitionBtn.setDisable(true);
            setWord.setText("");
            meaningArea.getEngine().loadContent("");
            // thông báo xóa thành công
            alert.successMessage("Xóa từ thành công!");
            updateListAddWord();
        }
    }

    // cho phép người dùng chỉnh sửa từ nằm trong list từ đã thêm
    @FXML
    public void editDefinition(ActionEvent event) {
        if (currentSelectedWord != null) {
            saveBtn.setDisable(false);
            // cho phep nguoi dung sua selectedWord va definition
            definitionWebEngine.setJavaScriptEnabled(true);
            definitionWebEngine.executeScript("document.body.contentEditable = true;");
            // cho phép chỉnh sửa selectword
            setWord.setEditable(true);
        }
    }

    // update từ được chỉnh sửa vào bảng add_word update cả nghĩa và từ
    public void updateWordInAddWordTable(String word, String word_replace, String meaning) {
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE add_word SET word = ?, meaning = ? WHERE word = ? AND userID = ?");
            preparedStatement.setString(1, word_replace);
            preparedStatement.setString(2, meaning);
            preparedStatement.setString(3, word);
            preparedStatement.setInt(4, currentUser.getUserID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}