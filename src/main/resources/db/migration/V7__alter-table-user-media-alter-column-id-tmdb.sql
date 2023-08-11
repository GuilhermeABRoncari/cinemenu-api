ALTER TABLE user_media
ADD COLUMN id_tmdb_new BIGINT;

UPDATE user_media
SET id_tmdb_new = CAST(id_tmdb AS BIGINT);

ALTER TABLE user_media
DROP COLUMN id_tmdb;

ALTER TABLE user_media
RENAME COLUMN id_tmdb_new TO id_tmdb;