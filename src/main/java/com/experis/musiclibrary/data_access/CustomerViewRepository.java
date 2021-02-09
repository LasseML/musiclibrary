package com.experis.musiclibrary.data_access;

import com.experis.musiclibrary.models.Track;

import java.sql.*;
import java.util.ArrayList;


public class CustomerViewRepository {
    // Setting up the connection object we need.
    private String URL = ConnectionHelper.CONNECTION_URL;
    private Connection conn = null;

    // takes a searchQuery as a string and returns a list of tracks that contain that string in their name
    public ArrayList<Track> getTrackSearch(String searchQuery){
        ArrayList<Track> tracks = new ArrayList<>();
        try {

            //Connection
            conn = DriverManager.getConnection(URL);

            PreparedStatement preparedStatement =
                    conn.prepareStatement("""
                                             SELECT Track.Name as Track, Album.Title as Album, Artist.Name as Artist, Genre.Name as Genre
                                             FROM Track
                                                      INNER JOIN Album, Artist, Genre
                                                      WHERE Track.AlbumId == Album.AlbumId
                                                       AND Album.ArtistId == Artist.ArtistId
                                                       AND Track.GenreId == Genre.GenreId
                                                       AND Track.Name LIKE ?""");

            preparedStatement.setString(1, '%' + searchQuery + '%');
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                tracks.add(
                        new Track(
                                resultSet.getString("Track"),
                                resultSet.getString("Artist"),
                                resultSet.getString("Album"),
                                resultSet.getString("Genre")
                        )
                );
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        finally {
            try {
                conn.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return tracks;
    }


    //The 3 functions below are very alike, and could have been made to one function
    //by string concatenation of the table name, this is bad form as we dont want to spread out expression
    //the statement could also have been passed from the controller, this is also bad form as we do not want
    //sql in the controller

    public ArrayList<String> getRandomArtist(int number){
        ArrayList<String> artists = new ArrayList<>();
        try {
            //connection
            conn = DriverManager.getConnection(URL);

            PreparedStatement preparedStatement =
                    conn.prepareStatement("""
                            SELECT Name FROM Artist
                            ORDER BY random()
                            LIMIT ?;""");
            preparedStatement.setInt(1, number);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                artists.add(
                        resultSet.getString("Name")
                );
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        finally {
            try {
                conn.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return artists;
    }

    public ArrayList<String> getRandomTrack(int number){
        ArrayList<String> tracks = new ArrayList<>();
        try {
            //connection
            conn = DriverManager.getConnection(URL);

            PreparedStatement preparedStatement =
                    conn.prepareStatement("""
                            SELECT Name FROM Track
                            ORDER BY random()
                            LIMIT ?;""");
            preparedStatement.setInt(1, number);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                tracks.add(
                        resultSet.getString("Name")
                );
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        finally {
            try {
                conn.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return tracks;
    }

    public ArrayList<String> getRandomGenre(int number){
        ArrayList<String> genres = new ArrayList<>();
        try {
            //connection
            conn = DriverManager.getConnection(URL);

            PreparedStatement preparedStatement =
                    conn.prepareStatement("""
                            SELECT Name FROM Genre
                            ORDER BY random()
                            LIMIT ?;""");
            preparedStatement.setInt(1, number);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                genres.add(
                        resultSet.getString("Name")
                );
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        finally {
            try {
                conn.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return genres;
    }

}
