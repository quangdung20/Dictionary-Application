package JavaCode;
import Models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.HashMap;
import java.util.Objects;
import java.util.ResourceBundle;

import AlertBox.AlertMessage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import static Constants.Constant.*;


public class LoginController extends DatabaseConnection implements Initializable {

    @FXML
    private AnchorPane main_form;

    // SigUp layer
    @FXML
    private AnchorPane signup_form;
    @FXML
    private PasswordField signup_password, confirm_pass;
    @FXML
    private TextField nameSignup, emailSignup, answer_signup;
    @FXML
    private ComboBox<String> selectQuestion_signup;
    @FXML
    private Button signupBacktoLogin, signup_btn;


    // Login layer
    @FXML
    private AnchorPane login_form;
    @FXML
    private Button login_btn, loginToSigUp;
    @FXML
    private PasswordField login_password;
    @FXML
    private TextField login_username;
    @FXML
    private Text forgotPassText;

    // Forgot Pass layer.
    @FXML
    private AnchorPane forgot_form;
    @FXML
    private TextField forgot_username, forgot_answer;
    @FXML
    private ComboBox<String> answerCheckPass;
    @FXML
    private Button forgot_proceedBtn, forgot_backBtn;

    // Change pass layer.
    @FXML
    private AnchorPane changePass_form;
    @FXML
    private Button changePass_backBtn, changePass_proceedBtn;
    @FXML
    private TextField changeConFirmPassword, changePass_password;


    @FXML
    void changePassword(ActionEvent event) {
        AlertMessage alert = new AlertMessage();
        if (changePass_password.getText().isEmpty() || changeConFirmPassword.getText().isEmpty()) {
            alert.errorMessage("Bạn cần nhập đầy đủ thông tin");
        } else if (!changePass_password.getText().equals(changeConFirmPassword.getText())) {
            alert.errorMessage("Mật khẩu xác nhận không trùng khớp");
        } else if(changePass_password.getText().length() < 8) {
            alert.errorMessage("Mật khẩu phải có ít nhất 8 ký tự");
        }else {
            String updatePass = "UPDATE user_account SET password = ? WHERE username = ?";
            try (Connection connectDB = getConnection()) {
                PreparedStatement preparedStatement = connectDB.prepareStatement(updatePass);
                preparedStatement.setString(1, changePass_password.getText());
                preparedStatement.setString(2, forgot_username.getText());
                preparedStatement.executeUpdate();
                alert.successMessage("Đổi mật khẩu thành công");
            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }
        }

    }
    @FXML
    void forgotPassword(ActionEvent event) {
        AlertMessage alert = new AlertMessage();

        if(forgot_username.getText().isEmpty() || forgot_answer.getText().isEmpty()
                || answerCheckPass.getValue().isEmpty()) {
            alert.errorMessage("Bạn cần nhập đầy đủ thông tin");
        } else {
            String CheckAnswer = "SELECT count(1) FROM user_account WHERE username = '"
                    + forgot_username.getText() + "' AND answer = '" + forgot_answer.getText() +
                    "' AND question = '" + answerCheckPass.getValue() + "'";
            try (Connection connectDB = getConnection()) {
                PreparedStatement preparedStatement = connectDB.prepareStatement(CheckAnswer);
                ResultSet queryResult = preparedStatement.executeQuery();
                if (queryResult.next() && queryResult.getInt(1) == 1) {
                    changePass_form.setVisible(true);
                    forgot_form.setVisible(false);
                } else {
                    alert.errorMessage("Câu trả lời không chính xác, vui lòng kiểm tra lại");
                }
            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }
        }

    }

    @FXML
    void login(ActionEvent event) {
        AlertMessage alert = new AlertMessage();
        if (login_username.getText().isEmpty() || login_password.getText().isEmpty()) {
            alert.errorMessage("Bạn cần nhập đầy đủ thông tin");
        } else {
            checkLogIn();
        }
    }

    private void checkLogIn() {
        AlertMessage alert = new AlertMessage();
        String verifyLogin = "SELECT count(1) FROM user_account WHERE username = " +
                "'" + login_username.getText() + "' AND password = '" + login_password.getText() + "'";

        try (Connection connectDB = getConnection()){
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    saveCurrentUser(login_username.getText());
                    gotoMainApp();
                    getScoreUsers();

                } else {
                    alert.errorMessage("Thông tin đăng nhập không chính xác, vui lòng kiểm tra lại");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    private void gotoMainApp() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Layers/Menubarlayer.fxml")));
        Stage primaryStage = new Stage();
        primaryStage.setTitle(NAME_APP);
        primaryStage.setResizable(false);
        ClassLoader classLoader = Dictionary_main.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(LOGO_IMAGE_PATH);
        assert inputStream != null;
        Image image = new Image(inputStream);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(image);
        primaryStage.show();
        main_form.getScene().getWindow().hide();
    }

    public void SelectQuestion(ActionEvent event) {
        String questionSign = selectQuestion_signup.getValue();
        String questionPass = answerCheckPass.getValue();
    }

    @FXML
    void SigUp(ActionEvent event) throws SQLException {
        AlertMessage alert = new AlertMessage();
        if (nameSignup.getText().isEmpty() || emailSignup.getText().isEmpty()
                || answer_signup.getText().isEmpty() || signup_password.getText().isEmpty()
                || confirm_pass.getText().isEmpty() || selectQuestion_signup.getValue().isEmpty()) {
            alert.errorMessage("Bạn cần nhập đầy đủ thông tin");
        } else if (!signup_password.getText().equals(confirm_pass.getText())) {
            alert.errorMessage("Mật khẩu xác nhận không trùng khớp");
        } else if (signup_password.getText().length() < 8) {
            alert.errorMessage("Mật khẩu phải có ít nhất 8 ký tự");
        } else {
            checkSigUp();
        }
    }

    // Check if the username is already in the database
    // If not, insert the user into the database
    private void checkSigUp() {
        AlertMessage alert = new AlertMessage();
        String usernameCheckQuery = "SELECT count(1) FROM user_account WHERE username = ?";

        try (Connection connectDB = getConnection()){
            PreparedStatement preparedStatement = connectDB.prepareStatement(usernameCheckQuery);
            preparedStatement.setString(1, nameSignup.getText());
            ResultSet queryResult = preparedStatement.executeQuery();
            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    alert.errorMessage("Tên " + nameSignup.getText() + " đã tồn tại");
                } else {
                    DatabaseConnection databaseConnection = new DatabaseConnection();
                    databaseConnection.insertUser(new User(nameSignup.getText(), signup_password.getText(),
                            emailSignup.getText(), selectQuestion_signup.getValue(), answer_signup.getText(), 0));
                    alert.successMessage("Đăng ký thành công" + "\n" + "Chào mừng bạn đến với " + NAME_APP);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }


    @FXML
    void switchForm(ActionEvent event) {
        // the login form will be visible
        if (event.getSource() == signupBacktoLogin || event.getSource() == forgot_backBtn
                || event.getSource() == signupBacktoLogin) {
            signup_form.setVisible(false);
            login_form.setVisible(true);
            forgot_form.setVisible(false);
            changePass_form.setVisible(false);
        } else if (event.getSource() == loginToSigUp) { // the SigUp form will be visible
            signup_form.setVisible(true);
            login_form.setVisible(false);
            forgot_form.setVisible(false);
            changePass_form.setVisible(false);
        } else if (event.getSource() == forgotPassText || event.getSource() == changePass_backBtn) {
            signup_form.setVisible(false);
            login_form.setVisible(false);
            forgot_form.setVisible(true);
            changePass_form.setVisible(false);
        } else if (event.getSource() == changePass_proceedBtn) {
            signup_form.setVisible(false);
            login_form.setVisible(false);
            forgot_form.setVisible(false);
            changePass_form.setVisible(true);
        }
    }

    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        ObservableList<String> questions = FXCollections.observableArrayList(
                "Món ăn nào bạn thích nhất",
                "Màu sắc yêu thích nhất của bạn?",
                "Tên thú cưng của bạn?",
                "Môn thể thao bạn yêu thích nhất ?"
        );
        selectQuestion_signup.setItems(questions);
        answerCheckPass.setItems(questions);

        // forgotPassText is a Text object
        // when the user clicks on the text, the forgot form will be visible
        forgotPassText.setOnMouseClicked(event -> {
            signup_form.setVisible(false);
            login_form.setVisible(false);
            forgot_form.setVisible(true);
            changePass_form.setVisible(false);
        });
    }


}
