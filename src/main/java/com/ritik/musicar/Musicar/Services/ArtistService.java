package com.ritik.musicar.Musicar.Services;

import com.ritik.musicar.Musicar.Models.Artist;
import com.ritik.musicar.Musicar.Repository.ArtistRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ArtistService {
    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }
    public Artist saveArtist(Artist artist) {
        return artistRepository.save(artist);
    }
    public Iterable<Artist> saveArtists(Iterable<Artist> artists) {
        return artistRepository.saveAll(artists);
    }
    public Artist getArtistById(String id) {
        return artistRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }
    public Iterable<Artist> getArtists() {
        return artistRepository.findAll();
    }
    public Iterable<Artist> getArtistsByName(String name) {
        return artistRepository.findAllByName(name);
    }
    public Artist updateArtist(Artist artist) {
        Artist oldArtist= artistRepository.findById(artist.getArtistId()).orElseThrow(() -> new EntityNotFoundException(String.valueOf(artist.getArtistId())));
        oldArtist.setName(artist.getName());
        return artistRepository.save(oldArtist);
    }

    public void deleteArtist(String artistId) {
        artistRepository.deleteById(artistId);
    }
}
