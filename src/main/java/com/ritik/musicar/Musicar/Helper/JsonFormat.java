package com.ritik.musicar.Musicar.Helper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class JsonFormat {
    private static final String IMAGE_URL_FIELD_NAME = "image";
    public static JsonNode formatSong(JsonNode data) {
        ((ObjectNode)data).put("media_url", decryptUrl(data.get("encrypted_media_url").asText()));
        if (!"true".equals(data.get("320kbps").asText())) {
            ((ObjectNode)data).put("media_url", data.get("media_url").asText().replace("_320.mp4","_160.mp4"));
        }

        ((ObjectNode)data).put("song", format(data.get("song").asText()));
        ((ObjectNode)data).put("music", format(data.get("music").asText()));
        ((ObjectNode)data).put("singers", format(data.get("singers").asText()));
        ((ObjectNode)data).put("starring", format(data.get("starring").asText()));
        ((ObjectNode)data).put("album", format(data.get("album").asText()));
        ((ObjectNode)data).put("primary_artists", format(data.get("primary_artists").asText()));
        ((ObjectNode)data).put("image", data.get("image").asText().replace("150x150","500x500"));

        try {
            ((ObjectNode)data).put("copyright_text",
                    data.get("copyright_text").asText().replaceAll("&copy;", "©"));
        } catch (NullPointerException e) {
            // Do nothing
        }
        return data;
    }
    public static JsonNode formatAlbum(JsonNode data) {
        String image = data.get("image").asText().replace("150x150", "500x500");
        String name = format(data.get("name").asText());
        String primaryArtists = format(data.get("primary_artists").asText());
        String title = format(data.get("title").asText());
        JsonNode songs = data.get("songs");
        for (JsonNode song : songs) {
            formatSong(song);
        }
        ((ObjectNode) data).put("image", image);
        ((ObjectNode) data).put("name", name);
        ((ObjectNode) data).put("primary_artists", primaryArtists);
        ((ObjectNode) data).put("title", title);
        return data;
    }
    public static JsonNode formatPlaylist(JsonNode data) {
        ((ObjectNode) data).put("firstname", data.get("firstname").asText());
        ((ObjectNode) data).put("listname", data.get("listname").asText());
        ArrayNode songs = (ArrayNode) data.get("songs");
        for (int i = 0; i < songs.size(); i++) {
            JsonNode song = songs.get(i);
            JsonNode formattedSong = formatSong(song);
            songs.set(i, formattedSong);
        }
        return data;
    }

    public static JsonNode replaceImageUrl(JsonNode node) {
        if (node.isArray()) {
            ArrayNode arrayNode = (ArrayNode) node;
            for (int i = 0; i < arrayNode.size(); i++) {
                JsonNode childNode = arrayNode.get(i);
                if (!childNode.has("songs")) {
                    arrayNode.set(i, replaceImageUrl(childNode));
                }
            }
            return arrayNode;
        } else if (node.isObject()) {
            ObjectNode objectNode = (ObjectNode) node;
            objectNode.fields().forEachRemaining(entry -> {
                String fieldName = entry.getKey();
                JsonNode fieldValue = entry.getValue();
                if (!fieldName.equals("songs")) {
                    objectNode.set(fieldName, replaceImageUrl(fieldValue));
                }
            });

            if (objectNode.has(IMAGE_URL_FIELD_NAME)) {
                String imageUrl = objectNode.get(IMAGE_URL_FIELD_NAME).asText();
                imageUrl = imageUrl.replace("50x50", "500x500");
                objectNode.put(IMAGE_URL_FIELD_NAME, imageUrl);
            }
            return objectNode;
        } else {
            return node;
        }
    }
    public static String decryptUrl(String url) {
        final String KEY = "38346591";
        final String CIPHER_INSTANCE = "DES/ECB/PKCS5Padding";
        final String ENCODING = "UTF-8";
        System.out.println("encrypted url:" + url);
        byte[] encrypted = Base64.decodeBase64(url);

        try {
            Cipher cipher = Cipher.getInstance(CIPHER_INSTANCE);
            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes("UTF-8"), "DES");

            cipher.init(Cipher.DECRYPT_MODE, keySpec);

            byte[] decryptedLink = cipher.doFinal(encrypted);
            String result = new String(decryptedLink, ENCODING);
            System.out.println("decrepted url:" + result);
            return result.replace("_96.mp4", "_320.mp4");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static JsonNode formatDiscover(JsonNode data) {
        if (data instanceof ObjectNode) {
            ObjectNode object = (ObjectNode) data;
            object.remove("radio");
            object.remove("browse_discover");
            object.remove("top_shows");
        }
        JsonNode newAlbumsNode = data.get("new_albums");
        for (JsonNode nd : newAlbumsNode) {
            StringBuilder sb = new StringBuilder();
            JsonNode musicNode = nd.get("Artist").get("music");
            for (JsonNode music : musicNode) {
                sb.append(music.get("name").asText());
                sb.append(", ");
            }
            String names = sb.toString().trim().replaceAll(",$", "");
            ObjectNode object = (ObjectNode) nd;
            object.remove("Artist");
            object.put("description", names);
            object.put("id", object.get("albumid"));
            object.remove("albumid");
        }
        JsonNode newTrendingNode = data.get("new_trending");
        for (JsonNode nd : newTrendingNode) {
            if(nd.get("type").asText().equals("album")) {
                String names = nd.at("/details/artist/name").asText();
                ObjectNode object = (ObjectNode) nd.get("details");
                object.remove("artist");
                object.put("description", names);
                object.put("id", object.get("albumid"));
                object.remove("albumid");
                object.put("url", object.get("perma_url"));
                object.remove("perma_url");
            }
        }
        JsonNode topPlaylistsNode = data.get("top_playlists");
        for (JsonNode nd : topPlaylistsNode) {
            ((ObjectNode) nd).put("id", nd.get("listid"));
            ((ObjectNode) nd).remove("listid");
            ((ObjectNode) nd).put("title", nd.get("listname"));
            ((ObjectNode) nd).remove("listname");
            ((ObjectNode) nd).put("description", nd.get("firstname"));
            ((ObjectNode) nd).remove("firstname");
            ((ObjectNode) nd).put("url", nd.get("perma_url"));
            ((ObjectNode) nd).remove("perma_url");
        }

        JsonNode chartsNode = data.get("charts");
        for (JsonNode nd : chartsNode) {
            if (nd.get("type").asText().equals("playlist")) {
                ((ObjectNode) nd).put("url", nd.get("perma_url"));
                ((ObjectNode) nd).remove("perma_url");
                ((ObjectNode) nd).put("description", nd.get("more_info").get("firstname"));
                ((ObjectNode) nd).remove("more_info");
            }
        }

        return data;
    }
    public static String format(String string) {
        String formattedString = string
                .replaceAll("&quot;", "'")
                .replaceAll("&amp;", "&")
                .replaceAll("&#039;", "'");
        formattedString = StringEscapeUtils.unescapeJava(formattedString);
        return formattedString;
    }
}
