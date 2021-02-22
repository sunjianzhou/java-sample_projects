DROP TABLE IF EXISTS `hello_world`;
CREATE TABLE `hello_world` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(64) COLLATE utf8mb4_croatian_ci DEFAULT NULL,
  `hello_world_name` varchar(64) COLLATE utf8mb4_croatian_ci DEFAULT NULL,
  `status` int(1) DEFAULT '0',
  `tenant_id` int(10) DEFAULT NULL COMMENT '租户id',
  `deleted` int(11) DEFAULT '0',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_croatian_ci COMMENT='测试表';
