package moviedatabase;

/**
 *
 * @author sveinbra
 */

import java.sql.*;
import java.util.*;
import java.util.Date;

public class Genre extends ActiveDomainObject {
  protected int genreId;
  protected String name;


  public Genre(int genreId) {
    this.genreId = genreId;
  }

  public String getName() {
    return name;
  }

  public int getGenreId() {
    return genreId;
  }

  public void initialize(Connection connection) {
    try {
      Statement stmt = connection.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT Name FROM Genre WHERE GenerId=" + genreId);
      while (rs.next()) {
        name = rs.getString("Name");
      }

    } catch (Exception e) {
      System.out.println("db error during select of genre with id: " + genreId + e);
      return;
    }
  }

  public void refresh(Connection conn) {
    initialize(conn);
  }

  public void save(Connection conn) {
    try {
      Statement stmt = conn.createStatement();
      stmt.executeQuery("UPDATE Genre set Name=" + name + "where GenreId=" + genreId);
    } catch (Exception e) {
      System.out.println("db error during update of genre with id: " + genreId + e);
      return;
    }
  }

}
