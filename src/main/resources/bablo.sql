-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema bablo
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema bablo
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `bablo` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `bablo` ;

-- -----------------------------------------------------
-- Table `bablo`.`players`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bablo`.`players` (
  `idplayers` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idplayers`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bablo`.`result`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bablo`.`result` (
  `idresult` INT NOT NULL AUTO_INCREMENT,
  `score` VARCHAR(3) NOT NULL,
  `set1` VARCHAR(5) NOT NULL,
  `set2` VARCHAR(5) NOT NULL,
  `set3` VARCHAR(5) NOT NULL,
  `set4` VARCHAR(5) NOT NULL,
  `set5` VARCHAR(5) NULL DEFAULT NULL,
  `set6` VARCHAR(5) NULL DEFAULT NULL,
  `set7` VARCHAR(5) NULL DEFAULT NULL,
  PRIMARY KEY (`idresult`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bablo`.`matches`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bablo`.`matches` (
  `idmatches` INT NOT NULL AUTO_INCREMENT,
  `player1` INT NOT NULL,
  `player2` INT NOT NULL,
  `result` INT NOT NULL,
  `date` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idmatches`),
  INDEX `player2_idx` (`player2` ASC) VISIBLE,
  INDEX `player1_idx` (`player1` ASC) VISIBLE,
  INDEX `result_idx` (`result` ASC) VISIBLE,
  CONSTRAINT `player1`
    FOREIGN KEY (`player1`)
    REFERENCES `bablo`.`players` (`idplayers`),
  CONSTRAINT `player2`
    FOREIGN KEY (`player2`)
    REFERENCES `bablo`.`players` (`idplayers`),
  CONSTRAINT `result`
    FOREIGN KEY (`result`)
    REFERENCES `bablo`.`result` (`idresult`))


    ALTER TABLE `bablo`.`result`
CHANGE COLUMN `set4` `set4` VARCHAR(5) NULL ;
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
