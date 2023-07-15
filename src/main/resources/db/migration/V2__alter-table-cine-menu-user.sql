CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
ALTER TABLE cine_menu_user DROP CONSTRAINT cine_menu_user_pkey;

ALTER TABLE cine_menu_user DROP COLUMN id;

ALTER TABLE cine_menu_user ADD COLUMN id VARCHAR(255) PRIMARY KEY;