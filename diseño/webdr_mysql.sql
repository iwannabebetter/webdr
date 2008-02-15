-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.0.45-community-nt


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema webdr
--

CREATE DATABASE IF NOT EXISTS webdr;
USE webdr;

--
-- Definition of table `app_user`
--

DROP TABLE IF EXISTS `app_user`;
CREATE TABLE `app_user` (
  `id` bigint(20) NOT NULL auto_increment,
  `username` varchar(50) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password_hint` varchar(255) default NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `phone_number` varchar(255) default NULL,
  `website` varchar(255) default NULL,
  `account_expired` bit(1) NOT NULL,
  `account_locked` bit(1) NOT NULL,
  `credentials_expired` bit(1) NOT NULL,
  `city` varchar(50) NOT NULL,
  `province` varchar(100) default NULL,
  `postal_code` varchar(15) NOT NULL,
  `address` varchar(150) default NULL,
  `country` varchar(100) default NULL,
  `account_enabled` bit(1) default NULL,
  `version` int(11) default NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `app_user`
--

/*!40000 ALTER TABLE `app_user` DISABLE KEYS */;
INSERT INTO `app_user` (`id`,`username`,`email`,`password_hint`,`first_name`,`last_name`,`phone_number`,`website`,`account_expired`,`account_locked`,`credentials_expired`,`city`,`province`,`postal_code`,`address`,`country`,`account_enabled`,`version`,`password`) VALUES 
 (-9,'bambam','meyer.hugo4@gmail.com','Same as username.','Bambam','Mármol','021-481298','-',0x00,0x00,0x00,'Asunción','ASU','0000','Ybyturuzu','PY',0x01,1,'d8680aea3e8adf668a249eba62192fa1ed9336f1'),
 (-8,'ppicapiedra','meyer.hugo3@gmail.com','Same as username.','Pedro','Picapiedra','021-481298','-',0x00,0x00,0x00,'Asunción','ASU','0000','Ybyturuzu','PY',0x01,1,'d8680aea3e8adf668a249eba62192fa1ed9336f1'),
 (-7,'pmarmol','meyer.hugo1@gmail.com','Same as username.','Pablo','Mármol','021-481298','-',0x00,0x00,0x00,'Asunción','ASU','0000','Ybyturuzu','PY',0x01,1,'d8680aea3e8adf668a249eba62192fa1ed9336f1'),
 (-6,'hmeyer','meyer.hugo@gmail.com','Same as username.','Hugo','Meyer','021-481298','-',0x00,0x00,0x00,'Asunción','ASU','0000','Ybyturuzu','PY',0x01,1,'c17707978d3df96e1f4cd1fa03d62b793925c159'),
 (-5,'cparra','cdparra@gmail.com','Same as username.','Cristhian','Parra','021-642403','-',0x00,0x00,0x00,'Luque','ASU','0000','Cap. Bado Nº 21','PY',0x01,1,'49f8a6d8dd2ec83241d511b4a1a001542bdf4440'),
 (-4,'fmancia','fernandomancia@gmail.com','Same as username.','Fernando','Mancía','681012','-',0x00,0x00,0x00,'Asunción','ASU','0000','Ybyturuzu','PY',0x01,1,'7d31e6ad1e4a7b942b2dac0c5d970bf3be8eebd2'),
 (-3,'ghuttemann','ghuttemann@gmail.com','Same as username.','Germán','Hüttemann','','http://ghuttemann.googlepages.com',0x00,0x00,0x00,'Asunción','ASU','12345','Cerro Porteño','PY',0x01,1,'d8680aea3e8adf668a249eba62192fa1ed9336f1'),
 (-2,'admin','matt@raibledesigns.com','Not a female kitty.','Matt','Raible','','http://raibledesigns.com',0x00,0x00,0x00,'Denver','CO','80210','Cueva','US',0x01,1,'d033e22ae348aeb5660fc2140aec35850c4da997'),
 (-1,'user','matt_raible@yahoo.com','A male kitty.','Tomcat','User','','http://tomcat.apache.org',0x00,0x00,0x00,'Denver','CO','80210','En su casa','US',0x01,1,'12dea96fec20593566ab75692c9949596833adc9'),
 (1,'mrodas','rodas.marcelo@gmail.com','','marcelo','rodas','','',0x00,0x00,0x00,'Asunción','','555','','',0x01,0,'e6f71654283c01938369585c658527c2f76a2cda');
/*!40000 ALTER TABLE `app_user` ENABLE KEYS */;


--
-- Definition of table `consulta`
--

DROP TABLE IF EXISTS `consulta`;
CREATE TABLE `consulta` (
  `id` bigint(20) NOT NULL auto_increment,
  `fecha` datetime NOT NULL,
  `peso_actual` double default NULL,
  `altura_actual` int(11) default NULL,
  `edad_actual` int(11) NOT NULL,
  `edad_meses` bit(1) NOT NULL,
  `sintomas` text NOT NULL,
  `diagnostico` text NOT NULL,
  `recetario` text,
  `indicaciones` text,
  `paciente_id` bigint(20) NOT NULL,
  `doctor_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FKDE2881F597180A7` (`paciente_id`),
  KEY `FKDE2881F557E83927` (`doctor_id`),
  CONSTRAINT `FKDE2881F557E83927` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`id`),
  CONSTRAINT `FKDE2881F597180A7` FOREIGN KEY (`paciente_id`) REFERENCES `paciente` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `consulta`
--

/*!40000 ALTER TABLE `consulta` DISABLE KEYS */;
INSERT INTO `consulta` (`id`,`fecha`,`peso_actual`,`altura_actual`,`edad_actual`,`edad_meses`,`sintomas`,`diagnostico`,`recetario`,`indicaciones`,`paciente_id`,`doctor_id`) VALUES 
 (-3,'2000-10-09 09:00:00',72,171,21,0x00,'Dolor de Cabeza','Intoxicacion','Aspirina','Tomar una pastilla por dia',-4,-3),
 (-2,'1998-11-23 07:00:00',80,155,27,0x00,'Jaqueca','Chupo Mucho','Aspirina','Tomar dos pastilla por dia',-4,-3),
 (-1,'1984-05-05 10:00:00',76,178,22,0x00,'Esta por morir','murio','cualquiera','suicidate',-4,-3);
/*!40000 ALTER TABLE `consulta` ENABLE KEYS */;


--
-- Definition of table `doctor`
--

DROP TABLE IF EXISTS `doctor`;
CREATE TABLE `doctor` (
  `id` bigint(20) NOT NULL,
  `fechanac` date NOT NULL,
  `registro` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `registro` (`registro`),
  KEY `FKB0EF911F1B80D7F3` (`id`),
  CONSTRAINT `FKB0EF911F1B80D7F3` FOREIGN KEY (`id`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `doctor`
--

/*!40000 ALTER TABLE `doctor` DISABLE KEYS */;
INSERT INTO `doctor` (`id`,`fechanac`,`registro`) VALUES 
 (-9,'1982-04-28',6792),
 (-8,'1982-04-28',6791),
 (-7,'1982-04-28',6790),
 (-3,'1982-04-28',6789),
 (1,'1985-02-05',12345);
/*!40000 ALTER TABLE `doctor` ENABLE KEYS */;


--
-- Definition of table `doctor_especialidad`
--

DROP TABLE IF EXISTS `doctor_especialidad`;
CREATE TABLE `doctor_especialidad` (
  `doctor_id` bigint(20) NOT NULL,
  `especialidad_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`doctor_id`,`especialidad_id`),
  KEY `FKDC3E9C5232460E87` (`especialidad_id`),
  KEY `FKDC3E9C5257E83927` (`doctor_id`),
  CONSTRAINT `FKDC3E9C5232460E87` FOREIGN KEY (`especialidad_id`) REFERENCES `especialidad` (`id`),
  CONSTRAINT `FKDC3E9C5257E83927` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `doctor_especialidad`
--

/*!40000 ALTER TABLE `doctor_especialidad` DISABLE KEYS */;
INSERT INTO `doctor_especialidad` (`doctor_id`,`especialidad_id`) VALUES 
 (-8,-6),
 (-9,-5),
 (1,-5),
 (-9,-4),
 (1,-4),
 (-9,-2),
 (-7,-2),
 (-7,-1),
 (-3,-1);
/*!40000 ALTER TABLE `doctor_especialidad` ENABLE KEYS */;


--
-- Definition of table `especialidad`
--

DROP TABLE IF EXISTS `especialidad`;
CREATE TABLE `especialidad` (
  `id` bigint(20) NOT NULL auto_increment,
  `nombre` varchar(50) NOT NULL,
  `descripcion` text NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `especialidad`
--

/*!40000 ALTER TABLE `especialidad` DISABLE KEYS */;
INSERT INTO `especialidad` (`id`,`nombre`,`descripcion`) VALUES 
 (-7,'Oftalmología','Trata problemas de la vista'),
 (-6,'Oncología','Trata problemas de cáncer y tumores'),
 (-5,'Odontología','Trata problemas de la boca y los dientes'),
 (-4,'Hematología','Trata problemas de la sangre'),
 (-3,'Cardiología','Trata problemas del corazón'),
 (-2,'Dermatología','Trata problemas de la piel'),
 (-1,'Psiquiatría','Trata los problemas de enfermos mentales');
/*!40000 ALTER TABLE `especialidad` ENABLE KEYS */;


--
-- Definition of table `horario`
--

DROP TABLE IF EXISTS `horario`;
CREATE TABLE `horario` (
  `id` bigint(20) NOT NULL auto_increment,
  `dia` int(11) NOT NULL,
  `hora_inicio` time NOT NULL,
  `hora_fin` time NOT NULL,
  `doctor_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FK416647E257E83927` (`doctor_id`),
  CONSTRAINT `FK416647E257E83927` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `horario`
--

/*!40000 ALTER TABLE `horario` DISABLE KEYS */;
INSERT INTO `horario` (`id`,`dia`,`hora_inicio`,`hora_fin`,`doctor_id`) VALUES 
 (-3,4,'10:30:00','22:00:00',-3),
 (-2,1,'08:30:00','16:00:00',-3),
 (-1,0,'07:30:00','14:00:00',-3),
 (1,2,'10:00:00','18:00:00',1),
 (2,4,'10:00:00','18:00:00',-9);
/*!40000 ALTER TABLE `horario` ENABLE KEYS */;


--
-- Definition of table `paciente`
--

DROP TABLE IF EXISTS `paciente`;
CREATE TABLE `paciente` (
  `id` bigint(20) NOT NULL,
  `fechanac` date NOT NULL,
  `fecingreso` date NOT NULL,
  `cedula` int(11) NOT NULL,
  `tiposangre_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `cedula_idx` (`cedula`),
  KEY `FK2CA713711B80D7F3` (`id`),
  KEY `FK2CA71371280CC867` (`tiposangre_id`),
  CONSTRAINT `FK2CA713711B80D7F3` FOREIGN KEY (`id`) REFERENCES `app_user` (`id`),
  CONSTRAINT `FK2CA71371280CC867` FOREIGN KEY (`tiposangre_id`) REFERENCES `tiposangre` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `paciente`
--

/*!40000 ALTER TABLE `paciente` DISABLE KEYS */;
INSERT INTO `paciente` (`id`,`fechanac`,`fecingreso`,`cedula`,`tiposangre_id`) VALUES 
 (-6,'1984-06-05','2007-01-02',3832548,-1),
 (-5,'1984-06-21','2007-01-02',2045856,-4),
 (-4,'1984-10-23','2007-01-02',3298639,-1);
/*!40000 ALTER TABLE `paciente` ENABLE KEYS */;


--
-- Definition of table `reserva`
--

DROP TABLE IF EXISTS `reserva`;
CREATE TABLE `reserva` (
  `id` bigint(20) NOT NULL auto_increment,
  `fecha_realizacion` datetime NOT NULL,
  `fecha_reservada` datetime NOT NULL,
  `cancelado` bit(1) default NULL,
  `observ_cancel` text,
  `turno_id` bigint(20) default NULL,
  `paciente_id` bigint(20) NOT NULL,
  `consulta_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `turno_id` (`turno_id`),
  UNIQUE KEY `consulta_id` (`consulta_id`),
  KEY `fec_reserv_idx` (`fecha_reservada`),
  KEY `fec_realiz_idx` (`fecha_realizacion`),
  KEY `FK41640CB8400EC227` (`consulta_id`),
  KEY `FK41640CB85C97730D` (`turno_id`),
  KEY `FK41640CB897180A7` (`paciente_id`),
  CONSTRAINT `FK41640CB8400EC227` FOREIGN KEY (`consulta_id`) REFERENCES `consulta` (`id`),
  CONSTRAINT `FK41640CB85C97730D` FOREIGN KEY (`turno_id`) REFERENCES `turno` (`id`),
  CONSTRAINT `FK41640CB897180A7` FOREIGN KEY (`paciente_id`) REFERENCES `paciente` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `reserva`
--

/*!40000 ALTER TABLE `reserva` DISABLE KEYS */;
INSERT INTO `reserva` (`id`,`fecha_realizacion`,`fecha_reservada`,`cancelado`,`observ_cancel`,`turno_id`,`paciente_id`,`consulta_id`) VALUES 
 (-3,'2008-01-01 00:00:00','2008-02-16 00:00:00',0x00,'',NULL,-6,NULL),
 (-2,'2008-01-01 00:00:00','2008-02-11 00:00:00',0x00,'',NULL,-5,NULL),
 (-1,'2008-01-01 00:00:00','2008-02-02 00:00:00',0x00,'',NULL,-4,NULL);
/*!40000 ALTER TABLE `reserva` ENABLE KEYS */;


--
-- Definition of table `role`
--

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL auto_increment,
  `name` varchar(20) default NULL,
  `description` varchar(64) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `role`
--

/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` (`id`,`name`,`description`) VALUES 
 (-4,'ROLE_PACIENTE','Rol por defecto para los Pacientes Registrados'),
 (-3,'ROLE_DOCTOR','Rol por defecto para los Doctores Registrados'),
 (-2,'ROLE_USER','Default role for all Users'),
 (-1,'ROLE_ADMIN','Administrator role (can edit Users)');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;


--
-- Definition of table `tiposangre`
--

DROP TABLE IF EXISTS `tiposangre`;
CREATE TABLE `tiposangre` (
  `id` bigint(20) NOT NULL auto_increment,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tiposangre`
--

/*!40000 ALTER TABLE `tiposangre` DISABLE KEYS */;
INSERT INTO `tiposangre` (`id`,`nombre`) VALUES 
 (-3,'A negativo'),
 (-4,'A positivo'),
 (-1,'O negativo'),
 (-2,'O Positivo');
/*!40000 ALTER TABLE `tiposangre` ENABLE KEYS */;


--
-- Definition of table `turno`
--

DROP TABLE IF EXISTS `turno`;
CREATE TABLE `turno` (
  `id` bigint(20) NOT NULL auto_increment,
  `hora` time NOT NULL,
  `horario_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `hora_idx` (`hora`),
  KEY `FK6998F9253615A4E` (`horario_id`),
  CONSTRAINT `FK6998F9253615A4E` FOREIGN KEY (`horario_id`) REFERENCES `horario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `turno`
--

/*!40000 ALTER TABLE `turno` DISABLE KEYS */;
INSERT INTO `turno` (`id`,`hora`,`horario_id`) VALUES 
 (-5,'13:30:00',-3),
 (-4,'12:30:00',-3),
 (-3,'11:30:00',-3),
 (-2,'10:30:00',-2),
 (-1,'07:30:00',-2);
/*!40000 ALTER TABLE `turno` ENABLE KEYS */;


--
-- Definition of table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`user_id`,`role_id`),
  KEY `FK143BF46A6D90F507` (`role_id`),
  KEY `FK143BF46A12BBB8E7` (`user_id`),
  CONSTRAINT `FK143BF46A12BBB8E7` FOREIGN KEY (`user_id`) REFERENCES `app_user` (`id`),
  CONSTRAINT `FK143BF46A6D90F507` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user_role`
--

/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` (`user_id`,`role_id`) VALUES 
 (-6,-4),
 (-5,-4),
 (-4,-4),
 (-9,-3),
 (-8,-3),
 (-7,-3),
 (-3,-3),
 (1,-3),
 (-9,-2),
 (-8,-2),
 (-7,-2),
 (-6,-2),
 (-5,-2),
 (-4,-2),
 (-3,-2),
 (-1,-2),
 (1,-2),
 (-2,-1);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
