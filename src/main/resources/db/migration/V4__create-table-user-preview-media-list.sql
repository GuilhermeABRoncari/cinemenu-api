CREATE TABLE user_media_list (
   id VARCHAR(255) PRIMARY KEY,
   title VARCHAR(255),
   description VARCHAR(255),
   visibility VARCHAR(14),
   amount_like BIGINT,
   amount_copy BIGINT,
   created_at TIMESTAMP,
   user_id VARCHAR(255) REFERENCES cine_menu_user (id)
);

UPDATE user_media_list SET created_at = NOW();

ALTER TABLE cine_menu_user ADD COLUMN media_list_id VARCHAR(255) REFERENCES user_media_list (id);
