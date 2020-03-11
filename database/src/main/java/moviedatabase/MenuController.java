package src.main.java.moviedatabase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class MenuController {

  private ArrayList<Actor> actors = new ArrayList<Actor>();
  private ArrayList<Genre> genres = new ArrayList<Genre>();
  Connection connection;

  public MenuController(Connection connection) {
    this.connection = connection;
  }

  private Actor actorMenu() {

    int j = 0;
    while (true) {
      Actor actor = new Actor(j);
      actor.initialize(connection);
      if (actor.getName().isEmpty()) {
        break;
      }
      actors.add(actor);
      j++;
    }

    Scanner sc = new Scanner(System.in);

    while (true) {
      System.out.println("Choose actor!");
      for (int i = 0; i < actors.size(); i++) {
        System.out.println(i + ": " + actors.get(i).getName());
      }
      int actor = sc.nextInt();
      if (actor >= 0 && actor < actors.size()) {
        sc.close();
        return actors.get(actor);
      }
    }

  }

  private Genre genreMenu() {
    int j = 0;
    while (true) {
      Genre genre = new Genre(j);
      genre.initialize(connection);
      if (genre.getName().isEmpty()) {
        break;
      }
      genres.add(genre);
      j++;
    }

    Scanner sc = new Scanner(System.in);

    while (true) {
      System.out.println("Choose genre!");
      for (int i = 0; i < genres.size(); i++) {
        System.out.println(i + ": " + genres.get(i).getName());
      }
      int genre = sc.nextInt();
      if (genre >= 0 && genre < genres.size()) {
        sc.close();
        return genres.get(genre);
      }
    }
  }

  public int countMoviesPerCompany(Genre genre) {
    try {
      Statement stmt = connection.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT MAX(COUNT(CompanyID)) FROM " + 
      "((Genre INNER JOIN MediasIsGenre ON Genre.GenreId=MediaIsGenre.GenreId) " + 
      "INNER JOIN Media ON MediaIsGenre.MediaId=Media.MediaId)genreId "+
      "where GenreId =" + genre.getGenreId());
      while (rs.next()) {
        return rs.getInt("CompanyId");
      }
      return 0;
    } catch (Exception e) {
      System.out.println("db error during counting" + e);
      return 0;
    }
  }

  public void mainMenu() {
    System.out.println("1: Find roles of an actor");
    System.out.println("2: Find movies an actor is in");
    System.out.println("3: Who makes the most movies of a certain genre");
    System.out.println("4: Insert new movie");
    System.out.println("5: Insert nye review of an episode");
    Scanner sc = new Scanner(System.in);
    int i = sc.nextInt();
    sc.close();

    switch (i) {
      case 1:
        Actor actor = actorMenu();
        actor.getRoles().forEach(role -> System.out.println(role));
        break;
      case 2:
        actor = actorMenu();
        actor.getMovies().forEach(movie -> System.out.println(movie));
        break;
      case 3:
        Genre genre = genreMenu();
        System.out.println(countMoviesPerCompany(genre));
        break;
      case 4:
        break;
      case 5:
        break;
    }
  }
}
