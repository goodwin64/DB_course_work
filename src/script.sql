DROP SCHEMA IF EXISTS solving_tasks;
CREATE SCHEMA IF NOT EXISTS solving_tasks;
USE solving_tasks;

#---------------------------------------------
#--create table users
#---------------------------------------------

DROP TABLE IF EXISTS users;
CREATE TABLE IF NOT EXISTS users (
  id INT NOT NULL UNIQUE AUTO_INCREMENT,
  login VARCHAR(45) NULL UNIQUE,
  password VARCHAR(45) NULL,
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
  id_executor INT,
  task TEXT,
  datetime_start DATE,
  datetime_end DATE,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB;

INSERT INTO users (login, password, name, surname, type) VALUES ('maximdonchenko', 'maxim123', 'Max', 'Donchenko', 3);
INSERT INTO users (login, password, name, surname, type) VALUES ('pavelshevchenko', 'pavel123', 'Pavel', 'Shevchenko', 1);
INSERT INTO users (login, password, name, surname, type) VALUES ('test', 'test', 'TestName', 'TestSurname', 2);
