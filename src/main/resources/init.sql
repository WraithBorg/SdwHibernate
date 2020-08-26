-- 初始化数据库sql
DROP DATABASE IF EXISTS sampledb;

CREATE DATABASE sampledb DEFAULT CHARACTER SET UTF8;

USE sampledb;

CREATE TABLE t_user (
	user_id INT AUTO_INCREMENT PRIMARY KEY,
	user_name VARCHAR(30),
	credits INT,
	password  VARCHAR(32),
	last_visit datetime,
	last_ip VARCHAR(23)
)ENGINE = INNODB;

CREATE TABLE t_log (
	id INT AUTO_INCREMENT PRIMARY KEY,
	user_id INT,
	ip VARCHAR(23) ,
	login_datetime datetime
)ENGINE = INNODB;
