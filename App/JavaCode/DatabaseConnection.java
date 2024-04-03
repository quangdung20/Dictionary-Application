package JavaCode;

import java.sql.Connection;
import java.sql.DriverManager;
import Constants.Constant;
public class DatabaseConnection {
    public Connection databaseLink;

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
}
