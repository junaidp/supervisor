-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.5.5-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win32
-- HeidiSQL Version:             11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for supervisor
DROP DATABASE IF EXISTS `supervisor`;
CREATE DATABASE IF NOT EXISTS `supervisor` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `supervisor`;

-- Dumping structure for table supervisor.host
CREATE TABLE IF NOT EXISTS `host` (
  `hostid` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `ipaddress` varchar(50) DEFAULT NULL,
  `description` varchar(50) DEFAULT NULL,
  `lastping` int(11) DEFAULT NULL,
  `lastevent` int(11) DEFAULT NULL,
  PRIMARY KEY (`hostid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



-- Dumping structure for table event
CREATE TABLE IF NOT EXISTS `event` (
  `eventid` int(10) NOT NULL AUTO_INCREMENT,
  `hostid` int(10) NOT NULL DEFAULT '0',
  `localtime` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `description` char(255) DEFAULT '',
  `type` tinyint(4) DEFAULT NULL,
  `group` int(11) NOT NULL DEFAULT '0',
  `utctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`eventid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



-- Dumping structure for table props
CREATE TABLE IF NOT EXISTS `props` (
  `propid` int(10) NOT NULL AUTO_INCREMENT,
  `hostid` int(10) NOT NULL DEFAULT '0',
  `category` varchar(10) DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`propid`),
  UNIQUE KEY `idx` (`hostid`,`category`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


INSERT INTO `props` (`propid`, `hostid`, `category`, `name`, `value`) VALUES (1, 0, 'db', 'version', '1');












-- Data exporti
-- Data exporting was unselected.

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
