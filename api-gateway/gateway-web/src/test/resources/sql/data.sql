INSERT INTO `test_icity_api_gateway`.`filter`(`id`, `filter_name`, `filter_value`, `filter_desc`, `api_id`, `created_time`, `updated_time`) VALUES (7, 'aa', 'a', 'a', 1, '2021-01-04 20:13:00', '2021-01-04 20:13:00');

INSERT INTO `test_icity_api_gateway`.`application`(`id`, `name`, `app_desc`, `app_key`, `app_secret`, `pub_key`, `app_code`, `user_id`, `created_time`, `updated_time`, `aes_private_key`, `encrypt_public_key`, `encrypt_private_key`, `sign_public_key`, `sign_private_key`, `client_id`) VALUES (4, 'name', 'desc', 'app_key', 'app_secret', 'pbu_key', 'app_code', 11111, '2021-01-06 14:42:58', '2021-01-06 14:42:58', 'aes_private_key', 'encrypt_key', 'encrypt_private_key', 'sign_public_key', 'sign_private_key', 'clicent_id');

INSERT INTO `test_icity_api_gateway`.`api_front_config`(`id`, `front_path`, `method`, `interface_pro`, `api_id`, `created_time`, `updated_time`) VALUES (7, 'path', 'method', 'protocol', 1, '2021-01-06 16:36:41', '2021-01-06 16:36:41');

INSERT INTO `test_icity_api_gateway`.`api_front_parameter`(`id`, `parameter_name`, `parameter_pos`, `data_type`, `required`, `description`, `api_id`, `created_time`, `updated_time`) VALUES (2, 'name', 'pos', 'type', 'require', 'desc', 1, '2021-01-06 18:01:09', '2021-01-06 18:01:09');

INSERT INTO `test_icity_api_gateway`.`api_back_parameter`(`id`, `parameter_name`, `parameter_pos`, `front_para_name`, `front_para_pos`, `description`, `api_id`, `created_time`, `updated_time`) VALUES (2, 'name', 'pos', 'front_name', 'front_pos', 'desc', 1, '2021-01-06 20:17:48', '2021-01-06 20:17:48');

INSERT INTO `test_icity_api_gateway`.`api_back_config`(`id`,`back_addr`,`back_path`,`method`,`interface_pro`,`api_id`) VALUES(7,'backAddr','backPath','method','interfacePro',7);