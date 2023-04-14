/*
Navicat MySQL Data Transfer

Source Server         : aboutlive
Source Server Version : 50562
Source Host           : 124.220.174.134:3306
Source Database       : aboutlive

Target Server Type    : MYSQL
Target Server Version : 50562
File Encoding         : 65001

Date: 2022-12-27 20:54:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userKey` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `imagesBase64` longtext COLLATE utf8_bin,
  `content` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `author` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `from` int(11) DEFAULT '1' COMMENT '1是摘抄，2是自创',
  `time` datetime DEFAULT NULL,
  `featured` varchar(1) COLLATE utf8_bin DEFAULT '0',
  `type` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `state` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for articleLike
-- ----------------------------
DROP TABLE IF EXISTS `articleLike`;
CREATE TABLE `articleLike` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userKey` varchar(255) DEFAULT NULL,
  `articleId` varchar(255) DEFAULT NULL,
  `read` int(11) DEFAULT '0' COMMENT '0是未读，1是已读',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=133 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `articleId` int(11) DEFAULT NULL,
  `userKey` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `userContent` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `likeNumber` int(11) DEFAULT '0',
  `nextCommentId` int(11) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=131 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for focusAndFan
-- ----------------------------
DROP TABLE IF EXISTS `focusAndFan`;
CREATE TABLE `focusAndFan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fan` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `focus` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3220 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for information
-- ----------------------------
DROP TABLE IF EXISTS `information`;
CREATE TABLE `information` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userKey` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `content` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `type` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `sendUserKey` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `read` int(11) DEFAULT '1',
  `articleId` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=182 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for key_word
-- ----------------------------
DROP TABLE IF EXISTS `key_word`;
CREATE TABLE `key_word` (
  `id` int(11) NOT NULL,
  `key_words` text COLLATE utf8_bin,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `id` int(11) NOT NULL,
  `version` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `content` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for topic
-- ----------------------------
DROP TABLE IF EXISTS `topic`;
CREATE TABLE `topic` (
  `id` int(11) NOT NULL,
  `userKey` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `title` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `image` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userKey` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `UName` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `UPwd` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `headImage` longtext COLLATE utf8_bin,
  `email` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `sex` int(1) DEFAULT '1' COMMENT '1男，2女',
  `born` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `experience` double DEFAULT '0' COMMENT '经验值',
  `state` int(11) DEFAULT '1',
  `fingerprint` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for user_mute
-- ----------------------------
DROP TABLE IF EXISTS `user_mute`;
CREATE TABLE `user_mute` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userKey` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `muteStart` date DEFAULT NULL,
  `muteEnd` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for version
-- ----------------------------
DROP TABLE IF EXISTS `version`;
CREATE TABLE `version` (
  `id` int(11) DEFAULT NULL,
  `version` varchar(255) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
