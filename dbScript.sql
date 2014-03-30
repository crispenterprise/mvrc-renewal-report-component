delimiter $$

CREATE DATABASE `genaudit` /*!40100 DEFAULT CHARACTER SET utf8 */$$

delimiter $$

CREATE TABLE `application` (
  `application_no` int(11) NOT NULL AUTO_INCREMENT,
  `account_no` int(11) DEFAULT NULL,
  `plate_no` varchar(32) DEFAULT NULL,
  `renewal_period` int(11) DEFAULT NULL,
  `new_reg_expiry_date` datetime DEFAULT NULL,
  PRIMARY KEY (`application_no`),
  KEY `fk_application_account_idx` (`account_no`),
  CONSTRAINT `fk_application_account` FOREIGN KEY (`account_no`) REFERENCES `user_account` (`account_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$

delimiter $$

CREATE TABLE `event` (
  `event_id` int(11) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$

delimiter $$

CREATE TABLE `event_audit` (
  `audit_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `comment` text,
  `dtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `event_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`audit_id`),
  KEY `event_id_idx` (`event_id`),
  CONSTRAINT `event_id` FOREIGN KEY (`event_id`) REFERENCES `event` (`event_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1$$

delimiter $$

CREATE TABLE `payment` (
  `payment_id` int(11) NOT NULL AUTO_INCREMENT,
  `application_no` int(11) DEFAULT NULL,
  `payment_dtime` datetime DEFAULT NULL,
  `ammount` decimal(6,2) DEFAULT NULL,
  PRIMARY KEY (`payment_id`),
  KEY `fk_payment_application_idx` (`application_no`),
  CONSTRAINT `fk_payment_application` FOREIGN KEY (`application_no`) REFERENCES `application` (`application_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$

delimiter $$

CREATE TABLE `person` (
  `person_id` int(11) NOT NULL AUTO_INCREMENT,
  `trn` varchar(9) DEFAULT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `street_no` varchar(15) DEFAULT NULL,
  `street_name` varchar(45) DEFAULT NULL,
  `parish` varchar(45) DEFAULT NULL,
  `home_tel` varchar(15) DEFAULT NULL,
  `cell_tel` varchar(15) DEFAULT NULL,
  `create_username` varchar(32) DEFAULT NULL,
  `create_dtime` datetime DEFAULT NULL,
  `update_username` varchar(45) DEFAULT NULL,
  `update_dtime` datetime DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`person_id`),
  UNIQUE KEY `trn_UNIQUE` (`trn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$

delimiter $$

CREATE TABLE `receipt` (
  `receipt_no` int(11) NOT NULL AUTO_INCREMENT,
  `payment_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`receipt_no`),
  KEY `fk_receipt_payment_idx` (`payment_id`),
  CONSTRAINT `fk_receipt_payment` FOREIGN KEY (`payment_id`) REFERENCES `payment` (`payment_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$

delimiter $$

CREATE TABLE `user_account` (
  `account_no` int(11) NOT NULL AUTO_INCREMENT,
  `person_id` int(11) DEFAULT NULL,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `date_joined` datetime DEFAULT NULL,
  `secret_question` varchar(128) DEFAULT NULL,
  `secret_answer` varchar(45) DEFAULT NULL,
  `status` varchar(3) DEFAULT NULL,
  PRIMARY KEY (`account_no`),
  KEY `fk_account_person_idx` (`person_id`),
  CONSTRAINT `fk_account_person` FOREIGN KEY (`person_id`) REFERENCES `person` (`person_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$

