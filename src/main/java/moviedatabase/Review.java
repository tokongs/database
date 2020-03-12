package moviedatabase;

import java.sql.ResultSet;

/**
 * Review
 */
public class Review {

    private int ReviewID, Rating, UserID, MediaID, Number, SeriesID;
    private String Text = "";

    public Review() {

    }

    public Review(final int ReviewID) {
        this.ReviewID = ReviewID;
    }

    public void initialize(final Connection connection) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(
                "SELECT Text, Rating, UserID, MediaID, Number, SeriesID  FROM Review WHERE ReviewId="
                    + ReviewID);
            while (rs.next()) {
              Text = rs.getString("Text");
              Rating = rs.getInt("Rating");
              UserID = rs.getInt("UserID");
              MediaID = rs.getInt("MediaID");
              Number = rs.getInt("Number");
              SeriesID = rs.getInt("SeriesID");
            }
      
          } catch (Exception e) {
            System.out.println("db error during select of media with id: " + mediaId + e);
            return;
          }
    }

    public void insert(final Connection connection) {
        try {
                  
          } catch (Exception e) {
            System.out.println("db error during insert" + e);
            return;
          }
    }

