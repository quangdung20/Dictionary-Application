package JavaCode;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class AfterLogin {

    @FXML
    private Button logout;


    public void userLogOut(ActionEvent event) throws IOException {
        Dictionary_main m = new Dictionary_main();
        m.changeScene("Layers/LoginView.fxml");

    }
}
