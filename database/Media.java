package avtalebok;

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
          stmt.executeQuery("select title, length, publicationYear, launchDate, description where mediaId=" + mediaId);
      while (rs.next()) {
        title = rs.getString("navn");
        length = rs.getString("epost");
        publicationYear = rs.getString("publicationYear");
        launchDate = rs.getDate("launchDate");
        description = rs.getString("description");

      }

    } catch (Exception e) {
      System.out.println("db error during select of bruker= " + e);
      return;
    }

  }

  public void refresh(Connection conn) {
    initialize(conn);
  }

  public void save(Connection conn) {
    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("update Bruker set title=" + title + ", length=" + length
          + ", publicationYear=" + publicationYear +  ", launchDate=" + launchDate + ", description=" 
          + description + " where mediaId=" + mediaId);
    } catch (Exception e) {
      System.out.println("db error during update of bruker=" + e);
      return;
    }
  }

}
