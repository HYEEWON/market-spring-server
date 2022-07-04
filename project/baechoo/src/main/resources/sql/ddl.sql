-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema baechoo
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `baechoo` ;

-- -----------------------------------------------------
-- Schema baechoo
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `baechoo` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `baechoo` ;

-- -----------------------------------------------------
-- Table `baechoo`.`member`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `baechoo`.`member` ;

CREATE TABLE IF NOT EXISTS `baechoo`.`member` (
  `member_idx` INT NOT NULL AUTO_INCREMENT,
  `id` VARCHAR(20) NOT NULL,
  `encrypt_password` VARCHAR(16) NOT NULL,
  `nickname` VARCHAR(20) NOT NULL,
  `birthdate` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`member_idx`),
  UNIQUE INDEX `member_id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `baechoo`.`member_secede`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `baechoo`.`member_secede` ;

CREATE TABLE IF NOT EXISTS `baechoo`.`member_secede` (
  `member_idx` INT NOT NULL AUTO_INCREMENT,
  `id` VARCHAR(15) NOT NULL,
  `secede_date` DATETIME(6) NOT NULL,
  PRIMARY KEY (`member_idx`),
  UNIQUE INDEX `member_id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
