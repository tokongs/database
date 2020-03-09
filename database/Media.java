package moviedatabase

/**
 *
 * @author sveinbra
 */

import java.sql.*;
import java.util.*;
import java.util.Date;

public class Media extends ActiveDomainObject {
  private int mediaId;
  private String title;
  private String length;
  private String publicationYear;
  private Date launchDate;
  private String description;

  public Media(int mediaId) {
    this.mediaId = mediaId;
  }

  public int getMediaId() {
    return mediaId;
  }

  public void initialize(Connection connection) {
    try {
      Statement stmt = connection.createStatement();
      ResultSet rs =
          stmt.executeQuery("SELECT title, length, publicationYear, launchDate, description FROM Media where MediaId=" + mediaId);
      while (rs.next()) {
        title = rs.getString("title");
        length = rs.getString("length");
        publicationYear = rs.getString("publicationYear");
        launchDate = rs.getDate("launchDate");
        description = rs.getString("description");

      }

    } catch (Exception e) {
      System.out.println("db error during select of media with id: " + mediaId+ e);
      return;
    }

  }

  public void refresh(Connection conn) {
    initialize(conn);
  }

  public void save(Connection conn) {
    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("UPDATE Media SET title=" + title + ", length=" + length
          + ", publicationYear=" + publicationYear +  ", launchDate=" + launchDate + ", description=" 
          + description + " WHERE mediaId=" + mediaId);
    } catch (Exception e) {
      System.out.println("db error during updat of media with id: " + mediaId + e);
      return;
    }
  }

}
