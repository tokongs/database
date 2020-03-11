package moviedatabase;

/**
 *
 * @author Svein Erik
 */

import java.sql.*;
import java.util.Properties;

public abstract class DBConnection {
    protected Connection connection;
    public DBConnection () {
    }
    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            //Class.forName("com.mysql.cj.jdbc.Driver"); //when you are using MySQL 8.0.
            // Properties for user and password.
            Properties p = new Properties();
            p.put("user", "chrstens_super");
            p.put("password", "db14");
            connection = DriverManager.getConnection("jdbc:mysql://mysql.stud.ntnu.no:3306/chrstens_movdb", p);
        } catch (Exception e)
        {
            throw new RuntimeException("Unable to connect", e);
        }
    }
}
/*
package moviedatabase;

import java.sql.*;
import java.util.Properties;

public class DBConnection {

    private final String url  = "jdbc:mysql://mysql.stud.ntnu.no:3306/chrstens_movdb";

    private final String username = "chrstens_super";
    private final String password = "db14";

    protected Connection connection;

    public DBConnection (String url, String username, String password) {
        //this.url = url;
        //this.username = username;
        //this.password = password;
    }

    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
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
*/