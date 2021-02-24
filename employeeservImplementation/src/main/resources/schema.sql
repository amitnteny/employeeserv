DROP SCHEMA IF EXISTS `employeedb` cascade;

CREATE SCHEMA `employeedb`;

use `employeedb`;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `address` cascade;

CREATE TABLE `address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `line_1` varchar(128) NOT NULL,
  `line_2` varchar(128),
  `city` varchar(100) NOT NULL,
  `state` varchar(100) NOT NULL,
  `country` varchar(100) NOT NULL,
  `zip_code` varchar(6) NOT NULL,
  PRIMARY KEY (`id`)
);


DROP TABLE IF EXISTS `employee` cascade;

CREATE TABLE `employee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `date_of_birth` varchar(10) NOT NULL,
  `address_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`address_id`) REFERENCES `address` (`id`)
);

SET FOREIGN_KEY_CHECKS = 1;
