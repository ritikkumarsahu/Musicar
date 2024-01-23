package com.ritik.musicar.Musicar.Controllers.Rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.ritik.musicar.Musicar.Services.JioSaavnService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiController {
    private final JioSaavnService jioSaavnService;

    public ApiController(JioSaavnService jioSaavnService) {
        this.jioSaavnService = jioSaavnService;
    }
    @GetMapping("/search")
    public JsonNode search(@RequestParam("q")  String query) {
        return jioSaavnService.search(query);
    }

    @GetMapping("/top-weekly-songs")
    public JsonNode topWeeklySongs() {
        String topWeeklySongsId = jioSaavnService.getPlaylistId("https://www.jiosaavn.com/featured/weekly-top-songs/8MT-LQlP35c_");
        return jioSaavnService.getPlaylist(topWeeklySongsId).get("songs");
    }
    @GetMapping("/discover")
    public JsonNode discover() {
        String language = "hindi,english";
        return jioSaavnService.getDiscover(language);
    }
}
