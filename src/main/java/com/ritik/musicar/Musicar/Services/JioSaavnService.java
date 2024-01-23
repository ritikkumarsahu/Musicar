package com.ritik.musicar.Musicar.Services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ritik.musicar.Musicar.Helper.JsonFormat;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

import static com.ritik.musicar.Musicar.Helper.Requests.http;


@Service
public class JioSaavnService {
    private final HttpClient client;
    private final HttpRequest.Builder requestBuilder;

    @Value("${baseURL}")
    private String baseURL;
    @Value("${endpoint_modules}")
    private String endpoint_modules;
    @Value("${endpoint_search_all}")
    private String endpoint_search_all;
    @Value("${endpoint_search_songs}")
    private String endpoint_search_songs;
    @Value("${endpoint_search_albums}")
    private String endpoint_search_albums;
    @Value("${endpoint_search_artists}")
    private String endpoint_search_artists;
    @Value("${endpoint_search_playlists}")
    private  String endpoint_search_playlists;
    @Value("${endpoint_songs_id}")
    private  String endpoint_songs_id;
    @Value("${endpoint_songs_link}")
    private  String endpoint_songs_link;
    @Value("${endpoint_albums_id}")
    private  String endpoint_albums_id;
    @Value("${endpoint_albums_link}")
    private  String endpoint_albums_link;
    @Value("${endpoint_playlists_id}")
    private  String endpoint_playlists_id;
    @Value("${endpoint_artists_id}")
    private  String endpoint_artists_id;
    @Value("${endpoint_artists_link}")
    private  String endpoint_artists_link;
    @Value("${endpoint_artists_songs}")
    private  String endpoint_artists_songs;
    @Value("${endpoint_artists_albums}")
    private  String endpoint_artists_albums;
    @Value("${endpoint_artists_topSongs}")
    private  String endpoint_artists_topSongs;
    @Value("${endpoint_lyrics}")
    private  String endpoint_lyrics;

    public JioSaavnService(HttpClient client, HttpRequest.Builder requestBuilder) {
        this.client = client;
        this.requestBuilder = requestBuilder;
    }

    public JioSaavnService() {
        this.client = HttpClient.newBuilder().build();
        this.requestBuilder = HttpRequest.newBuilder();
    }

    private String getHtmlPage(String url) {
        HttpRequest request = requestBuilder
                .GET()
                .uri(URI.create(url))
                .header("Content-Type", "text/html; charset=utf-8")
                .header("User-Agent", "Mozilla/5.0")
                .build();

        try {
            String response = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
            return response;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public JsonNode search(String query){
        JsonNode response = null;
        try {
            Map<String, Object> q = new HashMap<String, Object>();
            q.put("query",URLEncoder.encode(query, "UTF-8"));
            JsonNode rawResponse = http(baseURL, endpoint_search_all, false, q);
            response = JsonFormat.replaceImageUrl(rawResponse);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            JsonNode songsData = response.get("songs").get("data");
            Iterator<JsonNode> elements = songsData.elements();
            ArrayNode songs = JsonNodeFactory.instance.arrayNode();
            while(elements.hasNext()){
                JsonNode song = elements.next();
                String id = song.get("id").asText();
                Map<String, Object> q = new HashMap<String, Object>();
                q.put("pids",id);
                JsonNode songResponse = http(baseURL, endpoint_songs_id, false, q);
                songs.add(JsonFormat.formatSong(songResponse.get(id)));
            }
            ((ObjectNode) response.get("songs")).put("data", songs);
            return response;
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public JsonNode getSong(String id){
        Map<String, Object> query = new HashMap<String, Object>();
        query.put("pids",id);
        try {
            JsonNode response = http(baseURL, endpoint_songs_id, false, query);
            return JsonFormat.formatSong(response.get(id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public String getSongId(String url) {
        String response = getHtmlPage(url);
        String songId = null;
        try {
            songId = response.split("\"song\":\\{\"status\":\"")[1].split("\",\"has_lyrics\":")[1].split("\"id\":\"")[1].split("\",\"image\":")[0];
        } catch (IndexOutOfBoundsException e) {
            songId = response.split("\"pid\":\"")[1].split("\",\"")[0];
        }
        return songId;
    }

    public JsonNode getAlbum(String id) {
        Map<String, Object> query = new HashMap<String, Object>();
        query.put("albumid",id);
        try {
            JsonNode response = http(baseURL, endpoint_albums_id, false, query);
            return JsonFormat.formatAlbum(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public String getAlbumId(String url) {
        String response = getHtmlPage(url);
        String albumId = null;
        try {
            albumId = response.split("\"album_id\":\"")[1].split("\"")[0];
        } catch (IndexOutOfBoundsException e) {
            albumId = response.split("\"albumid\":\"")[1].split("\"")[0];
        }
        return albumId;
    }

    public JsonNode getPlaylist(String id) {
        Map<String, Object> query = new HashMap<String, Object>();
        query.put("listid",id);
        try {
            JsonNode response = http(baseURL, endpoint_playlists_id, false, query);
            return JsonFormat.formatPlaylist(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public String getPlaylistId(String url) {
        String response = getHtmlPage(url);
        String playlistId = null;
        try {
            playlistId = response.split("\"type\":\"playlist\",\"id\":\"")[1].split("\"")[0];
        } catch (IndexOutOfBoundsException e) {
            playlistId = response.split("\"listid\":\"")[1].split("\"")[0];
        }
        return playlistId;
    }
    public JsonNode getDiscover(String language) {
        Map<String, Object> query = new HashMap<String, Object>();
        query.put("language",language);
        try {
            JsonNode response = http(baseURL, endpoint_modules, false, query);
            return JsonFormat.formatDiscover(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
