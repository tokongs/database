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
                    "SELECT Text, Rating, UserID, MediaID, Number, SeriesID  FROM Review WHERE ReviewId=" + ReviewID);
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

    public void insert(final Connection connection, Scanner sc) {
        try {
            System.out.println("Write your review here");
            Text = sc.next();
            System.out.println("Rating from 1 to 10");
            Rating = sc.nextInt();
            System.out.println("UserID of the user giving the review");
            UserID = sc.nextInt();
            System.out.println("MediaID of the media getting reviewed");
            MediaID = sc.nextInt();
            System.out.println("If it is a series, what season is it?");
            Number = sc.nextInt();
            System.out.println("SeriesID of the series");
            SeriesID = sc.nextInt();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("INSERT INTO Review(Text, Rating, UserID, MediaID, Number, SeriesID) VALUES(" + Text + ", " + Rating + ", " + UserID + ", " + MediaID + ", " + Number + ", " + SeriesID + ")");
          } catch (Exception e) {
            System.out.println("db error during insert" + e);
            return;
          }
    }
