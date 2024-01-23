package com.ritik.musicar.Musicar.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "Genre")
public class Genre {

    @Id
    @Column(name = "genre_id", length = 36)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String genreId;

    @Column(name = "name", length = 255)
    private String name;

    public Genre() {}

    public Genre(String genreId, String name) {
        this.genreId = genreId;
        this.name = name;
    }

    public String getGenreId() {
        return genreId;
    }

    public void setGenreId(String genreId) {
        this.genreId = genreId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
