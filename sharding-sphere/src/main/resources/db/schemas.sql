-- 默认数据库
CREATE SCHEMA `sharding` DEFAULT CHARACTER SET utf8 ;

create table t_user(
	id int primary key auto_increment,
    name varchar(20) not null,
    username varchar(20) not null,
    password varchar(32) not null,
    status char(1) not null,
    create_time datetime
)

-- 分库0
CREATE SCHEMA `sharding0` DEFAULT CHARACTER SET utf8 ;

-- 分库1
CREATE SCHEMA `sharding1` DEFAULT CHARACTER SET utf8 ;

-- 分库下的分表，每个分库都有
CREATE TABLE `sys_log_202007` (
  `id` BIGINT UNSIGNED NOT NULL,
  `value` int NOT NULL,
  `operation_id` int NOT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
);


CREATE TABLE `sys_log_202008` (
  `id` BIGINT UNSIGNED NOT NULL,
  `value` int NOT NULL,
  `operation_id` int NOT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
);


CREATE TABLE `sys_log_202009` (
  `id` BIGINT UNSIGNED NOT NULL,
  `value` int NOT NULL,
  `operation_id` int NOT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
);


CREATE TABLE `sys_log_202010` (
  `id` BIGINT UNSIGNED NOT NULL,
  `value` int NOT NULL,
  `operation_id` int NOT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
);