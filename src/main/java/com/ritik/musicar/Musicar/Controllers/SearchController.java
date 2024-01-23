package com.ritik.musicar.Musicar.Controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ritik.musicar.Musicar.Services.JioSaavnService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {
    private JioSaavnService jioSaavnService;

    public SearchController(JioSaavnService jioSaavnService) {
        this.jioSaavnService = jioSaavnService;
    }
    @GetMapping
    public String search(@RequestParam("query")  String query, Model model) {
        JsonNode results = jioSaavnService.search(query);
        ArrayNode albums = ((ArrayNode)  results.get("albums").get("data"));
        ArrayNode songs = ((ArrayNode)  results.get("songs").get("data"));
        ArrayNode playlists = ((ArrayNode)  results.get("playlists").get("data"));
        ArrayNode artists = ((ArrayNode)  results.get("artists").get("data"));
        JsonNode topquery = results.get("topquery").get("data").get(0);
        ((ObjectNode) topquery).put("type", topquery.get("type").asText().toUpperCase());
        List<Integer> arbPos = new ArrayList<>();
        arbPos.add(results.get("albums").get("position").asInt());
        arbPos.add(results.get("songs").get("position").asInt());
        arbPos.add(results.get("playlists").get("position").asInt());
        arbPos.add(results.get("artists").get("position").asInt());
        List<AbstractMap.SimpleEntry<String, ArrayNode>> data = new ArrayList<>();
        for (int i=0; i<4;i++) {
            data.add(new AbstractMap.SimpleEntry<>(String.valueOf(i),JsonNodeFactory.instance.arrayNode()));
        }
        int small = Collections.min(arbPos);
        data.set((arbPos.get(0) -small),new AbstractMap.SimpleEntry<>("ALBUMS",albums));
        data.set((arbPos.get(1) -small),new AbstractMap.SimpleEntry<>("SONGS",songs));
        data.set((arbPos.get(2) -small),new AbstractMap.SimpleEntry<>("PLAYLISTS",playlists));
        data.set((arbPos.get(3) -small),new AbstractMap.SimpleEntry<>("ARTISTS",artists));

        model.addAttribute("data", data);
        model.addAttribute("topquery", topquery);
        return "search";
    }
}
