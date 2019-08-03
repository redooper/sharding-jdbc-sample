CREATE DATABASE ds0 DEFAULT CHARSET utf8mb4;

USE ds0;

CREATE TABLE `t_foo` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL COMMENT '姓名',
  `age` tinyint(3) unsigned NOT NULL COMMENT '年龄',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

CREATE TABLE `t_user_00` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL COMMENT '姓名',
  `age` tinyint(3) unsigned NOT NULL COMMENT '年龄',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

CREATE TABLE t_user_01 LIKE t_user_00;
CREATE TABLE t_user_02 LIKE t_user_00;
CREATE TABLE t_user_03 LIKE t_user_00;
CREATE TABLE t_user_04 LIKE t_user_00;
CREATE TABLE t_user_05 LIKE t_user_00;
CREATE TABLE t_user_06 LIKE t_user_00;
CREATE TABLE t_user_07 LIKE t_user_00;
CREATE TABLE t_user_08 LIKE t_user_00;
CREATE TABLE t_user_09 LIKE t_user_00;
CREATE TABLE t_user_10 LIKE t_user_00;
CREATE TABLE t_user_11 LIKE t_user_00;
CREATE TABLE t_user_12 LIKE t_user_00;
CREATE TABLE t_user_13 LIKE t_user_00;
CREATE TABLE t_user_14 LIKE t_user_00;
CREATE TABLE t_user_15 LIKE t_user_00;
CREATE TABLE t_user_16 LIKE t_user_00;
CREATE TABLE t_user_17 LIKE t_user_00;
CREATE TABLE t_user_18 LIKE t_user_00;
CREATE TABLE t_user_19 LIKE t_user_00;
CREATE TABLE t_user_20 LIKE t_user_00;
CREATE TABLE t_user_21 LIKE t_user_00;
CREATE TABLE t_user_22 LIKE t_user_00;
CREATE TABLE t_user_23 LIKE t_user_00;
CREATE TABLE t_user_24 LIKE t_user_00;
CREATE TABLE t_user_25 LIKE t_user_00;
CREATE TABLE t_user_26 LIKE t_user_00;
CREATE TABLE t_user_27 LIKE t_user_00;
CREATE TABLE t_user_28 LIKE t_user_00;
CREATE TABLE t_user_29 LIKE t_user_00;
CREATE TABLE t_user_30 LIKE t_user_00;
CREATE TABLE t_user_31 LIKE t_user_00;
CREATE TABLE t_user_32 LIKE t_user_00;
CREATE TABLE t_user_33 LIKE t_user_00;
CREATE TABLE t_user_34 LIKE t_user_00;
CREATE TABLE t_user_35 LIKE t_user_00;
CREATE TABLE t_user_36 LIKE t_user_00;
CREATE TABLE t_user_37 LIKE t_user_00;
CREATE TABLE t_user_38 LIKE t_user_00;
CREATE TABLE t_user_39 LIKE t_user_00;
CREATE TABLE t_user_40 LIKE t_user_00;
CREATE TABLE t_user_41 LIKE t_user_00;
CREATE TABLE t_user_42 LIKE t_user_00;
CREATE TABLE t_user_43 LIKE t_user_00;
CREATE TABLE t_user_44 LIKE t_user_00;
CREATE TABLE t_user_45 LIKE t_user_00;
CREATE TABLE t_user_46 LIKE t_user_00;
CREATE TABLE t_user_47 LIKE t_user_00;
CREATE TABLE t_user_48 LIKE t_user_00;
CREATE TABLE t_user_49 LIKE t_user_00;
CREATE TABLE t_user_50 LIKE t_user_00;
CREATE TABLE t_user_51 LIKE t_user_00;
CREATE TABLE t_user_52 LIKE t_user_00;
CREATE TABLE t_user_53 LIKE t_user_00;
CREATE TABLE t_user_54 LIKE t_user_00;
CREATE TABLE t_user_55 LIKE t_user_00;
CREATE TABLE t_user_56 LIKE t_user_00;
CREATE TABLE t_user_57 LIKE t_user_00;
CREATE TABLE t_user_58 LIKE t_user_00;
CREATE TABLE t_user_59 LIKE t_user_00;
CREATE TABLE t_user_60 LIKE t_user_00;
CREATE TABLE t_user_61 LIKE t_user_00;
CREATE TABLE t_user_62 LIKE t_user_00;
CREATE TABLE t_user_63 LIKE t_user_00;
