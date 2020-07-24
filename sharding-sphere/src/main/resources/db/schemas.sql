CREATE SCHEMA `sharding0` DEFAULT CHARACTER SET utf8 ;


CREATE SCHEMA `sharding1` DEFAULT CHARACTER SET utf8 ;


CREATE TABLE `sys_log_202007` (
  `id` int NOT NULL AUTO_INCREMENT,
  `value` int NOT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
);


CREATE TABLE `sys_log_202008` (
  `id` int NOT NULL AUTO_INCREMENT,
  `value` int NOT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
);


CREATE TABLE `sys_log_202009` (
  `id` int NOT NULL AUTO_INCREMENT,
  `value` int NOT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
);


CREATE TABLE `sys_log_202010` (
  `id` int NOT NULL AUTO_INCREMENT,
  `value` int NOT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
);