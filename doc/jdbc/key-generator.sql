CREATE TABLE `sequence` (
  `name` varchar(128) NOT NULL,
  `value` bigint(20) NOT NULL,
  `gmt_modified` datetime NOT NULL,
  UNIQUE KEY `name_idx` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8