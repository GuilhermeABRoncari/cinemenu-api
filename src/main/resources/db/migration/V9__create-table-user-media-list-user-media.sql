CREATE TABLE user_media_list_user_medias (
       media_list_id VARCHAR(255) REFERENCES user_media_list (id),
        user_medias_id VARCHAR(255) REFERENCES user_media (id)
    );