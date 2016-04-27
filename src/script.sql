DROP SCHEMA IF EXISTS solving_tasks;
CREATE SCHEMA IF NOT EXISTS solving_tasks;
USE solving_tasks;

#---------------------------------------------
#--create table users
#---------------------------------------------

DROP TABLE IF EXISTS users;
CREATE TABLE IF NOT EXISTS users (
  id INT NOT NULL UNIQUE AUTO_INCREMENT,
  name VARCHAR(45) NULL,
  surname VARCHAR(45) NULL,
  type INT NOT NULL,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB;

#---------------------------------------------
#--create table orders
#---------------------------------------------

DROP TABLE IF EXISTS orders;
CREATE TABLE IF NOT EXISTS orders (
  id INT NOT NULL AUTO_INCREMENT,
  id_customer INT NOT NULL,
  id_executor INT NOT NULL,
  datetime_start DATETIME,
  datetime_end DATETIME,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB;
