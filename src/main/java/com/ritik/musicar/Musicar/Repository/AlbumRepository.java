package com.ritik.musicar.Musicar.Repository;

import com.ritik.musicar.Musicar.Models.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AlbumRepository extends CrudRepository<Album, String> {
    Iterable<Album> findAllByTitle(String title);
}
