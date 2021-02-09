package com.experis.musiclibrary.models;

public class Track {
    private String trackName;
    private String artistName;
    private String albumName;
    private String genre;

    public Track(String trackName, String artistName, String albumName, String genre) {
        this.trackName = trackName;
        this.artistName = artistName;
        this.albumName = albumName;
        this.genre = genre;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }


}
