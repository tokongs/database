package moviedatabase;

public class Main {

  public static void main(String[] args) {
    DBConnection connection = new DBConnection(
        "jdbc:mysql://127.0.0.1:3306/moviedatabase?autoReconnect=true&useSSL=false", "tokongs", "tokongs123");
    connection.connect();
    MenuController menu = new MenuController(connection.connection);
    menu.mainMenu();

  }
}
