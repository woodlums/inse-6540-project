-- MariaDB dump 10.19  Distrib 10.5.15-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: inse6540
-- ------------------------------------------------------
-- Server version	10.5.15-MariaDB-0+deb11u1

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
-- Table structure for table `logs`
--

DROP TABLE IF EXISTS `logs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `logs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL DEFAULT current_timestamp(),
  `log_text` varchar(45) DEFAULT NULL,
  `severity` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_severity_idx` (`severity`),
  CONSTRAINT `fk_severity` FOREIGN KEY (`severity`) REFERENCES `severity` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logs`
--

LOCK TABLES `logs` WRITE;
/*!40000 ALTER TABLE `logs` DISABLE KEYS */;
/*!40000 ALTER TABLE `logs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reading_type`
--

DROP TABLE IF EXISTS `reading_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reading_type` (
  `type_code` char(2) NOT NULL,
  `type_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`type_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reading_type`
--

LOCK TABLES `reading_type` WRITE;
/*!40000 ALTER TABLE `reading_type` DISABLE KEYS */;
INSERT INTO `reading_type` VALUES ('H','Humidity'),('T','Temperature');
/*!40000 ALTER TABLE `reading_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `readings`
--

DROP TABLE IF EXISTS `readings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `readings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date_time` datetime NOT NULL DEFAULT current_timestamp(),
  `value` float NOT NULL,
  `unit` char(1) NOT NULL,
  `reading_type` char(2) NOT NULL,
  `hashed_value` char(97) NOT NULL,
  `bc_written` tinyint(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `fk_unit_idx` (`unit`),
  KEY `fk_reading_type_idx` (`reading_type`),
  CONSTRAINT `fk_type_code` FOREIGN KEY (`reading_type`) REFERENCES `reading_type` (`type_code`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_unit` FOREIGN KEY (`unit`) REFERENCES `units` (`unit_code`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `readings`
--

LOCK TABLES `readings` WRITE;
/*!40000 ALTER TABLE `readings` DISABLE KEYS */;
INSERT INTO `readings` VALUES (98,'2024-07-29 16:14:22',23.8,'C','T','cd3626a33cb60c94d4eeaf477d7990f5a780a859677162db9640d6a48c8f8d36:583e42d263a71d9d7c0c93c9c9af0a70',0),(99,'2024-07-29 16:47:32',23.8,'C','T','4216d61da96b1202931d33873aa8404d2037181ab0ef9ff78ab09cbd16ec857f:f02a003073081a357a11a73e6f95151a',0),(100,'2024-07-29 16:47:33',27,'R','H','f2850eb20eb1a06da48c80065532aacdab4db0fab307986d50a9f7afd3c59d6a:74a9699d2cfbc3e7b10a6a4100de488d',1),(101,'2024-07-29 16:55:29',23.4,'C','T','4de553da60ff47ce0ba75c801d3d6e27dcf778b96fe066f03e15e16811a31096:a337fe233dc75b8432393105ec1f16cd',0),(102,'2024-07-29 16:55:30',27,'R','H','8b398dac2dcef0fffaa48c5deeb560db5088e2f7c751cfc006c585fefe6960f1:1280393884c3fefca543fff22a4e8a75',0),(103,'2024-07-29 16:57:02',23.4,'C','T','d8ce9c8242c14f4c2f1ff3f4af76e060c2de07f13db5bf3e36f69b15833cbbfd:3d58c4ba6b9f00dd1a28d51662998f9f',0),(104,'2024-07-29 16:57:03',27,'R','H','0003df2b1942a710ec193768ba98b7dc1375c2b6869d35b5c7812ba850af9ffd:d209da5e8531d52d91ac8931001c0a95',0),(105,'2024-07-29 16:58:04',23.4,'C','T','f7500ceb9dd542c135403f7b2697ed0f0779cf3c76f5794a305de993981adccd:681c6da72493ac42c2ff3e8765c051cb',0),(106,'2024-07-29 16:58:05',27,'R','H','8c1f5d3f617351f531114f59f999886c5c0504202d5128785cb08e81da6da9d8:a76b5299d765776a77e8ed266dc9cdd1',0),(107,'2024-07-29 16:59:06',23,'C','T','498a7ac82882d43fb443e616ad9711d52b0438fe69c08e485dd5334d3dc09a09:493423e359a254e751c51f5ba18bb768',0),(108,'2024-07-29 16:59:06',27,'R','H','85d162f87d1f94a450a5a27a58912b39f3bd13f34b364108a9b6b54698077739:e4ad9fe655ef12cd32644d77fd46cee4',0),(109,'2024-07-29 17:00:39',23,'C','T','64c110e5bf827cb79b6552a4f68dcb8ef1d64b8c062de85fc8738b5869c61c1a:465979ffad183750c421bce4a2c61bc4',0),(110,'2024-07-29 17:00:39',27,'R','H','c0a097fc2811b370e30bc4595a20e725f055902756adcee2ad363102eb48ea6e:7e67646cc3d1d1e92972f2d3095274b3',0),(111,'2024-07-29 19:36:13',22.7,'C','T','91f78eb0df12c348b66923a57cbed17b067a3fbd03cd6a437fcd8577a9aefa6c:64c43d72f6cd6154eff332b9de58d2a8',0),(112,'2024-07-29 19:36:14',26,'R','H','6c5a087c204eade4bf4b11226a43f5be66d4902714133ae6f703665190449e6b:70fe3f2ad85d3d0e0274b0cbd7669d59',0);
/*!40000 ALTER TABLE `readings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `readings_display`
--

DROP TABLE IF EXISTS `readings_display`;
/*!50001 DROP VIEW IF EXISTS `readings_display`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `readings_display` (
  `Timestamp` tinyint NOT NULL,
  `Value` tinyint NOT NULL,
  `Unit` tinyint NOT NULL,
  `Type` tinyint NOT NULL,
  `Unique Hash` tinyint NOT NULL,
  `In Blockchain` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `severity`
--

DROP TABLE IF EXISTS `severity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `severity` (
  `id` int(11) NOT NULL,
  `explanation` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `severity`
--

LOCK TABLES `severity` WRITE;
/*!40000 ALTER TABLE `severity` DISABLE KEYS */;
/*!40000 ALTER TABLE `severity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `units`
--

DROP TABLE IF EXISTS `units`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `units` (
  `unit_code` char(1) NOT NULL,
  `unit_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`unit_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `units`
--

LOCK TABLES `units` WRITE;
/*!40000 ALTER TABLE `units` DISABLE KEYS */;
INSERT INTO `units` VALUES ('C','Celcius'),('F','Fahrenheit'),('R','Relative Humidity');
/*!40000 ALTER TABLE `units` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `readings_display`
--

/*!50001 DROP TABLE IF EXISTS `readings_display`*/;
/*!50001 DROP VIEW IF EXISTS `readings_display`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`remote`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `readings_display` AS select `readings`.`date_time` AS `Timestamp`,`readings`.`value` AS `Value`,`units`.`unit_name` AS `Unit`,`reading_type`.`type_name` AS `Type`,`readings`.`hashed_value` AS `Unique Hash`,if(`readings`.`bc_written`,'✔','-') AS `In Blockchain` from ((`readings` join `units`) join `reading_type`) where `units`.`unit_code` = `readings`.`unit` and `reading_type`.`type_code` = `readings`.`reading_type` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-30 15:46:40
