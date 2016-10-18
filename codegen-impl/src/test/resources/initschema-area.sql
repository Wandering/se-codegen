CREATE TABLE `se_country` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(16) DEFAULT NULL COMMENT '名称',
  `number` varchar(16) DEFAULT NULL COMMENT '简称',
  `ename` varchar(16) DEFAULT NULL COMMENT '英文名称',
  `status` tinyint(4) NOT NULL COMMENT '状态',
  `creator` int(11) NOT NULL COMMENT '创建人',
  `createDate` bigint(20) NOT NULL COMMENT '创建时间',
  `lastModifier` int(11) NOT NULL COMMENT '修改人',
  `lastModDate` bigint(20) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='国家|国家管理|基础管理|CreateBaseDomain\n国家';


CREATE TABLE `se_province` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(16) DEFAULT NULL COMMENT '名称',
  `number` varchar(16) DEFAULT NULL COMMENT '简称',
  `xpoint` varchar(32) DEFAULT NULL COMMENT '经度',
  `ypoint` varchar(32) DEFAULT NULL COMMENT '纬度',
  `countryId` varchar(11) DEFAULT NULL COMMENT '国家',
  `status` tinyint(4) NOT NULL COMMENT '状态',
  `creator` int(11) NOT NULL COMMENT '创建人',
  `createDate` bigint(20) NOT NULL COMMENT '创建时间',
  `lastModifier` int(11) NOT NULL COMMENT '修改人',
  `lastModDate` bigint(20) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='省份|省份管理|基础管理|CreateBaseDomain\n省份';

CREATE TABLE `se_city` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(16) DEFAULT NULL COMMENT '名称',
  `number` varchar(16) DEFAULT NULL COMMENT '简称',
  `xpoint` varchar(32) DEFAULT NULL COMMENT '经度',
  `ypoint` varchar(32) DEFAULT NULL COMMENT '纬度',
  `provinceId` varchar(11) DEFAULT NULL COMMENT '省份',
  `status` tinyint(4) NOT NULL COMMENT '状态',
  `creator` int(11) NOT NULL COMMENT '创建人',
  `createDate` bigint(20) NOT NULL COMMENT '创建时间',
  `lastModifier` int(11) NOT NULL COMMENT '修改人',
  `lastModDate` bigint(20) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='城市|城市管理|基础管理|CreateBaseDomain\n城市';

CREATE TABLE `se_town` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(16) DEFAULT NULL COMMENT '名称',
  `number` varchar(16) DEFAULT NULL COMMENT '简称',
  `xpoint` varchar(32) DEFAULT NULL COMMENT '经度',
  `ypoint` varchar(32) DEFAULT NULL COMMENT '纬度',
  `cityId` varchar(11) DEFAULT NULL COMMENT '城市',
  `status` tinyint(4) NOT NULL COMMENT '状态',
  `creator` int(11) NOT NULL COMMENT '创建人',
  `createDate` bigint(20) NOT NULL COMMENT '创建时间',
  `lastModifier` int(11) NOT NULL COMMENT '修改人',
  `lastModDate` bigint(20) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='区县|区县管理|基础管理|CreateBaseDomain\n区县';

CREATE TABLE `se_street` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(32) DEFAULT NULL COMMENT '名称',
  `number` varchar(16) DEFAULT NULL COMMENT '简称',
  `xpoint` varchar(32) DEFAULT NULL COMMENT '经度',
  `ypoint` varchar(32) DEFAULT NULL COMMENT '纬度',
  `townId` varchar(11) DEFAULT NULL COMMENT '区县',
  `status` tinyint(4) NOT NULL COMMENT '状态',
  `creator` int(11) NOT NULL COMMENT '创建人',
  `createDate` bigint(20) NOT NULL COMMENT '创建时间',
  `lastModifier` int(11) NOT NULL COMMENT '修改人',
  `lastModDate` bigint(20) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='街道|街道管理|基础管理|CreateBaseDomain\n街道';