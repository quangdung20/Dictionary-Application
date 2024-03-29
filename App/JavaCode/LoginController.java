package JavaCode;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

import AlertBox.AlertMessage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;


public class LoginController {

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
    private TextField changePass_cPassword, changePass_password;

    @FXML
    void changePassword(ActionEvent event) {

    }

    @FXML
    void forgotPassword(ActionEvent event) {


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
        Dictionary_main m = new Dictionary_main();
        AlertMessage alert = new AlertMessage();
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String verifyLogin = "SELECT count(1) FROM user_account WHERE username = " +
                "'" + login_username.getText() + "' AND password = '" + login_password.getText() + "'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {

                    m.changeScene("Layers/afterLogin.fxml");
                } else {
                    alert.errorMessage("Thông tin đăng nhập không chính xác, vui lòng kiểm tra lại");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
    public void SelectQuestion(ActionEvent event) {
        String question = selectQuestion_signup.getValue();
    }

    public void initialize() {
        ObservableList<String> questions = FXCollections.observableArrayList(
                "Món ăn nào bạn thích nhất",
                "Màu sắc yêu thích nhất của bạn?",
                "Tên thú cưng của bạn?",
                "Môn thể thao bạn yêu thích nhất ?"
        );
        selectQuestion_signup.setItems(questions);
    }

    @FXML
    void register(ActionEvent event) throws SQLException {
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
    private void checkSigUp() throws SQLException {
        AlertMessage alert = new AlertMessage();
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String username = "SELECT count(1) FROM user_account WHERE username = '" + nameSignup.getText() + "'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(username);
            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    alert.errorMessage("Tên " + username + "đã tồn tại");
                } else {
                    String insertSignUp = "INSERT INTO user_account(username, password, email, question, answer) VALUES " +
                            "('" + nameSignup.getText() + "', '" + signup_password.getText() + "'," +
                            " '" + emailSignup.getText() + "', '" + selectQuestion_signup.getValue() +
                            "', '" + answer_signup.getText() + "' )";
                    statement.executeUpdate(insertSignUp);
                    alert.successMessage("Đăng ký thành công!");
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
}
