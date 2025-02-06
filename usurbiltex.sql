-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: usurbiltex
-- ------------------------------------------------------
-- Server version	8.0.40

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `usurbiltex`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `usurbiltex` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `usurbiltex`;

--
-- Table structure for table `categorias`
--

DROP TABLE IF EXISTS `categorias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categorias` (
  `id_categoria` int NOT NULL,
  `Nombre` varchar(50) DEFAULT NULL,
  `descripcion` varchar(400) DEFAULT NULL,
  PRIMARY KEY (`id_categoria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categorias`
--

LOCK TABLES `categorias` WRITE;
/*!40000 ALTER TABLE `categorias` DISABLE KEYS */;
INSERT INTO `categorias` VALUES (1,'Pantalones','Variedad de pantalones para todas las ocasiones'),(2,'Sudaderas','Sudaderas cómodas y con estilo'),(3,'Camisetas','Camisetas de diferentes colores y estilos'),(4,'Camisas','Camisas formales e informales'),(5,'Chaquetas','Chaquetas para todas las estaciones');
/*!40000 ALTER TABLE `categorias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalle_pedido`
--

DROP TABLE IF EXISTS `detalle_pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalle_pedido` (
  `id_pedido` int DEFAULT NULL,
  `id_producto` int DEFAULT NULL,
  `Cantidad` int DEFAULT NULL,
  `Precio` int DEFAULT NULL,
  `id_detallePedido` int DEFAULT NULL,
  UNIQUE KEY `id_detallePedido` (`id_detallePedido`),
  KEY `id_pedido` (`id_pedido`),
  KEY `id_producto` (`id_producto`),
  CONSTRAINT `detalle_pedido_ibfk_1` FOREIGN KEY (`id_pedido`) REFERENCES `pedidos` (`id_pedido`),
  CONSTRAINT `detalle_pedido_ibfk_2` FOREIGN KEY (`id_producto`) REFERENCES `productos` (`id_producto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_pedido`
--

LOCK TABLES `detalle_pedido` WRITE;
/*!40000 ALTER TABLE `detalle_pedido` DISABLE KEYS */;
INSERT INTO `detalle_pedido` VALUES (1,1,2,40,1),(1,3,1,15,2),(2,2,1,43,3),(2,4,1,15,4),(3,5,1,30,5),(4,6,2,30,6),(4,7,1,28,7),(5,8,3,20,8),(5,9,2,23,9),(6,10,1,15,10),(6,11,1,28,11),(6,12,1,30,12),(7,1,1,40,13),(7,2,1,43,14),(7,3,1,28,15),(8,4,1,60,16),(9,5,2,40,17),(10,6,1,75,18),(7,1,1,40,19),(7,2,1,43,20),(7,3,1,28,21),(8,4,1,60,22),(9,5,2,40,23),(10,6,1,75,24),(11,1,5,40,25),(11,3,5,35,26),(11,5,2,40,27),(12,6,8,75,28),(12,9,6,20,29),(12,10,4,15,30),(13,11,7,28,31),(13,12,6,30,32),(13,14,5,50,33),(14,3,2,35,34),(14,7,1,30,35),(14,13,3,25,36),(15,5,3,40,37),(15,10,2,20,38),(16,4,1,60,39),(16,6,2,75,40),(17,12,3,20,41),(17,9,2,23,42),(18,1,3,40,43),(18,4,2,60,44),(18,6,1,75,45),(19,7,1,30,46),(19,9,3,20,47),(19,12,2,28,48),(20,2,5,43,49),(20,8,2,35,50),(20,10,1,15,51),(21,1,4,40,52),(21,14,1,50,53),(22,5,3,40,54),(22,3,2,35,55),(23,12,1,15,56),(23,13,2,25,57),(24,8,4,33,58),(24,16,3,45,59),(25,2,2,43,60),(25,6,1,75,61),(25,14,2,50,62),(26,11,2,28,63),(26,10,3,15,64);
/*!40000 ALTER TABLE `detalle_pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedidos`
--

DROP TABLE IF EXISTS `pedidos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pedidos` (
  `id_pedido` int NOT NULL,
  `PrecioTotal` int DEFAULT NULL,
  `FechaPedido` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `id_user` int DEFAULT NULL,
  `Estado` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id_pedido`),
  KEY `id_user` (`id_user`),
  CONSTRAINT `pedidos_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `usuario` (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedidos`
--

LOCK TABLES `pedidos` WRITE;
/*!40000 ALTER TABLE `pedidos` DISABLE KEYS */;
INSERT INTO `pedidos` VALUES (1,90,'2025-01-31 22:00:00',1,'Completado'),(2,45,'2025-02-01 22:00:00',2,'Pendiente'),(3,30,'2025-02-02 22:00:00',3,'Enviado'),(4,100,'2025-02-03 22:00:00',4,'Completado'),(5,150,'2025-02-04 22:00:00',5,'Enviado'),(6,80,'2025-02-05 22:00:00',6,'Pendiente'),(7,110,'2025-02-06 22:00:00',7,'Completado'),(8,60,'2025-02-07 22:00:00',8,'Enviado'),(9,90,'2025-02-08 22:00:00',9,'Pendiente'),(10,75,'2025-02-09 22:00:00',10,'Completado'),(11,110,'2025-02-06 22:00:00',7,'Completado'),(12,60,'2025-02-07 22:00:00',8,'Enviado'),(13,90,'2025-01-08 22:00:00',9,'Pendiente'),(14,75,'2025-01-09 22:00:00',10,'Completado'),(15,521,'2025-01-10 22:00:00',1,'Completado'),(16,611,'2025-01-11 22:00:00',3,'Enviado'),(17,546,'2024-12-12 22:00:00',5,'Pendiente'),(18,400,'2024-12-14 22:00:00',1,'Completado'),(19,271,'2025-02-15 22:00:00',1,'Pendiente'),(20,130,'2024-12-16 22:00:00',1,'Enviado'),(21,216,'2024-07-31 20:00:00',1,'Completado'),(22,124,'2024-08-04 20:00:00',2,'Enviado'),(23,346,'2024-08-09 20:00:00',3,'Pendiente'),(24,530,'2024-08-31 20:00:00',4,'Completado'),(25,119,'2024-09-14 20:00:00',5,'Enviado'),(26,403,'2024-10-01 20:00:00',6,'Pendiente'),(27,150,'2024-10-11 20:00:00',7,'Completado'),(28,328,'2024-10-31 22:00:00',8,'Completado'),(29,220,'2024-11-09 22:00:00',9,'Enviado'),(30,500,'2024-11-30 22:00:00',10,'Pendiente');
/*!40000 ALTER TABLE `pedidos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productos`
--

DROP TABLE IF EXISTS `productos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productos` (
  `id_producto` int NOT NULL,
  `Nombre` varchar(50) DEFAULT NULL,
  `Descripcion` varchar(300) DEFAULT NULL,
  `Precio` int DEFAULT NULL,
  `Stock` int DEFAULT NULL,
  `FechaCreacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `id_categoria` int DEFAULT NULL,
  `Imagen` varchar(10000) DEFAULT NULL,
  PRIMARY KEY (`id_producto`),
  KEY `id_categoria` (`id_categoria`),
  CONSTRAINT `productos_ibfk_1` FOREIGN KEY (`id_categoria`) REFERENCES `categorias` (`id_categoria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productos`
--

LOCK TABLES `productos` WRITE;
/*!40000 ALTER TABLE `productos` DISABLE KEYS */;
INSERT INTO `productos` VALUES (1,'Pantalón vaquero','Pantalón de mezclilla azul',40,20,'2025-02-02 10:01:09',1,'https://upload.wikimedia.org/wikipedia/commons/9/92/Denimjeans2.JPG'),(2,'Pantalón cargo','Pantalón cómodo con bolsillos adicionales',43,25,'2025-02-02 10:01:09',1,'https://upload.wikimedia.org/wikipedia/commons/c/cd/Cargo_pants_001.jpg'),(3,'Pantalón deportivo','Pantalón ajustado para hacer ejercicio',35,30,'2025-02-02 10:01:09',1,'https://upload.wikimedia.org/wikipedia/commons/8/84/Tracksuit_bottoms.jpg'),(4,'Pantalón chino','Pantalón elegante para ocasiones formales',45,18,'2025-02-02 10:01:09',1,'https://upload.wikimedia.org/wikipedia/commons/3/3e/Chino_pants.jpg'),(5,'Pantalón de lino','Pantalón ligero para el verano',40,1,'2025-02-02 10:01:09',1,'https://upload.wikimedia.org/wikipedia/commons/8/82/Trousers_MET_1978.88.12_F.jpg'),(6,'Sudadera con capucha','Sudadera negra con bolsillo delantero',30,15,'2025-02-02 10:01:09',2,'https://upload.wikimedia.org/wikipedia/commons/e/e5/Sudadera_urban.jpg'),(7,'Sudadera sin capucha','Sudadera básica para cualquier ocasión',28,0,'2025-02-02 10:01:09',2,'https://upload.wikimedia.org/wikipedia/commons/e/ea/Jersei-coll-alt.jpg'),(8,'Sudadera estampada','Sudadera con diseño gráfico',33,10,'2025-02-02 10:01:09',2,'https://upload.wikimedia.org/wikipedia/commons/1/16/Dale_of_Norway_sweater.jpg'),(9,'Sudadera deportiva','Sudadera ajustada para entrenar',35,12,'2025-02-02 10:01:09',2,'https://upload.wikimedia.org/wikipedia/commons/6/6d/Windjack_-_Windbreaker.jpg'),(10,'Camiseta blanca','Camiseta de algodón blanco',15,30,'2025-02-02 10:01:09',3,'https://upload.wikimedia.org/wikipedia/commons/c/cd/T-Shirt_Wikipedia_white.jpg'),(11,'Camiseta negra','Camiseta básica de color negro',15,25,'2025-02-02 10:01:09',3,'https://upload.wikimedia.org/wikipedia/commons/3/37/T-Shirt_Wikipedia_black.jpg'),(12,'Camiseta con estampado','Camiseta con diseño moderno',20,2,'2025-02-02 10:01:09',3,'https://upload.wikimedia.org/wikipedia/commons/c/c9/New-style-T-shirt-00455657.jpg'),(13,'Camiseta de manga larga','Camiseta cómoda para clima frío',23,18,'2025-02-02 10:01:09',3,'https://fakestoreapi.com/img/71YXzeOuslL._AC_UY879_.jpg'),(14,'Camisa de lino','Camisa ligera ideal para verano',25,10,'2025-02-02 10:01:09',4,'https://upload.wikimedia.org/wikipedia/commons/6/68/Shirt%2C_man%27s_%28AM_2016.80.2-3%29.jpg'),(15,'Camisa a cuadros','Camisa de estilo casual',28,20,'2025-02-02 10:01:09',4,'https://upload.wikimedia.org/wikipedia/commons/2/2c/Check_shirt.jpg'),(16,'Camisa formal','Camisa para ocasiones elegantes',30,18,'2025-02-02 10:01:09',4,'https://upload.wikimedia.org/wikipedia/commons/0/09/Shirt%2C_men%27s_%28AM_2015.44.1-1%29.jpg'),(17,'Camisa hawaiana','Camisa fresca con estampado tropical',25,14,'2025-02-02 10:01:09',4,'https://upload.wikimedia.org/wikipedia/commons/3/3c/Guiness_hilo_hattie_aloha_shirt.jpg'),(18,'Chaqueta de cuero','Chaqueta de cuero sintético',60,3,'2025-02-02 10:01:09',5,'https://upload.wikimedia.org/wikipedia/commons/b/b8/Leather_Jacket_%2851011162080%29.jpg'),(19,'Chaqueta de mezclilla','Chaqueta estilo denim',50,8,'2025-02-02 10:01:09',5,'https://upload.wikimedia.org/wikipedia/commons/1/10/Denim_Jacket_%2851079649933%29.jpg'),(20,'Chaqueta acolchada','Chaqueta para clima frío',70,12,'2025-02-02 10:01:09',5,'https://upload.wikimedia.org/wikipedia/commons/d/df/MA-1_Jacket_in_petrol.jpg'),(21,'Chaqueta impermeable','Chaqueta para días de lluvia',75,0,'2025-02-02 10:01:09',5,'https://upload.wikimedia.org/wikipedia/commons/4/46/Windbreaker_Jacket%2C_Hood_Outside.jpg');
/*!40000 ALTER TABLE `productos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id_user` int NOT NULL,
  `Nombre` varchar(50) DEFAULT NULL,
  `DNI` varchar(9) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `contraseña` varchar(100) DEFAULT NULL,
  `FechaRegistro` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `Rol` varchar(100) DEFAULT NULL,
  `Direccion` varchar(1000) DEFAULT NULL,
  `telefono` varchar(15) DEFAULT NULL,
  `Apellido` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id_user`),
  UNIQUE KEY `id_user` (`id_user`),
  UNIQUE KEY `DNI` (`DNI`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `contraseña` (`contraseña`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'ikerb','78451236K','ikerb@email.com','14e1b600b1fd579f47433b88e8d85291','2025-02-02 10:01:20','usuario','Kale Nagusia 123','123456789','Bengoetxea'),(2,'aintza','98653274p','aintza@email.com','14e1b600b1fd579f47433b88e8d85292','2025-02-02 10:01:20','admin','Euskal Herria 45','987654321','Zubiri'),(3,'gorkaa','54213687p','gorkaa@email.com','14e1b600b1fd579f47433b88e8d85293','2025-02-02 10:01:20','usuario','Aldapa 12','654321987','Aldaz'),(4,'maialenl','85214795t','maialenl@email.com','14e1b600b1fd579f47433b88e8d85294','2025-02-02 10:01:20','usuario','Urumea Etorbidea 78','111223344','Larrañaga'),(5,'unaiog','96321457w','unaiog@email.com','14e1b600b1fd579f47433b88e8d85295','2025-02-02 10:01:20','usuario','Donosti Kalea 56','222334455','Oiarzabal'),(6,'aneet','98655487b','aneet@email.com','14e1b600b1fd579f47433b88e8d85296','2025-02-02 10:01:20','usuario','Mikel Zarate Plaza 7','333445566','Etxebarria'),(7,'anderu','12457864a','anderu@email.com','14e1b600b1fd579f47433b88e8d85297','2025-02-02 10:01:20','usuario','Jauregi Bidea 9','444556677','Ugalde'),(8,'itziah','32549865K','itziah@email.com','14e1b600b1fd579f47433b88e8d85298','2025-02-02 10:01:20','usuario','Goikoa Kalea 3','555667788','Agirre'),(9,'jonpa','21545878M','jonpa@email.com','14e1b600b1fd579f47433b88e8d85299','2025-02-02 10:01:20','usuario','Ondarreta Pasealekua 23','666778899','Patxi'),(10,'nereai','72548146J','nereai@email.com','14e1b600b1fd579f47433b88e8d85290','2025-02-02 10:01:20','usuario','Hondarribia Kalea 34','777889900','Irigoien');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-02-06 11:57:36
