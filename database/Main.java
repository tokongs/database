package moviedatabase;

import moviedatabase.DBConnection;

public class Main {

  public static void main(String[] args) {
    DBConnection connection = new DBConnection("jdbc:mysql://127.0.0.1/avtalebok?autoReconnect=true&useSSL=false", "username", "password");
    connection.connect();
  }
}
