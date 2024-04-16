package JavaCode;

import Models.StudyRecord;
import Models.User;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import java.io.IOException;
import java.net.URL;
import java.security.cert.PolicyNode;
import java.sql.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.ResourceBundle;

import Constants.Constant;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

public class DatabaseConnection {

    @FXML
    AnchorPane container;

    public Connection databaseLink;
    protected static Timeline countdownTimer;
    protected static User currentUser;
    public static ArrayList<User> listUsers = new ArrayList<>();

    public Connection getConnection() {

        try {
            Class.forName(Constant.DRIVER);
            databaseLink = DriverManager.getConnection(Constant.URL, Constant.DATABASE_USER, Constant.DATABASE_PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return databaseLink;
    }

    void showComponent(String path) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource(path));
            container.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // sau khi đăng ký thành công, thực hiện kéo uerID của người dùng đó để tạo row mới trong bảng score
    public void setScoreNewUsers(String username) {
        // dựa vào username để lấy ID trong bảng user_account
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user_account WHERE username = ?");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int userID = resultSet.getInt("userID");
                PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT INTO study (userID, score, times_attend, total_question, correct_question, incorrect_question ) VALUES (?, ?, ?, ?, ?, ?)");
                preparedStatement1.setInt(1, userID);
                preparedStatement1.setInt(2, 0);
                preparedStatement1.setInt(3, 0);
                preparedStatement1.setInt(4, 0);
                preparedStatement1.setInt(5, 0);
                preparedStatement1.setInt(6, 0);
                preparedStatement1.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // lấy thông tin người dùng khi đăng nhập thành công vào current user để có thể sử dụng ở các controller khác
    public void setCurrentUser(String username) {
        Connection connection = getConnection();
        String query = "SELECT * FROM user_account WHERE username = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int userID = resultSet.getInt("userID");
                String username1 = resultSet.getString("username");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                currentUser = new User(userID, username1, password, email);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }



    // lấy thông tin study của người dùng từ ID đã lưu trong current user
    public void getStudyRecord() {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM study WHERE userID = ?");
            preparedStatement.setInt(1, currentUser.getUserID());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int score = resultSet.getInt("score");
                int timesAttend = resultSet.getInt("times_attend");
                int totalQuestion = resultSet.getInt("total_question");
                int correctQuestion = resultSet.getInt("correct_question");
                int incorrectQuestion = resultSet.getInt("incorrect_question");
                currentUser.setStudyRecord(new StudyRecord(score, timesAttend, totalQuestion, correctQuestion, incorrectQuestion));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // trong bảng study có cột userId và cột score, tôi cần lấy ra tên của tất cả ngươid dùng và điểm số của họ và sắp xếp điểm theo chiều giảm dần.
    public void getRanking() {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user_account INNER JOIN study ON user_account.userID = study.userID");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int userID = resultSet.getInt("userID");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                int score = resultSet.getInt("score");
                int timesAttend = resultSet.getInt("times_attend");
                int totalQuestion = resultSet.getInt("total_question");
                int correctQuestion = resultSet.getInt("correct_question");
                int incorrectQuestion = resultSet.getInt("incorrect_question");
                User user = new User(userID, username, password, email);
                user.setStudyRecord(new StudyRecord(score, timesAttend, totalQuestion, correctQuestion, incorrectQuestion));
                listUsers.add(user);
            }
            Collections.sort(listUsers, (o1, o2) -> o2.getStudyRecord().getScore() - o1.getStudyRecord().getScore());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // lấy tất cả các userID trong bảng user_account sang bảng study

    public static void main(String[] args) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.getConnection();
        databaseConnection.getRanking();
        databaseConnection.setCurrentUser("quangdung");
        databaseConnection.getStudyRecord();
        for (User user : listUsers) {
            System.out.println(user.getUserID() + " " + user.getUsername() + " " + user.getEmail() + " " + user.getStudyRecord().getScore() + " " + user.getStudyRecord().getTimesAttend() + " " + user.getStudyRecord().getTotalQuestion() + " " + user.getStudyRecord().getCorrectQuestions() + " " + user.getStudyRecord().getIncorrectQuestions());
        }
//            System.out.println(currentUser.getUserID()+ " " + currentUser.getUsername() + " " + currentUser.getEmail() + " " + currentUser.getStudyRecord().getScore() + " " + currentUser.getStudyRecord().getTimesAttend() + " " + currentUser.getStudyRecord().getTotalQuestion() + " " + currentUser.getStudyRecord().getCorrectQuestions() + " " + currentUser.getStudyRecord().getIncorrectQuestions());
        }


}