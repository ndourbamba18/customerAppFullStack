DROP DATABASE IF EXISTS `customer_db`;

CREATE DATABASE IF NOT EXISTS `customer_db`;

USE customer_db;

DROP TABLE IF EXISTS `customer`;

CREATE TABLE IF NOT EXISTS `customer`(
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(60) NOT NULL,
    `email` VARCHAR(80) NOT NULL,
    `phone` VARCHAR(15) NOT NULL,
    `address` VARCHAR(100) NOT NULL,
    PRIMARY KEY(`id`) 
)
ENGINE=InnoDB;

INSERT INTO `customer`(name, email, phone, address) VALUES("VSoumare", "vsoumare@gmail.com", "(+221)778451452", "Ponit E, Dakar");
INSERT INTO `customer`(name, email, phone, address) VALUES("Bamba Ndour", "ndour@gmail.com", "(+221)778457541", "Ouakam, Dakar");
INSERT INTO `customer`(name, email, phone, address) VALUES("Aminata Bah", "bah@test.edu.sn", "(+221)784511452", "Pikine, Dakar");
INSERT INTO `customer`(name, email, phone, address) VALUES("Bintou Ndiaye", "ndiaye-bintou@test.edu.sn", "(+221)777511452", "Ouakam, Dakar");
INSERT INTO `customer`(name, email, phone, address) VALUES("Salimata Sow", "sow-saly@test.edu.sn", "(+221)778854252", "Thies, Senegal");

SELECT * FROM `customer`;