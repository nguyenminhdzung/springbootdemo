CREATE TABLE `tbl_role` (
	`id` VARCHAR(255) NOT NULL,
	`name` VARCHAR(255) NOT NULL,
	PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE TABLE `tbl_user` (
	`id` VARCHAR(255) NOT NULL,
	`username` VARCHAR(255) NOT NULL,
	`password` VARCHAR(255) NOT NULL,
	`firstname` VARCHAR(255) NOT NULL,
	`lastname` VARCHAR(255) NOT NULL,
	`email` VARCHAR(255) NULL DEFAULT NULL,
	`address1` VARCHAR(255) NULL DEFAULT NULL,
	`address2` VARCHAR(255) NULL DEFAULT NULL,
	`address3` VARCHAR(255) NULL DEFAULT NULL,
	PRIMARY KEY (`id`),
	UNIQUE INDEX `UK_username` (`username`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE TABLE `tbl_user_role` (
	`user_id` VARCHAR(255) NOT NULL,
	`role_id` VARCHAR(255) NOT NULL,
	INDEX `FK_USER_ROLE_ROLE` (`role_id`),
	INDEX `FK_user_role_user` (`user_id`),
	CONSTRAINT `FK_user_role_role` FOREIGN KEY (`role_id`) REFERENCES `tbl_role` (`id`) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT `FK_user_role_user` FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;


-- Dumping structure for table demo.oauth_access_token
CREATE TABLE IF NOT EXISTS `oauth_access_token` (
  `TOKEN_ID` varchar(255) DEFAULT NULL,
  `TOKEN` blob,
  `AUTHENTICATION_ID` varchar(255) NOT NULL,
  `USER_NAME` varchar(255) DEFAULT NULL,
  `CLIENT_ID` varchar(255) DEFAULT NULL,
  `AUTHENTICATION` blob,
  `REFRESH_TOKEN` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`AUTHENTICATION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table demo.oauth_approvals
CREATE TABLE IF NOT EXISTS `oauth_approvals` (
  `USERID` varchar(255) DEFAULT NULL,
  `CLIENTID` varchar(255) DEFAULT NULL,
  `SCOPE` varchar(255) DEFAULT NULL,
  `STATUS` varchar(10) DEFAULT NULL,
  `EXPIRESAT` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `LASTMODIFIEDAT` timestamp(6) NOT NULL DEFAULT '1970-01-01 00:00:01'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table demo.oauth_client_details
CREATE TABLE IF NOT EXISTS `oauth_client_details` (
  `CLIENT_ID` varchar(255) NOT NULL,
  `RESOURCE_IDS` varchar(255) DEFAULT NULL,
  `CLIENT_SECRET` varchar(255) DEFAULT NULL,
  `SCOPE` varchar(255) DEFAULT NULL,
  `AUTHORIZED_GRANT_TYPES` varchar(255) DEFAULT NULL,
  `WEB_SERVER_REDIRECT_URI` varchar(255) DEFAULT NULL,
  `AUTHORITIES` varchar(255) DEFAULT NULL,
  `ACCESS_TOKEN_VALIDITY` int(11) DEFAULT NULL,
  `REFRESH_TOKEN_VALIDITY` int(11) DEFAULT NULL,
  `ADDITIONAL_INFORMATION` varchar(4000) DEFAULT NULL,
  `AUTOAPPROVE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`CLIENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table demo.oauth_client_token
CREATE TABLE IF NOT EXISTS `oauth_client_token` (
  `TOKEN_ID` varchar(255) DEFAULT NULL,
  `TOKEN` blob,
  `AUTHENTICATION_ID` varchar(255) NOT NULL,
  `USER_NAME` varchar(255) DEFAULT NULL,
  `CLIENT_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`AUTHENTICATION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table demo.oauth_code
CREATE TABLE IF NOT EXISTS `oauth_code` (
  `CODE` varchar(255) DEFAULT NULL,
  `AUTHENTICATION` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table demo.oauth_refresh_token
CREATE TABLE IF NOT EXISTS `oauth_refresh_token` (
  `TOKEN_ID` varchar(255) DEFAULT NULL,
  `TOKEN` blob,
  `AUTHENTICATION` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- Test client
INSERT INTO `oauth_client_details` (`CLIENT_ID`, `RESOURCE_IDS`, `CLIENT_SECRET`, `SCOPE`, `AUTHORIZED_GRANT_TYPES`, `WEB_SERVER_REDIRECT_URI`, `AUTHORITIES`, `ACCESS_TOKEN_VALIDITY`, `REFRESH_TOKEN_VALIDITY`, `ADDITIONAL_INFORMATION`, `AUTOAPPROVE`) VALUES
	('test-client', 'backend-api', 'test-client', 'backend', 'password,refresh_token', NULL, NULL, 1800, 2592000, NULL, '0');


