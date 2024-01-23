package com.ritik.musicar.Musicar.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "Playlist_Track")
public class PlaylistTrack {

    @Id
    @ManyToOne
    @JoinColumn(name="playlist_id", referencedColumnName = "playlist_id")
    private Playlist playlist;

    @Id
    @ManyToOne
    @JoinColumn(name="track_id", referencedColumnName = "track_id")
    private Track track;

    // Constructor
    public PlaylistTrack() {
    }

    public PlaylistTrack(Playlist playlist, Track track) {
        this.playlist = playlist;
        this.track = track;
    }
}