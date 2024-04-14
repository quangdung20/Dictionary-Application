package JavaCode;

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


import static JavaCode.LoginController.currentUser;

public class DatabaseConnection {

    @FXML
    AnchorPane container;

    public Connection databaseLink;
    protected static Timeline countdownTimer;

    protected static User user, currentUser;


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

    public void getScoreUsers() {
        Connection connection = getConnection();
        String query = "SELECT username, score\n" +
                "FROM user_account\n" +
                "ORDER BY score DESC;";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                int score = resultSet.getInt("score");
                User user = new User(username, score);
                listUsers.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateScoreUser(User user) {
        Connection connection = getConnection();
        String query = "UPDATE user_account\n" +
                "SET score = ?\n" +
                "WHERE username = ?;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, user.getScore());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertUser(User user) {
        Connection connection = getConnection();
        String insertUser = "INSERT INTO user_account(username, password, email, question, answer, score) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertUser);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getQuestion());
            preparedStatement.setString(5, user.getAnswer());
            preparedStatement.setInt(6, user.getScore());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lưu username và score của user hiện tại sau khi đăng nhập thành công
    public void saveCurrentUser(String username) {
        Connection connection = getConnection();
        String query = "SELECT username, email, score\n" +
                "FROM user_account\n" +
                "WHERE username = ?;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String username1 = resultSet.getString("username");
                int score = resultSet.getInt("score");
                String email = resultSet.getString("email");
                currentUser = new User(username1, score, email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // test score of user
    public static void main(String[] args) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.getScoreUsers();
        databaseConnection.updateScoreUser(new User("quangdung", 1000));

        // print username and score of user
        for (User user : listUsers) {
            System.out.println(user.getUsername() + " \t" + user.getScore());
        }
    }
}
