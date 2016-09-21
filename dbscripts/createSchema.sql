SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Hard way of removing users
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `tempschema` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;

SET SQL_MODE='ANSI';
USE `tempschema`;
DROP PROCEDURE IF EXISTS tempschema.drop_user_if_exists ;
DELIMITER $$
CREATE PROCEDURE `tempschema`.drop_user_if_exists()
BEGIN
  DECLARE foo BIGINT DEFAULT 0 ;
  SELECT COUNT(*)
  INTO foo
    FROM mysql.user
      WHERE User = 'siauser' and  Host = 'localhost';
   IF foo > 0 THEN
         DROP USER 'siauser'@'localhost' ;
   END IF;
  SELECT COUNT(*)
  INTO foo
    FROM mysql.user
      WHERE User = 'siauser' and  Host = '%';
   IF foo > 0 THEN
         DROP USER 'siauser'@'%' ;
  	END IF;
END ;$$
DELIMITER ;
CALL `tempschema`.drop_user_if_exists() ;
DROP PROCEDURE IF EXISTS `tempschema`.drop_user_if_exists ;
DROP SCHEMA IF EXISTS `tempschema`;

SET SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema siadb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `siadb` ;

-- -----------------------------------------------------
-- Schema siadb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `siadb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE siadb ;

-- -----------------------------------------------------
-- User and privileges
-- -----------------------------------------------------

CREATE USER 'siauser'@'localhost' IDENTIFIED BY 'siapwd';
GRANT ALL PRIVILEGES ON *.* TO 'siauser'@'localhost' WITH GRANT OPTION;
CREATE USER 'siauser'@'%' IDENTIFIED BY 'siapwd';
GRANT ALL PRIVILEGES ON *.* TO 'siauser'@'%' WITH GRANT OPTION;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
