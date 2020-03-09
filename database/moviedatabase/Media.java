package moviedatabase

/**
 *
 * @author sveinbra
 */

import java.sql.*;
import java.util.*;
import java.util.Date;

public abstract class Media extends ActiveDomainObject {
  protected int mediaId;
  protected String title;
  protected String length;
  protected String publicationYear;
  protected Date launchDate;
  protected String description;

  public Media(int mediaId) {
    this.mediaId = mediaId;
  }

  public int getMediaId() {
    return mediaId;
  }

  public abstract void initialize(Connection connection);

  public void refresh(Connection conn) {
    initialize(conn);
  }

  public void save(Connection conn) {
    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("UPDATE Media SET Title=" + title + ", Length=" + length
          + ", PublicationYear=" + publicationYear +  ", LaunchDate=" + launchDate + ", Description=" 
          + description + " WHERE MediaId=" + mediaId);
    } catch (Exception e) {
      System.out.println("db error during updat of media with id: " + mediaId + e);
      return;
    }
  }

}
