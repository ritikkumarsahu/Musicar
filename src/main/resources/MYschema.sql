CREATE TABLE Artist (
  artist_id VARCHAR(36) PRIMARY KEY,
  name VARCHAR2(255)
);

CREATE TABLE Album (
  album_id VARCHAR(36)  PRIMARY KEY,
  title VARCHAR2(255),
  artist_id VARCHAR(36),
  FOREIGN KEY (artist_id) REFERENCES Artist(artist_id)
);

CREATE TABLE Track (
  track_id VARCHAR(36)  PRIMARY KEY,
  title VARCHAR2(255),
  album_id VARCHAR(36),
  duration INT,
  release_date DATE,
  description VARCHAR2(255),
  image_url VARCHAR2(255),
  FOREIGN KEY (album_id) REFERENCES Album(album_id)
);

CREATE TABLE Genre (
  genre_id VARCHAR(36)  PRIMARY KEY,
  name VARCHAR2(255)
);

CREATE TABLE Track_Genre (
  track_id VARCHAR(36),
  genre_id VARCHAR(36),
  PRIMARY KEY (track_id, genre_id),
  FOREIGN KEY (track_id) REFERENCES Track(track_id),
  FOREIGN KEY (genre_id) REFERENCES Genre(genre_id)
);

CREATE TABLE Playlist (
  playlist_id VARCHAR(36) PRIMARY KEY,
  playlist_name VARCHAR2(255),
  created_date DATE,
  --user_id VARCHAR(36),
  --FOREIGN KEY (user_id) REFERENCES User(user_id)
);

CREATE TABLE Playlist_Track (
  playlist_id VARCHAR(36),
  track_id VARCHAR(36),
  PRIMARY KEY (playlist_id, track_id),
  FOREIGN KEY (playlist_id) REFERENCES Playlist(playlist_id),
  FOREIGN KEY (track_id) REFERENCES Track(track_id)
);

