/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package betalyricsanalyzer;

import java.io.Serializable;

/**
 *
 * @author landonpalmer
 */

// Object properties:
// title, arist, year, bbposition, lyrics, swear count, swearwords

public class Song implements Serializable{
    
    private String year;
    private String rank;
    private String title;
    private String artist;
    private String lyrics;

    
    

    public Song() {
        year = "";
        rank = "";
        title = "";
        artist = "";
        lyrics = "";
    }

    public Song(String year, String rank, String title, String artist, String lyrics) {
        this.year = year;
        this.rank = rank;
        this.title = title;
        this.artist = artist;
        this.lyrics = lyrics;
    }
    
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
    
    public String getLyrics() {
        return lyrics;
    }
    
    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }
    
    @Override
    public String toString() {
        return "Song{" + "year= " + year + ", rank=" + rank + ", title=" + title + ", artist=" + artist + 
                ", lyrics=" + lyrics + '}';
    }
    
    
}
