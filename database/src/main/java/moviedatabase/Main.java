package src.main.java.moviedatabase;

import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    DBConnection connection = new DBConnection(
        "jdbc:mysql://127.0.0.1:3306/moviedatase?autoReconnect=true&useSSL=false", "root", "");
    connection.connect();
    MenuController menu = new MenuController(connection.connection);
    menu.mainMenu();

  }
}
