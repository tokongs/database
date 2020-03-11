package src.main.java.moviedatabase;

import java.sql.*;
import java.util.Properties;

public class DBConnection {

    private final String driver = "com.mysql.jdbc.Driver";
    private final String url;

    private final String username;
    private final String password;

    protected Connection connection;

    public DBConnection (String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public void connect() {
        try {
            Class.forName(driver).newInstance();
            // Class.forName("com.mysql.cj.jdbc.Driver"); when you are using MySQL 8.0.
            // Properties for user and password.
            Properties p = new Properties();
            p.put("user", username);
            p.put("password", password);
            connection = DriverManager.getConnection(url, p);
        } catch (Exception e) {
            throw new RuntimeException("Unable to connect to database at " + url, e);
        }
    }
}
