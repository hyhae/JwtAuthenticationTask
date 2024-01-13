CREATE TABLE IF NOT EXISTS `admin` (
    `admin_id`         INTEGER  PRIMARY KEY AUTO_INCREMENT,
    `username` VARCHAR(150) NOT NULL,
    `password` VARCHAR(70) NOT NULL
);

CREATE TABLE IF NOT EXISTS `user` (
   `user_id`         INTEGER  PRIMARY KEY AUTO_INCREMENT,
   `username` VARCHAR(150) NOT NULL,
   `email` VARCHAR(250) NOT NULL
);

CREATE SEQUENCE hibernate_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE token (
  id INT NOT NULL,
   token VARCHAR(255),
   token_type VARCHAR(255),
   expired BOOLEAN,
   revoked BOOLEAN,
   admin_id INT,
   CONSTRAINT pk_token PRIMARY KEY (id)
);

ALTER TABLE token ADD CONSTRAINT FK_TOKEN_ON_ADMIN FOREIGN KEY (admin_id) REFERENCES admin (admin_id);
