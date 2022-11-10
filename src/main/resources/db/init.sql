CREATE TABLE `log_server_properties` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `server_name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `server_ip` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `server_log_dir` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO log_server_properties(`id`, `server_name`, `server_ip`, `server_log_dir`) VALUES (1, 'gather', '192.168.44.190', '/opt/app/gather/test/catalina.out');
INSERT INTO log_server_properties(`id`, `server_name`, `server_ip`, `server_log_dir`) VALUES (2, 'etl', '192.168.44.193', '/opt/app/testetl/catalina.out');
INSERT INTO log_server_properties(`id`, `server_name`, `server_ip`, `server_log_dir`) VALUES (3, 'datax', '192.168.44.197', '/opt/app/datax/test/catalina.out');
INSERT INTO log_server_properties(`id`, `server_name`, `server_ip`, `server_log_dir`) VALUES (4, 'user', '192.168.44.197', '/opt/app/testuser/catalina.out');
INSERT INTO log_server_properties(`id`, `server_name`, `server_ip`, `server_log_dir`) VALUES (5, 'standard', '192.168.44.196', '/opt/app/teststandard/catalina.out');
INSERT INTO log_server_properties(`id`, `server_name`, `server_ip`, `server_log_dir`) VALUES (6, 'quality', '192.168.44.197', '/opt/app/quality/test/catalina.out');
INSERT INTO log_server_properties(`id`, `server_name`, `server_ip`, `server_log_dir`) VALUES (7, 'spark', '192.168.44.189', '/opt/app/spark-service/catalina.out');
INSERT INTO log_server_properties(`id`, `server_name`, `server_ip`, `server_log_dir`) VALUES (8, 'asset', '192.168.44.190', '/opt/app/asset/test/catalina.out');
INSERT INTO log_server_properties(`id`, `server_name`, `server_ip`, `server_log_dir`) VALUES (9, 'safeIndex', '192.168.44.190', '/opt/app/safeIndex/test/catalina.out');
INSERT INTO log_server_properties(`id`, `server_name`, `server_ip`, `server_log_dir`) VALUES (10, 'uploadInfo', '192.168.44.197', '/opt/app/document/test/catalina.out');
