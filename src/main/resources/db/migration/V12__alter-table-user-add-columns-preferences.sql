CREATE TABLE user_profile_genre_preferences (
    cine_menu_user_id VARCHAR(255) REFERENCES cine_menu_user (id),
    genre_preference VARCHAR(32)
);

CREATE TABLE user_profile_media_references (
    cine_menu_user_id VARCHAR(255) REFERENCES cine_menu_user (id),
    media_id BIGINT,
    media_type VARCHAR(5)
);