package com.ritik.musicar.Musicar;

import com.fasterxml.jackson.databind.JsonNode;
import com.ritik.musicar.Musicar.Helper.JsonFormat;
import com.ritik.musicar.Musicar.Models.Album;
import com.ritik.musicar.Musicar.Models.Artist;
import com.ritik.musicar.Musicar.Repository.AlbumRepository;
import com.ritik.musicar.Musicar.Services.JioSaavnService;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Iterator;

@SpringBootApplication
public class MusicarApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext app =  SpringApplication.run(MusicarApplication.class, args);

//		String playlistId = service.getPlaylistId("https://www.jiosaavn.com/featured/weekly-top-songs/8MT-LQlP35c_");
//		System.out.println("playlist id: "+ playlistId);
//		String playlist = service.getPlaylist(playlistId).toPrettyString();
//		System.out.println(playlist);
		String urrrl = JsonFormat.decryptUrl("ID2ieOjCrwfgWvL5sXl4B1ImC5QfbsDyDSJHg46qOzoFAnJTf5nY97wQY75zNxkLAw4mVkueggY0hRwYJ4PwTBw7tS9a8Gtq");
		System.out.println(urrrl);
	}
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Value("${frontend.url}")
			private String frontendUrl;
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/**").allowedOrigins(frontendUrl);
			}
		};
	}
}
