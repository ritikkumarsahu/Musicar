package com.ritik.musicar.Musicar.Controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.ritik.musicar.Musicar.Services.AlbumService;
import com.ritik.musicar.Musicar.Services.JioSaavnService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    private final JioSaavnService jioSaavnService;

    public HomeController(JioSaavnService jioSaavnService) {
        this.jioSaavnService = jioSaavnService;
    }

    @GetMapping
    public String index(Model model) {
        String hotAlbumId = jioSaavnService.getAlbumId("https://www.jiosaavn.com/album/pathaan/kw5AWtM1BZk_");
        JsonNode hotAlbum = jioSaavnService.getAlbum(hotAlbumId);
        model.addAttribute("hotAlbum", hotAlbum);

        String topWeeklySongsId = jioSaavnService.getPlaylistId("https://www.jiosaavn.com/featured/weekly-top-songs/8MT-LQlP35c_");
        ArrayNode topWeeklySongs = (ArrayNode) jioSaavnService.getPlaylist(topWeeklySongsId).get("songs");
        ObjectMapper mapper = new ObjectMapper();
        List<ArrayNode> topWeeklySongsModel = new ArrayList<>();
        Iterator<JsonNode> topWeeklySongsIterator = topWeeklySongs.iterator();
        while (topWeeklySongsIterator.hasNext()) {
            ArrayNode songsFive = mapper.createArrayNode();
            for (int i = 0; i < 5 && topWeeklySongsIterator.hasNext(); i++) {
                songsFive.add(topWeeklySongsIterator.next());
            }
            topWeeklySongsModel.add(songsFive);
        }
        System.out.println(hotAlbum.toPrettyString());
        model.addAttribute("topWeeklySongs", topWeeklySongsModel);
        return "index";
    }
}
