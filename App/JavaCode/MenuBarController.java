package JavaCode;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MenuBarController extends DatabaseConnection {
    @FXML
    private Button addWordBtn;
    @FXML
    private Button btnLogout;

    @FXML
    private Button learningEngBtn;
    @FXML
    private Button searchBtn;
    @FXML
    private Button translateBtn;

    @FXML
    void setLogout(ActionEvent event) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Đăng xuất");
        alert.setHeaderText("Bạn có muốn đăng xuất?");
        ButtonType buttonTypeOK = new ButtonType("Thoát", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeCancel = new ButtonType("Hủy", ButtonBar.ButtonData.NO);

        alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);

        // Get the result of the confirmation dialog.
        // If the user presses OK, the application will return to the login screen.
        //
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOK) {
            Dictionary_main main = new Dictionary_main();
            Stage stage = (Stage) btnLogout.getScene().getWindow();
            main.initializeStage(stage);
        }
    }
    @FXML
    void initialize() {

        // Set the default component to display when the application is started.
        showComponent("Layers/SearchLayer.fxml");
        addWordBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showComponent("layers/AddWordLayer.fxml");
            }
        });
        searchBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showComponent("Layers/SearchLayer.fxml");
            }
        });
        translateBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showComponent("Layers/TranslateLayer.fxml");
            }
        });
        learningEngBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showComponent("Layers/ActiveLayer.fxml");
            }
        });

    }

}
