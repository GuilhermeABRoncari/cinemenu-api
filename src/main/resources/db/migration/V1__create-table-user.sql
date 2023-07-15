CREATE TABLE cine_menu_user (
   id SERIAL PRIMARY KEY,
   name VARCHAR(80) NOT NULL,
   username VARCHAR(16) NOT NULL UNIQUE,
   email VARCHAR(80) NOT NULL UNIQUE,
   password VARCHAR(255) NOT NULL,
   registration_date TIMESTAMP NOT NULL
);