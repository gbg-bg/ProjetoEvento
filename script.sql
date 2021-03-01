CREATE DATABASE IF NOT EXISTS `eventdatabase`;

USE `eventdatabase`;

CREATE TABLE IF NOT EXISTS `participant` (
   `id` INT NOT NULL PRIMARY KEY,
   `name` VARCHAR(150) NOT NULL,
   `surname` VARCHAR(150) NOT NULL,
   `coffeeroom_id` INT NOT NULL
);

CREATE TABLE IF NOT EXISTS `trainningroom_participant` (
   `participant_id` INT NOT NULL,
   `trainningroom_id` INT NOT NULL,
   `stage` INT NOT NULL
);

CREATE TABLE IF NOT EXISTS `trainningroom` (
   `id` INT NOT NULL PRIMARY KEY,
   `name` VARCHAR(150) NOT NULL,
   `capacity` INT(8) NOT NULL
);

CREATE TABLE IF NOT EXISTS `coffeeroom` (
   `id` INT NOT NULL PRIMARY KEY,
   `name` VARCHAR(150) NOT NULL,
   `capacity` INT(8) NOT NULL
);

ALTER TABLE trainningroom_participant ADD FOREIGN KEY (participant_id) REFERENCES participant(id);

ALTER TABLE trainningroom_participant ADD FOREIGN KEY (trainningroom_id) REFERENCES trainningroom(id);

ALTER TABLE participant ADD FOREIGN KEY (coffeeroom_id) REFERENCES coffeeroom(id);