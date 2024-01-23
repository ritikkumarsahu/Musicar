package com.ritik.musicar.Musicar.Services;

import com.ritik.musicar.Musicar.Models.Album;
import com.ritik.musicar.Musicar.Repository.AlbumRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AlbumService {
    private final AlbumRepository albumRepository;

    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }
    public Album saveAlbum(Album album) {
        return albumRepository.save(album);
    }
    public Iterable<Album> saveAlbums(Iterable<Album> albums) {
        return albumRepository.saveAll(albums);
    }
    public Album getAlbumById(String id) {
        return albumRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }
    public Iterable<Album> getAlbums() {
        return albumRepository.findAll();
    }
    public Iterable<Album> getAlbumsByTitle(String title) {
        return albumRepository.findAllByTitle(title);
    }
    public Album updateAlbum(Album album) {
        Album oldAlbum = albumRepository.findById(album.getAlbumId()).orElseThrow(() -> new EntityNotFoundException(String.valueOf(album.getAlbumId())));
        oldAlbum.setArtistId(album.getArtistId());
        oldAlbum.setTitle(album.getTitle());
        return albumRepository.save(oldAlbum);
    }

    public void deleteAlbum(String albumId) {
        albumRepository.deleteById(albumId);
    }

}
