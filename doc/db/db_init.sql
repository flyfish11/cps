create database `cloud_service` default character set utf8mb4 collate utf8mb4_general_ci;

USE cloud_service;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for black_ip
-- ----------------------------
DROP TABLE IF EXISTS `black_ip`;
CREATE TABLE `black_ip` (
  `id` varchar(100) NOT NULL COMMENT '主键id',
  `ip` varchar(32) NOT NULL COMMENT 'IP地址',
  `create_by` varchar(100) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(100) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='黑名单表';

-- ----------------------------
-- Table structure for file_info
-- ----------------------------
DROP TABLE IF EXISTS `file_info`;
CREATE TABLE `file_info` (
  `id` varchar(100) NOT NULL COMMENT '主键id',
  `file_name` varchar(100) NOT NULL COMMENT '文件名',
  `bucket_name` varchar(200) DEFAULT NULL COMMENT '保存路径',
  `original` varchar(100) NOT NULL COMMENT '原文件名',
  `type` varchar(100) DEFAULT NULL COMMENT '文件类型',
  `file_size` bigint(50) DEFAULT NULL COMMENT '文件大小',
  `created_by` varchar(100) DEFAULT NULL COMMENT '上传人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件管理表';

-- ----------------------------
-- Table structure for gateway_api_define
-- ----------------------------
DROP TABLE IF EXISTS `gateway_api_define`;
CREATE TABLE `gateway_api_define` (
  `id` varchar(100) NOT NULL,
  `path` varchar(255) NOT NULL,
  `service_id` varchar(50) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `retryable` tinyint(1) DEFAULT NULL,
  `enabled` tinyint(1) NOT NULL,
  `strip_prefix` int(11) DEFAULT NULL,
  `api_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of gateway_api_define
-- ----------------------------
BEGIN;
INSERT INTO `gateway_api_define` VALUES ('应用管理中心', '/api/api-a/**', 'app-manager-center', NULL, 0, 1, 1, '应用管理中心');
INSERT INTO `gateway_api_define` VALUES ('文件中心', '/api/api-f/**', 'file-center', NULL, 0, 1, 1, '文件中心');
INSERT INTO `gateway_api_define` VALUES ('日志中心', '/api/api-l/**', 'log-center', NULL, 0, 1, 1, '日志中心');
INSERT INTO `gateway_api_define` VALUES ('用户中心', '/api/api-u/**', 'user-center', '', 0, 1, 1, '用户中心');
INSERT INTO `gateway_api_define` VALUES ('网关模块', '/api/api-g/**', 'gateway-zuul', NULL, 0, 1, 1, '网关模块');
INSERT INTO `gateway_api_define` VALUES ('认证中心', '/api/api-o/**', 'oauth-center', '', 0, 1, 1, '认证中心');
COMMIT;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(32) NOT NULL,
  `resource_ids` varchar(256) DEFAULT NULL,
  `client_secret` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `authorized_grant_types` varchar(256) DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
BEGIN;
INSERT INTO `oauth_client_details` VALUES ('ad', NULL, 'ad', 'app', 'authorization_code,password,refresh_token', NULL, NULL, 28800, NULL, NULL, NULL);
INSERT INTO `oauth_client_details` VALUES ('system', NULL, '$2a$10$QN9vg9iX3WFovHnDX7bJO.rWWDkS0VP7HYhV.HDiVEE56xPwZfjKe', 'app', 'authorization_code,password,refresh_token', NULL, NULL, 28800, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for oauth_code
-- ----------------------------
DROP TABLE IF EXISTS `oauth_code`;
CREATE TABLE `oauth_code` (
  `code` varchar(32) NOT NULL,
  `authentication` blob,
  PRIMARY KEY (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
  `roleId` varchar(50) NOT NULL COMMENT '角色id',
  `menuId` varchar(50) NOT NULL COMMENT '菜单id',
  PRIMARY KEY (`roleId`,`menuId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
BEGIN;
INSERT INTO `role_menu` VALUES ('1', '02da7b13a43c48d2b32136f0e9f6a60c');
INSERT INTO `role_menu` VALUES ('1', '05616a13cf924252a59ea5ae98933b00');
INSERT INTO `role_menu` VALUES ('1', '0df5ceccb49e452c9a040cfa646e126e');
INSERT INTO `role_menu` VALUES ('1', '0ed926b252e742879ff543d5fa9a91a9');
INSERT INTO `role_menu` VALUES ('1', '1');
INSERT INTO `role_menu` VALUES ('1', '13d1127ca9104e9ea5d871472eac07cb');
INSERT INTO `role_menu` VALUES ('1', '1595871b63474016b480ca6b56e53fa9');
INSERT INTO `role_menu` VALUES ('1', '171e3e157ea24c8ca75242f62417cbbb');
INSERT INTO `role_menu` VALUES ('1', '211374d304e34b2dbb2714239eb4f677');
INSERT INTO `role_menu` VALUES ('1', '26929b3329744ac0bdf84f29c55eb336');
INSERT INTO `role_menu` VALUES ('1', '26ae9f753b6e429ba58871756fb8e32d');
INSERT INTO `role_menu` VALUES ('1', '2ba380748e774f7ab10671e4595b947e');
INSERT INTO `role_menu` VALUES ('1', '30f4336b6ed74eef8be914506c48b326');
INSERT INTO `role_menu` VALUES ('1', '34426ee09adf43a8af5340597ae08617');
INSERT INTO `role_menu` VALUES ('1', '4237ab4478ab4b069650af5642a960e6');
INSERT INTO `role_menu` VALUES ('1', '44235f8e90844f5c8e7cc1876fab8ed7');
INSERT INTO `role_menu` VALUES ('1', '48b92b35b5874fb29680ee51e3562021');
INSERT INTO `role_menu` VALUES ('1', '4aa6ea86edff488fba169836016640a8');
INSERT INTO `role_menu` VALUES ('1', '4bedb2f2e05744ddadc236ccc099a5d8');
INSERT INTO `role_menu` VALUES ('1', '4ddfdcc818b2441f902154f2a9a599d4');
INSERT INTO `role_menu` VALUES ('1', '4f8dbe14d1764e91b844fb3c42c995a6');
INSERT INTO `role_menu` VALUES ('1', '58f0eb16b2fa4239b1e94eb476506412');
INSERT INTO `role_menu` VALUES ('1', '5d8b416c1bf64b0e8bd3b8a8641b63b6');
INSERT INTO `role_menu` VALUES ('1', '691ac5f947674e588e537aca62dffc54');
INSERT INTO `role_menu` VALUES ('1', '6b29bf39d8d240da803929a9e30cb400');
INSERT INTO `role_menu` VALUES ('1', '720f37f8cc9943228b5886e290dab460');
INSERT INTO `role_menu` VALUES ('1', '748c6f5285ce4682a74db1975f7bee48');
INSERT INTO `role_menu` VALUES ('1', '7ab9e84ad3014f52b799f70e8e5729c1');
INSERT INTO `role_menu` VALUES ('1', '7adb7fadd0054257b43e8540b0c96ea9');
INSERT INTO `role_menu` VALUES ('1', '7bb72907014943bd838082b709e2b85f');
INSERT INTO `role_menu` VALUES ('1', '7d8889f9a2bc4fa6ac76431522c1720f');
INSERT INTO `role_menu` VALUES ('1', '7ede16e11aa24a2c9d0141e372a29dc9');
INSERT INTO `role_menu` VALUES ('1', '84f5d82362b1468cb8bb6758162c811e');
INSERT INTO `role_menu` VALUES ('1', '87452a15de1642e595316664e520df51');
INSERT INTO `role_menu` VALUES ('1', '8a29c5164780463c80722f2601cf0a24');
INSERT INTO `role_menu` VALUES ('1', '8b02b70f07ba4a11a1701584ac71faac');
INSERT INTO `role_menu` VALUES ('1', '9063a158fc0947f0afe56d1a2ad1016c');
INSERT INTO `role_menu` VALUES ('1', '9b47f0229d8d4d768cad078fb86ab7a7');
INSERT INTO `role_menu` VALUES ('1', 'a2a059b891144124b19df355029cd010');
INSERT INTO `role_menu` VALUES ('1', 'ad5a28debb8646458f5e495f5e8f2b88');
INSERT INTO `role_menu` VALUES ('1', 'b19798545fc243f0911d16e42314d234');
INSERT INTO `role_menu` VALUES ('1', 'be7e75c3c3ee4046adcceb1bab225827');
INSERT INTO `role_menu` VALUES ('1', 'c89cb42f142b4644ad312c12c9485223');
INSERT INTO `role_menu` VALUES ('1', 'cc0766812d62409a9caf42a44d70b2d6');
INSERT INTO `role_menu` VALUES ('1', 'd4868d083e2348f98b9d34fb127b213a');
INSERT INTO `role_menu` VALUES ('1', 'd54e067a6a0d49af91499a45bcd63e11');
INSERT INTO `role_menu` VALUES ('1', 'd8f970a0fff147368f48dd2a8bd8d572');
INSERT INTO `role_menu` VALUES ('1', 'd9cce957cf4b42fab29c9299acb28400');
INSERT INTO `role_menu` VALUES ('1', 'da539a7475cb41219a1be394756de58a');
INSERT INTO `role_menu` VALUES ('1', 'dfcc05d2ad894960972c58090c384202');
INSERT INTO `role_menu` VALUES ('1', 'e3fc2c3417c4456aa548b35c84c9f458');
INSERT INTO `role_menu` VALUES ('1', 'ec73c937fb334b18881fb0343ce20d94');
INSERT INTO `role_menu` VALUES ('1', 'ef27c6b8085d4ec1bb4f47446bb61839');
INSERT INTO `role_menu` VALUES ('1', 'efcc2da66bab48ee9109e7a4014ecba7');
INSERT INTO `role_menu` VALUES ('1', 'f0d92d1beb0c44b7a131e59f42600dbd');
INSERT INTO `role_menu` VALUES ('1', 'f91ebbfd7bf34f5bb12cc6f5d0a2ec5a');
INSERT INTO `role_menu` VALUES ('1', 'fabf3f2274b34291977f7a3eca1c1fac');
COMMIT;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` int(25) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `pid` int(25) DEFAULT NULL COMMENT '父部门id',
  `fullname` varchar(255) DEFAULT NULL COMMENT '部门名称',
  `sdeptname` varchar(255) DEFAULT NULL COMMENT '部门简称',
  `num` int(11) DEFAULT '0' COMMENT '排序',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='部门表';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
BEGIN;
INSERT INTO `sys_dept` VALUES (1, 0, '华龙讯达', '华龙', 1, 'admin', '2020-04-21 15:38:01', NULL, NULL, NULL);
INSERT INTO `sys_dept` VALUES (3, 1, '测试部', '测试部', 2, 'admin', '2020-04-21 19:45:30', 'admin', '2020-04-21 19:45:30', NULL);
INSERT INTO `sys_dept` VALUES (5, 3, '测试一部', '测试一部', 1, 'admin', '2020-04-21 19:44:56', 'admin', '2020-04-21 19:44:56', '');
INSERT INTO `sys_dept` VALUES (6, 3, '测试二部', '测试二部', 2, 'admin', '2020-04-21 19:45:30', 'admin', '2020-04-26 12:54:22', '');
COMMIT;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `type` varchar(100) DEFAULT NULL COMMENT '类型',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `system` char(1) DEFAULT '0' COMMENT '0-否|1-是',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `sys_dict_del_flag` (`del_flag`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典表';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict` VALUES ('12f80dd3116948c09c9ab984a9d779d2', 'app_type', '应用类型', '2020-04-24 17:02:33', '2020-04-24 17:02:56', NULL, '0', '0');
INSERT INTO `sys_dict` VALUES ('48f80dd3116948c09c9ab984a9d779f5', 'sys_sex', '性别', NULL, '2019-12-27 16:46:32', '男;女;未知', NULL, '0');
COMMIT;

-- ----------------------------
-- Table structure for sys_dict_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_item`;
CREATE TABLE `sys_dict_item` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `dict_id` varchar(64) DEFAULT NULL,
  `value` varchar(100) DEFAULT NULL COMMENT '数据值',
  `label` varchar(100) DEFAULT NULL COMMENT '标签名',
  `type` varchar(100) DEFAULT NULL COMMENT '类型',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `sort` int(10) DEFAULT NULL COMMENT '排序（升序）',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `sys_dict_value` (`value`) USING BTREE,
  KEY `sys_dict_label` (`label`) USING BTREE,
  KEY `sys_dict_del_flag` (`del_flag`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典项';

-- ----------------------------
-- Records of sys_dict_item
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict_item` VALUES ('0c495e758d2d471f88249ddf96e9da6a', '12f80dd3116948c09c9ab984a9d779d2', '2', 'IOS应用', 'app_type', 'IOS应用', 2, NULL, '2020-04-27 13:56:25', 'IOS应用', '0');
INSERT INTO `sys_dict_item` VALUES ('2f67e7d8fd4e45e28336878d1c654e59', '12f80dd3116948c09c9ab984a9d779d2', '4', 'Windows客户端', 'app_type', 'Windows客户端', 4, NULL, '2020-04-27 13:56:26', 'Windows客户端', '0');
INSERT INTO `sys_dict_item` VALUES ('6bd1751881004c6ba73d4f703a6cf2a1', '48f80dd3116948c09c9ab984a9d779f5', '3', '未知', 'sys_sex', '未知', 3, '2019-12-27 16:46:11', '2020-04-24 17:16:35', '未知', '0');
INSERT INTO `sys_dict_item` VALUES ('84eef9f19d16411bb5e4d20bd83cc04b', '12f80dd3116948c09c9ab984a9d779d2', '6', 'Web应用', 'app_type', 'Web应用', 6, NULL, '2020-04-27 13:56:28', 'Web应用', '0');
INSERT INTO `sys_dict_item` VALUES ('896cc9f37e374a029013a768b73a5eb4', '12f80dd3116948c09c9ab984a9d779d2', '3', '安卓应用', 'app_type', '安卓应用', 3, NULL, '2020-04-27 13:56:33', '安卓应用', '0');
INSERT INTO `sys_dict_item` VALUES ('9as1751481004c6ba73d4f703a6cf2a3', '48f80dd3116948c09c9ab984a9d779f5', '1', '男', 'sys_sex', '男', 1, NULL, '2020-04-24 17:16:46', '男', '0');
INSERT INTO `sys_dict_item` VALUES ('bad1751881004c6ba73d4f703a6cf2aw', '48f80dd3116948c09c9ab984a9d779f5', '2', '女', 'sys_sex', '女', 2, NULL, '2019-12-27 16:46:11', '女', '0');
INSERT INTO `sys_dict_item` VALUES ('d55f43d0fa75455499edc589c19e915d', '12f80dd3116948c09c9ab984a9d779d2', '5', 'Mac应用', 'app_type', 'Mac应用', 5, NULL, '2020-04-27 13:56:35', 'Mac应用', '0');
COMMIT;

-- ----------------------------
-- Table structure for sys_ldap_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_ldap_config`;
CREATE TABLE `sys_ldap_config` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `url` varchar(255) DEFAULT NULL COMMENT 'LDAP访问地址',
  `port` int(10) DEFAULT NULL COMMENT '端口号',
  `dn` varchar(255) DEFAULT NULL COMMENT 'DN',
  `admin_account` varchar(255) DEFAULT NULL COMMENT '管理员账号',
  `admin_password` varchar(255) DEFAULT NULL COMMENT '管理员密码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='LDAP配置表';

-- ----------------------------
-- Records of sys_ldap_config
-- ----------------------------
BEGIN;
INSERT INTO `sys_ldap_config` VALUES (1, 'ldap://192.168.11.37', 389, 'dc=tobacco,dc=com', 'admin', '123456');
COMMIT;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `type` tinyint(1) DEFAULT NULL COMMENT '是否成功(0成功,1失败)',
  `title` varchar(255) DEFAULT NULL COMMENT '日志主题',
  `service_id` varchar(100) DEFAULT NULL COMMENT '服务ID',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remote_addr` varchar(255) DEFAULT NULL COMMENT '客户端IP',
  `user_agent` varchar(1000) DEFAULT NULL COMMENT '客户端类型',
  `request_uri` varchar(255) DEFAULT NULL COMMENT '请求地址',
  `method` varchar(10) DEFAULT NULL COMMENT '请求方式',
  `params` text COMMENT '请求参数',
  `execute_time` mediumtext CHARACTER SET utf8 COMMENT '执行时间',
  `delete_flag` tinyint(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `exception` text COMMENT '异常信息',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `sys_log_create_by` (`create_by`) USING BTREE,
  KEY `sys_log_request_uri` (`request_uri`) USING BTREE,
  KEY `sys_log_type` (`type`) USING BTREE,
  KEY `sys_log_create_date` (`create_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2607 DEFAULT CHARSET=utf8mb4 COMMENT='日志表';

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` varchar(50) NOT NULL COMMENT '菜单id',
  `title` varchar(50) NOT NULL COMMENT '菜单名称',
  `pId` varchar(50) DEFAULT NULL COMMENT '父菜单id',
  `menu_type` varchar(4) DEFAULT NULL COMMENT '菜单类型(C菜单,F按钮）',
  `path` varchar(255) DEFAULT NULL COMMENT '前端路由',
  `url` varchar(200) DEFAULT NULL COMMENT '请求地址(ifream)',
  `perms` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `order_num` int(4) DEFAULT '0' COMMENT '显示顺序',
  `visible` char(1) DEFAULT '0' COMMENT '菜单状态(0显示,1隐藏)',
  `icon` varchar(100) DEFAULT NULL COMMENT '菜单图标',
  `menu_scope` tinyint(1) DEFAULT NULL COMMENT '是否内部系统(0否,1是)',
  `application_id` varchar(50) DEFAULT NULL COMMENT '所属应用id',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单权限表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` VALUES ('02da7b13a43c48d2b32136f0e9f6a60c', '运维管理', '1', 'C', 'operation.manage', NULL, NULL, 5, '0', 'el-icon-s-order', 1, '1', 'admin', '2019-04-25 22:00:01', '', '2019-09-24 13:56:06', '');
INSERT INTO `sys_menu` VALUES ('05616a13cf924252a59ea5ae98933b00', '配置服务', '4237ab4478ab4b069650af5642a960e6', 'F', NULL, NULL, 'service:config', 4, '0', NULL, 1, '1', 'admin', '2020-04-17 10:02:17', '', '2020-04-17 10:02:17', '');
INSERT INTO `sys_menu` VALUES ('0df5ceccb49e452c9a040cfa646e126e', '用户管理', '1', 'C', 'userCenter', NULL, NULL, 1, '0', 'el-icon-s-custom', 1, '1', 'admin', '2019-04-25 20:49:34', '', '2019-12-19 09:22:55', '');
INSERT INTO `sys_menu` VALUES ('0ed926b252e742879ff543d5fa9a91a9', '修改字典项', 'dfcc05d2ad894960972c58090c384202', 'F', NULL, NULL, 'dict:item_edit', 5, '0', NULL, 1, '1', 'admin', '2020-04-17 14:17:44', '', '2020-04-17 14:17:44', '');
INSERT INTO `sys_menu` VALUES ('1', '微服务中心', '0', 'C', 'micservice.center', NULL, NULL, 1, '0', NULL, 1, '1', 'admin', '2019-04-25 22:00:01', '', '2019-09-24 13:53:03', '');
INSERT INTO `sys_menu` VALUES ('1074e97645634f22bb25ac09065998e2', '我的审核', '3b778958b90e4e2f8d57d3799dc2cc93', 'C', 'my.auditing', NULL, NULL, 4, '0', NULL, 1, '1', 'admin', '2019-04-25 21:50:55', '', '2019-04-25 21:50:55', '');
INSERT INTO `sys_menu` VALUES ('13d1127ca9104e9ea5d871472eac07cb', '修改菜单', '211374d304e34b2dbb2714239eb4f677', 'F', NULL, NULL, 'menu:edit', 2, '0', NULL, 1, '1', 'admin', '2020-04-17 09:55:29', '', '2020-04-17 09:55:29', '');
INSERT INTO `sys_menu` VALUES ('1595871b63474016b480ca6b56e53fa9', '代码仓库', '02da7b13a43c48d2b32136f0e9f6a60c', 'C', 'codewarehouse', 'http://10.130.86.36:8889', NULL, 1, '0', NULL, 1, '1', 'admin', '2019-04-25 22:01:28', '', '2019-09-24 18:33:56', '');
INSERT INTO `sys_menu` VALUES ('171e3e157ea24c8ca75242f62417cbbb', '修改字典类型', 'dfcc05d2ad894960972c58090c384202', 'F', NULL, NULL, 'dict:type_edit', 2, '0', NULL, 1, '1', 'admin', '2020-04-17 10:05:38', '', '2020-04-17 10:05:38', '');
INSERT INTO `sys_menu` VALUES ('1bd67c9ef9404182a905920483b6d3f2', '模型管理', '3b778958b90e4e2f8d57d3799dc2cc93', 'C', 'model.manage', NULL, NULL, 1, '0', NULL, 1, '1', 'admin', '2019-04-25 21:49:28', '', '2019-04-25 21:44:29', '');
INSERT INTO `sys_menu` VALUES ('1e29bf137c5c44b7a4ffa0e6c9b5eef0', '我的流程', '3b778958b90e4e2f8d57d3799dc2cc93', 'C', 'my.flow', NULL, NULL, 3, '0', NULL, 1, '1', 'admin', '2019-04-25 21:49:46', '', '2019-04-25 21:49:46', '');
INSERT INTO `sys_menu` VALUES ('211374d304e34b2dbb2714239eb4f677', '菜单管理', '1', 'C', 'menuCenter', NULL, NULL, 2, '0', 'el-icon-menu', 1, '1', 'admin', '2019-04-25 21:05:55', '', '2019-09-24 13:46:55', '');
INSERT INTO `sys_menu` VALUES ('26929b3329744ac0bdf84f29c55eb336', '用户管理', '0df5ceccb49e452c9a040cfa646e126e', 'C', 'user', NULL, NULL, 1, '0', NULL, 1, '1', 'admin', '2019-04-25 20:45:11', '', '2019-09-24 09:48:37', '');
INSERT INTO `sys_menu` VALUES ('26ae9f753b6e429ba58871756fb8e32d', '新增部门', 'f0d92d1beb0c44b7a131e59f42600dbd', 'F', NULL, NULL, 'dept:add', 1, '0', NULL, 1, '1', 'admin', '2020-04-17 09:50:30', '', '2020-04-17 09:50:30', '');
INSERT INTO `sys_menu` VALUES ('2ba380748e774f7ab10671e4595b947e', '删除部门', 'f0d92d1beb0c44b7a131e59f42600dbd', 'F', NULL, NULL, 'dept:delete', 3, '0', NULL, 1, '1', 'admin', '2020-04-17 09:51:45', '', '2020-04-17 09:51:45', '');
INSERT INTO `sys_menu` VALUES ('30f4336b6ed74eef8be914506c48b326', '日志管理', '1', 'C', 'logCenter', NULL, NULL, 8, '0', 'el-icon-s-open', 1, '1', 'admin', '2019-04-25 22:08:08', '', '2019-09-24 13:55:15', '');
INSERT INTO `sys_menu` VALUES ('34426ee09adf43a8af5340597ae08617', '重置密码', '26929b3329744ac0bdf84f29c55eb336', 'F', NULL, NULL, 'user:reset_pwd', 4, '0', NULL, 1, '1', 'admin', '2020-04-17 09:37:59', '', '2020-04-17 09:37:59', '');
INSERT INTO `sys_menu` VALUES ('3b778958b90e4e2f8d57d3799dc2cc93', '流程管理', '1', 'C', 'flow.mana', NULL, NULL, 10, '0', 'fa fa-exchange', 1, '1', 'admin', '2019-04-25 21:39:49', '', '2019-04-25 21:45:59', '');
INSERT INTO `sys_menu` VALUES ('4237ab4478ab4b069650af5642a960e6', '服务管理', '4bedb2f2e05744ddadc236ccc099a5d8', 'C', 'service', NULL, NULL, 1, '0', NULL, 1, '1', 'admin', '2019-04-25 21:25:25', '', '2019-09-24 09:50:02', '');
INSERT INTO `sys_menu` VALUES ('44235f8e90844f5c8e7cc1876fab8ed7', '新增服务', '4237ab4478ab4b069650af5642a960e6', 'F', NULL, NULL, 'service:add', 1, '0', NULL, 1, '1', 'admin', '2020-04-17 09:59:38', '', '2020-04-17 09:59:38', '');
INSERT INTO `sys_menu` VALUES ('48b92b35b5874fb29680ee51e3562021', '服务发布', '02da7b13a43c48d2b32136f0e9f6a60c', 'C', 'servicerelease', 'http://10.130.86.36:8888/j_acegi_security_check?j_username=admin&j_password=admin', NULL, 2, '0', NULL, 1, '1', 'admin', '2019-04-25 22:02:38', '', '2019-09-24 18:34:17', '');
INSERT INTO `sys_menu` VALUES ('4aa6ea86edff488fba169836016640a8', '修改角色', 'fabf3f2274b34291977f7a3eca1c1fac', 'F', NULL, NULL, 'role:edit', 2, '0', NULL, 1, '1', 'admin', '2020-04-17 09:46:27', '', '2020-04-17 09:46:27', '');
INSERT INTO `sys_menu` VALUES ('4bedb2f2e05744ddadc236ccc099a5d8', '服务中心', '1', 'C', 'serviceCenter', NULL, NULL, 3, '0', 'el-icon-s-ticket', 1, '1', 'admin', '2019-04-25 21:21:13', '', '2019-09-24 13:58:24', '');
INSERT INTO `sys_menu` VALUES ('4ddfdcc818b2441f902154f2a9a599d4', '删除服务', '4237ab4478ab4b069650af5642a960e6', 'F', NULL, NULL, 'service:delete', 3, '0', NULL, 1, '1', 'admin', '2020-04-17 10:01:48', '', '2020-04-17 10:01:48', '');
INSERT INTO `sys_menu` VALUES ('4f8dbe14d1764e91b844fb3c42c995a6', '事务监控', '8a29c5164780463c80722f2601cf0a24', 'C', 'txMonitor', NULL, NULL, 4, '0', NULL, 1, '1', 'admin', '2020-04-13 09:52:02', '', '2020-04-13 09:52:02', '');
INSERT INTO `sys_menu` VALUES ('58f0eb16b2fa4239b1e94eb476506412', '修改用户', '26929b3329744ac0bdf84f29c55eb336', 'F', NULL, NULL, 'user:edit', 2, '0', NULL, 1, '1', 'admin', '2020-04-17 09:24:02', '', '2020-04-17 13:50:38', '');
INSERT INTO `sys_menu` VALUES ('5d8b416c1bf64b0e8bd3b8a8641b63b6', '修改路由', '7d8889f9a2bc4fa6ac76431522c1720f', 'F', NULL, NULL, 'router:edit', 2, '0', NULL, 1, '1', 'admin', '2020-04-17 10:03:05', '', '2020-04-17 10:03:05', '');
INSERT INTO `sys_menu` VALUES ('691ac5f947674e588e537aca62dffc54', '新增用户', '26929b3329744ac0bdf84f29c55eb336', 'F', NULL, NULL, 'user:add', 1, '0', NULL, 1, '1', 'admin', '2020-04-17 09:21:02', '', '2020-04-17 09:21:02', '');
INSERT INTO `sys_menu` VALUES ('6b29bf39d8d240da803929a9e30cb400', '修改部门', 'f0d92d1beb0c44b7a131e59f42600dbd', 'F', NULL, NULL, 'dept:edit', 2, '0', NULL, 1, '1', 'admin', '2020-04-17 09:50:55', '', '2020-04-17 09:50:55', '');
INSERT INTO `sys_menu` VALUES ('720f37f8cc9943228b5886e290dab460', '新增字典类型', 'dfcc05d2ad894960972c58090c384202', 'F', NULL, NULL, 'dict:type_add', 1, '0', NULL, 1, '1', 'admin', '2020-04-17 10:05:18', '', '2020-04-17 10:05:18', '');
INSERT INTO `sys_menu` VALUES ('748c6f5285ce4682a74db1975f7bee48', '注册中心', '8a29c5164780463c80722f2601cf0a24', 'C', 'register', 'http://10.130.86.37:30020/', NULL, 1, '0', NULL, 1, '1', 'admin', '2019-04-25 21:17:59', '', '2019-09-24 14:05:16', '');
INSERT INTO `sys_menu` VALUES ('7ab9e84ad3014f52b799f70e8e5729c1', '系统管理', '1', 'C', 'system', NULL, NULL, 6, '0', 'el-icon-s-home', 1, '1', 'admin', '2019-04-25 21:02:21', '', '2019-09-24 13:48:29', '');
INSERT INTO `sys_menu` VALUES ('7adb7fadd0054257b43e8540b0c96ea9', '新增菜单', '211374d304e34b2dbb2714239eb4f677', 'F', NULL, NULL, 'menu:add', 1, '0', NULL, 1, '1', 'admin', '2020-04-17 09:55:03', '', '2020-04-17 09:55:03', '');
INSERT INTO `sys_menu` VALUES ('7bb72907014943bd838082b709e2b85f', '删除字典项', 'dfcc05d2ad894960972c58090c384202', 'F', NULL, NULL, 'dict:item_delete', 6, '0', NULL, 1, '1', 'admin', '2020-04-17 14:18:14', '', '2020-04-17 14:18:14', '');
INSERT INTO `sys_menu` VALUES ('7d8889f9a2bc4fa6ac76431522c1720f', '动态路由', '4bedb2f2e05744ddadc236ccc099a5d8', 'C', 'router', NULL, NULL, 2, '0', NULL, 1, '1', 'admin', '2019-07-16 23:40:51', '', '2019-09-24 09:50:11', '');
INSERT INTO `sys_menu` VALUES ('7ede16e11aa24a2c9d0141e372a29dc9', '调用链拓扑', '8a29c5164780463c80722f2601cf0a24', 'C', 'callchain', 'http://10.130.86.37:30411/zipkin/', NULL, 3, '0', NULL, 1, '1', 'admin', '2019-04-25 21:22:06', '', '2019-09-24 18:26:24', '');
INSERT INTO `sys_menu` VALUES ('84f5d82362b1468cb8bb6758162c811e', '删除黑名单', 'b19798545fc243f0911d16e42314d234', 'F', NULL, NULL, 'blackip:delete', 3, '0', NULL, 1, '1', 'admin', '2020-04-17 10:10:42', '', '2020-04-17 10:10:42', '');
INSERT INTO `sys_menu` VALUES ('87452a15de1642e595316664e520df51', '修改服务', '4237ab4478ab4b069650af5642a960e6', 'F', NULL, NULL, 'service:edit', 2, '0', NULL, 1, '1', 'admin', '2020-04-17 10:00:54', '', '2020-04-17 10:00:54', '');
INSERT INTO `sys_menu` VALUES ('8a29c5164780463c80722f2601cf0a24', '服务治理', '1', 'C', 'service.govern', NULL, NULL, 4, '0', 'el-icon-s-fold', 1, '1', 'admin', '2019-04-25 21:25:16', '', '2019-09-24 13:57:30', '');
INSERT INTO `sys_menu` VALUES ('8b02b70f07ba4a11a1701584ac71faac', '上传jar包', '4237ab4478ab4b069650af5642a960e6', 'F', NULL, NULL, 'service:upload', 4, '0', NULL, 1, '1', 'admin', '2020-04-17 14:00:37', '', '2020-04-17 14:01:17', '');
INSERT INTO `sys_menu` VALUES ('9063a158fc0947f0afe56d1a2ad1016c', '删除文件', 'c89cb42f142b4644ad312c12c9485223', 'F', NULL, NULL, 'file:delete', 1, '0', NULL, 1, '1', 'admin', '2020-04-17 10:08:27', '', '2020-04-17 10:08:27', '');
INSERT INTO `sys_menu` VALUES ('9b47f0229d8d4d768cad078fb86ab7a7', '在线事务', '8a29c5164780463c80722f2601cf0a24', 'C', 'txOnlines', NULL, NULL, 5, '0', NULL, 1, '1', 'admin', '2020-04-13 10:57:38', '', '2020-04-13 10:57:38', '');
INSERT INTO `sys_menu` VALUES ('a2a059b891144124b19df355029cd010', '新增字典项', 'dfcc05d2ad894960972c58090c384202', 'F', NULL, NULL, 'dict:item_add', 4, '0', NULL, 1, '1', 'admin', '2020-04-17 10:06:42', '', '2020-04-17 10:06:42', '');
INSERT INTO `sys_menu` VALUES ('ad5a28debb8646458f5e495f5e8f2b88', '删除字典类型', 'dfcc05d2ad894960972c58090c384202', 'F', NULL, NULL, 'dict:type_delete', 3, '0', NULL, 1, '1', 'admin', '2020-04-17 10:05:57', '', '2020-04-17 10:05:57', '');
INSERT INTO `sys_menu` VALUES ('b19798545fc243f0911d16e42314d234', '黑白名单', '1', 'C', 'blackipindex', NULL, NULL, 9, '0', 'el-icon-s-opportunity', 1, '1', 'admin', '2019-04-25 21:37:15', '', '2019-09-24 18:39:06', '');
INSERT INTO `sys_menu` VALUES ('be7e75c3c3ee4046adcceb1bab225827', '新增角色', 'fabf3f2274b34291977f7a3eca1c1fac', 'F', NULL, NULL, 'role:add', 1, '0', NULL, 1, '1', 'admin', '2020-04-17 09:45:57', '', '2020-04-17 09:45:57', '');
INSERT INTO `sys_menu` VALUES ('c89cb42f142b4644ad312c12c9485223', '文件管理', '1', 'C', 'fileCenter', NULL, NULL, 7, '0', 'el-icon-picture', 1, '1', 'admin', '2019-04-25 21:08:15', '', '2019-09-24 13:50:37', '');
INSERT INTO `sys_menu` VALUES ('cc0766812d62409a9caf42a44d70b2d6', '删除用户', '26929b3329744ac0bdf84f29c55eb336', 'F', NULL, NULL, 'user:delete', 3, '0', NULL, 1, '1', 'admin', '2020-04-17 09:37:12', '', '2020-04-17 09:37:12', '');
INSERT INTO `sys_menu` VALUES ('d3831beb48e54b80a5911e9242a878dc', '审核流程', '3b778958b90e4e2f8d57d3799dc2cc93', 'C', 'audit.flow', NULL, NULL, 2, '0', NULL, 1, '1', 'admin', '2019-04-25 21:54:47', '', '2019-04-25 21:54:47', '');
INSERT INTO `sys_menu` VALUES ('d4868d083e2348f98b9d34fb127b213a', '镜像仓库', '02da7b13a43c48d2b32136f0e9f6a60c', 'C', 'mirrorwarehouse', 'http://10.130.86.36/', NULL, 3, '0', NULL, 1, '1', 'admin', '2019-04-25 22:03:59', '', '2019-09-24 18:34:07', '');
INSERT INTO `sys_menu` VALUES ('d54e067a6a0d49af91499a45bcd63e11', '新增黑名单', 'b19798545fc243f0911d16e42314d234', 'F', NULL, NULL, 'blackip:add', 1, '0', NULL, 1, '1', 'admin', '2020-04-17 10:09:49', '', '2020-04-17 10:09:49', '');
INSERT INTO `sys_menu` VALUES ('d8f970a0fff147368f48dd2a8bd8d572', '分配角色', '26929b3329744ac0bdf84f29c55eb336', 'F', NULL, NULL, 'user:setrole', 5, '0', NULL, 1, '1', 'admin', '2020-04-17 09:39:49', '', '2020-04-17 09:40:19', '');
INSERT INTO `sys_menu` VALUES ('d9cce957cf4b42fab29c9299acb28400', '分配权限', 'fabf3f2274b34291977f7a3eca1c1fac', 'F', NULL, NULL, 'role:set_pers', 4, '0', NULL, 1, '1', 'admin', '2020-04-17 09:47:51', '', '2020-04-17 09:47:51', '');
INSERT INTO `sys_menu` VALUES ('da539a7475cb41219a1be394756de58a', '新增路由', '7d8889f9a2bc4fa6ac76431522c1720f', 'F', NULL, NULL, 'router:add', 1, '0', NULL, 1, '1', 'admin', '2020-04-17 10:02:48', '', '2020-04-17 10:02:48', '');
INSERT INTO `sys_menu` VALUES ('dfcc05d2ad894960972c58090c384202', '字典管理', '7ab9e84ad3014f52b799f70e8e5729c1', 'C', 'dictionary', NULL, NULL, 1, '0', NULL, 1, '1', 'admin', '2019-04-25 21:05:56', '', '2019-09-24 09:50:49', '');
INSERT INTO `sys_menu` VALUES ('e3fc2c3417c4456aa548b35c84c9f458', '删除路由', '7d8889f9a2bc4fa6ac76431522c1720f', 'F', NULL, NULL, 'router:delete', 3, '0', NULL, 1, '1', 'admin', '2020-04-17 10:03:29', '', '2020-04-17 10:03:29', '');
INSERT INTO `sys_menu` VALUES ('ec73c937fb334b18881fb0343ce20d94', '监控中心', '8a29c5164780463c80722f2601cf0a24', 'C', 'monitor', 'http://10.130.86.37:30901', NULL, 2, '0', NULL, 1, '1', 'admin', '2019-04-25 21:07:55', '', '2019-09-24 18:26:07', '');
INSERT INTO `sys_menu` VALUES ('ef27c6b8085d4ec1bb4f47446bb61839', '修改黑名单', 'b19798545fc243f0911d16e42314d234', 'F', NULL, NULL, 'blackip:edit', 2, '0', NULL, 1, '1', 'admin', '2020-04-17 10:10:14', '', '2020-04-17 10:10:14', '');
INSERT INTO `sys_menu` VALUES ('efcc2da66bab48ee9109e7a4014ecba7', '删除角色', 'fabf3f2274b34291977f7a3eca1c1fac', 'F', NULL, NULL, 'role:delete', 3, '0', NULL, 1, '1', 'admin', '2020-04-17 09:47:08', '', '2020-04-17 09:47:08', '');
INSERT INTO `sys_menu` VALUES ('f0d92d1beb0c44b7a131e59f42600dbd', '部门管理', '1', 'C', 'departmentCenter', NULL, NULL, 1, '0', 'el-icon-office-building', 1, '1', 'admin', '2019-12-19 09:22:33', '', '2019-12-19 09:32:13', '');
INSERT INTO `sys_menu` VALUES ('f91ebbfd7bf34f5bb12cc6f5d0a2ec5a', '删除菜单', '211374d304e34b2dbb2714239eb4f677', 'F', NULL, NULL, 'menu:delete', 3, '0', NULL, 1, '1', 'admin', '2020-04-17 09:56:06', '', '2020-04-17 09:56:06', '');
INSERT INTO `sys_menu` VALUES ('fabf3f2274b34291977f7a3eca1c1fac', '角色管理', '0df5ceccb49e452c9a040cfa646e126e', 'C', 'role', NULL, NULL, 2, '0', NULL, 1, '1', 'admin', '2019-04-25 20:51:27', '', '2019-09-24 09:48:52', '');
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(32) NOT NULL,
  `code` varchar(32) NOT NULL COMMENT '角色code',
  `name` varchar(50) NOT NULL COMMENT '角色名',
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES ('0', 'temp', '临时用户', '2019-04-29 15:06:52', '2019-09-25 17:26:11');
INSERT INTO `sys_role` VALUES ('1', 'SUPER_ADMIN', '超级管理员', '2018-01-19 20:32:16', '2019-09-25 17:25:59');
INSERT INTO `sys_role` VALUES ('2', 'test', '测试角色', '2020-04-21 20:39:12', '2020-04-21 20:39:12');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user` (
  `userId` int(11) NOT NULL,
  `roleId` varchar(32) NOT NULL,
  PRIMARY KEY (`userId`,`roleId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_user` VALUES (1, '1');
INSERT INTO `sys_role_user` VALUES (2, '0');
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `account` varchar(32) DEFAULT NULL COMMENT '账号',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `name` varchar(32) DEFAULT NULL COMMENT '名字',
  `sex` int(11) DEFAULT NULL COMMENT '性别(1男,2女,3未知)',
  `email` varchar(45) DEFAULT NULL COMMENT '电子邮件',
  `phone` varchar(45) DEFAULT NULL COMMENT '电话',
  `roleid` varchar(255) DEFAULT NULL COMMENT '角色id',
  `deptid` int(11) DEFAULT NULL COMMENT '部门id',
  `status` int(11) DEFAULT NULL COMMENT '平台账号状态(0禁用,1启用）',
  `num` int(11) DEFAULT '0' COMMENT '部门内的排序值',
  `product_register_count` int(10) DEFAULT '0' COMMENT '产品注册数量',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='管理员表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (1, '', 'admin', '$2a$10$i8SYk4m4zprMJEloydbZ1ey8iR35e4bSMASq.sV28A7pXWQ8hktMy', '系统管理员', 1, 'admin@hlxd.com', '18237237232', '1', 1, 1, 1, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES (2, NULL, 'hlxd1', '$2a$10$kgTVU.ol5UfShs3oGDIq0.1v8AniS1pbsCJTD.WwAMGKcW6yykNNm', '测试用户', 2, 'hlxd1@hlxd.com', '15277362532', '0', 2, 1, 1, 7, NULL, NULL, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for t_application
-- ----------------------------
DROP TABLE IF EXISTS `t_application`;
CREATE TABLE `t_application` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `logo_url` varchar(255) DEFAULT NULL COMMENT 'logo地址',
  `name` varchar(100) DEFAULT NULL COMMENT '应用名称',
  `short_name` varchar(100) DEFAULT NULL COMMENT '应用简称',
  `version` varchar(32) DEFAULT NULL COMMENT '应用版本号',
  `platform_group` int(11) DEFAULT NULL COMMENT '应用分组',
  `app_type` varchar(1) DEFAULT NULL COMMENT '应用类型(ios,android,mac,windows,web)',
  `screenshots` text COMMENT '应用截图',
  `description` varchar(255) DEFAULT NULL COMMENT '应用简介',
  `index_url` varchar(255) DEFAULT NULL COMMENT '访问地址',
  `order_num` int(10) DEFAULT '0' COMMENT '排序值',
  `run_status` int(1) DEFAULT '0' COMMENT '运行状态(0未运行,1运行中)',
  `user_register_url` varchar(255) DEFAULT NULL COMMENT '注册用户地址',
  `user_logout_url` varchar(255) DEFAULT NULL COMMENT '注销用户地址',
  `user_check_url` varchar(255) DEFAULT NULL COMMENT '校验用户地址',
  `user_test_url` varchar(255) DEFAULT NULL COMMENT '用户测试地址',
  `app_token` varchar(255) DEFAULT NULL COMMENT '应用token',
  `admin_account` varchar(255) DEFAULT NULL COMMENT '应用管理员账号',
  `admin_password` varchar(255) DEFAULT NULL COMMENT '应用管理员密码',
  `responsible_dept` int(11) DEFAULT NULL COMMENT '应用所属部门',
  `app_admin` int(20) DEFAULT NULL COMMENT '应用管理员',
  `app_use_scope` text COMMENT '应用使用范围(部门)',
  `app_use_person` text COMMENT '应用使用人员',
  `app_use_person_name` text COMMENT '应用使用人员名称',
  `del_flag` int(1) DEFAULT '0' COMMENT '删除状态(0未删除,1已删除)',
  `create_by` varchar(100) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(100) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='应用表';

-- ----------------------------
-- Table structure for t_application_user_sync
-- ----------------------------
DROP TABLE IF EXISTS `t_application_user_sync`;
CREATE TABLE `t_application_user_sync` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int(20) DEFAULT NULL COMMENT '用户id',
  `app_id` int(20) DEFAULT NULL COMMENT '应用id',
  `create_by` varchar(100) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='应用用户同步表';

-- ----------------------------
-- Table structure for t_banner_config
-- ----------------------------
DROP TABLE IF EXISTS `t_banner_config`;
CREATE TABLE `t_banner_config` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `description` text COMMENT '简介',
  `image_url` varchar(255) DEFAULT NULL COMMENT '图片地址',
  `action_url` varchar(255) DEFAULT NULL COMMENT '访问地址',
  `order_num` int(10) DEFAULT NULL COMMENT '排序值',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='banner配置表';

-- ----------------------------
-- Table structure for t_platform_group
-- ----------------------------
DROP TABLE IF EXISTS `t_platform_group`;
CREATE TABLE `t_platform_group` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `group_name` varchar(64) DEFAULT NULL COMMENT '分组名称',
  `num` int(10) DEFAULT '0' COMMENT '排序值',
  `description` varchar(255) DEFAULT NULL COMMENT '组简介',
  `enable` int(1) DEFAULT '0' COMMENT '状态(0停用,1启用)',
  `del_flag` int(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `create_by` varchar(100) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(100) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='应用分组表';

-- ----------------------------
-- Records of t_platform_group
-- ----------------------------
BEGIN;
INSERT INTO `t_platform_group` VALUES (1, '研发设计类', 1, '研发设计类应用', 1, 0, 'admin', '2020-04-24 10:17:46', 'wangcb', '2020-04-29 10:32:42');
INSERT INTO `t_platform_group` VALUES (2, '生产制造类', 2, '生产制造类应用', 1, 0, 'admin', '2020-04-24 10:18:00', 'wangcb', '2020-04-29 10:32:51');
INSERT INTO `t_platform_group` VALUES (3, '运营维护类', 3, '运营维护类应用', 1, 0, 'admin', '2020-04-24 10:18:14', 'wangcb', '2020-04-29 10:32:59');
INSERT INTO `t_platform_group` VALUES (4, '经营管理类', 4, '经营管理类应用', 1, 0, 'admin', '2020-04-24 10:18:29', 'wangcb', '2020-04-29 10:33:06');
COMMIT;

-- ----------------------------
-- Table structure for t_user_application
-- ----------------------------
DROP TABLE IF EXISTS `t_user_application`;
CREATE TABLE `t_user_application` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `app_id` int(10) DEFAULT NULL COMMENT '应用id',
  `app_name` varchar(100) DEFAULT NULL COMMENT '应用名称',
  `logo_url` varchar(255) DEFAULT NULL COMMENT 'logo地址',
  `index_url` varchar(255) DEFAULT NULL COMMENT '访问地址',
  `recent_visit_time` datetime DEFAULT NULL COMMENT '最近访问时间',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户应用关联表';

SET FOREIGN_KEY_CHECKS = 1;
