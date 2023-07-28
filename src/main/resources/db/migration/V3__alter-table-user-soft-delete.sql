ALTER TABLE cine_menu_user ADD COLUMN deleted BOOLEAN DEFAULT FALSE NOT NULL;
UPDATE cine_menu_user SET deleted = false;
ALTER TABLE cine_menu_user ADD COLUMN deleted_at TIMESTAMP DEFAULT NULL;
UPDATE cine_menu_user SET deleted_at = NULL;