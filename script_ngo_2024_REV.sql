-- MySQL dump 10.13  Distrib 8.0.36, for macos14 (arm64)
--
-- Host: 127.0.0.1    Database: ngo_2024
-- ------------------------------------------------------
-- Server version	8.0.36

use ngo_2024;
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8m    b4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Ta bort proceduren om den redan finns
DROP PROCEDURE IF EXISTS setup_ngo_2024;
DELIMITER //

CREATE PROCEDURE setup_ngo_2024()
BEGIN
    DECLARE user_exists INT DEFAULT 0;

    -- Skapa databasen om den inte redan finns
    DROP DATABASE IF EXISTS ngo_2024;
    CREATE DATABASE ngo_2024;

    -- Kontrollera om användaren 'dbAdmin2024'@'localhost' finns
    SELECT COUNT(*) INTO user_exists
    FROM mysql.user
    WHERE user = 'dbAdmin2024' AND host = 'localhost';

    -- Om användaren inte finns, skapa användaren och tilldela behörigheter
    IF user_exists = 0 THEN
        CREATE USER 'dbAdmin2024'@'localhost' IDENTIFIED BY 'dbAdmin2024PW';
        GRANT ALL PRIVILEGES ON ngo_2024.* TO 'dbAdmin2024'@'localhost';
        FLUSH PRIVILEGES;
    END IF;
END;
//
DELIMITER ;

-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `aid` int NOT NULL,
  `behorighetsniva` int DEFAULT NULL,
  PRIMARY KEY (`aid`),
  CONSTRAINT `admin_ibfk_1` FOREIGN KEY (`aid`) REFERENCES `anstalld` (`aid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1,1),
                           (2,2),
                           (6,1),
                           (7,2),
                           (11,1),
                           (12,2),
                           (16,2),
                           (17,2),
                           (14,1),
                           (15,1),
                           (18,2),
                           (19,1),
                           (21,1),
                           (22,1),
                           (23,1),
                           (24,2),
                           (25,1),
                           (31,1),
                           (32,1),
                           (33,2),
                           (34,1),
                           (35,1),
                           (36,1),
                           (37,1),
                           (38,2),
                           (39,1);
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ans_proj`
--

DROP TABLE IF EXISTS `ans_proj`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ans_proj` (
  `pid` int NOT NULL,
  `aid` int NOT NULL,
  PRIMARY KEY (`pid`,`aid`),
  KEY `Aid` (`aid`),
  CONSTRAINT `ans_proj_ibfk_1` FOREIGN KEY (`pid`) REFERENCES `projekt` (`pid`),
  CONSTRAINT `ans_proj_ibfk_2` FOREIGN KEY (`aid`) REFERENCES `anstalld` (`aid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ans_proj`
--

LOCK TABLES `ans_proj` WRITE;
/*!40000 ALTER TABLE `ans_proj` DISABLE KEYS */;
INSERT INTO `ans_proj` VALUES (1,1),
                              (1,2),
                              (1,3),
                              (2,6),
                              (2,7),
                              (2,8),
                              (3,11),
                              (3,12),
                              (3,13),
                              (4,16),
                              (4,17),
                              (4,18),
                              (5,21),
                              (5,22),
                              (5,23),
                              (6,26),
                              (6,27),
                              (6,28),
                              (7,31),
                              (7,32),
                              (7,33),
                              (8,36),
                              (8,37),
                              (8,38),
                              (9,41),
                              (9,42),
                              (9,43),
                              (10,46),
                              (10,47),
                              (10,48);
/*!40000 ALTER TABLE `ans_proj` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `anstalld`
--

DROP TABLE IF EXISTS `anstalld`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `anstalld` (
  `aid` int NOT NULL,
  `fornamn` varchar(100) DEFAULT NULL,
  `efternamn` varchar(100) DEFAULT NULL,
  `adress` varchar(255) DEFAULT NULL,
  `epost` varchar(255) DEFAULT NULL,
  `telefon` varchar(20) DEFAULT NULL,
  `anstallningsdatum` date DEFAULT NULL,
  `losenord` varchar(255) DEFAULT NULL,
  `avdelning` int NOT NULL,
  PRIMARY KEY (`aid`),
  KEY `avdelning` (`avdelning`),
  CONSTRAINT `anstalld_ibfk_1` FOREIGN KEY (`avdelning`) REFERENCES `avdelning` (`avdid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `anstalld`
--

LOCK TABLES `anstalld` WRITE;
/*!40000 ALTER TABLE `anstalld` DISABLE KEYS */;
INSERT INTO `anstalld` VALUES (1,'Maria','Gonzalez','123 Main Street, Anytown','maria.g@example.com','123-456-7890','2023-01-15','password123',1),
                              (2,'John','Smith','456 Oak Avenue, Othertown','john.smith@example.com','234-567-8901','2022-11-20','password456',2),
                              (3,'Ahmed','Khan','789 Elm Street, Anothercity','ahmed.khan@example.com','345-678-9012','2023-03-10','password789',3),
                              (4,'Chen','Wei','321 Pine Road, Newtown','chen.wei@example.com','456-789-0123','2022-12-05','passwordabc',1),
                              (5,'Aya','Sato','654 Cedar Lane, Townsville','aya.sato@example.com','567-890-1234','2023-02-28','passwordxyz',1),
                              (6,'Michael','Johnson','987 Maple Drive, Cityville','michael.j@example.com','678-901-2345','2022-10-18','password789',1),
                              (7,'Fatima','Al-Mansouri','159 Birch Street, Metropolis','fatima.alm@example.com','789-012-3456','2023-04-22','passwordxyz',2),
                              (8,'Pablo','Rodriguez','753 Pinecrest Road, Urbantown','pablo.r@example.com','890-123-4567','2022-09-30','password123',2),
                              (9,'Ling','Wang','369 Cedar Avenue, Suburbia','ling.w@example.com','901-234-5678','2023-01-05','password456',2),
                              (10,'Sofia','Andersson','852 Oakwood Boulevard, Villagetown','sofia.a@example.com','012-345-6789','2022-11-12','passwordabc',1),
                              (11,'Hiro','Tanaka','456 Birch Lane, Hamletsville','hiro.t@example.com','123-456-7890','2023-03-18','passwordxyz',1),
                              (12,'Emily','Chen','753 Willow Street, Countryside','emily.c@example.com','234-567-8901','2022-12-08','password123',2),
                              (13,'Mohammed','Ali','159 Elmwood Drive, Outskirts','mohammed.a@example.com','345-678-9012','2023-02-15','password456',1),
                              (14,'Anna','Larsson','357 Cedar Lane, Outland','anna.l@example.com','456-789-0123','2022-10-22','passwordabc',1),
                              (15,'Javier','Garcia','852 Pinecrest Road, Farawaytown','javier.g@example.com','567-890-1234','2023-04-02','passwordxyz',1),
                              (16,'Yumi','Nakamura','963 Oakwood Boulevard, Beyondville','yumi.n@example.com','678-901-2345','2022-09-14','password123',2),
                              (17,'Muhammad','Ahmed','123 Maple Street, Uptown','muhammad.a@example.com','789-012-3456','2023-01-28','password456',2),
                              (18,'Luis','Martinez','456 Cedar Avenue, Downtown','luis.m@example.com','890-123-4567','2022-11-30','passwordabc',2),
                              (19,'Yuna','Kim','753 Oakwood Boulevard, Midtown','yuna.k@example.com','901-234-5678','2023-03-25','passwordxyz',1),
                              (20,'Marta','Gutierrez','963 Pine Road, Centre City','marta.g@example.com','012-345-6789','2022-12-12','password123',1),
                              (21,'Ahmed','Mohammed','159 Birch Street, Capital City','ahmed.m@example.com','123-456-7890','2023-02-18','password456',1),
                              (22,'Yusuke','Suzuki','357 Oak Avenue, Business District','yusuke.s@example.com','234-567-8901','2022-10-25','passwordabc',1),
                              (23,'Elena','Gonzalez','852 Cedar Lane, Financial Centre','elena.g@example.com','345-678-9012','2023-04-12','passwordxyz',1),
                              (24,'Mohammed','Ali','123 Pine Road, Tech Park','mohammed.a2@example.com','456-789-0123','2022-09-05','password123',2),
                              (25,'Kim','Lee','963 Elm Street, Silicon Valley','kim.l@example.com','567-890-1234','2023-01-02','password456',1),
                              (26,'Chen','Li','369 Oak Avenue, Tech Hub','chen.l@example.com','678-901-2345','2022-11-15','passwordabc',1),
                              (27,'Mohammed','Abdelaziz','753 Pine Road, Innovation District','mohammed.ab@example.com','789-012-3456','2023-03-20','passwordxyz',1),
                              (28,'Sophia','Johnson','159 Cedar Lane, Research Park','sophia.j@example.com','890-123-4567','2022-12-30','password123',1),
                              (29,'Priya','Patel','456 Birch Street, Tech Valley','priya.p@example.com','901-234-5678','2023-02-10','password456',2),
                              (30,'Ahmed','Ibrahim','852 Pinecrest Road, Science City','ahmed.i@example.com','012-345-6789','2022-10-18','passwordabc',1),
                              (31,'Nadia','Khan','963 Elmwood Drive, Future Town','nadia.k@example.com','123-456-7890','2023-04-22','passwordxyz',3),
                              (32,'Emma','Smith','123 Willow Street, Innovation Hub','emma.s@example.com','234-567-8901','2022-09-30','password123',3),
                              (33,'Mohammad','Hassan','357 Pine Road, Digital Centre','mohammad.h@example.com','345-678-9012','2023-01-05','password456',3),
                              (34,'Mia','Andersson','753 Cedar Avenue, Technology Campus','mia.a@example.com','456-789-0123','2022-11-12','passwordabc',3),
                              (35,'Yuki','Takahashi','159 Oakwood Boulevard, Innovation Park','yuki.t@example.com','567-890-1234','2023-03-18','passwordxyz',3),
                              (36,'Ali','Rahman','456 Maple Street, Digital City','ali.r@example.com','678-901-2345','2022-12-08','password123',2),
                              (37,'Luna','Kim','852 Pinecrest Road, Tech Valley','luna.k@example.com','789-012-3456','2023-02-15','password456',2),
                              (38,'Chen','Wu','963 Birch Lane, Cyber Centre','chen.wu@example.com','890-123-4567','2022-10-22','passwordabc',2),
                              (39,'Maria','Silva','123 Cedar Avenue, Data District','maria.s@example.com','901-234-5678','2023-04-02','passwordxyz',1),
                              (40,'Rahul','Gupta','357 Elmwood Drive, Tech Park','rahul.g@example.com','012-345-6789','2022-09-14','password123',3),
                              (41,'Sara','Lopez','753 Oakwood Boulevard, Digital City','sara.l@example.com','123-456-7890','2023-01-28','password456',1),
                              (42,'David','Brown','963 Pine Road, IT Centre','david.b@example.com','234-567-8901','2022-11-30','passwordabc',2),
                              (43,'Rina','Yamamoto','159 Birch Street, Tech Hub','rina.y@example.com','345-678-9012','2023-03-25','passwordxyz',1),
                              (44,'Sanjay','Patel','456 Oak Avenue, Research Centre','sanjay.p@example.com','456-789-0123','2022-12-12','password123',1),
                              (45,'Chen','Yang','852 Cedar Lane, Digital Campus','chen.y@example.com','567-890-1234','2023-02-18','password456',1),
                              (46,'Olga','Ivanova','963 Pine Road, Science Park','olga.i@example.com','678-901-2345','2022-10-25','passwordabc',2),
                              (47,'Hassan','Ali','159 Oakwood Boulevard, Tech Valley','hassan.a@example.com','789-012-3456','2023-04-12','passwordxyz',1),
                              (48,'Sofia','Martinez','357 Elm Street, Digital City','sofia.m@example.com','890-123-4567','2022-09-05','password123',1),
                              (49,'Ramesh','Singh','753 Birch Lane, Innovation District','ramesh.s@example.com','901-234-5678','2023-01-02','password456',1),
                              (50,'Chen','Liu','963 Pinecrest Road, Silicon Valley','chen.liu@example.com','012-345-6789','2022-10-18','passwordabc',2);
/*!40000 ALTER TABLE `anstalld` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `avd_hallbarhet`
--

DROP TABLE IF EXISTS `avd_hallbarhet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `avd_hallbarhet` (
  `avdid` int NOT NULL,
  `hid` int NOT NULL,
  PRIMARY KEY (`avdid`,`hid`),
  KEY `Hid` (`hid`),
  CONSTRAINT `avd_hallbarhet_ibfk_1` FOREIGN KEY (`avdid`) REFERENCES `avdelning` (`avdid`),
  CONSTRAINT `avd_hallbarhet_ibfk_2` FOREIGN KEY (`hid`) REFERENCES `hallbarhetsmal` (`hid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `avd_hallbarhet`
--

LOCK TABLES `avd_hallbarhet` WRITE;
/*!40000 ALTER TABLE `avd_hallbarhet` DISABLE KEYS */;
INSERT INTO `avd_hallbarhet` VALUES (1,1),
                                    (1,2),
                                    (1,3),
                                    (2,4),
                                    (2,5),
                                    (2,6),
                                    (3,7),
                                    (3,8),
                                    (3,9);
/*!40000 ALTER TABLE `avd_hallbarhet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `avdelning`
--

DROP TABLE IF EXISTS `avdelning`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `avdelning` (
  `avdid` int NOT NULL,
  `namn` varchar(255) DEFAULT NULL,
  `beskrivning` varchar(255) DEFAULT NULL,
  `adress` varchar(255) DEFAULT NULL,
  `epost` varchar(255) DEFAULT NULL,
  `telefon` varchar(20) DEFAULT NULL,
  `stad` int DEFAULT NULL,
  `chef` int DEFAULT NULL,
  PRIMARY KEY (`avdid`),
  KEY `stad` (`stad`),
  KEY `chef` (`chef`),
  CONSTRAINT `avdelning_handlaggare_aid_fk` FOREIGN KEY (`chef`) REFERENCES `handlaggare` (`aid`),
  CONSTRAINT `avdelning_ibfk_1` FOREIGN KEY (`stad`) REFERENCES `stad` (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `avdelning`
--

LOCK TABLES `avdelning` WRITE;
/*!40000 ALTER TABLE `avdelning` DISABLE KEYS */;
INSERT INTO `avdelning` VALUES (1,'Avdelning för Hållbar Energi och Klimatförändringar','fokuserar på att främja användningen av förnybar energi och att bekämpa klimatförändringar genom utbildning, projektimplementering och politiska påverkansinsatser.','NGO-gatan 1','policy@ngo.org','123456789',1,NULL),
                               (2,'Avdelning för Samhällsutveckling och Utbildning','Teknisk utveckling och innovation för mänskliga rättigheter.','NGO-vägen 2','tech@ngo.org','123456789',2,NULL),
                               (3,'Avdelning för Teknologisk Innovation och Entreprenörskap','Kommunikation och medvetenhet om digitala rättigheter.','NGO-vägen 3','communication@ngo.org','123456789',3,NULL);
/*!40000 ALTER TABLE `avdelning` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hallbarhetsmal`
--

DROP TABLE IF EXISTS `hallbarhetsmal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hallbarhetsmal` (
  `hid` int NOT NULL,
  `namn` varchar(255) DEFAULT NULL,
  `malnummer` int DEFAULT NULL,
  `beskrivning` varchar(255) DEFAULT NULL,
  `prioritet` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`hid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hallbarhetsmal`
--

LOCK TABLES `hallbarhetsmal` WRITE;
/*!40000 ALTER TABLE `hallbarhetsmal` DISABLE KEYS */;
INSERT INTO `hallbarhetsmal` VALUES (1,'Inga fattiga',1,'Uppnå noll hunger och utrota all form av fattigdom över hela världen.','Hög'),
                                    (2,'Noll hunger',2,'Säkerställ hållbar matproduktionssystem och främja jordbruket för att uppnå näringsriktig kost för alla.','Hög'),
                                    (3,'God hälsa och välbefinnande',3,'Säkerställ hälsosamt liv och främja välbefinnande för alla i alla åldrar.','Hög'),
                                    (4,'Hållbart jordbruk',4,'Säkerställ hållbar matproduktion och främja hållbart jordbruk.','Hög'),
                                    (5,'Jämställdhet',5,'Uppnå jämställdhet och stärka alla kvinnor och flickor.','Hög'),
                                    (6,'Rent vatten och sanitet',6,'Säkerställ tillgång till rent vatten och sanitet för alla.','Hög'),
                                    (7,'Förnybar energi',7,'Säkerställ tillgång till pålitlig, hållbar och modern energi för alla.','Hög'),
                                    (8,'Anständiga arbetsvillkor',8,'Främja varaktig, inkluderande och hållbar ekonomisk tillväxt, full och produktiv sysselsättning och anständigt arbete för alla.','Hög'),
                                    (9,'Innovation och infrastruktur',9,'Bygga robust infrastruktur, främja inkluderande och hållbar industrialisering och öka innovation.','Medel'),
                                    (10,'Minskad ojämlikhet',10,'Minska ojämlikhet inom och mellan länder.','Medel'),
                                    (11,'Hållbara städer och samhällen',11,'Göra städer och mänskliga bosättningar inkluderande, säkra, motståndskraftiga och hållbara.','Medel'),
                                    (12,'Hållbara konsumtions- och produktionsmönster',12,'Säkerställa hållbara konsumtions- och produktionsmönster.','Medel'),
                                    (13,'Bekämpa klimatförändringarna',13,'Vidta omedelbara åtgärder för att bekämpa klimatförändringarna och dess påverkan.','Medel'),
                                    (14,'Hav och marina resurser',14,'Bevara och hållbart nyttja haven, haven och marina resurser för hållbar utveckling.','Låg'),
                                    (15,'Livet på land',15,'Skydda, återställa och främja hållbar användning av ekosystem, hållbart skogsbruk, bekämpa ökenspridning, stoppa och vända markdegradering och förlust av biologisk mångfald.','Låg'),
                                    (16,'Fredliga och inkluderande samhällen',16,'Främja fredliga och inkluderande samhällen för hållbar utveckling, ge tillgång till rättvisa för alla och bygga effektiva, ansvariga och inkluderande institutioner på alla nivåer.','Låg'),
                                    (17,'Samverkan och genomförande',17,'Stärka genomförandemedlen och revitalisera den globala partnerskapet för hållbar utveckling.','Låg');
/*!40000 ALTER TABLE `hallbarhetsmal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `handlaggare`
--

DROP TABLE IF EXISTS `handlaggare`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `handlaggare` (
  `aid` int NOT NULL,
  `ansvarighetsomrade` varchar(255) DEFAULT NULL,
  `mentor` int DEFAULT NULL,
  PRIMARY KEY (`aid`),
  KEY `handlaggare_handlaggare_aid_fk` (`mentor`),
  CONSTRAINT `handlaggare_handlaggare_aid_fk` FOREIGN KEY (`mentor`) REFERENCES `handlaggare` (`aid`),
  CONSTRAINT `handlaggare_ibfk_1` FOREIGN KEY (`aid`) REFERENCES `anstalld` (`aid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `handlaggare`
--

LOCK TABLES `handlaggare` WRITE;
/*!40000 ALTER TABLE `handlaggare` DISABLE KEYS */;
INSERT INTO `handlaggare` VALUES (5,'Energi och miljövänliga teknologier',8),
                                 (8,'Hållbart byggande och infrastruktur',NULL),
                                 (3,'Hållbara affärsprinciper och etik',NULL),
                                 (4,'Hållbar matproduktion och jordbruk',NULL),
                                 (10,'Energi och miljövänliga teknologier',5),
                                 (9,'Vattenförvaltning och renhållning',NULL),
                                 (13,'Hållbar matproduktion och jordbruk',4),
                                 (20,'Hållbar matproduktion och jordbruk',4),
                                 (30,'Energi och miljövänliga teknologier',1),
                                 (40,'Vattenförvaltning och renhållning',9),
                                 (50,'Hållbart byggande och infrastruktur',8),
                                 (26,'Hållbar matproduktion och jordbruk',3),
                                 (27,'Energi och miljövänliga teknologier',NULL),
                                 (28,'Vattenförvaltning och renhållning',NULL),
                                 (29,'Hållbart byggande och infrastruktur',26),
                                 (41,'Hållbart byggande och infrastruktur',29),
                                 (42,'Hållbar matproduktion och jordbruk',20),
                                 (43,'Energi och miljövänliga teknologier',NULL),
                                 (44,'Vattenförvaltning och renhållning',NULL),
                                 (45,'Hållbart byggande och infrastruktur',NULL),
                                 (46,'Energi och miljövänliga teknologier',NULL),
                                 (47,'Energi och miljövänliga teknologier',NULL),
                                 (48,'Energi och miljövänliga teknologier',NULL),
                                 (49,'Energi och miljövänliga teknologier',NULL);

/*!40000 ALTER TABLE `handlaggare` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `land`
--

DROP TABLE IF EXISTS `land`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `land` (
  `lid` int NOT NULL,
  `namn` varchar(100) DEFAULT NULL,
  `sprak` varchar(100) DEFAULT NULL,
  `valuta` decimal(13,4) DEFAULT NULL,
  `tidszon` varchar(20) DEFAULT NULL,
  `politisk_struktur` varchar(255) DEFAULT NULL,
  `ekonomi` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`lid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `land`
--

LOCK TABLES `land` WRITE;
/*!40000 ALTER TABLE `land` DISABLE KEYS */;
INSERT INTO `land` VALUES (1,'Land 1','Sprak 1',10.1234,'Tidszon 1','Politisk struktur 1','Ekonomi 1'),
                          (2,'Land 2','Sprak 2',20.5678,'Tidszon 2','Politisk struktur 2','Ekonomi 2'),
                          (3,'Land 3','Sprak 3',30.9876,'Tidszon 3','Politisk struktur 3','Ekonomi 3'),
                          (4,'Land 4','Sprak 4',40.5432,'Tidszon 4','Politisk struktur 4','Ekonomi 4'),
                          (5,'Land 5','Sprak 5',50.8765,'Tidszon 5','Politisk struktur 5','Ekonomi 5'),
                          (6,'Land 6','Sprak 6',60.4321,'Tidszon 6','Politisk struktur 6','Ekonomi 6');
/*!40000 ALTER TABLE `land` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `partner`
--

DROP TABLE IF EXISTS `partner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `partner` (
  `pid` int NOT NULL,
  `namn` varchar(255) DEFAULT NULL,
  `kontaktperson` varchar(255) DEFAULT NULL,
  `kontaktepost` varchar(255) DEFAULT NULL,
  `telefon` varchar(20) DEFAULT NULL,
  `adress` varchar(255) DEFAULT NULL,
  `branch` varchar(255) DEFAULT NULL,
  `stad` int DEFAULT NULL,
  PRIMARY KEY (`pid`),
  KEY `stad` (`stad`),
  CONSTRAINT `partner_ibfk_1` FOREIGN KEY (`stad`) REFERENCES `stad` (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `partner`
--

LOCK TABLES `partner` WRITE;
/*!40000 ALTER TABLE `partner` DISABLE KEYS */;
INSERT INTO `partner` VALUES (1,'GreenTech Solutions','Anna Berg','anna@greentech.com','+1234567890','123 Green Street','Ren energi',1),
                             (2,'Eco-Friendly Textiles','David Nilsson','david@ecofabrics.com','+0987654321','456 Eco Road','Hållbart mode',2),
                             (3,'CleanWater Initiative','Maria Rodriguez','maria@cleanwater.org','+1122334455','789 Blue Avenue','Rent vatten',3),
                             (4,'Sustainable Agriculture Co.','Michael Smith','michael@sustainableagri.com','+3344556677','101 Farm Lane','Hållbar matproduktion',4),
                             (5,'Recycle Now','Emma Johnson','emma@recyclenow.org','+5566778899','246 Recycle Boulevard','Återvinning',5),
                             (6,'Green Building Solutions','Peter Wang','peter@greenbuildings.com','+9988776655','369 Green Lane','Miljövänliga byggmaterial',1),
                             (7,'Renewable Energy Co.','Sophie Brown','sophie@renewenergyco.com','+1122334455','753 Solar Street','Förnybar energi',2),
                             (8,'FairTrade Crafts','Luisa Martinez','luisa@faircrafts.com','+3322114455','852 Fair Avenue','Rättvis handel',3),
                             (9,'Ocean Conservation Group','Jack Wilson','jack@oceanconservation.org','+1231231234','963 Ocean Road','Havsmiljö',4),
                             (10,'Eco-Friendly Transportation','Emily Green','emily@ecotransport.com','+9876543210','741 Eco Lane','Miljövänlig transport',5);
/*!40000 ALTER TABLE `partner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proj_hallbarhet`
--

DROP TABLE IF EXISTS `proj_hallbarhet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `proj_hallbarhet` (
  `pid` int NOT NULL,
  `hid` int NOT NULL,
  PRIMARY KEY (`pid`,`hid`),
  KEY `Hid` (`hid`),
  CONSTRAINT `proj_hallbarhet_ibfk_1` FOREIGN KEY (`pid`) REFERENCES `projekt` (`pid`),
  CONSTRAINT `proj_hallbarhet_ibfk_2` FOREIGN KEY (`hid`) REFERENCES `hallbarhetsmal` (`hid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proj_hallbarhet`
--

LOCK TABLES `proj_hallbarhet` WRITE;
/*!40000 ALTER TABLE `proj_hallbarhet` DISABLE KEYS */;
INSERT INTO `proj_hallbarhet` VALUES (1,1),
                                     (6,1),
                                     (1,2),
                                     (7,2),
                                     (1,3),
                                     (7,3),
                                     (2,4),
                                     (7,4),
                                     (2,5),
                                     (8,5),
                                     (2,6),
                                     (8,6),
                                     (3,7),
                                     (8,7),
                                     (3,8),
                                     (9,8),
                                     (3,9),
                                     (9,9),
                                     (4,10),
                                     (9,10),
                                     (4,11),
                                     (10,11),
                                     (4,12),
                                     (10,12),
                                     (5,13),
                                     (10,13),
                                     (5,14),
                                     (5,15),
                                     (6,16),
                                     (6,17);
/*!40000 ALTER TABLE `proj_hallbarhet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `projekt`
--

DROP TABLE IF EXISTS `projekt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `projekt` (
  `pid` int NOT NULL,
  `projektnamn` varchar(255) DEFAULT NULL,
  `beskrivning` varchar(255) DEFAULT NULL,
  `startdatum` date DEFAULT NULL,
  `slutdatum` date DEFAULT NULL,
  `kostnad` decimal(12,2) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  `prioritet` varchar(100) DEFAULT NULL,
  `projektchef` int DEFAULT NULL,
  `land` int DEFAULT NULL,
  PRIMARY KEY (`pid`),
  KEY `projektchef` (`projektchef`),
  KEY `land` (`land`),
  CONSTRAINT `projekt_ibfk_1` FOREIGN KEY (`projektchef`) REFERENCES `anstalld` (`aid`),
  CONSTRAINT `projekt_ibfk_2` FOREIGN KEY (`land`) REFERENCES `land` (`lid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projekt`
--

LOCK TABLES `projekt` WRITE;
/*!40000 ALTER TABLE `projekt` DISABLE KEYS */;
INSERT INTO `projekt` VALUES (1,'Projekt 1','Beskrivning av projekt 1','2023-01-01','2023-06-30',10000.00,'Pågående','Hög',1,1),
                             (2,'Projekt 2','Beskrivning av projekt 2','2023-02-01','2023-07-31',15000.00,'Planerat','Medel',2,2),
                             (3,'Projekt 3','Beskrivning av projekt 3','2023-03-01','2023-08-31',12000.00,'Avslutat','Låg',3,3),
                             (4,'Projekt 4','Beskrivning av projekt 4','2023-04-01','2023-09-30',18000.00,'Pågående','Hög',4,4),
                             (5,'Projekt 5','Beskrivning av projekt 5','2023-05-01','2023-10-31',13000.00,'Pågående','Medel',5,5),
                             (6,'Projekt 6','Beskrivning av projekt 6','2023-06-01','2023-11-30',20000.00,'Planerat','Låg',6,1),
                             (7,'Projekt 7','Beskrivning av projekt 7','2023-07-01','2023-12-31',16000.00,'Pågående','Hög',7,2),
                             (8,'Projekt 8','Beskrivning av projekt 8','2023-08-01','2024-01-31',14000.00,'Avslutat','Medel',8,3),
                             (9,'Projekt 9','Beskrivning av projekt 9','2023-09-01','2024-02-29',17000.00,'Pågående','Låg',9,4),
                             (10,'Projekt 10','Beskrivning av projekt 10','2023-10-01','2024-03-31',22000.00,'Planerat','Hög',10,5);
/*!40000 ALTER TABLE `projekt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `projekt_partner`
--

DROP TABLE IF EXISTS `projekt_partner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `projekt_partner` (
  `pid` int NOT NULL,
  `partner_pid` int NOT NULL,
  PRIMARY KEY (`pid`,`partner_pid`),
  KEY `partner_pid` (`partner_pid`),
  CONSTRAINT `projekt_partner_ibfk_1` FOREIGN KEY (`pid`) REFERENCES `projekt` (`pid`),
  CONSTRAINT `projekt_partner_ibfk_2` FOREIGN KEY (`partner_pid`) REFERENCES `partner` (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projekt_partner`
--

LOCK TABLES `projekt_partner` WRITE;
/*!40000 ALTER TABLE `projekt_partner` DISABLE KEYS */;
INSERT INTO `projekt_partner` VALUES (1,1),
                                     (6,1),
                                     (1,2),
                                     (8,2),
                                     (2,3),
                                     (6,3),
                                     (2,4),
                                     (8,4),
                                     (3,5),
                                     (7,5),
                                     (3,6),
                                     (9,6),
                                     (4,7),
                                     (7,7),
                                     (4,8),
                                     (9,8),
                                     (5,9),
                                     (10,9),
                                     (5,10),
                                     (10,10);
/*!40000 ALTER TABLE `projekt_partner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stad`
--

DROP TABLE IF EXISTS `stad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stad` (
  `sid` int NOT NULL,
  `namn` varchar(255) DEFAULT NULL,
  `land` int DEFAULT NULL,
  PRIMARY KEY (`sid`),
  KEY `land` (`land`),
  CONSTRAINT `stad_ibfk_1` FOREIGN KEY (`land`) REFERENCES `land` (`lid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stad`
--

LOCK TABLES `stad` WRITE;
/*!40000 ALTER TABLE `stad` DISABLE KEYS */;
INSERT INTO `stad` VALUES (1,'Örebro',1),
                          (2,'Stockholm',1),
                          (3,'Horndal',1),
                          (4,'Stad 4',2),
                          (5,'Stad 5',3),
                          (6,'Stad 6',3);
/*!40000 ALTER TABLE `stad` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-23  9:01:22

