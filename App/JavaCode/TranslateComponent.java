package JavaCode;

import Interface.TranslateAPI;
import javafx.animation.PauseTransition;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.fxml.FXML;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.scene.text.TextFlow;
import javafx.util.Duration;

public class TranslateComponent implements Initializable {
        String languageFrom = "";
        String languageTo = "vi";
        String nameFrom;
        String speakFrom;
        String nameTo;
        String speakTo;

        @FXML
        private ChoiceBox<String> choseLangDestination;

        @FXML
        private ChoiceBox<String> choseLangSource;

        @FXML
        private TextArea inputSentence;

        @FXML
        private TextArea outputMeaning;



        //
//        public void resetStyleLangFrom() {
////                langFromFirst.getStyleClass().removeAll("active");
////                langFromSecond.getStyleClass().removeAll("active");
////                langFromThird.getStyleClass().removeAll("active");
////                langFromFourth.getStyleClass().removeAll("active");
//        }
//
//        public void detect() {
//                resetStyleLangFrom();
//                langFromFirst.getStyleClass().add("active");
//                languageFrom = "";
////                text1.setText("Phát hiện n.ngữ");
//                nameFrom = "Linda";
//                speakFrom = "en-gb";
//        }
//
//        @FXML
//        void eng() {
//                resetStyleLangFrom();
//                langFromSecond.getStyleClass().add("active");
//                languageFrom = "en";
////                text1.setText("Tiếng Anh");
//                nameFrom = "Linda";
//                speakFrom = "en-gb";
//        }
//
//        @FXML
//        void vie1() {
//                resetStyleLangFrom();
//                langFromThird.getStyleClass().add("active");
////                text1.setText("Tiếng Việt");
//                languageFrom = "vi";
//                nameFrom = "Chi";
//                speakFrom = "vi-vn";
//        }
//
//        @FXML
//        void korea() {
//                resetStyleLangFrom();
//                langFromFourth.getStyleClass().add("active");
////                text1.setText("Tiếng Hàn");
//                languageFrom = "ko";
//                nameFrom = "Nari";
//                speakFrom = "ko-kr";
//        }
//
//        public void resetStyleLangTo() {
//                langToFirst.getStyleClass().removeAll("active");
//                langToSecond.getStyleClass().removeAll("active");
//                langToThird.getStyleClass().removeAll("active");
//                langToFourth.getStyleClass().removeAll("active");
//                langToFifth.getStyleClass().removeAll("active");
//        }
//
//        @FXML
//        void vie2() throws IOException {
//                resetStyleLangTo();
//                langToFirst.getStyleClass().add("active");
////                text2.setText("Tiếng Việt");
//                languageTo = "vi";
//                nameTo = "Chi";
//                speakTo = "vi-vn";
//                if (!Objects.equals(area1.getText(), "")) {
//                        area2.setText(TranslateAPI.googleTranslate(languageFrom, languageTo, area1.getText()));
//                }
//        }
//
//        @FXML
//        void eng2() throws IOException {
//                resetStyleLangTo();
//                langToSecond.getStyleClass().add("active");
////                text2.setText("Tiếng Anh");
//                languageTo = "en";
//                nameTo = "Linda";
//                speakTo = "en-gb";
//                if (!Objects.equals(area1.getText(), "")) {
//                        area2.setText(TranslateAPI.googleTranslate(languageFrom, languageTo, area1.getText()));
//                }
//        }
//
//        @FXML
//        void korea2() throws IOException {
//                resetStyleLangTo();
//                langToThird.getStyleClass().add("active");
////                text2.setText("Tiếng Hàn");
//                languageTo = "ko";
//                nameTo = "Nari";
//                speakTo = "ko-kr";
//                if (!Objects.equals(area1.getText(), "")) {
//                        area2.setText(TranslateAPI.googleTranslate(languageFrom, languageTo, area1.getText()));
//                }
//        }
//
//        @FXML
//        void rus() throws IOException {
//                resetStyleLangTo();
//                langToFourth.getStyleClass().add("active");
////                text2.setText("Tiếng Nga");
//                languageTo = "ru";
//                nameTo = "Marina";
//                speakTo = "ru-ru";
//                if (!Objects.equals(area1.getText(), "")) {
//                        area2.setText(TranslateAPI.googleTranslate(languageFrom, languageTo, area1.getText()));
//                }
//        }
//
//        @FXML
//        void chinese() throws IOException {
//                resetStyleLangTo();
//                langToFifth.getStyleClass().add("active");
//                languageTo = "zh";
////                text2.setText("Tiếng Trung");
//                nameTo = "Luli";
//                speakTo = "zh-cn";
//                if (!Objects.equals(area1.getText(), "")) {
//                        area2.setText(TranslateAPI.googleTranslate(languageFrom, languageTo, area1.getText()));
//                }
//        }
//
////        @FXML
////        void translate() throws IOException {
////                if (!Objects.equals(area1.getText(), "")) {
////                        area2.setText(TranslateAPI.googleTranslate(languageFrom, languageTo, area1.getText()));
////                }
////        }
        void LangSource() {
                choseLangSource.getItems().addAll("Phát hiện ngôn ngữ", "Tiếng Anh", "Tiếng Việt", "Tiếng Hàn", "Tiếng Nga", "Tiếng Trung");
                choseLangSource.setValue("Phát hiện ngôn ngữ");
                choseLangSource.setOnAction(event -> {
                        switch (choseLangSource.getValue()) {
                                case "Phát hiện ngôn ngữ":
                                        languageFrom = ""; // Đặt ngôn ngữ từ là rỗng để phát hiện ngôn ngữ
                                        nameFrom = "Linda";
                                        speakFrom = "English_UnitedStates";
                                        break;
                                case "Tiếng Anh":
                                        languageFrom = "en";
                                        nameFrom = "Linda";
                                        speakFrom = "en-gb";
                                        break;
                                case "Tiếng Việt":
                                        languageFrom = "vi";
                                        nameFrom = "Chi";
                                        speakFrom = "vi-vn";
                                        break;
                                case "Tiếng Hàn":
                                        languageFrom = "ko";
                                        nameFrom = "Nari";
                                        speakFrom = "ko-kr";
                                        break;
                                case "Tiếng Nga":
                                        languageFrom = "ru";
                                        nameFrom = "Marina";
                                        speakFrom = "ru-ru";
                                        break;
                                case "Tiếng Trung":
                                        languageFrom = "zh";
                                        nameFrom = "Luli";
                                        speakFrom = "zh-cn";
                                        break;
                        }
                });
        }
        void LangDestination() {
                choseLangDestination.getItems().addAll("Tiếng Việt", "Tiếng Anh", "Tiếng Hàn", "Tiếng Nga", "Tiếng Trung");
                choseLangDestination.setValue("Tiếng Việt");
                choseLangDestination.setOnAction(event -> {
                        switch (choseLangDestination.getValue()) {
                                case "Tiếng Việt":
                                        languageTo = "vi";
                                        nameTo = "Chi";
                                        speakTo = "vi-vn";
                                        break;
                                case "Tiếng Anh":
                                        languageTo = "en";
                                        nameTo = "Linda";
                                        speakTo = "en-gb";
                                        break;
                                case "Tiếng Hàn":
                                        languageTo = "ko";
                                        nameTo = "Nari";
                                        speakTo = "ko-kr";
                                        break;
                                case "Tiếng Nga":
                                        languageTo = "ru";
                                        nameTo = "Marina";
                                        speakTo = "ru-ru";
                                        break;
                                case "Tiếng Trung":
                                        languageTo = "zh";
                                        nameTo = "Luli";
                                        speakTo = "zh-cn";
                                        break;
                        }
                });
        }


        @FXML
        void speckLangSource() throws Exception {
                if (!Objects.equals(inputSentence.getText(), "")) {
                        RequestAPI wordListening = new RequestAPI(inputSentence.getText());
                        wordListening.valueProperty().addListener(
                                (observable, oldValue, newValue) -> newValue.start());
                        Thread thread = new Thread(wordListening);
                        thread.setDaemon(true);
                        thread.start();
                }
        }

        @FXML
        void speckLangDestination() throws Exception {
                if (!Objects.equals(outputMeaning.getText(), "")) {
                        RequestAPI wordListening = new RequestAPI(outputMeaning.getText());
                        wordListening.valueProperty().addListener(
                                (observable, oldValue, newValue) -> newValue.start());
                        Thread thread = new Thread(wordListening);
                        thread.setDaemon(true);
                        thread.start();
                }
        }

        @Override
        public void initialize(URL location, ResourceBundle resources) {

                LangSource();
                LangDestination();
                // Thiết lập "Phát hiện ngôn ngữ" là mặc định
                choseLangSource.setValue("Phát hiện ngôn ngữ");
                choseLangSource.setOnAction(event -> {
                        languageFrom = ""; // Đặt ngôn ngữ từ là rỗng để phát hiện ngôn ngữ
                        nameFrom = "Linda";
                        speakFrom = "English_UnitedStates";
                });
                inputSentence.setText("");
                nameFrom = "Linda";
                speakFrom = "English_UnitedStates";


//                text2.setText("Tiếng Việt");
                nameTo = "Chi";
                speakTo = "vi-vn";
                languageTo = "vi";

                if (inputSentence.getText().isBlank()) {
//            area2.setText(TranslateAPI.googleTranslate(languageFrom, languageTo, area1.getText()));

                        PauseTransition pause = new PauseTransition(Duration.seconds(1));
                        inputSentence.textProperty().addListener((observable, oldValue, newValue) -> {
                                pause.setOnFinished(event -> {
                                        try {
                                                // Gọi API dịch từ
                                                String translatedText = TranslateAPI.googleTranslate(languageFrom, languageTo, newValue);
                                                // Hiển thị kết quả dịch vào TextArea
                                                outputMeaning.setText(translatedText);
                                        } catch (IOException e) {
                                                e.printStackTrace();
                                        }
                                });
                                pause.playFromStart();
                        });
                }
        }
}
