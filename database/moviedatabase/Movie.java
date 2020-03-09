package moviedatabase

import java.sql.*;
import java.util.*;
import java.util.Date;

public class Movie extends Media {

  public Movie(int mediaId) {
    super(mediaId);
  }

  public void initialize(Connection connection) {
    try {
      Statement stmt = connection.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT * FROM Movie WHERE MediaId=" + mediaId
          + " AND MediaId NOT IN(SELECT MediaId FROM MediaInSeason)");
      while (rs.next()) {
        title = rs.getString("Title");
        length = rs.getString("Length");
        publicationYear = rs.getString("PublicationYear");
        launchDate = rs.getDate("LaunchDate");
        description = rs.getString("Description");

      }

    } catch (Exception e) {
      System.out.println("db error during select of movie with id: " + mediaId + e);
      return;
    }

  }
}
