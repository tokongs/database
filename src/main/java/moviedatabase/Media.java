package moviedatabase;

/**
 *
 * @author sveinbra
 */

import java.sql.*;
import java.util.*;
import java.util.Date;

public class Media extends ActiveDomainObject {

  public enum Type {
    MOVIE, SERIES
  }

  public int mediaId;
  public String title;
  public String length;
  public String publicationYear;
  public Date launchDate;
  public String description;
  public Type type;
  public int companyId;
  public String genre;

  public Media(int mediaId) {
    this.mediaId = mediaId;
  }

  public int getMediaId() {
    return mediaId;
  }

  public String getGenre() {
    return genre;
  }

  public void initialize(Connection connection) {
    try {
      Statement stmt = connection.createStatement();
      ResultSet rs = stmt.executeQuery(
          "SELECT Title, Length, PublicationYear, LaunchDate, Description, CompanyId,  FROM Media WHERE MediaId="
              + mediaId);
      while (rs.next()) {
        title = rs.getString("Title");
        length = rs.getString("Length");
        publicationYear = rs.getString("PublicationYear");
        launchDate = rs.getDate("LaunchDate");
        description = rs.getString("Description");
        companyId = rs.getInt("CompanyId");
      }

    } catch (Exception e) {
      System.out.println("db error during select of media with id: " + mediaId + e);
      return;
    }

    try {
      Statement stmt = connection.createStatement();
      ResultSet rs = stmt.executeQuery(
          "SELECT Name FROM ((Media INNER JOIN MediaIsGenre ON Media.MediaId=MediaIsGenre.MediaId) INNER JOIN Genre ON MediaIsGenre.GenerId=Genre.GenreId) WHERE MediaId="
              + mediaId);
      while (rs.next()) {
        genre = rs.getString("Name");
      }

    } catch (Exception e) {
      System.out.println("db error during select of media with id: " + mediaId + e);
      return;
    }

    try {
      Statement stmt = connection.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT Count(MediaId) AS count FROM SeasonHasEpisode where MediaId=" + mediaId);
      while (rs.next()) {
        if (rs.getInt("count") == 0)
          type = Type.MOVIE;
        else
          type = Type.SERIES;
      }

    } catch (Exception e) {
      System.out.println("db error during select of media with id: " + mediaId + e);
      return;
    }

  }

  public void refresh(Connection conn) {
    initialize(conn);
  }

  public void insert(Connection conn) {
    try {
      String sql = "INSERT INTO Media(Title, Length, PublicationYear, LaunchDate, description, CompanyID) "
          + "VALUES(\"" + title + "\", \"" + length + "\", \"" + publicationYear + "\", \"" + launchDate.toString()
          + "\", \"" + description + "\", \"" + companyId + "\")";
      PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      stmt.executeUpdate();
      ResultSet rs = stmt.getGeneratedKeys();
      rs.next();
      mediaId = rs.getInt(1);

    } catch (Exception e) {
      System.out.println("db error during insert of media with id:" + e);
      return;
    }
  }

  public void save(Connection conn) {
    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(
          "UPDATE Media SET Title=" + title + ", Length=" + length + ", PublicationYear=" + publicationYear
              + ", LaunchDate=" + launchDate + ", Description=" + description + " WHERE MediaId=" + mediaId);
    } catch (Exception e) {
      System.out.println("db error during updat of media with id: " + mediaId + e);
      return;
    }
  }

  public Type getType() {
    return type;
  }

}
