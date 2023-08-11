CREATE TABLE user_media (
   id VARCHAR(255) PRIMARY KEY,
   id_tmdb VARCHAR(255),
   note VARCHAR(255),
   user_rating DOUBLE PRECISION,
   watched BOOLEAN
);

ALTER TABLE user_media_list ADD COLUMN user_media_id VARCHAR(255) REFERENCES user_media(id);