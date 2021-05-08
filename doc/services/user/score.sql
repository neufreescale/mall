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

update school set sync=0 where sync=1;
-- http://localhost:8082/school/syncAll?source_=base&t_=test&year=2017
select sl.name as 学校,sl.f985 as 是否985,sl.f211 as 是否211,left(sc.name,20) as 专业,sc.`min` as 分数,sc.`rank` as 排名,sc.year as 年份 from score sc inner join school sl on sc.school_id=sl.id where year = 2020 order by `rank` asc limit 100;