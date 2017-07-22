/*
Navicat MySQL Data Transfer
Source Host     : localhost:3306
Source Database : human
Target Host     : localhost:3306
Target Database : human
Date: 2012-07-03 14:17:11
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for login
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
INSERT INTO `login` VALUES ('1', '1');

-- ----------------------------
-- Table structure for user
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
INSERT INTO `user` VALUES ('1', '1', '1', '1');





   select t1.oneid,t1.twoid,t1.onename,t1.towname,count(*) num ,
          concat('brandChaosi.getSubTypeList().add(new BrandStat("',t1.twoid,'","',t1.towname,'","',count(*),'","2",new ArrayList<BrandStat>(10)) );') as rule
  from cg_brand_type  t,
  cg_brand b,
  (
      select r.name onename,s.name towname,r.id oneid,s.id twoid from cg_shop_type s ,cg_shop_type_relation r
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
  
  where b.id = t.brand_id
  and t.type =t1.twoid
  and t.mark = 1 and b.mark = 1
  group by t1.oneid,t1.twoid; 
  
  
  select t1.oneid,t1.onename,count(*) num 
  from cg_brand_type  t,
  cg_brand b,
  (
      select r.name onename,s.name towname,r.id oneid,s.id twoid from cg_shop_type s ,cg_shop_type_relation r
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
  
  where b.id = t.brand_id
  and t.type =t1.twoid
  and t.mark = 1 and b.mark = 1
  group by t1.oneid; 
