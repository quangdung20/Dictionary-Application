package JavaCode;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

import static Constants.Constant.*;
import static Models.Request_ListWord.*;


public class Dictionary_main extends Application {
    private static Stage stg;

    public void initializeStage(Stage primaryStage) throws IOException {
        stg = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource(LOGIN_LAYER));
        primaryStage.setTitle("Dictionary Application");


        primaryStage.setResizable(false);
        ClassLoader classLoader = Dictionary_main.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(LOGO_IMAGE_PATH);
        assert inputStream != null;
        Image image = new Image(inputStream);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(image);
        primaryStage.show();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        initializeStage(primaryStage);
        readData(dataVieEng, DATA_VE_FILE_PATH, EDITED_WORD_VE_FILE);
        readData(dataEngVie, DATA_EV_FILE_PATH, EDITED_WORD_EV_FILE);
    }

    public void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stg.getScene().setRoot(pane);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
