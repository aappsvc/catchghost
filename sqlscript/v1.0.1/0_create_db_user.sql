-- Create DB
CREATE DATABASE IF NOT EXISTS `workphotodb` CHARACTER SET utf8mb4 COLLATE UTF8MB4_UNICODE_CI;

-- Create user
CREATE USER IF NOT EXISTS `dbadmin`@`%` IDENTIFIED BY 'admindb';

-- Grant privileges
GRANT EXECUTE ON *.* TO `dbadmin`@`%`;
GRANT SELECT ON *.* TO `dbadmin`@`%`;
GRANT SHOW VIEW  ON *.* TO `dbadmin`@`%`;
GRANT Show databases ON *.* TO `dbadmin`@`%`;
GRANT Reload ON *.* TO `dbadmin`@`%`;
GRANT Process ON *.* TO `dbadmin`@`%`;
GRANT Alter ON `workphotodb`.* TO `dbadmin`@`%`;
GRANT Create ON `workphotodb`.* TO `dbadmin`@`%`;
GRANT Create view ON `workphotodb`.* TO `dbadmin`@`%`;
GRANT Delete ON `workphotodb`.* TO `dbadmin`@`%`;
GRANT Delete history ON `workphotodb`.* TO `dbadmin`@`%`;
GRANT Drop ON `workphotodb`.* TO `dbadmin`@`%`;
GRANT Index ON `workphotodb`.* TO `dbadmin`@`%`;
GRANT Insert ON `workphotodb`.* TO `dbadmin`@`%`;
GRANT References ON `workphotodb`.* TO `dbadmin`@`%`;
GRANT Select ON `workphotodb`.* TO `dbadmin`@`%`;
GRANT Show view ON `workphotodb`.* TO `dbadmin`@`%`;
GRANT Trigger ON `workphotodb`.* TO `dbadmin`@`%`;
GRANT Update ON `workphotodb`.* TO `dbadmin`@`%`;
GRANT Alter routine ON `workphotodb`.* TO `dbadmin`@`%`;
GRANT Create routine ON `workphotodb`.* TO `dbadmin`@`%`;
GRANT Create temporary tables ON `workphotodb`.* TO `dbadmin`@`%`;
GRANT Execute ON `workphotodb`.* TO `dbadmin`@`%`;
GRANT EVENT ON `workphotodb`.* TO `dbadmin`@`%`;
GRANT Lock tables ON `workphotodb`.* TO `dbadmin`@`%`;

FLUSH PRIVILEGES;