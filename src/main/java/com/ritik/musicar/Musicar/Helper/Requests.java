package com.ritik.musicar.Musicar.Helper;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.text.StringEscapeUtils;

public final class Requests {
    private static final HttpClient httpClient = HttpClient.newHttpClient();



    public static JsonNode http(String baseURL, String key, boolean isVersion4, Map<String, Object> query) throws Exception {
        Map<String, Object> queryParams = new HashMap<String, Object>();
        if (isVersion4) {
            queryParams.put("api_version", 4);
        }
        queryParams.put("_format", "json");
        queryParams.put("_marker", "0");
        queryParams.put("ctx", "web6dot0");
        queryParams.putAll(query);

        URI uri = URI.create(baseURL + "?" + buildQueryString("__call", key, queryParams));
        HttpRequest request = HttpRequest.newBuilder(uri)
                .header("cookie", "L=english; gdpr_acceptance=true; DL=english")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String decodedResponse = response.body();
        return new ObjectMapper().readTree(decodedResponse);
    }

    private static String buildQueryString(String key, String value, Map<String, Object> queryParams) throws UnsupportedEncodingException {
        StringBuilder query = new StringBuilder(key + "=" + URLEncoder.encode(value, "UTF-8"));
        for (Map.Entry<String, Object> entry : queryParams.entrySet()) {
            String paramKey = entry.getKey();
            Object paramValue = entry.getValue();
            query.append("&" + paramKey + "=" + URLEncoder.encode(paramValue.toString(), "UTF-8"));
        }
        return query.toString();
    }

}
