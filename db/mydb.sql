/*
Navicat MySQL Data Transfer

Source Server         : ysh
Source Server Version : 50515
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50515
File Encoding         : 65001

Date: 2015-11-13 11:18:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `login`
-- ----------------------------
DROP TABLE IF EXISTS `login`;
CREATE TABLE `login` (
  `username` varchar(255) NOT NULL DEFAULT '',
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of login
-- ----------------------------
INSERT INTO `login` VALUES ('ysh', '111111');

-- ----------------------------
-- Table structure for `test1`
-- ----------------------------
DROP TABLE IF EXISTS `test1`;
CREATE TABLE `test1` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of test1
-- ----------------------------
INSERT INTO `test1` VALUES ('1', '11');
INSERT INTO `test1` VALUES ('2', '333');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(255) NOT NULL DEFAULT '',
  `name` varchar(255) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `bak` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('101', '校长', '90', '优秀');
INSERT INTO `user` VALUES ('102', '小王', '13', '差');

-- ----------------------------
-- Table structure for `user1`
-- ----------------------------
DROP TABLE IF EXISTS `user1`;
CREATE TABLE `user1` (
  `id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `name` char(15) NOT NULL DEFAULT '1',
  `pass` char(32) NOT NULL DEFAULT '1',
  `note` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user1
-- ----------------------------
INSERT INTO `user1` VALUES ('1', '11', '11', '11');
INSERT INTO `user1` VALUES ('2', '11', '11', '11');



  
  
 create table cg_type_12 
  (
   `type1id` SMALLINT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '一级业态id',
	`type1Name` VARCHAR(50) NOT NULL DEFAULT '00' COMMENT '一级业态名称',
	`type2id` SMALLINT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '二级业态id',
	`type2Name` VARCHAR(50) NOT NULL DEFAULT '00' COMMENT '二级业态名称',
	INDEX `stat_index` (`type1id`, `type1Name`, `type2id`,`type2Name`)
  )
  COMMENT='一级二级业态关系表'
  COLLATE='utf8_general_ci'
  ENGINE=MyISAM;
  
  insert into cg_type_12 (type1id,type1Name,type2id,type2Name)
  select r.id oneid,r.name onename,s.id twoid,s.name towname
      from cg_shop_type s ,cg_shop_type_relation r
      where 
      (
         locate(concat(',',s.id,','),r.child_ids) > 0
		   or 
		   locate(concat('A',s.id,','),concat('A',r.child_ids)) > 0
		   or
		   locate(concat(',',s.id,'A'),concat(r.child_ids,'A')) > 0		   			
      )
      and s.mark = 1 and r.mark = 1;
      
      
  
      ---查询一级二级业态品牌统计 此sql 查询时长 1秒多 
      select t1.oneid,t1.twoid,t1.onename,t1.towname,count(*) brandNum 
  from cg_brand_type  t,
  (
      select r.name onename,s.name towname,
      r.id oneid,s.id twoid 
      from cg_shop_type s ,cg_shop_type_relation r
      where 
      (
         locate(concat(',',s.id,','),r.child_ids) > 0
		   or 
		   locate(concat('A',s.id,','),concat('A',r.child_ids)) > 0
		   or
		   locate(concat(',',s.id,'A'),concat(r.child_ids,'A')) > 0		   			
      )
      and s.mark = 1 and r.mark = 1
      order by r.name
   ) t1
  
  where t.type = t1.twoid
  and t.mark = 1 
  group by t1.oneid,t1.twoid
  
   ---查询一级二级业态品牌统计  sql调优 此sql 查询时长 几十毫秒 
   select t1.oneid,t1.twoid,t1.onename,t1.towname,num brandNum
   from
  (
   select t.`type`,count(*) as num from cg_brand_type t where t.mark =1 group by t.`type` 
  ) s1,
  (
      select r.name onename,s.name towname,
      r.id oneid,s.id twoid 
      from cg_shop_type s ,cg_shop_type_relation r
      where 
      (
         locate(concat(',',s.id,','),r.child_ids) > 0
		   or 
		   locate(concat('A',s.id,','),concat('A',r.child_ids)) > 0
		   or
		   locate(concat(',',s.id,'A'),concat(r.child_ids,'A')) > 0		   			
      )
      and s.mark = 1 and r.mark = 1
      order by r.name
   ) t1
  where s1.type= t1.twoid
  order by t1.oneid,t1.twoid;
  
  ---查询一级二级业态品牌统计  sql进一步调优 查询出所有的业态 包括0的，用left join
   select t1.oneid,t1.twoid,t1.onename,t1.towname,num brandNum
   from
   (
      select r.name onename,s.name towname,
      r.id oneid,s.id twoid 
      from cg_shop_type s ,cg_shop_type_relation r
      where 
      (
         locate(concat(',',s.id,','),r.child_ids) > 0
		   or 
		   locate(concat('A',s.id,','),concat('A',r.child_ids)) > 0
		   or
		   locate(concat(',',s.id,'A'),concat(r.child_ids,'A')) > 0		   			
      )
      and s.mark = 1 and r.mark = 1
      order by r.name
   ) t1
   left join 
  (
   select t.type,count(*) as num from cg_brand_type t where t.mark =1 group by t.type
  ) s1 on  s1.type= t1.twoid
  order by t1.oneid,t1.twoid
