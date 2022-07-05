/*
 Navicat Premium Data Transfer

 Source Server         : 101.132.174.238
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : 101.132.174.238:3306
 Source Schema         : rxl-back-end-template

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 04/07/2022 19:53:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for rxl_pers
-- ----------------------------
DROP TABLE IF EXISTS `rxl_pers`;
CREATE TABLE `rxl_pers`  (
  `id` int(6) NOT NULL AUTO_INCREMENT,
  `name` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rxl_pers
-- ----------------------------
INSERT INTO `rxl_pers` VALUES (1, 'user:*:*', '');

-- ----------------------------
-- Table structure for rxl_role
-- ----------------------------
DROP TABLE IF EXISTS `rxl_role`;
CREATE TABLE `rxl_role`  (
  `id` int(6) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rxl_role
-- ----------------------------
INSERT INTO `rxl_role` VALUES (1, 'admin');
INSERT INTO `rxl_role` VALUES (2, 'user');
INSERT INTO `rxl_role` VALUES (3, 'product');

-- ----------------------------
-- Table structure for rxl_role_perms
-- ----------------------------
DROP TABLE IF EXISTS `rxl_role_perms`;
CREATE TABLE `rxl_role_perms`  (
  `id` int(6) NOT NULL,
  `roleid` int(6) NULL DEFAULT NULL,
  `permsid` int(6) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rxl_role_perms
-- ----------------------------
INSERT INTO `rxl_role_perms` VALUES (1, 1, 1);

-- ----------------------------
-- Table structure for rxl_user
-- ----------------------------
DROP TABLE IF EXISTS `rxl_user`;
CREATE TABLE `rxl_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `salt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `age` int(11) NULL DEFAULT NULL,
  `sex` int(255) NULL DEFAULT NULL COMMENT '1 女 0 男',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `del_flag` int(255) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rxl_user
-- ----------------------------
INSERT INTO `rxl_user` VALUES (1, 'rxl', '73b6b9c1d09d73faaf3dcc06b81cbfa9', 'xVSedMjY', 20, 0, '安徽', '2022-07-03 14:19:34', 0);
INSERT INTO `rxl_user` VALUES (2, 'user', 'b6ada86d38c784fa6311dc2c24d8ad50', 'YgxeWov#', 20, 0, '安徽', '2022-07-03 14:45:00', 0);

-- ----------------------------
-- Table structure for rxl_user_role
-- ----------------------------
DROP TABLE IF EXISTS `rxl_user_role`;
CREATE TABLE `rxl_user_role`  (
  `id` int(6) NOT NULL,
  `userid` int(6) NULL DEFAULT NULL,
  `roleid` int(6) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rxl_user_role
-- ----------------------------
INSERT INTO `rxl_user_role` VALUES (1, 1, 1);

SET FOREIGN_KEY_CHECKS = 1;
