DROP TABLE IF EXISTS `db_org`.`tb_org`;
CREATE TABLE `db_org`.`tb_org`
(
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `parent_id`   BIGINT                DEFAULT NULL COMMENT '父组织ID',
    `ancestors`   VARCHAR(255)          DEFAULT NULL COMMENT '祖籍列表',
    `code`        VARCHAR(50)  NOT NULL COMMENT '组织代码',
    `name`        VARCHAR(255) NOT NULL COMMENT '组织名称',
    `org_type`    VARCHAR(255) NOT NULL COMMENT '组织类型',
    `enable`      TINYINT      NOT NULL COMMENT '是否启用',
    `sort`        INT          NOT NULL COMMENT '排序',
    `description` VARCHAR(255)          DEFAULT NULL COMMENT '备注',
    `create_time` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`   BIGINT                DEFAULT NULL COMMENT '创建者',
    `modify_time` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `modify_by`   BIGINT                DEFAULT NULL COMMENT '修改者',
    `row_version` INT                   DEFAULT NULL COMMENT '记录版本',
    `row_valid`   TINYINT               DEFAULT NULL COMMENT '是否有效',
    PRIMARY KEY (`id`),
    UNIQUE KEY (`code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='组织结构';

DROP TABLE IF EXISTS `db_org`.`tb_dealership`;
CREATE TABLE `db_org`.`tb_dealership`
(
    `id`                 BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `code`               VARCHAR(50)  NOT NULL COMMENT '门店代码',
    `name`               VARCHAR(255) NOT NULL COMMENT '门店全称',
    `short_name`         VARCHAR(255)          DEFAULT NULL COMMENT '简称',
    `eng_name`           VARCHAR(255)          DEFAULT NULL COMMENT '英文名称',
    `former_name`        VARCHAR(255)          DEFAULT NULL COMMENT '曾用名称',
    `store_format`       SMALLINT              DEFAULT NULL COMMENT '经营类型：1-直营，2-授权，3-经销',
    `service_type`       VARCHAR(255)          DEFAULT NULL COMMENT '服务类型：S-销售，D-交付，A-售后',
    `registered_capital` DECIMAL(10, 2)        DEFAULT NULL COMMENT '注册资金',
    `showroom_area`      VARCHAR(255)          DEFAULT NULL COMMENT '展厅面积',
    `ground_area`        VARCHAR(255)          DEFAULT NULL COMMENT '占地面积',
    `business_scope`     VARCHAR(255)          DEFAULT NULL COMMENT '经营范围',
    `business_hours`     VARCHAR(255)          DEFAULT NULL COMMENT '营业时间',
    `address`            VARCHAR(255)          DEFAULT NULL COMMENT '门店地址',
    `lon`                VARCHAR(50)           DEFAULT NULL COMMENT '地址经度',
    `lat`                VARCHAR(50)           DEFAULT NULL COMMENT '地址纬度',
    `province_code`      VARCHAR(20)           DEFAULT NULL COMMENT '省级行政区代码',
    `city_code`          VARCHAR(20)           DEFAULT NULL COMMENT '地区级行政区代码',
    `county_code`        VARCHAR(20)           DEFAULT NULL COMMENT '县级行政区代码',
    `fax`                VARCHAR(50)           DEFAULT NULL COMMENT '传真',
    `tel`                VARCHAR(50)           DEFAULT NULL COMMENT '电话',
    `mobile`             VARCHAR(50)           DEFAULT NULL COMMENT '手机',
    `zipcode`            VARCHAR(50)           DEFAULT NULL COMMENT '邮编',
    `email`              VARCHAR(255)          DEFAULT NULL COMMENT '电子邮箱',
    `service_tel`        VARCHAR(255)          DEFAULT NULL COMMENT '服务电话',
    `legal_person`       VARCHAR(255)          DEFAULT NULL COMMENT '法人',
    `manager`            VARCHAR(255)          DEFAULT NULL COMMENT '店长',
    `state`              VARCHAR(255)          DEFAULT NULL COMMENT '门店状态：0-停业，1-营业，2-在建，3-取消，4-整改，5-撤销',
    `enable`             TINYINT      NOT NULL COMMENT '是否启用',
    `sort`               INT          NOT NULL COMMENT '排序',
    `description`        VARCHAR(255)          DEFAULT NULL COMMENT '备注',
    `create_time`        TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`          BIGINT                DEFAULT NULL COMMENT '创建者',
    `modify_time`        TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `modify_by`          BIGINT                DEFAULT NULL COMMENT '修改者',
    `row_version`        INT                   DEFAULT NULL COMMENT '记录版本',
    `row_valid`          TINYINT               DEFAULT NULL COMMENT '是否有效',
    PRIMARY KEY (`id`),
    UNIQUE KEY (`code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='销售门店';

DROP TABLE IF EXISTS `db_org`.`tb_dealership_staff`;
CREATE TABLE `db_org`.`tb_dealership_staff`
(
    `id`              BIGINT      NOT NULL AUTO_INCREMENT COMMENT '主键',
    `dealership_code` VARCHAR(50) NOT NULL COMMENT '门店代码',
    `user_id`         BIGINT      NOT NULL COMMENT '员工用户ID',
    `description`     VARCHAR(255)         DEFAULT NULL COMMENT '备注',
    `create_time`     TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`       BIGINT               DEFAULT NULL COMMENT '创建者',
    `modify_time`     TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `modify_by`       BIGINT               DEFAULT NULL COMMENT '修改者',
    `row_version`     INT                  DEFAULT NULL COMMENT '记录版本',
    `row_valid`       TINYINT              DEFAULT NULL COMMENT '是否有效',
    PRIMARY KEY (`id`),
    UNIQUE KEY (`user_id`),
    KEY `idx_dealership_code` (`dealership_code`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='销售门店员工';