package moviedatabase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class MenuController {

  private ArrayList<Actor> actors = new ArrayList<Actor>();
  private ArrayList<Genre> genres = new ArrayList<Genre>();
  Connection connection;
  Scanner sc;

  public MenuController(Connection connection) {
    this.connection = connection;
    sc = new Scanner(System.in);
    sc.useDelimiter(System.lineSeparator());

  }

  private Actor actorMenu() {

    int j = 1;
    while (true) {
      Actor actor = new Actor(j);
      actor.initialize(connection);
      if (actor.getName() == null) {
        break;
      }
      actors.add(actor);
      j++;
    }

    if (actors.isEmpty()) {
      System.out.println("There are no actors!");
      return null;
    }

    while (true) {
      System.out.println("Choose actor!");
      for (int i = 0; i < actors.size(); i++) {
        System.out.println(i + ": " + actors.get(i).getName());
      }

      int actor = sc.nextInt();
      if (actor >= 0 && actor < actors.size()) {
        return actors.get(actor);
      }
    }

  }

  private Genre genreMenu() {
    int j = 1;
    while (true) {
      Genre genre = new Genre(j);
      genre.initialize(connection);
      if (genre.getName() == null) {
        break;
      }
      genres.add(genre);
      j++;
    }

    if (genres.isEmpty()) {
      System.out.println("No genres!");
      return null;
    }

    while (true) {
      System.out.println("Choose genre!");
      for (int i = 0; i < genres.size(); i++) {
        System.out.println(i + ": " + genres.get(i).getName());
      }

      int genre = sc.nextInt();
      if (genre >= 0 && genre < genres.size()) {
        return genres.get(genre);
      }
    }
  }

  public String countMoviesPerCompany(Genre genre) {
    try {
      Statement stmt = connection.createStatement();
      ResultSet rs = stmt.executeQuery(
          "SELECT Genre.GenreID, Company.Name, COUNT(Company.CompanyID) AS occurence FROM "
              + "(((Genre INNER JOIN MediaIsGenre ON Genre.GenreID=MediaIsGenre.GenreID) "
              + "INNER JOIN Media ON MediaIsGenre.MediaID=Media.MediaID) "
              + "INNER JOIN Company ON Media.CompanyID=Company.CompanyID) " + "WHERE Genre.GenreID="
              + genre.genreId + " ORDER BY occurence DESC " + "LIMIT 1");
      while (rs.next()) {
        return rs.getString("Name");
      }
      return "";
    } catch (Exception e) {
      System.out.println("db error during counting" + e);
      return "";
    }
  }

  public void insertMoviewMenu() {
    System.out.print("Title: ");
    String title = sc.next();
    System.out.print("Length: ");
    String length = sc.next();
    System.out.print("PublicationYear: ");
    String publicationYear = sc.next();
    System.out.print("Launch year: ");
    String launchYear = sc.next();
    System.out.print("Launch month: ");
    String launchMonth = sc.next();
    System.out.print("Launch day: ");
    String launchDay = sc.next();
    System.out.print("Description: ");
    String description = sc.next();

    Director director = selectDirectorMenu();
  }

  public Director selectDirectorMenu() {
    ArrayList<Director> directors = new ArrayList<>();
    int j = 1;
    while (true) {
      Director director = new Director(j);
      director.initialize(connection);
      if (director.getName() == null) {
        break;
      }
      directors.add(director);
      j++;
    }

    if (directors.isEmpty()) {
      System.out.println("There are no directors!");
      return null;
    }

    while (true) {
      System.out.println("Choose director!");
      for (int i = 0; i < directors.size(); i++) {
        System.out.println(i + ": " + directors.get(i).getName());
      }
        System.out.println(directors.size() + ": Insert new direcor");
      int director = sc.nextInt();
      if (director >= 0 && director < directors.size()) {
        return directors.get(director);
      } else if (director == directors.size()){
        return insertDirectorMenu();
      }
    }
  }

  private Director insertDirectorMenu(){
    Director dir = new Director(0);
    System.out.print("Name: ");
    dir.name = sc.next();
    System.out.print("Birth Year: ");
    dir.birthYear = sc.next();
    System.out.print("Origin Country: ");
    dir.originCountry = sc.next();
    return dir;
  }

  public void mainMenu() {
    System.out.println("1: Find roles of an actor");
    System.out.println("2: Find movies an actor is in");
    System.out.println("3: Who makes the most movies of a certain genre");
    System.out.println("4: Insert new movie");
    System.out.println("5: Insert nye review of an episode");
    int i = sc.nextInt();

    switch (i) {
      case 1:
        Actor actor = actorMenu();
        if (actor == null)
          break;
        actor.getRoles().forEach(role -> System.out.println(role));
        break;
      case 2:
        actor = actorMenu();
        if (actor == null)
          break;
        actor.getMovies().forEach(movie -> System.out.println(movie));
        break;
      case 3:
        Genre genre = genreMenu();
        if (genre == null)
          break;
        System.out.println(countMoviesPerCompany(genre));
        break;
      case 4:
        insertMoviewMenu();
        break;
      case 5:
        break;
    }
  }
}
