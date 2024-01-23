package com.ritik.musicar.Musicar.Models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "Track")
public class Track {
    @Id
    @Column(name = "track_id", length = 36)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String trackId;

    @Column(name = "title", length = 255)
    private String title;

    @Column(name = "album_id", length = 36)
    private String albumId;

    @Column(name = "duration")
    private int duration;

    @Column(name = "release_date")
    private Date releaseDate;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id", referencedColumnName = "album_id", insertable = false, updatable = false)
    private Album album;

    public Track() {
        this.trackId = UUID.randomUUID().toString();
    }

    public Track(String trackId, String title, String albumId, int duration, Date releaseDate, String description, String imageUrl) {
        this.trackId = trackId;
        this.title = title;
        this.albumId = albumId;
        this.duration = duration;
        this.releaseDate = releaseDate;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
