package com.ritik.musicar.Musicar.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "Track_Genre")
public class TrackGenre {
    @Id
    @Column(name = "track_id", length = 36)
    private String trackId;

    @Column(name = "genre_id", length = 36)
    private String genreId;

    @ManyToOne
    @JoinColumn(name="track_id", referencedColumnName = "track_id", insertable=false, updatable=false)
    private Track track;

    @ManyToOne
    @JoinColumn(name="genre_id", referencedColumnName = "genre_id", insertable=false, updatable=false)
    private Genre genre;

    public TrackGenre() {
    }
    public TrackGenre(String trackId, String genreId) {
        this.trackId = trackId;
        this.genreId = genreId;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getGenreId() {
        return genreId;
    }

    public void setGenreId(String genreId) {
        this.genreId = genreId;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
