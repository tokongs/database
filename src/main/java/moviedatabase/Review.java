package moviedatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

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
                    "SELECT Text, Rating, UserID, MediaID, Number, SeriesID  FROM Review WHERE ReviewId=" + this.ReviewID);
            while (rs.next()) {
                this.Text = rs.getString("Text");
                this.Rating = rs.getInt("Rating");
                this.UserID = rs.getInt("UserID");
                this.MediaID = rs.getInt("MediaID");
                this.Number = rs.getInt("Number");
                this.SeriesID = rs.getInt("SeriesID");
            }

        } catch (Exception e) {
            System.out.println("db error during select of media with id: " + ReviewID + e);
            return;
        }
    }

    public void insert(final Connection connection, Scanner sc) {
        try {
            System.out.println("Write your review here");
            this.Text = sc.next();
            System.out.println("Rating from 1 to 10");
            this.Rating = sc.nextInt();
            System.out.println("UserID of the user giving the review");
            this.UserID = sc.nextInt();
            System.out.println("MediaID of the media getting reviewed");
            this.MediaID = sc.nextInt();
            System.out.println("If it is a series, what season is it?");
            this.Number = sc.nextInt();
            System.out.println("SeriesID of the series");
            this.SeriesID = sc.nextInt();
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("INSERT INTO Review(Text, Rating, UserID, MediaID, Number, SeriesID) VALUES(" + Text + ", " + Rating + ", " + UserID + ", " + MediaID + ", " + Number + ", " + SeriesID + ")");
          } catch (Exception e) {
            System.out.println("db error during insert" + e);
            return;
          }
    }
}