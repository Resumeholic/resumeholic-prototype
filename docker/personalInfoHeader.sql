-- MySQL dump 10.19  Distrib 10.3.36-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: resumeholic_personalinfoheader
-- ------------------------------------------------------
-- Server version	10.3.36-MariaDB-0+deb10u2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `resumeholic_personalinfoheader`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `resumeholic_personalinfoheader` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `resumeholic_personalinfoheader`;

--
-- Table structure for table `personalInformationHeaders`
--

DROP TABLE IF EXISTS `personalInformationHeaders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `personalInformationHeaders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(50) NOT NULL,
  `lastName` varchar(50) NOT NULL,
  `email` varchar(254) NOT NULL,
  `phoneNumber` varchar(16) NOT NULL,
  `resumeId` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personalInformationHeaders`
--

LOCK TABLES `personalInformationHeaders` WRITE;
/*!40000 ALTER TABLE `personalInformationHeaders` DISABLE KEYS */;
INSERT INTO `personalInformationHeaders` VALUES (1,'Alan','Chirn','ac@gmail.com','1234567890',2),(2,'Andy','Jones','andy.jones@demo.com','5467892504',5),(3,'Andy','Marks','andy.marks@gmail.com','2222222222',3),(4,'Joe','Hern','jhern@icloud.com','8620147985',4),(5,'JJ','Marr','jmarr@jmarr.com','1234567890',6),(6,'I\'m','Lucky','im.lucky@demo.com','6442658257',7),(7,'Joe','Hansen','jhansen@gmail.com','4756398889',8);
/*!40000 ALTER TABLE `personalInformationHeaders` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-06 20:53:10
