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
import javafx.scene.layout.AnchorPane;

import static JavaCode.ActiveComponent.listRankingUsers;

public abstract class DatabaseConnection {

    @FXML
    AnchorPane container;

    public Connection databaseLink;
    protected static Timeline countdownTimer;

    protected static User user;

    public static HashMap<String, User> mapUsers = new HashMap<>();
    public static ArrayList<User> listUsers = new ArrayList<>();

    public Connection getConnection(){

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

    public void getListUserData() {
        Connection connection = getConnection();
        String query = "SELECT * FROM user_account";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            listUsers.clear();
            mapUsers.clear();
            while (resultSet.next()) {
                User retrievedUser = new User();
                retrievedUser.setUsername(resultSet.getString("username"));
                // Set other fields...

                listUsers.add(retrievedUser);
                mapUsers.put(retrievedUser.getUsername(), retrievedUser);
                System.out.println("+1 user: " + retrievedUser.getUsername());
                // Set the user object to the retrievedUser
                user = retrievedUser;
            }

            if (listRankingUsers != null) {
                listRankingUsers.clear();
                sortRankingByPoint();
                listRankingUsers.addAll(listUsers);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void sortRankingByPoint() {
        Collections.sort(listUsers);
        Collections.reverse(listUsers);
    }

}
