CREATE TABLE `score` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `school_id` INT UNSIGNED NOT NULL,
  `name` varchar(2000) NOT NULL,
  `batch_name` varchar(200),
  `type_name` varchar(200) NOT NULL,
  `min` int NOT NULL,
  `rank` int NOT NULL,
  `year` int NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `school_id` (`school_id` ASC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


select sl.name,left(sc.name,20),sc.`min`,sc.`rank`,sc.year from score sc inner join school sl on sc.schoolid where year = 2019 order by `rank` asc limit 100;