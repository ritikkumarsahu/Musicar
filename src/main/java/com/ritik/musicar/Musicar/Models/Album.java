package com.ritik.musicar.Musicar.Models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "Album")
public class Album {
    @Id
    @Column(name = "album_id", length = 36)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String albumId;
    @Column(name = "title", length = 255)
    private String title;
    @Column(name = "artist_id", length = 36)
    private String artistId;

    public Album() {
        this.albumId = UUID.randomUUID().toString();
    }

    public Album(String albumId, String title, String artist_id) {
        this.albumId = albumId;
        this.title = title;
        this.artistId = artist_id;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String album_id) {
        this.albumId = album_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }
}
