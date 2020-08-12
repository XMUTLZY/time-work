/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50623
 Source Host           : localhost:3306
 Source Schema         : time-work

 Target Server Type    : MySQL
 Target Server Version : 50623
 File Encoding         : 65001

 Date: 12/08/2020 18:13:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence`  (
  `next_val` bigint(20) NULL DEFAULT NULL
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
INSERT INTO `hibernate_sequence` VALUES (9);
INSERT INTO `hibernate_sequence` VALUES (9);
INSERT INTO `hibernate_sequence` VALUES (9);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `user_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `sex` tinyint(4) NULL DEFAULT NULL COMMENT '性别 0：女  1：男',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `school` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所在学校',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '注册时间',
  `update_time` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10003 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (10001, '林哈哈', '13200013505', 'admin', 1, 22, '清华大学', '2020-08-12 10:34:21', '2020-08-12 10:34:20');
INSERT INTO `user` VALUES (10002, '阮宝贝', '13200013506', 'admin', 0, 21, '北京大学', '2020-08-12 15:08:57', '2020-08-12 15:08:59');

-- ----------------------------
-- Table structure for user_apply
-- ----------------------------
DROP TABLE IF EXISTS `user_apply`;
CREATE TABLE `user_apply`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `work_id` int(11) NULL DEFAULT NULL COMMENT '工作id',
  `status` tinyint(4) NULL DEFAULT 0 COMMENT '-1: 不通过 0:待审核 1:通过',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '申请时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user_apply
-- ----------------------------
INSERT INTO `user_apply` VALUES (1, 10001, 1, 1, '2020-08-12 15:08:19');
INSERT INTO `user_apply` VALUES (2, 10002, 1, 1, '2020-08-12 15:09:25');
INSERT INTO `user_apply` VALUES (3, 10002, 1, -1, '2020-08-12 15:09:29');
INSERT INTO `user_apply` VALUES (4, 10002, 1, 0, '2020-08-12 15:53:28');
INSERT INTO `user_apply` VALUES (5, 10002, 1, 0, '2020-08-12 16:45:23');
INSERT INTO `user_apply` VALUES (6, 10002, 1, 0, '2020-08-12 16:45:25');
INSERT INTO `user_apply` VALUES (7, 10002, 1, 1, '2020-08-12 16:48:06');
INSERT INTO `user_apply` VALUES (8, 10001, 2, 0, '2020-08-12 18:13:13');

-- ----------------------------
-- Table structure for work_info
-- ----------------------------
DROP TABLE IF EXISTS `work_info`;
CREATE TABLE `work_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '兼职信息编号',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '发布者用户编号',
  `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '兼职标题',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工作内容或要求描述',
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工作地址',
  `work_time` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工作时间描述',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '发布时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of work_info
-- ----------------------------
INSERT INTO `work_info` VALUES (1, 10001, '餐厅服务员', '擦盘子、送餐', '厦门市肯德基', '工作日早上8点到晚上6点', '2020-08-12 13:49:05');
INSERT INTO `work_info` VALUES (2, 10002, '发传单', '50块 2个小时', '软件园', '12点到14点', '2020-08-12 16:57:28');

SET FOREIGN_KEY_CHECKS = 1;
