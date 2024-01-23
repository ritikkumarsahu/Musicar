package com.ritik.musicar.Musicar.Controllers.Rest;

import com.ritik.musicar.Musicar.Models.Album;
import com.ritik.musicar.Musicar.Services.AlbumService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/album")
public class AlbumController{
    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }
    @PostMapping
    public ResponseEntity<Iterable<Album>> addAlbums(@RequestBody Iterable<Album> albums){
        final Iterable<Album> savedAlbums =  albumService.saveAlbums(albums);
        return new ResponseEntity<>(savedAlbums, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Iterable<Album>> getAlbums() {
        final Iterable<Album> albums = albumService.getAlbums();
        return  new ResponseEntity<>(albums, HttpStatus.OK);
    }
    @GetMapping("/{albumId}")
    public ResponseEntity<Album> getAlbumById(@PathVariable String albumId) {
        final Album album = albumService.getAlbumById(albumId);
        return new ResponseEntity<>(album, HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<Album> updateAlbum(@RequestBody Album album){
        final Album newAlbum = albumService.updateAlbum(album);
        return new ResponseEntity<>(newAlbum, HttpStatus.OK);
    }

    @DeleteMapping("/{albumId}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable String albumId){
        albumService.deleteAlbum(albumId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
