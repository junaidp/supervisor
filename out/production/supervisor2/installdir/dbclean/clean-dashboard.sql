# Dumping database structure for dashboard2
DROP DATABASE IF EXISTS `dashboard2`;
CREATE DATABASE IF NOT EXISTS `dashboard2` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `dashboard2`;

##------------------------------------------------------------------------------------------------
# Dumping structure for table dashboard2.dmpfiles
DROP TABLE IF EXISTS `dmpfiles`;
CREATE TABLE IF NOT EXISTS `dmpfiles` (
  `dbid` int(10) NOT NULL AUTO_INCREMENT,
  `sig` varchar(255) DEFAULT '0',
  `eventid` int(10) NOT NULL,
  `occurred` TIMESTAMP NOT NULL ,
  `version` varchar(25) DEFAULT '0',
  `stringid` int(10) NOT NULL,
  `lastadj` TIMESTAMP NOT NULL ,
  PRIMARY KEY (`dbid`)
) ENGINE=InnoDB ;
#) ENGINE=InnoDB DEFAULT CHARSET=

# Dumping data for table dashboard2.dmpfiles: 0 rows
DELETE FROM `dmpfiles`;
/*!40000 ALTER TABLE `dmpfiles` DISABLE KEYS */;
/*!40000 ALTER TABLE `dmpfiles` ENABLE KEYS */;



##------------------------------------------------------------------------------------------------
# Dumping structure for table dashboard2.stats
DROP TABLE IF EXISTS `stats`;
CREATE TABLE IF NOT EXISTS `stats` (
  `sid` int(10) NOT NULL AUTO_INCREMENT,
  `dbid` int(10) NOT NULL,
  `deviceid` int(10) NOT NULL,
  `nwid` int(10) NOT NULL,
  `occurred` TIMESTAMP NOT NULL ,
  `type` int(10) NOT NULL,
  `eventid` int(10) NOT NULL,
  `qty` int(5) NOT NULL,
  PRIMARY KEY (`sid`),
  KEY `nwtime` (nwid,occurred)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# Dumping data for table dashboard2.stats: 0 rows
DELETE FROM `stats`;
/*!40000 ALTER TABLE `stats` DISABLE KEYS */;
/*!40000 ALTER TABLE `stats` ENABLE KEYS */;



##------------------------------------------------------------------------------------------------
# Dumping structure for table dashboard2.properties
DROP TABLE IF EXISTS `properties`;
CREATE TABLE IF NOT EXISTS `properties` (
  `pid` int(10) NOT NULL AUTO_INCREMENT,
  `type` varchar(15) NOT NULL DEFAULT '0',
  `name` varchar(15) NOT NULL DEFAULT '0',
  `id` varchar(15) NOT NULL DEFAULT '0',
  `value` varchar(50) NOT NULL DEFAULT '0',
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# versions
# 1 140918 initial

# Dumping data for table dashboard2.properties: 0 rows
DELETE FROM `properties`;
/*!40000 ALTER TABLE `properties` DISABLE KEYS */;
INSERT INTO `properties` VALUES (1, 'db', 'DbVersion', '0', '3');
INSERT INTO `properties` VALUES (2, 'system', 'timezone', '0','America/New_York');
INSERT INTO `properties` VALUES (3, 'system', 'classname', '6','new ip');
INSERT INTO `properties` VALUES (4, 'system', 'classname', '17','mac move');
INSERT INTO `properties` VALUES (5, 'system', 'classname', '19','mac change');
INSERT INTO `properties` VALUES (6, 'system', 'classname', '20','ip change');
INSERT INTO `properties` VALUES (7, 'system', 'classname', '30','admin move');
INSERT INTO `properties` VALUES (8, 'system', 'classname', '35','speed');
INSERT INTO `properties` VALUES (9, 'system', 'classname', '101','ping over');
INSERT INTO `properties` VALUES (10, 'system', 'classname', '102','bw over');
INSERT INTO `properties` VALUES (11, 'system', 'classname', '103','new ip');
INSERT INTO `properties` VALUES (12, 'system', 'classname', '104','move');
INSERT INTO `properties` VALUES (13, 'system', 'classname', '107','name change');
INSERT INTO `properties` VALUES (14, 'system', 'classname', '108','speed2');
INSERT INTO `properties` VALUES (15, 'system', 'classname', '109','ENETIP change');
INSERT INTO `properties` VALUES (16, 'system', 'classname', '0','reserved');
INSERT INTO `properties` VALUES (17, 'system', 'classname', '0','reserved');
INSERT INTO `properties` VALUES (18, 'system', 'classname', '0','reserved');
INSERT INTO `properties` VALUES (19, 'system', 'classname', '0','reserved');
INSERT INTO `properties` VALUES (20, 'system', 'classname', '0','reserved');
INSERT INTO `properties` VALUES (21, 'system', 'classname', '0','reserved');
INSERT INTO `properties` VALUES (22, 'system', 'classname', '0','reserved');
INSERT INTO `properties` VALUES (23, 'db', 'current.db', '0','0');
INSERT INTO `properties` VALUES (24, 'system', 'tzoffset', '0','0');
/*!40000 ALTER TABLE `properties` ENABLE KEYS */;


##------------------------------------------------------------------------------------------------
# Dumping structure for table dashboard2.thresholds
DROP TABLE IF EXISTS `thresholds`;
CREATE TABLE IF NOT EXISTS `thresholds` (
  `tid` int(10) NOT NULL AUTO_INCREMENT,
  `dbid` int(10) NOT NULL,
  `ifdesc` int(10) NOT NULL,
  `deviceid` int(10) NOT NULL,
  `nwid` int(10) NOT NULL,
  `occurred` TIMESTAMP NOT NULL ,
  `ping` float NOT NULL DEFAULT '0',
  `pf` float NOT NULL DEFAULT '0',
  `xmit` float NOT NULL DEFAULT '0',
  `recv` float NOT NULL DEFAULT '0',
  `xbytes` int(10) NOT NULL DEFAULT '-1',
  `rbytes` int(10) NOT NULL DEFAULT '-1',
  PRIMARY KEY (`tid`),
  KEY `nwtime` (nwid,occurred)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# Dumping data for table dashboard2.thresholds: 0 rows
DELETE FROM `thresholds`;
/*!40000 ALTER TABLE `thresholds` DISABLE KEYS */;
/*!40000 ALTER TABLE `thresholds` ENABLE KEYS */;




##------------------------------------------------------------------------------------------------
## new memory tables for dashboard2 convenience
## data created on each startup

CREATE TABLE IF NOT EXISTS `deviceinfo` (
  `deviceid` int(10) NOT NULL,
  `ifdesc` int(10) NOT NULL,
  `parentid` int(10) NOT NULL,
  `childid` int(10) NOT NULL,
  `networkid` int(10) NOT NULL,
    
  `name`    varchar(20) ,
  `ipAddress`  varchar(20) ,
  `macAddress` varchar(20) ,
  `netmask`    varchar(20) ,

  `connected` TINYINT(4) NULL DEFAULT '0',
  `verified` TINYINT(4) NULL DEFAULT '0',
  `autoConnect` TINYINT(4) NULL DEFAULT '0',
  `isSwitch` TINYINT(4) NULL DEFAULT '0',
  `hasRedBox` TINYINT(4) NULL DEFAULT '0',
  `isWirelessDevice` TINYINT(4) NULL DEFAULT '0',
  `ifWasPinged` TINYINT(4) NULL DEFAULT '0',
  
  `kpiType`    int(10) ,
  `speed`      int(10) ,

  PRIMARY KEY (`deviceid`)
) ENGINE=MEMORY DEFAULT CHARSET=latin1;



CREATE TABLE IF NOT EXISTS `deviceNameInfo` (
  `deviceid` int(10) NOT NULL,
  `name`     varchar(50) ,
  `location` varchar(50) ,
  `ud1name`  varchar(50) ,
  `ud2name`  varchar(50) ,
  `ud3name`  varchar(50) ,
  `ud4name`  varchar(50) ,
  `ud5name`  varchar(50) ,
  `ud6name`  varchar(50) ,
  `vendorname`  varchar(50),
  
  `description` varchar(255),
  `notes`       varchar(255),
  PRIMARY KEY (`deviceid`)
) ENGINE=MEMORY DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `topoInfo` (
  `parent` int(10) NOT NULL,
  `child` int(10) NOT NULL,
  `childIp`     varchar(20) ,
  `name` varchar(50) ,
  `portno` int(10) NOT NULL,
  `upportno` int(10) NOT NULL,
  PRIMARY KEY (`parent`)
) ENGINE=MEMORY DEFAULT CHARSET=latin1;
