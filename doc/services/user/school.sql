CREATE TABLE `school` (
  `id` INT UNSIGNED NOT NULL,
  `name` varchar(200) NOT NULL,
  `belong` varchar(50),
  `f985` SMALLINT NOT NULL,
  `f211` SMALLINT NOT NULL,
  `province` varchar(50) NOT NULL,
  `city` varchar(50) NOT NULL,
  `town` varchar(50),
  `address` varchar(300),
  `site` varchar(200),
  `phone` varchar(200),
  `content` text,
  `sync` SMALLINT NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;