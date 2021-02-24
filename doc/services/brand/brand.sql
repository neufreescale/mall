CREATE TABLE `base`.`brand` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(55) NOT NULL,
  `logo` VARCHAR(64) NOT NULL,
  `certificate_code` VARCHAR(55) NULL,
  `desc` VARCHAR(350) NULL,
  `status` TINYINT(1) NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  INDEX `idx_status_name` (`status` ASC, `name` ASC),
  INDEX `idx_status_cert` (`status` ASC, `certificate_code` ASC));