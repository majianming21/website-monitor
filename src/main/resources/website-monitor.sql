/*

*/

CREATE DATABASE `website-monitor` CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_general_ci';

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for notice_methods
-- ----------------------------
DROP TABLE IF EXISTS `notice_methods`;
CREATE TABLE `notice_methods` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `notice_info` json DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of notice_methods
-- ----------------------------
INSERT INTO `notice_methods` VALUES ('7', '大森林', '{\"key\": \"6ce60972-3dd1-4305-9f6d-1393d514cba4\", \"url\": \"https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=7ce60972-3dd1-4305-9f6d-1393d514cba4\", \"sendTypeEnum\": \"WE_CHAT_WORK\"}', '1');
INSERT INTO `notice_methods` VALUES ('8', 'v2', '{\"key\": \"6ce60972-3dd1-4305-9f6d-1393d514cba4\", \"url\": \"https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=7ce60972-3dd1-4305-9f6d-1393d514cba4\", \"sendTypeEnum\": \"WE_CHAT_WORK\"}', '1');

-- ----------------------------
-- Table structure for websites
-- ----------------------------
DROP TABLE IF EXISTS `websites`;
CREATE TABLE `websites` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `url` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of websites
-- ----------------------------
INSERT INTO `websites` VALUES ('1', 'https://new-mask-html.dslbuy.com/newmain.html', '大森林', '1');
INSERT INTO `websites` VALUES ('2', 'https://www.v2ex.com', 'v2ex', '1');

-- ----------------------------
-- Table structure for website_jobs
-- ----------------------------
DROP TABLE IF EXISTS `website_jobs`;
CREATE TABLE `website_jobs` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `website_id` int(11) unsigned NOT NULL,
  `cron_expression` varchar(255) DEFAULT NULL,
  `watch_method` json DEFAULT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '1',
  `notice_type` tinyint(1) DEFAULT NULL,
  `set_recover_count` int(5) NOT NULL DEFAULT '0',
  `left_recover_count` int(5) NOT NULL DEFAULT '0',
  `notice_method_id` int(11) unsigned NOT NULL,
  `message` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of website_jobs
-- ----------------------------
INSERT INTO `website_jobs` VALUES ('7', '大森林', '1', '0 0 */2 * * ?', '{\"appear\": true, \"keyWord\": \"广州\", \"watchTypeEnum\": \"KEY_WORD\"}', '1', '4', '5', '4', '7', '口罩可以选广州了 快点到 https://new-mask-html.dslbuy.com/newmain.html 选吧');
INSERT INTO `website_jobs` VALUES ('8', 'v2', '2', '0 * * * * ?', '{\"appear\": true, \"keyWord\": \"面试\", \"watchTypeEnum\": \"KEY_WORD\"}', '1', '4', '5', '1', '8', '面试');
