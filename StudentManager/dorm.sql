/*
SQLyog Ultimate v9.30 
MySQL - 5.6.12-enterprise-commercial-advanced : Database - dorm
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`dorm` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `dorm`;

/*Table structure for table `tb_dormbuild` */

DROP TABLE IF EXISTS `tb_dormbuild`;

CREATE TABLE `tb_dormbuild` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `remark` varchar(50) DEFAULT NULL,
  `disabled` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `tb_dormbuild` */

insert  into `tb_dormbuild`(`id`,`name`,`remark`,`disabled`) values (1,'1号楼1','1号楼备注1',1),(2,'3号楼','3号楼名字不变',0),(3,'2号楼','2号楼备注1',0),(4,'测试','',1);

/*Table structure for table `tb_manage_dormbuild` */

DROP TABLE IF EXISTS `tb_manage_dormbuild`;

CREATE TABLE `tb_manage_dormbuild` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `dormBuild_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `dormBuild_id` (`dormBuild_id`),
  CONSTRAINT `tb_manage_dormbuild_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`),
  CONSTRAINT `tb_manage_dormbuild_ibfk_2` FOREIGN KEY (`dormBuild_id`) REFERENCES `tb_dormbuild` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

/*Data for the table `tb_manage_dormbuild` */

insert  into `tb_manage_dormbuild`(`id`,`user_id`,`dormBuild_id`) values (4,12,1),(5,12,2),(6,20,1),(7,21,1),(8,21,2),(18,22,1),(19,22,2),(22,11,1),(23,11,2),(24,10,3);

/*Table structure for table `tb_record` */

DROP TABLE IF EXISTS `tb_record`;

CREATE TABLE `tb_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) DEFAULT NULL COMMENT '学生id',
  `date` date DEFAULT NULL COMMENT '缺勤时间',
  `remark` varchar(50) DEFAULT NULL COMMENT '备注',
  `disabled` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `student_id` (`student_id`),
  CONSTRAINT `tb_record_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `tb_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `tb_record` */

insert  into `tb_record`(`id`,`student_id`,`date`,`remark`,`disabled`) values (1,32,'2019-11-01','111222',0),(2,35,'2019-11-06','222',1),(3,33,'2019-11-01','22222',0),(4,36,'2019-11-05','353535',0);

/*Table structure for table `tb_user` */

DROP TABLE IF EXISTS `tb_user`;

CREATE TABLE `tb_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `passWord` varchar(20) NOT NULL,
  `stu_code` varchar(20) DEFAULT NULL COMMENT '学号',
  `dorm_Code` varchar(20) DEFAULT NULL COMMENT '宿舍编号',
  `sex` varchar(10) DEFAULT NULL,
  `tel` varchar(15) DEFAULT NULL,
  `dormBuildId` int(11) DEFAULT NULL COMMENT '宿舍楼id',
  `role_id` int(11) DEFAULT NULL COMMENT '0 表示超级管理员 1宿舍管理员 2学生',
  `create_user_id` int(11) DEFAULT NULL COMMENT '创建人id',
  `disabled` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `stu_code` (`stu_code`),
  KEY `dormBuildId` (`dormBuildId`),
  KEY `create_user_id` (`create_user_id`),
  CONSTRAINT `tb_user_ibfk_1` FOREIGN KEY (`dormBuildId`) REFERENCES `tb_dormbuild` (`id`),
  CONSTRAINT `tb_user_ibfk_2` FOREIGN KEY (`create_user_id`) REFERENCES `tb_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

/*Data for the table `tb_user` */

insert  into `tb_user`(`id`,`name`,`passWord`,`stu_code`,`dorm_Code`,`sex`,`tel`,`dormBuildId`,`role_id`,`create_user_id`,`disabled`) values (1,'admin','222','111',NULL,NULL,'13539909876',NULL,0,NULL,0),(10,'管理员2','333','20191026',NULL,'男','13539909242',NULL,1,1,0),(11,'管理员31','111','20191027',NULL,'女','13532342341',NULL,1,1,0),(12,'管理员4','111','20191028',NULL,'男','13543257654',NULL,1,1,0),(20,'管理员2','111','20191031',NULL,'女','13539909242',NULL,1,1,0),(21,'管理员7','111','20191032',NULL,'女','13543257654',NULL,1,1,0),(22,'管理员9','111','20191033',NULL,'男','13539909241',NULL,1,1,0),(32,'学生1','111','15170231','3-201','女','13532342341',1,2,11,1),(33,'学生2','444','15170232','1-201','男','13532342342',1,2,11,0),(34,'学生3','111','15170233','3-101','男','13532342342',2,2,20,0),(35,'学生4','111','15170234','2-201','女','13532342342',3,2,1,0),(36,'学生5','111','15170235','1-101','男','13532342342',1,2,1,0),(37,'学生6','111','15170236','2-201','女','13532342346',3,2,1,0),(38,'学生7','111','15170237','5-201','女','13532342347',4,2,1,0),(39,'学生8','111','15170238','1-101','男','13532342342',1,2,1,1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
