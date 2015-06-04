
--建立点播次数表:
		CREATE TABLE `wasuna_201407` AS (SELECT userid,DATE(begin_date) begin_date,DATE(end_date) end_date,COUNT(billing_no) active_times
			  FROM wasun_201407 GROUP BY userid,DATE(begin_date),DATE(end_date));
		  ALTER TABLE wasuna_201407 ENGINE = MYISAM;
  CREATE INDEX wasuna_idx1 ON wasuna_201407(userid);

--建立登录次数
		CREATE TABLE `epgloga_201406` AS (SELECT userid,DATE(logtime) logtime,COUNT(resno) login_times
			  FROM epglog_201406 GROUP BY userid,DATE(logtime));
		  ALTER TABLE epgloga_201406 ENGINE = MYISAM;
 CREATE INDEX epgloga_idx1 ON epgloga_201406(userid);
 
 INSERT INTO epgloga_201407(userid,logtime,login_times) SELECT userid,DATE(logtime),COUNT(resno)
	 FROM epglog_201407 GROUP BY userid,DATE(logtime);
 
--以下新建BOSS表---------------------------------------------------------
	 
CREATE TABLE `boss_packdeal` (
  `packdealid` BIGINT(20) NOT NULL,
  `packdealname` VARCHAR(255) NOT NULL,
  `unitid` BIGINT(20) NOT NULL,
  `unit` VARCHAR(255) NULL,
  `unitcount` BIGINT(20) NULL,
  `price` BIGINT(20) NOT NULL,
  PRIMARY KEY (`packdealid`)
) ENGINE=MYISAM DEFAULT CHARSET=utf8

CREATE TABLE `boss_pack` (
  `packid` BIGINT(20) NOT NULL,
  `packname` VARCHAR(255) NOT NULL,
  `itemtypeid` BIGINT(20) NOT NULL,
  `itemtype` VARCHAR(255) NOT NULL,
  `unitid` BIGINT(20) NULL,
  `unit` VARCHAR(255) NULL,
  `price` BIGINT(20) NOT NULL,
  PRIMARY KEY (`packid`)
) ENGINE=MYISAM DEFAULT CHARSET=utf8

CREATE TABLE `boss_product` (
  `productid` BIGINT(20) NOT NULL,
  `productname` VARCHAR(255) NOT NULL,
  `feesystemid` BIGINT(20) NOT NULL,
  `feesystem` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`productid`)
) ENGINE=MYISAM DEFAULT CHARSET=utf8
 
CREATE TABLE `boss_syscode` (
  `codeid` BIGINT(20) NOT NULL,
  `state` BIGINT(10) NOT NULL,
  `crtime` DATETIME DEFAULT NULL,
  `memo` VARCHAR(100) DEFAULT NULL,
  `codename` VARCHAR(20) NOT NULL,
  `codecontent` VARCHAR(255) NOT NULL,
  `typeid` BIGINT(20) NOT NULL,
  PRIMARY KEY (`codeid`),
  KEY `boss_syscode_idxcodeconten` (`codecontent`)
) ENGINE=MYISAM DEFAULT CHARSET=utf8

CREATE TABLE `boss_syscode_syscode` (
  `codeid` BIGINT(20) NOT NULL,
  `codeidex` BIGINT(20) NOT NULL
) ENGINE=MYISAM DEFAULT CHARSET=utf8

CREATE TABLE `boss_customer` (
  `customerid` BIGINT(20) NOT NULL,
  `customername` VARCHAR(255) DEFAULT NULL,
  `servicecardid` BIGINT(20) DEFAULT NULL,
  `operator` VARCHAR(20) DEFAULT NULL,
  `crtime` DATETIME DEFAULT NULL,
  `districtid` BIGINT(20) DEFAULT NULL,
  `district` VARCHAR(50) DEFAULT NULL,
  `townid` BIGINT(20) DEFAULT NULL,
  `town` VARCHAR(50) DEFAULT NULL,
  `communityid` BIGINT(20) DEFAULT NULL,
  `community` VARCHAR(50) DEFAULT NULL,
  `villageid` BIGINT(20) DEFAULT NULL,
  `village` VARCHAR(50) DEFAULT NULL,
  `fulladdress` VARCHAR(255) DEFAULT NULL,
  `customertypeid` BIGINT(20) DEFAULT NULL,
  `customertype` VARCHAR(50) DEFAULT NULL,
  `importancelevelid` BIGINT(20) DEFAULT NULL,
  `importancelevel` VARCHAR(20) DEFAULT NULL,
  `securitylevelid` BIGINT(20) DEFAULT NULL,
  `securitylevel` VARCHAR(20) DEFAULT NULL,
  `certificatetypeid` BIGINT(20) DEFAULT NULL,
  `certificatetype` VARCHAR(50) DEFAULT NULL,
  `certificatecode` VARCHAR(50) DEFAULT NULL,
  `shopid` BIGINT(20) DEFAULT NULL,
  `shop` VARCHAR(50) DEFAULT NULL,
  `zipcode` VARCHAR(6) DEFAULT NULL,
  `memo` VARCHAR(2000) DEFAULT NULL,
  PRIMARY KEY (`customerid`),
  KEY `boss_customerIdx_customerid` (`customerid`),
  KEY `boss_customer_idx1` (`customertypeid`),
  KEY `boss_customer_idx2` (`townid`),
  KEY `boss_customer_idx3` (`villageid`),
  KEY `boss_customer_idx4` (`communityid`),
  KEY `boss_customer_idx5` (`crtime`)
) ENGINE=MYISAM DEFAULT CHARSET=utf8

CREATE TABLE `boss_user` (
  `userid` BIGINT(20) NOT NULL,
  `customerid` BIGINT(20) NOT NULL,
  `accountid` BIGINT(20) DEFAULT NULL,
  `contactid` BIGINT(20) DEFAULT NULL,
  `customertypeid` BIGINT(20) DEFAULT NULL,
  `customertype` VARCHAR(50) DEFAULT NULL,
  `importancelevelid` BIGINT(20) DEFAULT NULL,
  `importancelevel` VARCHAR(20) DEFAULT NULL,
  `resno` VARCHAR(50) DEFAULT NULL,
  `shortsim` VARCHAR(10) DEFAULT NULL,
  `simserialno` VARCHAR(50) DEFAULT NULL,
  `stbserialno` VARCHAR(50) DEFAULT NULL,
  `stbtypeid` BIGINT(20) DEFAULT NULL,
  `stbtype` VARCHAR(20) DEFAULT NULL,
  `stbid` BIGINT(20) DEFAULT NULL,
  `stb` VARCHAR(20) DEFAULT NULL,
  `obtainwayid` BIGINT(20) DEFAULT NULL,
  `obtainway` VARCHAR(20) DEFAULT NULL,
  `operator` VARCHAR(20) DEFAULT NULL,
  `crtime` DATETIME DEFAULT NULL,
  `shopid` BIGINT(20) DEFAULT NULL,
  `shop` VARCHAR(20) DEFAULT NULL,
  `userstateid` BIGINT(20) DEFAULT NULL,
  `userstate` VARCHAR(20) DEFAULT NULL,
  `usertypeid` BIGINT(20) DEFAULT NULL,
  `usertype` VARCHAR(20) DEFAULT NULL,
  `usergroupid` BIGINT(20) DEFAULT NULL,
  `usergroup` VARCHAR(20) DEFAULT NULL,
  `districtid` BIGINT(20) DEFAULT NULL,
  `townid` BIGINT(20) DEFAULT NULL,
  `communityid` BIGINT(20) DEFAULT NULL,
  `villageid` BIGINT(20) DEFAULT NULL,
  `district` VARCHAR(20) DEFAULT NULL,
  `town` VARCHAR(20) DEFAULT NULL,
  `community` VARCHAR(20) DEFAULT NULL,
  `village` VARCHAR(20) DEFAULT NULL,
  `fulladdress` VARCHAR(255) DEFAULT NULL,
  `customername` VARCHAR(100) DEFAULT NULL,
  `phone` VARCHAR(30) DEFAULT NULL,
  `mobile` VARCHAR(30) DEFAULT NULL,
  KEY `boss_user_idx1` (`userid`),
  KEY `boss_user_idx2` (`customerid`),
  KEY `boss_user_idx3` (`resno`),
  KEY `boss_user_idx4` (`crtime`),
  KEY `boss_user_idx5` (`simserialno`),
  KEY `boss_user_idx6` (`shortSim`),
  KEY `boss_user_idx7` (`customertypeid`),
  KEY `boss_user_idx8` (`townid`),
  KEY `boss_user_idx9` (`communityid`),
  KEY `boss_user_idx10` (`villageid`)
) ENGINE=MYISAM DEFAULT CHARSET=utf8
PARTITION BY RANGE (townid)
(
 PARTITION p2052 VALUES LESS THAN (2053),
 PARTITION p2053 VALUES LESS THAN (2054),
 PARTITION p5050 VALUES LESS THAN (5051),
 PARTITION p5051 VALUES LESS THAN (5052),
 PARTITION p5083 VALUES LESS THAN (5084),
 PARTITION p5084 VALUES LESS THAN (5085),
 PARTITION p5085 VALUES LESS THAN (5086),
 PARTITION p5086 VALUES LESS THAN (5087),
 PARTITION p_x VALUES LESS THAN MAXVALUE
)

CREATE TABLE `boss_userpack` (
  `userid` BIGINT(20) NOT NULL,
  `bookid` BIGINT(20) DEFAULT NULL,
  `packid` BIGINT(20) DEFAULT NULL,
  `packname` VARCHAR(50) NOT NULL,
  `fee` BIGINT(20) DEFAULT NULL,
  `begintime` DATETIME NOT NULL,
  `endtime` DATETIME NOT NULL,
  `crtime` DATETIME DEFAULT NULL,
  `giftcardid` BIGINT(20) DEFAULT NULL,
  `packtype` VARCHAR(50) DEFAULT NULL
) ENGINE=MYISAM DEFAULT CHARSET=utf8
PARTITION BY RANGE (YEAR(crtime)*100+MONTH(crtime))
(PARTITION p2012 VALUES LESS THAN (201301) ,
 PARTITION p2013a VALUES LESS THAN (201307) ,
 PARTITION p2013b VALUES LESS THAN (201401),
 PARTITION p2014 VALUES LESS THAN (201501),
 PARTITION p2015 VALUES LESS THAN (201601),
 PARTITION p2016 VALUES LESS THAN (201701) ,
 PARTITION p_catchall VALUES LESS THAN MAXVALUE ) 

CREATE TABLE `boss_userproduct` (
  `userid` bigint(20) NOT NULL,
  `bookid` bigint(20) DEFAULT NULL,
  `productid` bigint(20) DEFAULT NULL,
  `productname` varchar(50) NOT NULL,
  `begintime` datetime NOT NULL,
  `endtime` datetime NOT NULL,
  `producttype` varchar(50) DEFAULT NULL,
  `authoritycode` varchar(50) DEFAULT NULL,
  KEY `boss_userproductIdx0` (`userid`),
  KEY `boss_userproductIdx1` (`begintime`),
  KEY `boss_userproductIdx2` (`endtime`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8
PARTITION BY RANGE (YEAR(begintime)*100+MONTH(begintime))
(PARTITION p2011 VALUES LESS THAN (201201),
 PARTITION p201203 VALUES LESS THAN (201204),
 PARTITION p201206 VALUES LESS THAN (201207),
 PARTITION p201209 VALUES LESS THAN (201210),
 PARTITION p201212 VALUES LESS THAN (201301),
 PARTITION p201303 VALUES LESS THAN (201304),
 PARTITION p201306 VALUES LESS THAN (201307),
 PARTITION p201309 VALUES LESS THAN (201310),
 PARTITION p201312 VALUES LESS THAN (201401),
 PARTITION p201403 VALUES LESS THAN (201404),
 PARTITION p201406 VALUES LESS THAN (201407),
 PARTITION p201409 VALUES LESS THAN (201410),
 PARTITION p201412 VALUES LESS THAN (201501),
 PARTITION p201503 VALUES LESS THAN (201504),
 PARTITION p201506 VALUES LESS THAN (201507),
 PARTITION p201509 VALUES LESS THAN (201510),
 PARTITION p201512 VALUES LESS THAN (201601),
 PARTITION p_catchall VALUES LESS THAN MAXVALUE)
 
 CREATE TABLE `boss_wasusubcode` (
  `userid` BIGINT(20) DEFAULT NULL,
  `subcode` VARCHAR(50) DEFAULT NULL,
  `stateid` BIGINT(20) DEFAULT NULL,
  `state` VARCHAR(10) DEFAULT NULL,
  KEY `boss_wasusubcode_idx1` (`userid`),
  KEY `boss_wasusubcode_idx2` (`subcode`)
) ENGINE=MYISAM DEFAULT CHARSET=utf8


---------------------------------------
--以下从BOSS导出数据:
--系统代码:
select codeid,state,to_char(crtime,'yyyy-MM-dd') crtime,memo,codename,codecontent,typeid from syscode where typeid not in (1,2,3)

--系统代码关联
select ss.codeid,ss.codeidex
from syscode_syscode ss
join syscode s1 on s1.codeid = ss.codeid  and s1.typeid not in (1,2,3)
join syscode s2 on s2.codeid = ss.codeidex and s2.typeid not in (1,2,3)

--客户
select * from nbboss.view_vodstat_customer

--用户
select * from nbboss.view_vodstat_user

--用户产品包
select userid, bookid, packid, packname, fee*100 fee, 
to_char(begintime,'yyyy-MM-dd hh24:mi;ss') begintime, 
to_char(endtime,'yyyy-MM-dd hh24:mi;ss') endtime, 
to_char(crtime,'yyyy-MM-dd hh24:mi;ss') crtime, 
giftcardid, packtype 
from nbboss.view_userpack
where endtime > to_date('20130101','yyyymmdd')

--用户产品
select userid, bookid, productid, productname, to_char(begintime,'yyyy-MM-dd hh24:mi;ss') begintime, to_char(endtime,'yyyy-MM-dd hh24:mi;ss') endtime, producttype, authoritycode 
from nbboss.view_userproduct
where endtime > to_date('20130101','yyyymmdd')

--华数账号
select * from view_vodstat_wasusubcode

/*
UPDATE boss_customer bc SET bc.townid = 404,bc.town='无' WHERE bc.townid IS NULL;
UPDATE boss_customer bc SET bc.communityid = 403,bc.community = '无'  WHERE bc.communityid IS NULL;
UPDATE boss_customer bc SET bc.villageid = 401,bc.village = '无' WHERE bc.villageid IS NULL;

UPDATE boss_user bc SET bc.townid = 404,bc.town='无' WHERE bc.townid IS NULL;
UPDATE boss_user bc SET bc.communityid = 403,bc.community = '无' WHERE bc.communityid IS NULL;
UPDATE boss_user bc SET bc.villageid = 401,bc.village = '无' WHERE bc.villageid IS NULL;
*/

---------------------------------------
/* 修复表  */
REPAIR TABLE `table_name` 
/* 优化表  */
OPTIMIZE TABLE `table_name` 






-----------------------rep_vodarea以下是:
--建表
CREATE TABLE `rep_vodarea` (
  `rep_date` DATE DEFAULT NULL,
  `customertypeid` BIGINT(20) DEFAULT NULL,
  `customertype` VARCHAR(20) DEFAULT NULL,
  `townid` BIGINT(20) DEFAULT NULL,
  `town` VARCHAR(20) DEFAULT NULL,
  `communityid` BIGINT(20) DEFAULT NULL,
  `community` VARCHAR(20) DEFAULT NULL,
  `villageid` BIGINT(20) DEFAULT NULL,
  `village` VARCHAR(20) DEFAULT NULL,
  
  `customers` BIGINT(20) DEFAULT '0',
  `stb_users` BIGINT(20) DEFAULT '0',
  `vod_users` BIGINT(20) DEFAULT '0',
  `online_users` BIGINT(20) DEFAULT '0',
  `live_users` BIGINT(20) DEFAULT '0',
  
  `active_users` BIGINT(20) DEFAULT '0',
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`,`rep_date`),
  KEY `id` (`id`),
  KEY `rep_vodarea_201404_idx1` (`customertypeid`),
  KEY `rep_vodarea_201404_idx2` (`townid`),
  KEY `rep_vodarea_201404_idx3` (`communityid`),
  KEY `rep_vodarea_201404_idx4` (`villageid`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

PARTITION BY RANGE (YEAR(rep_date))
(PARTITION p2012 VALUES LESS THAN (2013) ENGINE = INNODB,
 PARTITION p2013 VALUES LESS THAN (2014) ENGINE = INNODB,
 PARTITION p2014 VALUES LESS THAN (2015) ENGINE = INNODB,
 PARTITION p2015 VALUES LESS THAN (2016) ENGINE = INNODB,
 PARTITION p2016 VALUES LESS THAN (2017) ENGINE = INNODB,
 PARTITION p_catchall VALUES LESS THAN MAXVALUE ENGINE = INNODB)


ALTER TABLE `vodstat`.`repd_vodarea`   
ADD COLUMN `analog_users` BIGINT(20) DEFAULT 0  NULL AFTER `vod_users`,
ADD COLUMN `bb_users0` BIGINT(20)  DEFAULT 0 NULL AFTER `active_users`,
ADD COLUMN `dvb_users0` BIGINT(20)  DEFAULT 0 NULL AFTER `bb_users0`,
ADD COLUMN `vod_users0` BIGINT(20)  DEFAULT 0 NULL AFTER `dvb_users0`,
ADD COLUMN `analog_users0` BIGINT(20)  DEFAULT 0 NULL AFTER `vod_users0`;
ADD COLUMN `vod_books` BIGINT(20) DEFAULT 0  NULL AFTER `analog_users0`;
ADD COLUMN `dvb_books` BIGINT(20) DEFAULT 0  NULL AFTER `analog_users0`;
ADD COLUMN `bb_books` BIGINT(20) DEFAULT 0  NULL AFTER `analog_users0`;
ADD COLUMN `vod_books_new` BIGINT(20) DEFAULT 0  NULL AFTER `analog_users0`;
ADD COLUMN `dvb_books_new` BIGINT(20) DEFAULT 0  NULL AFTER `analog_users0`;
ADD COLUMN `bb_books_new` BIGINT(20) DEFAULT 0  NULL AFTER `analog_users0`;
ADD COLUMN `host_starts` BIGINT(20) DEFAULT 0  NULL AFTER `dvb_books_new`,
ADD COLUMN `host_stops` BIGINT(20) DEFAULT 0  NULL AFTER `host_starts`,
ADD COLUMN `host_quits` BIGINT(20) DEFAULT 0  NULL AFTER `host_stops`,
ADD COLUMN `host_stop` BIGINT(20) DEFAULT 0  NULL AFTER `host_quits`,
ADD COLUMN `host_normal` BIGINT(20) DEFAULT 0  NULL AFTER `host_stop`;
ADD COLUMN `host_unpay1` BIGINT(20) DEFAULT 0  NULL AFTER `host_normal`,
ADD COLUMN `host_unpay2` BIGINT(20) DEFAULT 0  NULL AFTER `host_unpay1`;
 

ALTER TABLE `vodstat`.`repm_vodarea`   
  ADD COLUMN `vod_expiring` BIGINT(20) DEFAULT 0  NULL AFTER `inactive_offline_users`,
  ADD COLUMN `vod_expiring_book` BIGINT(20) DEFAULT 0  NULL AFTER `vod_expiring`,
  ADD COLUMN `vod_expired_book` BIGINT(20) DEFAULT 0  NULL AFTER `vod_expiring_book`,
  ADD COLUMN `vod_prebook` BIGINT(20) DEFAULT 0  NULL AFTER `vod_expired_book`,
  ADD COLUMN `bb_expiring` BIGINT(20) DEFAULT 0  NULL AFTER `vod_prebook`,
  ADD COLUMN `bb_expiring_book` BIGINT(20) DEFAULT 0  NULL AFTER `bb_expiring`,
  ADD COLUMN `bb_expired_book` BIGINT(20) DEFAULT 0  NULL AFTER `bb_expiring_book`,
  ADD COLUMN `bb_prebook` BIGINT(20) DEFAULT 0  NULL AFTER `bb_expired_book`,
  ADD COLUMN `REPORT_RANGE` VARCHAR(20) NULL AFTER `bb_prebook`,
  ADD COLUMN `CUSTOMERS` BIGINT(20) DEFAULT 0  NULL AFTER `REPORT_RANGE`,
  ADD COLUMN `DVB_USERS` BIGINT(20) DEFAULT 0  NULL AFTER `CUSTOMERS`,
  ADD COLUMN `BB_USERS` BIGINT(20) DEFAULT 0  NULL AFTER `DVB_USERS`,
  ADD COLUMN `VOD_USERS` BIGINT(20) DEFAULT 0  NULL AFTER `BB_USERS`,
  ADD COLUMN `VOD_BOOKED` BIGINT(20) DEFAULT 0  NULL AFTER `VOD_USERS`,
  ADD COLUMN `BB_BOOKED` BIGINT(20) DEFAULT 0  NULL AFTER `VOD_BOOKED`,
  ADD COLUMN `VOD_UNBOOK` BIGINT(20) DEFAULT 0  NULL AFTER `BB_BOOKED`,
  ADD COLUMN `BB_UNBOOK` BIGINT(20) DEFAULT 0  NULL AFTER `VOD_UNBOOK`,
  ADD COLUMN `NEW_DVB` BIGINT(20) DEFAULT 0  NULL AFTER `BB_UNBOOK`,
  ADD COLUMN `NEW_VOD` BIGINT(20) DEFAULT 0  NULL AFTER `NEW_DVB`,
  ADD COLUMN `NEW_BB` BIGINT(20) DEFAULT 0  NULL AFTER `NEW_VOD`,
  ADD COLUMN `NEW_VOD_BOOKED` BIGINT(20) DEFAULT 0  NULL AFTER `NEW_BB`,
  ADD COLUMN `NEW_BB_BOOKED` BIGINT(20) DEFAULT 0  NULL AFTER `NEW_VOD_BOOKED`;

--日报表初始化:

DELETE FROM rep_vodarea

INSERT INTO rep_vodarea(rep_date,
townid,communityid,villageid,customertypeid,
town,community,village,customertype,
active_users,online_users,book_users,vod_users,stb_users,customers)
SELECT STR_TO_DATE('20140101','%Y%m%d'),
c.`townid`,c.`communityid`,c.`villageid`,c.`customertypeid`,
c.`town`,c.`community`,c.`village`,c.`customertype`,-1,-1,-1,-1,-1,-1
FROM boss_user u
JOIN boss_customer  c ON c.`customerid` = u.`customerid`
WHERE u.userstateid != 8058 and u.crtime < STR_TO_DATE('20140102','%Y%m%d')
GROUP BY c.`townid`,c.`communityid`,c.`villageid`,c.`customertypeid`,
c.`town`,c.`community`,c.`village`,c.`customertype`


--1.日报表生成:客户数量(takes: 2 MIN 40 sec)
UPDATE repd_vodarea r
SET r.`customers` = 
(SELECT COUNT(*) FROM boss_customer b 
WHERE b.`customertypeid` = r.`customertypeid`
AND b.`townid` = r.`townid`
AND b.`communityid` = r.`communityid`
AND b.`villageid` = r.`villageid`
AND b.crtime < STR_TO_DATE('20140102','%Y%m%d')
AND EXISTS (SELECT * FROM boss_user bu WHERE bu.`customerid` = b.`customerid` AND bu.`userstateid` != 8058)
) where r.rep_date = STR_TO_DATE('20140101','%Y%m%d')


--2.日报表生成:机顶盒数量(takes: 9 MIN 6 sec)
UPDATE repd_vodarea r
SET r.`stb_users` = 
(SELECT COUNT(*) FROM boss_user b 
WHERE b.`customertypeid` = r.`customertypeid`
AND b.`townid` = r.`townid`
AND b.`communityid` = r.`communityid`
AND b.`villageid` = r.`villageid`
AND b.`userstateid`!=8058 AND b.`usertypeid` IN (1521,1051)
AND b.crtime < STR_TO_DATE('20140102','%Y%m%d')
) where r.rep_date = STR_TO_DATE('20140101','%Y%m%d')


--3.日报表生成:交互机顶盒用户数量(takes: 9 MIN 45 sec)
UPDATE repd_vodarea r
SET r.`vod_users`=
(SELECT COUNT(*) FROM boss_user b 
WHERE b.`customertypeid` = r.`customertypeid`
AND b.`townid` = r.`townid`
AND b.`communityid` = r.`communityid`
AND b.`villageid` = r.`villageid`
AND b.`userstateid`!=8058 AND b.`usertypeid` = 1051
AND b.crtime < STR_TO_DATE('20140102','%Y%m%d')
) where r.rep_date = STR_TO_DATE('20140101','%Y%m%d')


--4.日报表生成:在线用户数量(takes: 10 MIN 46 sec)
UPDATE rep_vodarea r
SET r.`book_users`=
(SELECT COUNT(*) FROM boss_user b 
WHERE b.`customertypeid` = r.`customertypeid`
AND b.`townid` = r.`townid`
AND b.`communityid` = r.`communityid`
AND b.`villageid` = r.`villageid`
AND b.`userstateid`!=8058 
--AND b.crtime < STR_TO_DATE('20140102','%Y%m%d')
AND EXISTS (
SELECT * FROM boss_userproduct up WHERE up.userid = b.`userid` AND up.`productid` IN (4217,4208)
AND up.`endtime` >= STR_TO_DATE('20140401','%Y%m%d') AND up.`begintime` <= STR_TO_DATE('20140401','%Y%m%d')
)
) where r.rep_date = STR_TO_DATE('20140101','%Y%m%d')



--5.日报表生成:线上用户(takes: 30.619 sec)
UPDATE rep_vodarea r
SET r.`online_users`=
(SELECT COUNT(*) FROM boss_user b 
WHERE b.`customertypeid` = r.`customertypeid`
AND b.`townid` = r.`townid`
AND b.`communityid` = r.`communityid`
AND b.`villageid` = r.`villageid`
AND EXISTS (SELECT * FROM epglog_2014001 up WHERE up.userid = b.`userid` and DATE_FORMAT((up.logtime,'%Y%m%d')='20140101')
) where r.rep_date = STR_TO_DATE('20140101','%Y%m%d')




--6.月报表生成:有点播的用户(takes: 9 MIN 16 sec)
UPDATE rep_vodarea_201404 r
SET r.`active_users`=
(SELECT COUNT(*) FROM boss_user b 
WHERE b.`customertypeid` = r.`customertypeid`
AND b.`townid` = r.`townid`
AND b.`communityid` = r.`communityid`
AND b.`villageid` = r.`villageid`
AND EXISTS (SELECT * FROM wasun_201404 up WHERE up.userid = b.`userid`)
##or exists (select * from wasue_201404 we where we.userid = b.userid)
) where r.rep_date = STR_TO_DATE('20140101','%Y%m%d')





SELECT bu.userid,bu.`town`,bu.`village`,bu.`fulladdress`,bu.`customername`,bu.`customertype`,bu.`crtime`,bu.`stb`,bu.`stbtype`
FROM boss_user bu 
WHERE EXISTS (SELECT * FROM boss_userproduct bp WHERE bp.`userid` = bu.`userid` AND bp.`productid`= 8120  AND bp.`endtime` > STR_TO_DATE('20140630','%Y%m%d'))
AND NOT EXISTS (SELECT * FROM epglog_201406 e WHERE e.`userid` = bu.`userid`)



ALTER TABLE wasun_201402 REORGANIZE PARTITION p28 INTO (PARTITION p28 VALUES IN (28,29,30,31));
ALTER TABLE wasue_201402 REORGANIZE PARTITION p28 INTO (PARTITION p28 VALUES IN (28,29,30,31));
ALTER TABLE wasun_201404 REORGANIZE PARTITION p30 INTO (PARTITION p30 VALUES IN (30,31));
ALTER TABLE wasue_201404 REORGANIZE PARTITION p30 INTO (PARTITION p30 VALUES IN (30,31));
ALTER TABLE wasun_201406 REORGANIZE PARTITION p30 INTO (PARTITION p30 VALUES IN (30,31));
ALTER TABLE wasue_201406 REORGANIZE PARTITION p30 INTO (PARTITION p30 VALUES IN (30,31));






ALTER TABLE vodstat.`wasun_201312` ENGINE='MyISAM';
ALTER TABLE vodstat.`wasun_201401` ENGINE='MyISAM';

OPTIMIZE TABLE wasue_201401;
OPTIMIZE TABLE wasue_201402;



--导入华数数据例子:
LOAD DATA LOCAL INFILE '/var/ftp/wasulog/20140401_valid.csv'
REPLACE
INTO TABLE wasulog.wasun_201403
CHARACTER SET GBK
FIELDS TERMINATED BY ','
OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\r\n'
IGNORE 1 LINES
(SUBSCRIBER_CODE,BILLING_NO,BEGIN_DATE,END_DATE,FEE,PPV_ID,FILM_NAME,DISPLAY_PATH);


LOAD DATA LOCAL INFILE '/var/ftp/wasulog/20140401_invalid.csv'
REPLACE
INTO TABLE wasulog.wasue_201403
CHARACTER SET GBK
FIELDS TERMINATED BY ','
OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\r\n'
IGNORE 1 LINES
(SUBSCRIBER_CODE,BILLING_NO,ERROR_CODE,BEGIN_DATE,END_DATE,FEE,PPV_ID,FILM_NAME,DISPLAY_PATH);

UPDATE wasulog.wasun_201312 w SET w.`USERID` = (SELECT b.userid FROM boss_wasusubcode b WHERE b.`subcode` = w.`SUBSCRIBER_CODE`);
UPDATE wasulog.wasue_201312 w SET w.`USERID` = (SELECT b.userid FROM boss_wasusubcode b WHERE b.`subcode` = w.`SUBSCRIBER_CODE`);
delete from wasulog.wasue_201312 where USERID is null;
