/*
SQLyog Community v13.3.0 (64 bit)
MySQL - 10.4.32-MariaDB : Database - certificationdb
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`certificationdb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;

USE `certificationdb`;

/*Table structure for table `certificationrecords_nolandholding` */

DROP TABLE IF EXISTS `certificationrecords_nolandholding`;

CREATE TABLE `certificationrecords_nolandholding` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `MaritalStatus` varchar(50) DEFAULT NULL,
  `ParentGuardian` varchar(100) DEFAULT NULL,
  `ParentGuardian2` varchar(100) DEFAULT NULL,
  `ParentSexIfSingle` varchar(20) DEFAULT NULL,
  `Barangay` varchar(100) DEFAULT NULL,
  `Patient` varchar(100) DEFAULT NULL,
  `Hospital` varchar(100) DEFAULT NULL,
  `Relationship` varchar(100) DEFAULT NULL,
  `HospitalAddress` varchar(100) DEFAULT NULL,
  `CertificationDate` varchar(100) DEFAULT NULL,
  `CertificationTime` varchar(100) DEFAULT NULL,
  `AmountPaid` varchar(45) DEFAULT NULL,
  `ReceiptNo` varchar(45) DEFAULT NULL,
  `ReceiptDateIssued` varchar(100) DEFAULT NULL,
  `PlaceIssued` varchar(45) DEFAULT NULL,
  `Type` varchar(100) DEFAULT NULL,
  `Guardian` varchar(3) DEFAULT NULL,
  `userInitials` varchar(50) DEFAULT NULL,
  `Signatory` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=423 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `certificationrecords_nolandholding` */

insert  into `certificationrecords_nolandholding`(`id`,`MaritalStatus`,`ParentGuardian`,`ParentGuardian2`,`ParentSexIfSingle`,`Barangay`,`Patient`,`Hospital`,`Relationship`,`HospitalAddress`,`CertificationDate`,`CertificationTime`,`AmountPaid`,`ReceiptNo`,`ReceiptDateIssued`,`PlaceIssued`,`Type`,`Guardian`,`userInitials`,`Signatory`) values 
(374,'MARRIED','JESUS JIMMY APLICANO CELESTIAL AND ROSALIE PASUELO CELESTIAL ','','Married','TAWAS','JOEY PASUELO CELESTIAL','BUDA COMMUNITY HEALTH CARE CENTER (COMMITTEE OF GERMAN DOCTORS)','son','BUDA, MARILOG DISTRICT, DAVAO CITY','2025-03-31','11:04:24','₱0.00',NULL,NULL,NULL,'Hospitalization',NULL,'RSRODRIGUEZ','ROEL H. MOLO, MMREM, REA, REB, LPT'),
(402,'MARRIED','JOHNMARK HERBITO AND KAREN JANE LIMBAGA ','','Married','POBLACION, KITAOTAO, BUKIDNON','JOHN KAREN LIMBAGA HERBITO','BUKIDNON PROVINCIAL HOSPITAL','son','MARAMAG, BUKIDNON','2025-03-31','03:13:05','₱300.00','123456','2025-03-31','MASSO KITAOTAO','Hospitalization',NULL,'RSRODRIGUEZ','ROEL H. MOLO, MMREM, REA, REB, LPT'),
(406,'SINGLE','ASD AND ASD ','',NULL,'ASD','ASD','SD',NULL,'SD','2025-03-31','02:50:23','₱0.00',NULL,NULL,NULL,'Hospitalization',NULL,'ADMIN','ROEL H. MOLO, MMREM, REA, REB, LPTa'),
(407,'MARRIED','JOHNMARK HERBITO AND KAREN JANE LIMBAGA ','','Married','POBLACION, KITAOTAO, BUKIDNON','JOHN KAREN LIMBAGA HERBITO','BUKIDNON PROVINCIAL HOSPITAL','son','MARAMAG, BUKIDNON','2025-03-31','02:52:00','₱0.00',NULL,NULL,NULL,'Hospitalization',NULL,'ADMIN','ROEL H. MOLO, MMREM, REA, REB, LPT'),
(408,'MARRIED','JOMARI R. CAINA AND SEEEEEEE ','','Married','DON CARLOS, BUKIDNON','MARY C. CAINA','DON CARLOS DOCTORS HOSPITAL','daughter','DON CARLOS, BUKIDNON','2025-03-31','04:06:54','₱0.00',NULL,NULL,NULL,'Hospitalization',NULL,'ADMIN','ROEL H. MOLO, MMREM, REA, REB, LPT'),
(409,'SINGLE','AD ','','Male','DAD','SSD','ASD','son','ASD','2025-03-31','04:13:34','₱0.00',NULL,NULL,NULL,'Hospitalization',NULL,'ADMIN','ROEL H. MOLO, MMREM, REA, REB, LPT'),
(410,'SINGLE','ASD ','','Male','ASD','ASD','ASD','son','ASD','2025-03-31','04:31:05','₱0.00',NULL,NULL,NULL,'Hospitalization',NULL,'ADMIN','ROEL H. MOLO, MMREM, REA, REB, LPT'),
(411,'SINGLE','ASD ','','Male','D','ASD','DA','son','SD','2025-03-31','02:55:46','₱0.00',NULL,NULL,NULL,'Hospitalization',NULL,'ADMIN','ROEL H. MOLO, MMREM, REA, REB, LPT'),
(412,'SINGLE','ASD ','','Male','ASD','ASD','ASD','son','ASD','2025-03-31','03:39:15','₱0.00',NULL,NULL,NULL,'Hospitalization',NULL,'ADMIN','ROEL H. MOLO, MMREM, REA, REB, LPT'),
(413,'SINGLE','ASD ','','Male','ASD','ASD','ASD','son','ASD','2025-03-31','03:41:01','₱0.00',NULL,NULL,NULL,'Hospitalization',NULL,'ADMIN','ROEL H. MOLO, MMREM, REA, REB, LPT'),
(414,'SINGLE','ASD ','','Male','SD','SD','ASD','son','SD','2025-03-31','03:45:58','₱0.00',NULL,NULL,NULL,'Hospitalization',NULL,'ADMIN','ROEL H. MOLO, MMREM, REA, REB, LPT'),
(415,'SINGLE','ASD ','','Male','ASD','SD','ASD','son','ASD','2025-03-31','03:49:42','₱0.00',NULL,NULL,NULL,'Hospitalization',NULL,'ADMIN','ROEL H. MOLO, MMREM, REA, REB, LPT'),
(416,'SINGLE','ASD ','','Male','SD','ASD','ASD','son','ASD','2025-03-31','03:50:39','₱0.00',NULL,NULL,NULL,'Scholarship',NULL,'ADMIN','ROEL H. MOLO, MMREM, REA, REB, LPT'),
(417,'SINGLE','ASD ','','Male','ASD','ASD','ASD','son','ASD','2025-03-31','03:51:11','₱0.00',NULL,NULL,NULL,'Scholarship',NULL,'ADMIN','ROEL H. MOLO, MMREM, REA, REB, LPT'),
(418,'SINGLE','ASD ','','Male','SD','ASD','ASD','son','DDASD','2025-03-31','01:07:24','₱0.00',NULL,NULL,NULL,'Hospitalization',NULL,'ADMIN','ROEL H. MOLO, MMREM, REA, REB, LPT'),
(419,'SINGLE','DDASD ASD A ','','Male','DDASD ASD A','DDASD ASD A','DDASD ASD A','son','DDASD ASD A','2025-03-31','01:08:04','₱0.00',NULL,NULL,NULL,'Hospitalization',NULL,'ADMIN','ROEL H. MOLO, MMREM, REA, REB, LPTa'),
(420,'SINGLE','TEST ','','Male','TEST','EST','TEST','son','TEST','2025-03-31','01:09:28','₱0.00',NULL,NULL,NULL,'Hospitalization',NULL,'ADMIN','ROEL H. MOLO, MMREM, REA, REB, LPTa'),
(421,'SINGLE','ASD ','','Male','ASD','ASD','ASD','son','SD','0000-00-00','03:49:27','₱0.00',NULL,NULL,NULL,'Hospitalization',NULL,'ADMIN','ROEL H. MOLO, MMREM, REA, REB, LPTa'),
(422,'SINGLE','ASD ','','Male','SD','SD','SD','son','SD','03-31-2025','03:50:11 PM','₱0.00',NULL,NULL,NULL,'Hospitalization',NULL,'ADMIN','ROEL H. MOLO, MMREM, REA, REB, LPTa');

/*Table structure for table `records` */

DROP TABLE IF EXISTS `records`;

CREATE TABLE `records` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `MaritalStatus` varchar(50) DEFAULT NULL,
  `ParentGuardian` varchar(100) DEFAULT NULL,
  `ParentSexIfSingle` varchar(20) DEFAULT NULL,
  `Barangay` varchar(100) DEFAULT NULL,
  `Patient` varchar(100) DEFAULT NULL,
  `Hospital` varchar(100) DEFAULT NULL,
  `Relationship` varchar(100) DEFAULT NULL,
  `HospitalAddress` varchar(100) DEFAULT NULL,
  `CertificationDate` date DEFAULT NULL,
  `CertificationTime` time DEFAULT NULL,
  `AmountPaid` decimal(10,2) DEFAULT NULL,
  `ReceiptNo` varchar(45) DEFAULT NULL,
  `ReceiptDateIssued` date DEFAULT NULL,
  `PlaceIssued` varchar(45) DEFAULT NULL,
  `Type` varchar(100) DEFAULT NULL,
  `Guardian` varchar(3) DEFAULT NULL,
  `UserInitials` varchar(50) DEFAULT NULL,
  `Signatory` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `records` */

insert  into `records`(`id`,`MaritalStatus`,`ParentGuardian`,`ParentSexIfSingle`,`Barangay`,`Patient`,`Hospital`,`Relationship`,`HospitalAddress`,`CertificationDate`,`CertificationTime`,`AmountPaid`,`ReceiptNo`,`ReceiptDateIssued`,`PlaceIssued`,`Type`,`Guardian`,`UserInitials`,`Signatory`) values 
(1,'MARRIED','JESUS JIMMY APLICANO CELESTIAL AND ROSALIE PASUELO CELESTIAL','Married','TAWAS','JOEY PASUELO CELESTIAL','BUDA COMMUNITY HEALTH CARE CENTER (COMMITTEE OF GERMAN DOCTORS)','son','BUDA, MARILOG DISTRICT, DAVAO CITY','2025-03-31','13:50:11',0.00,NULL,NULL,NULL,'Hospitalization',NULL,'RSRODRIGUEZ','ROEL H. MOLO, MMREM, REA, REB, LPT');

/*Table structure for table `reports` */

DROP TABLE IF EXISTS `reports`;

CREATE TABLE `reports` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `MaritalStatus` varchar(50) DEFAULT NULL,
  `ParentGuardian` varchar(100) DEFAULT NULL,
  `ParentGuardian2` varchar(100) DEFAULT NULL,
  `ParentSexIfSingle` varchar(20) DEFAULT NULL,
  `Barangay` varchar(100) DEFAULT NULL,
  `Patient` varchar(100) DEFAULT NULL,
  `Hospital` varchar(100) DEFAULT NULL,
  `Relationship` varchar(100) DEFAULT NULL,
  `HospitalAddress` varchar(100) DEFAULT NULL,
  `CertificationDate` date DEFAULT NULL,
  `CertificationTime` time DEFAULT NULL,
  `AmountPaid` varchar(45) DEFAULT NULL,
  `ReceiptNo` int(45) DEFAULT NULL,
  `ReceiptDateIssued` date DEFAULT NULL,
  `PlaceIssued` varchar(45) DEFAULT NULL,
  `Type` varchar(100) DEFAULT NULL,
  `Guardian` varchar(3) DEFAULT NULL,
  `userInitials` varchar(50) DEFAULT NULL,
  `Signatory` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=423 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `reports` */

insert  into `reports`(`id`,`MaritalStatus`,`ParentGuardian`,`ParentGuardian2`,`ParentSexIfSingle`,`Barangay`,`Patient`,`Hospital`,`Relationship`,`HospitalAddress`,`CertificationDate`,`CertificationTime`,`AmountPaid`,`ReceiptNo`,`ReceiptDateIssued`,`PlaceIssued`,`Type`,`Guardian`,`userInitials`,`Signatory`) values 
(374,'MARRIED','JESUS JIMMY APLICANO CELESTIAL AND ROSALIE PASUELO CELESTIAL ','','Married','TAWAS','JOEY PASUELO CELESTIAL','BUDA COMMUNITY HEALTH CARE CENTER (COMMITTEE OF GERMAN DOCTORS)','son','BUDA, MARILOG DISTRICT, DAVAO CITY','2025-03-31','11:04:24','₱0.00',NULL,NULL,NULL,'Hospitalization',NULL,'RSRODRIGUEZ','ROEL H. MOLO, MMREM, REA, REB, LPT'),
(402,'MARRIED','JOHNMARK HERBITO AND KAREN JANE LIMBAGA ','','Married','POBLACION, KITAOTAO, BUKIDNON','JOHN KAREN LIMBAGA HERBITO','BUKIDNON PROVINCIAL HOSPITAL','son','MARAMAG, BUKIDNON','2025-03-31','15:13:05','₱300.00',123456,'2025-03-31','MASSO KITAOTAO','Hospitalization',NULL,'RSRODRIGUEZ','ROEL H. MOLO, MMREM, REA, REB, LPT'),
(406,'SINGLE','ASD AND ASD ','',NULL,'ASD','ASD','SD',NULL,'SD','2025-03-31','14:50:23','₱0.00',NULL,NULL,NULL,'Hospitalization',NULL,'ADMIN','ROEL H. MOLO, MMREM, REA, REB, LPTa'),
(407,'MARRIED','JOHNMARK HERBITO AND KAREN JANE LIMBAGA ','','Married','POBLACION, KITAOTAO, BUKIDNON','JOHN KAREN LIMBAGA HERBITO','BUKIDNON PROVINCIAL HOSPITAL','son','MARAMAG, BUKIDNON','2025-03-31','14:52:00','₱0.00',NULL,NULL,NULL,'Hospitalization',NULL,'ADMIN','ROEL H. MOLO, MMREM, REA, REB, LPT'),
(408,'MARRIED','JOMARI R. CAINA AND SEEEEEEE ','','Married','DON CARLOS, BUKIDNON','MARY C. CAINA','DON CARLOS DOCTORS HOSPITAL','daughter','DON CARLOS, BUKIDNON','2025-03-31','16:06:54','₱0.00',NULL,NULL,NULL,'Hospitalization',NULL,'ADMIN','ROEL H. MOLO, MMREM, REA, REB, LPT'),
(409,'SINGLE','AD ','','Male','DAD','SSD','ASD','son','ASD','2025-03-31','16:13:34','₱0.00',NULL,NULL,NULL,'Hospitalization',NULL,'ADMIN','ROEL H. MOLO, MMREM, REA, REB, LPT'),
(410,'SINGLE','ASD ','','Male','ASD','ASD','ASD','son','ASD','2025-03-31','16:31:05','₱0.00',NULL,NULL,NULL,'Hospitalization',NULL,'ADMIN','ROEL H. MOLO, MMREM, REA, REB, LPT'),
(411,'SINGLE','ASD ','','Male','D','ASD','DA','son','SD','2025-03-31','14:55:46','₱0.00',NULL,NULL,NULL,'Hospitalization',NULL,'ADMIN','ROEL H. MOLO, MMREM, REA, REB, LPT'),
(412,'SINGLE','ASD ','','Male','ASD','ASD','ASD','son','ASD','2025-03-31','15:39:15','₱0.00',NULL,NULL,NULL,'Hospitalization',NULL,'ADMIN','ROEL H. MOLO, MMREM, REA, REB, LPT'),
(413,'SINGLE','ASD ','','Male','ASD','ASD','ASD','son','ASD','2025-03-31','15:41:01','₱0.00',NULL,NULL,NULL,'Hospitalization',NULL,'ADMIN','ROEL H. MOLO, MMREM, REA, REB, LPT'),
(414,'SINGLE','ASD ','','Male','SD','SD','ASD','son','SD','2025-03-31','15:45:58','₱0.00',NULL,NULL,NULL,'Hospitalization',NULL,'ADMIN','ROEL H. MOLO, MMREM, REA, REB, LPT'),
(415,'SINGLE','ASD ','','Male','ASD','SD','ASD','son','ASD','2025-03-31','15:49:42','₱0.00',NULL,NULL,NULL,'Hospitalization',NULL,'ADMIN','ROEL H. MOLO, MMREM, REA, REB, LPT'),
(416,'SINGLE','ASD ','','Male','SD','ASD','ASD','son','ASD','2025-03-31','15:50:39','₱0.00',NULL,NULL,NULL,'Scholarship',NULL,'ADMIN','ROEL H. MOLO, MMREM, REA, REB, LPT'),
(417,'SINGLE','ASD ','','Male','ASD','ASD','ASD','son','ASD','2025-03-31','15:51:11','₱0.00',NULL,NULL,NULL,'Scholarship',NULL,'ADMIN','ROEL H. MOLO, MMREM, REA, REB, LPT'),
(418,'SINGLE','ASD ','','Male','SD','ASD','ASD','son','DDASD','2025-03-31','13:07:24','₱0.00',NULL,NULL,NULL,'Hospitalization',NULL,'ADMIN','ROEL H. MOLO, MMREM, REA, REB, LPT'),
(419,'SINGLE','DDASD ASD A ','','Male','DDASD ASD A','DDASD ASD A','DDASD ASD A','son','DDASD ASD A','2025-03-31','13:08:04','₱0.00',NULL,NULL,NULL,'Hospitalization',NULL,'ADMIN','ROEL H. MOLO, MMREM, REA, REB, LPTa'),
(420,'SINGLE','TEST ','','Male','TEST','EST','TEST','son','TEST','2025-03-31','13:09:28','₱0.00',NULL,NULL,NULL,'Hospitalization',NULL,'ADMIN','ROEL H. MOLO, MMREM, REA, REB, LPTa'),
(421,'SINGLE','ASD ','','Male','ASD','ASD','ASD','son','SD','2025-04-02','15:49:27','₱0.00',NULL,NULL,NULL,'Hospitalization',NULL,'ADMIN','ROEL H. MOLO, MMREM, REA, REB, LPTa'),
(422,'SINGLE','ASD ','','Male','SD','SD','SD','son','SD','2025-04-02','15:50:11','₱0.00',NULL,NULL,NULL,'Hospitalization',NULL,'ADMIN','ROEL H. MOLO, MMREM, REA, REB, LPTa');

/*Table structure for table `sys_login_credentials` */

DROP TABLE IF EXISTS `sys_login_credentials`;

CREATE TABLE `sys_login_credentials` (
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `initial` varchar(50) DEFAULT NULL,
  `accesslevel` int(1) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `sys_login_credentials` */

insert  into `sys_login_credentials`(`username`,`password`,`initial`,`accesslevel`,`name`) values 
('admin','8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918','ADMIN',1,'ADMIN'),
('ricky','d572e82155c8e91135ac6691bce34552d1d65948a2a45a4f7989d9895ce110be','RSRODRIGUEZ',0,'RICKY SALIM RODRIGUEZ'),
('toryang','d6e89337ee23fb8a781412247cf32372700e24d69caa1e8710c8462a821b392d','JRC',0,'JOMARI R. CAIÑA');

/*Table structure for table `sys_nolandholding_internaltypesets` */

DROP TABLE IF EXISTS `sys_nolandholding_internaltypesets`;

CREATE TABLE `sys_nolandholding_internaltypesets` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `typesets` varchar(100) NOT NULL,
  PRIMARY KEY (`ID`,`typesets`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `sys_nolandholding_internaltypesets` */

insert  into `sys_nolandholding_internaltypesets`(`ID`,`typesets`) values 
(1,'Hospitalization'),
(2,'Bailbond'),
(3,'No Land Holding'),
(4,'PhilHealth'),
(5,'Scholarship');

/*Table structure for table `sys_signatories` */

DROP TABLE IF EXISTS `sys_signatories`;

CREATE TABLE `sys_signatories` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Assessor` varchar(100) NOT NULL,
  PRIMARY KEY (`ID`,`Assessor`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `sys_signatories` */

insert  into `sys_signatories`(`ID`,`Assessor`) values 
(1,'ROEL H. MOLO, MMREM, REA, REB, LPTa');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
