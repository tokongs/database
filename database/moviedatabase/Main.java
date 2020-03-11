package moviedatabase;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //DBConnection connection = new DBConnection("jdbc:mysql://127.0.0.1/avtalebok?autoReconnect=true&useSSL=false", "username", "password");
        //DBConnection connection = new DBConnection("jdbc:mysql://localhost:3306/movdb/chrstens_movdb", "username", "password");
        //DBConnection connection = new DBConnection();
        System.out.println("Welcome to the film database program!");
        System.out.println("The best way for you to DAB out some informastion about your " +
                "favorite movies and series! \n");
        Main.run();
    }

    public static void run() {
        AddMediaCtrl amc = new AddMediaCtrl();

        System.out.println("\n\n\nWhat do you want to do?");
        System.out.println("1) Find all the roles a spesific actor plays");
        System.out.println("2) Find which movies a specific actor appears");
        System.out.println("3) ---");
        System.out.println("4) Insert a new movie (you provide the information)");
        System.out.println("5) Create a new review of a movie or episode \n");
        System.out.println("Enter your choice: ");

        Scanner scanner = new Scanner(System.in);
        int choice = Integer.parseInt(scanner.nextLine());
        System.out.println("You chose option: " + choice + "\n");

        System.out.println("Enter the title of the movie: ");
        String title = scanner.nextLine();
        System.out.println("Enter the length of the movie in minutes: ");
        int length = Integer.parseInt(scanner.nextLine());

        amc.add(title, length);
        amc.finish();
    }
}