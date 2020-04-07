-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: banking-service
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account_table`
--

DROP TABLE IF EXISTS `account_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `account_table` (
  `account_id` int(11) NOT NULL AUTO_INCREMENT,
  `client_ssn` varchar(45) DEFAULT NULL,
  `account_type` varchar(45) NOT NULL,
  `date_of_creation` varchar(45) NOT NULL,
  `balance` float NOT NULL,
  PRIMARY KEY (`account_id`),
  KEY `client_FK_idx` (`client_ssn`),
  CONSTRAINT `client_FK` FOREIGN KEY (`client_ssn`) REFERENCES `client_table` (`SSN`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_table`
--

LOCK TABLES `account_table` WRITE;
/*!40000 ALTER TABLE `account_table` DISABLE KEYS */;
INSERT INTO `account_table` VALUES (2,'1990111180056','SAVINGS','2019-03-20 16:18:25.861',99000),(3,'1990111180056','CREDIT','2019-03-20 16:18:25.861',51300),(5,'1950608125780','CREDIT','2019-03-20 16:18:25.861',1500),(6,'1950608125780','SAVINGS','2019-03-29 15:15:36.307',1000000),(7,'1670616120685','EXPENSES','2019-03-29 21:35:36.817',1500),(8,'1670616120685','SAVINGS','2017-03-30 18:05:03.853',12345),(9,'1670616120684','CREDIT','2019-03-20 16:17:15.278',1234),(10,'1670616120684','SAVINGS','2019-03-20 16:18:22.959',1234),(11,'1670616120684','CREDIT','2019-03-20 16:18:25.119',1234),(12,'1990111180056','SAVINGS','2019-03-20 16:18:25.861',1234),(14,'1990111180056','SAVINGS','2019-03-20 17:42:04.452',0),(26,'1950208324796','SAVINGS','string',956);
/*!40000 ALTER TABLE `account_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_table`
--

DROP TABLE IF EXISTS `activity_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `activity_table` (
  `activity_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `activity_name` varchar(45) NOT NULL,
  `timestamp` varchar(45) NOT NULL,
  `description` varchar(150) NOT NULL,
  PRIMARY KEY (`activity_id`),
  KEY `employee_FK_idx` (`username`),
  CONSTRAINT `employee_FK` FOREIGN KEY (`username`) REFERENCES `employee_table` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_table`
--

LOCK TABLES `activity_table` WRITE;
/*!40000 ALTER TABLE `activity_table` DISABLE KEYS */;
INSERT INTO `activity_table` VALUES (3,'user3','getAllClients','2019-03-30 18:39:57.31','The employee having username: user3retrieved all the clients'),(5,'user3','getClientById','2019-03-30 20:29:02.755','The employee having username: user3 retrieved information about client 1670616120684'),(6,'user3','getAllAccounts','2019-03-30 20:50:17.058','The employee having username: user3 retrieved all the accounts'),(7,'user3','getAllClients','2019-03-30 21:22:32.232','The employee having username: user3 retrieved all the clients'),(8,'user4','getAllClients','2019-03-30 21:33:54.483','The employee having username: user4 retrieved all the clients'),(9,'user4','getAllClients','2019-03-30 21:54:06.4','The employee having username: user4 retrieved all the clients'),(10,'user4','getAllClients','2019-03-30 21:55:58.013','The employee having username: user4 retrieved all the clients'),(11,'user3','getAllClients','2019-03-31 19:01:39.161','The employee having username: user3 retrieved all the clients');
/*!40000 ALTER TABLE `activity_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client_table`
--

DROP TABLE IF EXISTS `client_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `client_table` (
  `SSN` varchar(13) NOT NULL,
  `firstname` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  `identity_card_number` varchar(45) NOT NULL,
  `address` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  PRIMARY KEY (`SSN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client_table`
--

LOCK TABLES `client_table` WRITE;
/*!40000 ALTER TABLE `client_table` DISABLE KEYS */;
INSERT INTO `client_table` VALUES ('1670616120684','Radu','Popescu','KX808580','Cluj-Napoca','popescu@yahoo.com'),('1670616120685','Radu2','Popescu2','1670616120685','Cluj-Napoca2','popescu2@yahoo.com'),('1950208324796','Gheorghe','Georgescu','1950208324796','Str. Piezisa','gheorghe@yahoo.com'),('1950608125780','Cipri','Ionescu','12345678','Strada Libertatii','ionescu@yahoo.com'),('1990111180056','Titi','Vasilescu','string','Observatorului, Cluj-Napoca','titi@yahoo.com');
/*!40000 ALTER TABLE `client_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee_table`
--

DROP TABLE IF EXISTS `employee_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `employee_table` (
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `type` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `telephone` varchar(45) NOT NULL,
  `address` varchar(45) NOT NULL,
  `hiring_date` varchar(45) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee_table`
--

LOCK TABLES `employee_table` WRITE;
/*!40000 ALTER TABLE `employee_table` DISABLE KEYS */;
INSERT INTO `employee_table` VALUES ('user1','abc','ADMIN','Ellen DeGeneres','00169543258','Str.Viitorului','03-06-2019'),('user2','abcde','ADMIN','Manuel Neuer','0049510236','Aleea Stadionului','27.03.2005'),('user3','abcd','REGULAR','Matts Hummels','00495632565','Str. Observatorului','26-03-2018'),('user4','1234','REGULAR','Ross Geller','00156987426','Str. Paris','30-03-2018');
/*!40000 ALTER TABLE `employee_table` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-04-01 10:30:42
