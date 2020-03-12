package moviedatabase;

/**
 *
 * @author sveinbra
 */

import java.sql.*;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import moviedatabase.Media.Type;

public class Actor extends Employee {

  private ArrayList<String> roles = new ArrayList<String>();
  private ArrayList<String> movies = new ArrayList<String>();
  private ArrayList<Integer> mediaIds = new ArrayList<Integer>();

  public Actor(final int employeeId) {
    super(employeeId);
  }

  public ArrayList<String> getRoles() {
    return roles;
  }

  public ArrayList<String> getMovies() {
    return movies;
  }

  public ArrayList<Media> getMovies(final DBConnection connection) {
    return mediaIds.stream().map(id -> new Media(id)).filter(media -> media.getType() == Type.MOVIE)
        .collect(Collectors.toCollection(ArrayList::new));
  }

  public void initialize(final Connection connection) {
    try {
      final Statement stmt = connection.createStatement();
      final ResultSet rs = stmt.executeQuery(
          "SELECT * FROM (Employee INNER JOIN EmployeeActs ON Employee.EmployeeID=EmployeeActs.EmployeeID) WHERE Employee.EmployeeID="
              + employeeId);
      while (rs.next()) {
        name = rs.getString("Name");
        birthYear = rs.getString("BirthYear");
        originCountry = rs.getString("OriginCountry");
        roles.add(rs.getString("Role"));
        mediaIds.add(rs.getInt("MediaId"));
      }

    } catch (final Exception e) {
      System.out.println("db error during select of Actor with id: " + employeeId + e);
      return;
    }
    try {
      final Statement stmt = connection.createStatement();
      final ResultSet rs = stmt.executeQuery(
          "SELECT Title FROM "+
          "((Employee INNER JOIN EmployeeActs ON Employee.EmployeeId=EmployeeActs.EmployeeId)" +
          "INNER JOIN Media ON EmployeeActs.MediaId=Media.MediaId)" +
          "WHERE Media.MediaID NOT IN (SELECT MediaID FROM SeasonHasEpisode) AND Employee.EmployeeID="
              + employeeId);
      while (rs.next()) {
        movies.add(rs.getString("Title"));
      }

    } catch (final Exception e) {
      System.out.println("db error during select of Actor with id: " + employeeId + e);
      return;
    }
  }



}
