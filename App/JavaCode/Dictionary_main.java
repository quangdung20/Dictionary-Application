package JavaCode;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.io.InputStream;

import static Constants.Constant.*;


public class Dictionary_main extends Application {
    private static Stage stg;

    public void initializeStage(Stage primaryStage) throws IOException {
        stg = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource(MENU_LAYER));
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
    }

    public void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stg.getScene().setRoot(pane);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
