package com.ritik.musicar.Musicar.Models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Playlist")
public class Playlist {
    @Id
    @Column(name = "playlist_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String playlistId;

    @Column(name = "playlist_name")
    private String name;

    @Column(name = "created_date")
    private Date createdDate;

    public Playlist() {}

    public Playlist(String name, Date createdDate) {
        this.name = name;
        this.createdDate = createdDate;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
