-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: localhost    Database: tender_db
-- ------------------------------------------------------
-- Server version	8.0.46

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tender_proposals`
--

DROP TABLE IF EXISTS `tender_proposals`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tender_proposals` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tenderId` int NOT NULL,
  `executorId` int NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `description` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_tender_executor` (`tenderId`,`executorId`),
  KEY `executorId` (`executorId`),
  CONSTRAINT `tender_proposals_ibfk_1` FOREIGN KEY (`tenderId`) REFERENCES `tenders` (`id`) ON DELETE CASCADE,
  CONSTRAINT `tender_proposals_ibfk_2` FOREIGN KEY (`executorId`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tender_proposals`
--

LOCK TABLES `tender_proposals` WRITE;
/*!40000 ALTER TABLE `tender_proposals` DISABLE KEYS */;
INSERT INTO `tender_proposals` VALUES (18,17,2,45000.00,'Готові виконати проект за 3 тижні. Включено дизайн, верстку та розгортання на сервері.'),(19,17,3,38500.50,'Маємо великий досвід розробки подібних сайтів. Пропонуємо знижку 10% на подальшу технічну підтримку.'),(20,18,4,125000.00,'Пропонуємо обладнання зі складу в Києві. Гарантія 3 роки, безкоштовна доставка та базове налаштування.'),(21,19,2,15000.00,'Маю 4 роки досвіду роботи з GitLab CI та Docker. Налаштую безперебійний pipeline за 3-4 дні.'),(22,20,1,85000.00,'Команда з 3 розробників. Працюємо на Flutter, реалізуємо весь описаний функціонал за 1.5 місяці.');
/*!40000 ALTER TABLE `tender_proposals` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tenders`
--

DROP TABLE IF EXISTS `tenders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tenders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` text,
  `status` varchar(50) DEFAULT NULL,
  `ownerId` int DEFAULT NULL,
  `executorId` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tenders`
--

LOCK TABLES `tenders` WRITE;
/*!40000 ALTER TABLE `tenders` DISABLE KEYS */;
INSERT INTO `tenders` VALUES (5,'1 км дороги в Чорнобилі)','Потрібні потужні люди, які не жаліються на радіацію і асфальт, який не проб\'є мутант','ACTIVE',3,NULL),(17,'Поставка 3000 крисЛіхтариків','Необхідно створити 3000 крисЛіхтариків і доставити їх на планету Крис','ACTIVE',4,NULL),(18,'Розробка корпоративного вебсайту','Створення адаптивного сайту для логістичної компанії, інтеграція з існуючою CRM-системою та налаштування адмін-панелі.','ACTIVE',1,NULL),(19,'Постачання серверного обладнання','Закупівля та встановлення двох серверів Dell PowerEdge R740 для розширення локального дата-центру.','CLOSED',2,3),(20,'Налаштування CI/CD пайплайну','Шукаємо DevOps-інженера для міграції проєкту на GitLab CI, налаштування Docker-контейнерів та автоматичного деплою.','ACTIVE',4,NULL),(21,'Розробка мобільного додатку (Flutter)','Створення кросплатформного MVP додатку для мережі кав\'ярень. Функціонал: програма лояльності, карта закладів, онлайн-замовлення.','CLOSED',1,4),(22,'SEO-оптимізація інтернет-магазину','Проведення глибокого технічного аудиту сайту на платформі OpenCart, збір семантичного ядра та внутрішня оптимізація сторінок.','ACTIVE',3,NULL),(23,'Оновлення парку комп\'ютерної техніки','Закупівля 20 ноутбуків для відділу розробки (мінімальні характеристики: 32GB RAM, 1TB SSD, процесор не нижче Intel Core i7 13-го покоління).','ACTIVE',2,NULL);
/*!40000 ALTER TABLE `tenders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `login` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Kris','Kris322','SercretPassword'),(2,'Sergi','Sergi123','Password123'),(3,'ТОВ ТехАвангард','tech_avangard','SecurePass2026'),(4,'Макс','m_krishka','Krishka!51');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-05-18  9:18:15
