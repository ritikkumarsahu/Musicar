package com.ritik.musicar.Musicar.Models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "Artist")
public class Artist {
    @Id
    @Column(name = "artist_id", length = 36)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String artistId;
    @Column(name = "name", length = 255)
    private String name;

    public Artist() {
        this.artistId = UUID.randomUUID().toString();
    }

    public Artist(String artistId, String name) {
        this.artistId = artistId;
        this.name = name;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
