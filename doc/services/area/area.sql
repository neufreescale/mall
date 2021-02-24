CREATE TABLE `base`.`area` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `code` CHAR(12) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `type` VARCHAR(10) NOT NULL,
  `parent_id` INT UNSIGNED NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uniq_code` (`code` ASC),
  INDEX `idx_parent` (`parent_id` ASC));