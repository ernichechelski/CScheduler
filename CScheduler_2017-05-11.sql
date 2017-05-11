# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.16)
# Database: CScheduler
# Generation Time: 2017-05-11 13:36:52 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table contributor
# ------------------------------------------------------------

DROP TABLE IF EXISTS `contributor`;

CREATE TABLE `contributor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `jhi_hash` varchar(32) NOT NULL,
  `owner_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_contributor_owner_id` (`owner_id`),
  CONSTRAINT `fk_contributor_owner_id` FOREIGN KEY (`owner_id`) REFERENCES `jhi_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `contributor` WRITE;
/*!40000 ALTER TABLE `contributor` DISABLE KEYS */;

INSERT INTO `contributor` (`id`, `name`, `jhi_hash`, `owner_id`)
VALUES
	(1,'Michał','adsfsadfsadfas',4),
	(2,'Ernest','afadsgfdgasdgag',4);

/*!40000 ALTER TABLE `contributor` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table DATABASECHANGELOG
# ------------------------------------------------------------

DROP TABLE IF EXISTS `DATABASECHANGELOG`;

CREATE TABLE `DATABASECHANGELOG` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int(11) NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `DATABASECHANGELOG` WRITE;
/*!40000 ALTER TABLE `DATABASECHANGELOG` DISABLE KEYS */;

INSERT INTO `DATABASECHANGELOG` (`ID`, `AUTHOR`, `FILENAME`, `DATEEXECUTED`, `ORDEREXECUTED`, `EXECTYPE`, `MD5SUM`, `DESCRIPTION`, `COMMENTS`, `TAG`, `LIQUIBASE`, `CONTEXTS`, `LABELS`, `DEPLOYMENT_ID`)
VALUES
	('00000000000001','jhipster','classpath:config/liquibase/changelog/00000000000000_initial_schema.xml','2017-05-11 15:33:08',1,'EXECUTED','7:64ccb6b6fdd6f22212d3e6ed914737e3','createTable tableName=jhi_user; createIndex indexName=idx_user_login, tableName=jhi_user; createIndex indexName=idx_user_email, tableName=jhi_user; createTable tableName=jhi_authority; createTable tableName=jhi_user_authority; addPrimaryKey tableN...','',NULL,'3.5.3',NULL,NULL,'4509587554'),
	('20170511133229-1','jhipster','classpath:config/liquibase/changelog/20170511133229_added_entity_Contributor.xml','2017-05-11 15:33:08',2,'EXECUTED','7:092fee9811c7bfa1e592a674c6161552','createTable tableName=contributor','',NULL,'3.5.3',NULL,NULL,'4509587554'),
	('20170511133230-1','jhipster','classpath:config/liquibase/changelog/20170511133230_added_entity_Plan.xml','2017-05-11 15:33:08',3,'EXECUTED','7:d92ca3a3088809f4e6862f11f59800d8','createTable tableName=plan','',NULL,'3.5.3',NULL,NULL,'4509587554'),
	('20170511133231-1','jhipster','classpath:config/liquibase/changelog/20170511133231_added_entity_Event.xml','2017-05-11 15:33:08',4,'EXECUTED','7:1b680d7360f2945a0e468c72862b9d52','createTable tableName=event; dropDefaultValue columnName=jhi_start, tableName=event; dropDefaultValue columnName=jhi_stop, tableName=event; createTable tableName=event_contributors; addPrimaryKey tableName=event_contributors','',NULL,'3.5.3',NULL,NULL,'4509587554'),
	('20170511133229-2','jhipster','classpath:config/liquibase/changelog/20170511133229_added_entity_constraints_Contributor.xml','2017-05-11 15:33:08',5,'EXECUTED','7:31231cd20b481b5566d4f5da9ab1080e','addForeignKeyConstraint baseTableName=contributor, constraintName=fk_contributor_owner_id, referencedTableName=jhi_user','',NULL,'3.5.3',NULL,NULL,'4509587554'),
	('20170511133230-2','jhipster','classpath:config/liquibase/changelog/20170511133230_added_entity_constraints_Plan.xml','2017-05-11 15:33:08',6,'EXECUTED','7:2853f64724e527b5a087b1309eeee51f','addForeignKeyConstraint baseTableName=plan, constraintName=fk_plan_owner_id, referencedTableName=jhi_user','',NULL,'3.5.3',NULL,NULL,'4509587554'),
	('20170511133231-2','jhipster','classpath:config/liquibase/changelog/20170511133231_added_entity_constraints_Event.xml','2017-05-11 15:33:08',7,'EXECUTED','7:5e2eab1d0b3b64683cc3128efa0124a7','addForeignKeyConstraint baseTableName=event, constraintName=fk_event_owner_id, referencedTableName=contributor; addForeignKeyConstraint baseTableName=event_contributors, constraintName=fk_event_contributors_events_id, referencedTableName=event; ad...','',NULL,'3.5.3',NULL,NULL,'4509587554');

/*!40000 ALTER TABLE `DATABASECHANGELOG` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table DATABASECHANGELOGLOCK
# ------------------------------------------------------------

DROP TABLE IF EXISTS `DATABASECHANGELOGLOCK`;

CREATE TABLE `DATABASECHANGELOGLOCK` (
  `ID` int(11) NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `DATABASECHANGELOGLOCK` WRITE;
/*!40000 ALTER TABLE `DATABASECHANGELOGLOCK` DISABLE KEYS */;

INSERT INTO `DATABASECHANGELOGLOCK` (`ID`, `LOCKED`, `LOCKGRANTED`, `LOCKEDBY`)
VALUES
	(1,b'0',NULL,NULL);

/*!40000 ALTER TABLE `DATABASECHANGELOGLOCK` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table event
# ------------------------------------------------------------

DROP TABLE IF EXISTS `event`;

CREATE TABLE `event` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `jhi_start` timestamp NULL,
  `jhi_stop` timestamp NULL,
  `owner_id` bigint(20) NOT NULL,
  `plan_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_event_owner_id` (`owner_id`),
  KEY `fk_event_plan_id` (`plan_id`),
  CONSTRAINT `fk_event_owner_id` FOREIGN KEY (`owner_id`) REFERENCES `contributor` (`id`),
  CONSTRAINT `fk_event_plan_id` FOREIGN KEY (`plan_id`) REFERENCES `plan` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `event` WRITE;
/*!40000 ALTER TABLE `event` DISABLE KEYS */;

INSERT INTO `event` (`id`, `name`, `jhi_start`, `jhi_stop`, `owner_id`, `plan_id`)
VALUES
	(1,'Służba :D','2017-05-11 08:00:00','2017-05-11 09:00:00',1,1);

/*!40000 ALTER TABLE `event` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table event_contributors
# ------------------------------------------------------------

DROP TABLE IF EXISTS `event_contributors`;

CREATE TABLE `event_contributors` (
  `contributors_id` bigint(20) NOT NULL,
  `events_id` bigint(20) NOT NULL,
  PRIMARY KEY (`events_id`,`contributors_id`),
  KEY `fk_event_contributors_contributors_id` (`contributors_id`),
  CONSTRAINT `fk_event_contributors_contributors_id` FOREIGN KEY (`contributors_id`) REFERENCES `contributor` (`id`),
  CONSTRAINT `fk_event_contributors_events_id` FOREIGN KEY (`events_id`) REFERENCES `event` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `event_contributors` WRITE;
/*!40000 ALTER TABLE `event_contributors` DISABLE KEYS */;

INSERT INTO `event_contributors` (`contributors_id`, `events_id`)
VALUES
	(2,1);

/*!40000 ALTER TABLE `event_contributors` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table jhi_authority
# ------------------------------------------------------------

DROP TABLE IF EXISTS `jhi_authority`;

CREATE TABLE `jhi_authority` (
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `jhi_authority` WRITE;
/*!40000 ALTER TABLE `jhi_authority` DISABLE KEYS */;

INSERT INTO `jhi_authority` (`name`)
VALUES
	('ROLE_ADMIN'),
	('ROLE_USER');

/*!40000 ALTER TABLE `jhi_authority` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table jhi_persistent_audit_event
# ------------------------------------------------------------

DROP TABLE IF EXISTS `jhi_persistent_audit_event`;

CREATE TABLE `jhi_persistent_audit_event` (
  `event_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `principal` varchar(100) NOT NULL,
  `event_date` timestamp NULL DEFAULT NULL,
  `event_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`event_id`),
  KEY `idx_persistent_audit_event` (`principal`,`event_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table jhi_persistent_audit_evt_data
# ------------------------------------------------------------

DROP TABLE IF EXISTS `jhi_persistent_audit_evt_data`;

CREATE TABLE `jhi_persistent_audit_evt_data` (
  `event_id` bigint(20) NOT NULL,
  `name` varchar(150) NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`event_id`,`name`),
  KEY `idx_persistent_audit_evt_data` (`event_id`),
  CONSTRAINT `fk_evt_pers_audit_evt_data` FOREIGN KEY (`event_id`) REFERENCES `jhi_persistent_audit_event` (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table jhi_social_user_connection
# ------------------------------------------------------------

DROP TABLE IF EXISTS `jhi_social_user_connection`;

CREATE TABLE `jhi_social_user_connection` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) NOT NULL,
  `provider_id` varchar(255) NOT NULL,
  `provider_user_id` varchar(255) NOT NULL,
  `rank` bigint(20) NOT NULL,
  `display_name` varchar(255) DEFAULT NULL,
  `profile_url` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `access_token` varchar(255) NOT NULL,
  `secret` varchar(255) DEFAULT NULL,
  `refresh_token` varchar(255) DEFAULT NULL,
  `expire_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`provider_id`,`provider_user_id`),
  UNIQUE KEY `user_id_2` (`user_id`,`provider_id`,`rank`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table jhi_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `jhi_user`;

CREATE TABLE `jhi_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login` varchar(100) NOT NULL,
  `password_hash` varchar(60) DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `image_url` varchar(256) DEFAULT NULL,
  `activated` bit(1) NOT NULL,
  `lang_key` varchar(5) DEFAULT NULL,
  `activation_key` varchar(20) DEFAULT NULL,
  `reset_key` varchar(20) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NOT NULL,
  `reset_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login` (`login`),
  UNIQUE KEY `idx_user_login` (`login`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `idx_user_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `jhi_user` WRITE;
/*!40000 ALTER TABLE `jhi_user` DISABLE KEYS */;

INSERT INTO `jhi_user` (`id`, `login`, `password_hash`, `first_name`, `last_name`, `email`, `image_url`, `activated`, `lang_key`, `activation_key`, `reset_key`, `created_by`, `created_date`, `reset_date`, `last_modified_by`, `last_modified_date`)
VALUES
	(1,'system','$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG','System','System','system@localhost','',b'1','pl',NULL,NULL,'system','2017-05-11 15:33:07',NULL,'system',NULL),
	(2,'anonymoususer','$2a$10$j8S5d7Sr7.8VTOYNviDPOeWX8KcYILUVJBsYV83Y5NtECayypx9lO','Anonymous','User','anonymous@localhost','',b'1','pl',NULL,NULL,'system','2017-05-11 15:33:07',NULL,'system',NULL),
	(3,'admin','$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC','Administrator','Administrator','admin@localhost','',b'1','pl',NULL,NULL,'system','2017-05-11 15:33:07',NULL,'system',NULL),
	(4,'user','$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K','User','User','user@localhost','',b'1','pl',NULL,NULL,'system','2017-05-11 15:33:07',NULL,'system',NULL);

/*!40000 ALTER TABLE `jhi_user` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table jhi_user_authority
# ------------------------------------------------------------

DROP TABLE IF EXISTS `jhi_user_authority`;

CREATE TABLE `jhi_user_authority` (
  `user_id` bigint(20) NOT NULL,
  `authority_name` varchar(50) NOT NULL,
  PRIMARY KEY (`user_id`,`authority_name`),
  KEY `fk_authority_name` (`authority_name`),
  CONSTRAINT `fk_authority_name` FOREIGN KEY (`authority_name`) REFERENCES `jhi_authority` (`name`),
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `jhi_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `jhi_user_authority` WRITE;
/*!40000 ALTER TABLE `jhi_user_authority` DISABLE KEYS */;

INSERT INTO `jhi_user_authority` (`user_id`, `authority_name`)
VALUES
	(1,'ROLE_ADMIN'),
	(3,'ROLE_ADMIN'),
	(1,'ROLE_USER'),
	(3,'ROLE_USER'),
	(4,'ROLE_USER');

/*!40000 ALTER TABLE `jhi_user_authority` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table plan
# ------------------------------------------------------------

DROP TABLE IF EXISTS `plan`;

CREATE TABLE `plan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `owner_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_plan_owner_id` (`owner_id`),
  CONSTRAINT `fk_plan_owner_id` FOREIGN KEY (`owner_id`) REFERENCES `jhi_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `plan` WRITE;
/*!40000 ALTER TABLE `plan` DISABLE KEYS */;

INSERT INTO `plan` (`id`, `name`, `owner_id`)
VALUES
	(1,'Centrum',4);

/*!40000 ALTER TABLE `plan` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
