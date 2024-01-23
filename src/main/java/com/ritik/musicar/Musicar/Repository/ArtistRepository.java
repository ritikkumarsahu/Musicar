package com.ritik.musicar.Musicar.Repository;

import com.ritik.musicar.Musicar.Models.Artist;
import org.springframework.data.repository.CrudRepository;

public interface ArtistRepository extends CrudRepository<Artist, String> {

    Iterable<Artist> findAllByName(String name);
}
