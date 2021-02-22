/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 5.7.27 : Database - test_icity_api_gateway
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE IF NOT EXISTS `test_icity_api_gateway`;

USE `test_icity_api_gateway`;
/*Table structure for table `api_app` */

CREATE TABLE IF NOT EXISTS `api_app` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT COMMENT '表的主键',
  `api_id` bigint(15) NOT NULL COMMENT 'api的id值',
  `app_id` bigint(15) NOT NULL COMMENT '应用的id值',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='api和app的映射关系表';

/*Table structure for table `api_back_config` */

CREATE TABLE IF NOT EXISTS `api_back_config` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT COMMENT 'id值',
  `back_addr` varchar(100) NOT NULL COMMENT '后端地址',
  `back_path` varchar(300) NOT NULL COMMENT '后端路径',
  `method` varchar(30) NOT NULL COMMENT '请求方式',
  `interface_pro` varchar(30) NOT NULL COMMENT '接口协议',
  `predicates` varchar(300) DEFAULT NULL COMMENT '断言',
  `api_id` bigint(15) NOT NULL COMMENT 'api的id',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='api的后端配置';

/*Table structure for table `api_back_parameter` */

CREATE TABLE IF NOT EXISTS `api_back_parameter` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT COMMENT 'id值',
  `parameter_name` varchar(100) NOT NULL COMMENT '后端参数名称',
  `parameter_pos` varchar(30) NOT NULL COMMENT '后端参数位置',
  `front_para_name` varchar(100) NOT NULL COMMENT '前端参数名称',
  `front_para_pos` varchar(30) NOT NULL COMMENT '前端参数位置',
  `description` varchar(300) DEFAULT NULL COMMENT '描述信息',
  `api_id` bigint(15) NOT NULL COMMENT 'api的id',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='api的后端参数';

/*Table structure for table `api_front_config` */

CREATE TABLE IF NOT EXISTS `api_front_config` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT COMMENT '前端配置id',
  `front_path` varchar(300) NOT NULL COMMENT '前端路径',
  `method` varchar(30) NOT NULL COMMENT '请求方式',
  `interface_pro` varchar(30) NOT NULL COMMENT '接口协议',
  `api_id` bigint(15) NOT NULL COMMENT 'api的id',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='api的前端配置';

/*Table structure for table `api_front_parameter` */

CREATE TABLE IF NOT EXISTS `api_front_parameter` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT COMMENT '前端参数id',
  `parameter_name` varchar(100) NOT NULL COMMENT '参数名称',
  `parameter_pos` varchar(30) NOT NULL COMMENT '参数位置',
  `data_type` varchar(30) NOT NULL COMMENT '数据类型',
  `required` varchar(30) NOT NULL COMMENT '是否必须',
  `description` varchar(300) DEFAULT NULL COMMENT '参数描述',
  `api_id` bigint(15) NOT NULL COMMENT 'api的id',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='api的前端参数';

/*Table structure for table `api_info` */

CREATE TABLE IF NOT EXISTS `api_info` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT COMMENT 'api基本信息id值',
  `api_name` varchar(64) NOT NULL COMMENT 'api的名称',
  `api_desc` varchar(300) DEFAULT NULL COMMENT 'api描述信息',
  `ase_key` varchar(100) DEFAULT NULL COMMENT 'aseKey值',
  `rsa_privatekey` varchar(100) DEFAULT NULL COMMENT 'rsaPrivatekey值',
  `rsa_publickey` varchar(100) DEFAULT NULL COMMENT 'rsaPublickey值',
  `serivce_id` bigint(15) NOT NULL COMMENT '服务id',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `api_name` (`api_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='api的基本信息';

/*Table structure for table `application` */

CREATE TABLE IF NOT EXISTS `application` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT COMMENT '主键id，也是AppId',
  `name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '应用的名称',
  `app_desc` varchar(300) COLLATE utf8_bin DEFAULT NULL COMMENT '应用的描述信息',
  `app_key` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT 'AppKey的值',
  `app_secret` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT 'AppSecret的值',
  `pub_key` varchar(1024) COLLATE utf8_bin DEFAULT NULL COMMENT 'app的公钥',
  `app_code` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT 'AppCode的值',
  `user_id` bigint(20) DEFAULT NULL COMMENT '应用的创建者',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `aes_private_key` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '加解密私钥',
  `encrypt_public_key` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '加密公钥',
  `encrypt_private_key` varchar(2048) COLLATE utf8_bin DEFAULT NULL COMMENT '加密私钥',
  `sign_public_key` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '签名公钥',
  `sign_private_key` varchar(2048) COLLATE utf8_bin DEFAULT NULL COMMENT '签名私钥',
  `client_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '客户端编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='应用信息表';

/*Table structure for table `cfg_app_client` */

CREATE TABLE IF NOT EXISTS `cfg_app_client` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `client_id` varchar(64) NOT NULL COMMENT '客户端编号',
  `app_code` varchar(64) NOT NULL COMMENT 'app编码',
  `client_type` varchar(16) NOT NULL COMMENT '客户端类型pc/android/ios/applet/h5',
  `encrypt_public_key` varchar(512) DEFAULT NULL COMMENT '加密公钥',
  `encrypt_private_key` varchar(2048) DEFAULT NULL COMMENT '加密私钥',
  `sign_public_key` varchar(512) DEFAULT NULL COMMENT '签名公钥',
  `sign_private_key` varchar(2048) DEFAULT NULL COMMENT '签名私钥',
  `secret` varchar(512) DEFAULT NULL COMMENT 'secret密钥',
  `access_token_check` varchar(8) NOT NULL DEFAULT 'N' COMMENT '检查access_token',
  `request_encrypt` varchar(8) NOT NULL DEFAULT 'N' COMMENT '请求参数是否加密',
  `response_encrypt` varchar(8) NOT NULL DEFAULT 'N' COMMENT '响应结果是否加密',
  `request_sign` varchar(8) NOT NULL DEFAULT 'Y' COMMENT '请求参数是否签名',
  `request_sign_type` varchar(64) NOT NULL DEFAULT 'SHA256WITHRSA' COMMENT '签名算法类型',
  `response_sign` varchar(8) NOT NULL DEFAULT 'N' COMMENT '响应结果是否签名',
  `param_complete_check` varchar(8) NOT NULL DEFAULT 'N' COMMENT '检查参数完整性',
  `param_ts_check` varchar(8) NOT NULL DEFAULT 'N' COMMENT '检查时间戳',
  `param_nonce_check` varchar(8) NOT NULL DEFAULT 'N' COMMENT '检查随机字符串',
  `status` varchar(32) NOT NULL DEFAULT 'normal' COMMENT '状态[可用normal,不可用deleted]',
  `creator` varchar(64) DEFAULT NULL COMMENT '记录创建者',
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uni_client_id` (`client_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1282922475468955656 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='客户端密钥配置表';

/*Table structure for table `cfg_filter` */

CREATE TABLE IF NOT EXISTS `cfg_filter` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `gateway_id` varchar(100) NOT NULL COMMENT '路由信息的id',
  `value` varchar(200) DEFAULT NULL COMMENT '一个路由id下用到的自定义过滤器',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='小程序的过滤器';

/*Table structure for table `cfg_nacos` */

CREATE TABLE IF NOT EXISTS `cfg_nacos` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `gateway_id` varchar(100) NOT NULL COMMENT '路由信息的id',
  `uri` varchar(200) NOT NULL COMMENT 'uri的值',
  `predicate` varchar(200) NOT NULL COMMENT '断言',
  `regular_path` varchar(200) DEFAULT NULL COMMENT '匹配规则',
  `actual_path` varchar(200) DEFAULT NULL COMMENT '实际路径',
  `parts_key` varchar(200) DEFAULT NULL COMMENT 'StripPrefix的值',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='小程序的路由配置';

/*Table structure for table `constant_parameter` */

CREATE TABLE IF NOT EXISTS `constant_parameter` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT COMMENT 'id字段',
  `parameter_name` varchar(100) NOT NULL COMMENT '参数名称',
  `parameter_pos` varchar(30) NOT NULL COMMENT '参数位置',
  `data_type` varchar(30) NOT NULL COMMENT '数据类型',
  `required` varchar(30) NOT NULL COMMENT '是否必须',
  `value` varchar(300) DEFAULT NULL COMMENT '参数数值',
  `description` varchar(300) DEFAULT NULL COMMENT '描述',
  `api_id` bigint(15) NOT NULL COMMENT 'api的id',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='常量参数表';

/*Table structure for table `filter` */

CREATE TABLE IF NOT EXISTS `filter` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `filter_name` varchar(100) NOT NULL COMMENT 'filter的名称',
  `filter_value` varchar(100) NOT NULL COMMENT 'filter的值',
  `filter_desc` varchar(300) DEFAULT NULL COMMENT 'filter描述信息',
  `api_id` bigint(15) NOT NULL COMMENT '对应的api的id',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='过滤器表';

/*Table structure for table `sentinel` */

CREATE TABLE IF NOT EXISTS `sentinel` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT COMMENT '限流降级表的主键id',
  `api_id` bigint(15) NOT NULL COMMENT '对应的api的id',
  `flow_grade` varchar(1) DEFAULT NULL COMMENT '0:线程数,1:QPS',
  `flow_count` int(8) DEFAULT NULL COMMENT '线程数或QPS峰值',
  `flow_startegy` varchar(1) DEFAULT NULL COMMENT '0:直接，1:关联，2:链路',
  `flow_behavior` varchar(1) DEFAULT NULL COMMENT '0:直接失败，1:warm up，2:排队等待，3:warm+排队等待',
  `flow_cluster` varchar(10) DEFAULT NULL COMMENT 'true:集群模式，false:非集群模式',
  `degrade_grade` varchar(1) DEFAULT NULL COMMENT '0:RT，1:异常比例',
  `degrade_count` int(8) DEFAULT NULL COMMENT 'RT阈值和异常数',
  `degrade_time` int(8) DEFAULT NULL COMMENT '降级窗口时间',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='sentinel规则表';

/*Table structure for table `service` */

CREATE TABLE IF NOT EXISTS `service` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT COMMENT 'service表id',
  `service_name` varchar(100) NOT NULL COMMENT '服务名称',
  `service_desc` varchar(300) DEFAULT NULL COMMENT '服务描述',
  `user_id` bigint(15) NOT NULL COMMENT '服务负责人',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='分组信息表';

/*Table structure for table `user_info` */

CREATE TABLE IF NOT EXISTS `user_info` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_name` varchar(64) NOT NULL COMMENT '用户名',
  `nick_name` varchar(64) NOT NULL COMMENT '用户昵称',
  `password` varchar(128) NOT NULL COMMENT '密码',
  `email` varchar(128) NOT NULL COMMENT '邮箱',
  `phone` varchar(32) NOT NULL COMMENT '电话',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `USERNAME` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户信息表';

