package moviedatabase;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MenuController {

  Connection connection;
  Scanner sc;

  public MenuController(Connection connection) {
    this.connection = connection;
    sc = new Scanner(System.in);
    sc.useDelimiter(System.lineSeparator());

  }

  private Actor actorMenu() {
    ArrayList<Actor> actors = new ArrayList<>();
    try {
      Statement stmt = connection.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT EmployeeID from EmployeeActs");
      while (rs.next()) {
        Actor actor = new Actor(rs.getInt("EmployeeID"));
        actor.initialize(connection);
        actors.add(actor);
      }

    } catch (Exception e) {
      System.out.println("db error during select" + e);
      return null;
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
    ArrayList<Genre> genres = new ArrayList<Genre>();

    try {
      Statement stmt = connection.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT GenreID from Genre");
      while (rs.next()) {
        Genre genre = new Genre(rs.getInt("Genre"));
        genre.initialize(connection);
        genres.add(genre);
      }

    } catch (Exception e) {
      System.out.println("db error during select" + e);
      return null;
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
      ResultSet rs = stmt.executeQuery("SELECT Genre.GenreID, Company.Name, COUNT(Company.CompanyID) AS occurence FROM "
          + "(((Genre INNER JOIN MediaIsGenre ON Genre.GenreID=MediaIsGenre.GenreID) "
          + "INNER JOIN Media ON MediaIsGenre.MediaID=Media.MediaID) "
          + "INNER JOIN Company ON Media.CompanyID=Company.CompanyID) " + "WHERE Genre.GenreID=" + genre.genreId
          + " ORDER BY occurence DESC " + "LIMIT 1");
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
    Media movie = new Media(0);
    System.out.print("Title: ");
    movie.title = sc.next();
    System.out.print("Length: ");
    movie.length = sc.next();
    System.out.print("PublicationYear: ");
    movie.publicationYear = sc.next();
    System.out.print("Launch year: ");
    int launchYear = sc.nextInt();
    System.out.print("Launch month: ");
    int launchMonth = sc.nextInt();
    System.out.print("Launch day: ");
    int launchDay = sc.nextInt();
    System.out.print("Description: ");
    movie.description = sc.next();
    movie.launchDate = new Date(launchYear, launchMonth, launchDay);

    Company comp = selectCompanyMenu();
    movie.companyId = comp.companyId;
    movie.insert(connection);

    System.out.print("How many directors are there?: ");
    int num_director = sc.nextInt();
    ArrayList<Director> directors = new ArrayList<Director>();
    for (int i = 0; i < num_director; i++) {
      directors.add(selectDirectorMenu());
    }

    for (Director dir : directors) {
      try {
        String sql = "INSERT INTO EmployeeDirected(EmployeeID, MediaID) " + "VALUES(\"" + dir.employeeId + "\", \""
            + movie.mediaId + "\")";
        PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.executeUpdate();

      } catch (Exception e) {
        System.out.println("db error during insert of employee with id:" + e);
        return;
      }
    }

    System.out.print("How many writers are there?: ");
    int num_writer = sc.nextInt();
    ArrayList<Writer> writers = new ArrayList<Writer>();
    for (int i = 0; i < num_writer; i++) {
      writers.add(selectWriterMenu());
    }

    for (Writer writer : writers) {
      try {
        String sql = "INSERT INTO EmployeeWrote(EmployeeID, MediaID) " + "VALUES(\"" + writer.employeeId + "\", \""
            + movie.mediaId + "\")";
        PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.executeUpdate();

      } catch (Exception e) {
        System.out.println("db error during insert of employee with id:" + e);
        return;
      }
    }

    System.out.print("How many actors are there?: ");
    int num_actor = sc.nextInt();
    HashMap<String, Actor> actors = new HashMap<String, Actor>();
    for (int i = 0; i < num_actor; i++) {
      Actor actor = selectActorMenu();
      System.out.print("Name of the role: ");
      String role = sc.next();
      actors.put(role, actor);
    }

    for (Map.Entry<String, Actor> entry : actors.entrySet())
      try {
        String sql = "INSERT INTO EmployeeActs(EmployeeID, MediaID, Role) " + "VALUES(\"" + entry.getValue().employeeId
            + "\", \"" + movie.mediaId + "\", \"" + entry.getKey() + "\")";
        PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.executeUpdate();

      } catch (Exception e) {
        System.out.println("db error during insert of employee with id:" + e);
        return;
      }
  }

  public Company selectCompanyMenu() {
    ArrayList<Company> companys = new ArrayList<>();
    try {
      Statement stmt = connection.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT CompanyID from Company");
      while (rs.next()) {
        Company company = new Company(rs.getInt("CompanyID"));
        company.initialize(connection);
        companys.add(company);
      }

    } catch (Exception e) {
      System.out.println("db error during select" + e);
      return null;
    }

    while (true) {
      System.out.println("Choose company!");
      for (int i = 0; i < companys.size(); i++) {
        System.out.println(i + ": " + companys.get(i).getName());
      }
      System.out.println(companys.size() + ": Insert new company");
      int company = sc.nextInt();
      if (company >= 0 && company < companys.size()) {
        return companys.get(company);
      } else if (company == companys.size()) {
        return insertCompanyMenu();
      }
    }
  }

  public Writer selectWriterMenu() {
    ArrayList<Writer> writers = new ArrayList<>();
    try {
      Statement stmt = connection.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT EmployeeID from EmployeeWrote");
      while (rs.next()) {
        Writer writer = new Writer(rs.getInt("EmployeeID"));
        writer.initialize(connection);
        writers.add(writer);
      }

    } catch (Exception e) {
      System.out.println("db error during select" + e);
      return null;
    }

    while (true) {
      System.out.println("Choose writer!");
      for (int i = 0; i < writers.size(); i++) {
        System.out.println(i + ": " + writers.get(i).getName());
      }
      System.out.println(writers.size() + ": Insert new writer");
      int writer = sc.nextInt();
      if (writer >= 0 && writer < writers.size()) {
        return writers.get(writer);
      } else if (writer == writers.size()) {
        return insertWriterMenu();
      }
    }
  }

  public Director selectDirectorMenu() {
    ArrayList<Director> directors = new ArrayList<>();
    try {
      Statement stmt = connection.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT EmployeeID from EmployeeDirected");
      while (rs.next()) {
        Director director = new Director(rs.getInt("EmployeeID"));
        director.initialize(connection);
        directors.add(director);
      }

    } catch (Exception e) {
      System.out.println("db error during select" + e);
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
      } else if (director == directors.size()) {
        return insertDirectorMenu();
      }
    }
  }

  public Actor selectActorMenu() {
    ArrayList<Actor> actors = new ArrayList<>();
    try {
      Statement stmt = connection.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT EmployeeID from EmployeeActs");
      while (rs.next()) {
        Actor actor = new Actor(rs.getInt("EmployeeID"));
        actor.initialize(connection);
        actors.add(actor);
      }

    } catch (Exception e) {
      System.out.println("db error during select" + e);
      return null;
    }

    while (true) {
      System.out.println("Choose actor!");
      for (int i = 0; i < actors.size(); i++) {
        System.out.println(i + ": " + actors.get(i).getName());
      }
      System.out.println(actors.size() + ": Insert new actor");
      int actor = sc.nextInt();
      if (actor >= 0 && actor < actors.size()) {
        return actors.get(actor);
      } else if (actor == actors.size()) {
        return insertActorMenu();
      }
    }
  }

  private Company insertCompanyMenu() {
    Company dir = new Company(0);
    System.out.print("Name: ");
    dir.name = sc.next();
    System.out.print("URL: ");
    dir.URL = sc.next();
    System.out.print("Country: ");
    dir.country = sc.next();
    System.out.print("Address: ");
    dir.address = sc.next();
    dir.insert(connection);
    return dir;
  }

  private Director insertDirectorMenu() {
    Director dir = new Director(0);
    System.out.print("Name: ");
    dir.name = sc.next();
    System.out.print("Birth Year: ");
    dir.birthYear = sc.next();
    System.out.print("Origin Country: ");
    dir.originCountry = sc.next();
    dir.insert(connection);
    return dir;

  }

  private Writer insertWriterMenu() {
    Writer dir = new Writer(0);
    System.out.print("Name: ");
    dir.name = sc.next();
    System.out.print("Birth Year: ");
    dir.birthYear = sc.next();
    System.out.print("Origin Country: ");
    dir.originCountry = sc.next();
    dir.insert(connection);
    return dir;
  }

  private Actor insertActorMenu() {
    Actor dir = new Actor(0);
    System.out.print("Name: ");
    dir.name = sc.next();
    System.out.print("Birth Year: ");
    dir.birthYear = sc.next();
    System.out.print("Origin Country: ");
    dir.originCountry = sc.next();
    dir.insert(connection);
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
