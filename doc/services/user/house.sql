CREATE TABLE `house` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `building_num` SMALLINT NOT NULL,
  `location` varchar(200) NOT NULL,
  `house_count` SMALLINT NOT NULL,
  `unit` SMALLINT NOT NULL,
  `floor` SMALLINT NOT NULL,
  `house_num` SMALLINT NOT NULL,
  `area` FLOAT NOT NULL,
  `inner_area` FLOAT NOT NULL,
  `area_percent` FLOAT NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;