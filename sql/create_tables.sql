DROP DATABASE `quick`;

CREATE DATABASE `quick`
    DEFAULT CHARACTER SET utf8mb4
    COLLATE utf8mb4_general_ci;

USE `quick`;

CREATE TABLE `quick_enum`
(
    `id`          INT         NOT NULL AUTO_INCREMENT COMMENT '选择项ID',
    `value`       VARCHAR(64) NOT NULL COMMENT '枚举值',
    `text`        VARCHAR(64) NOT NULL COMMENT '枚举',
    `source`      VARCHAR(64) NOT NULL DEFAULT '' COMMENT '枚举分组',
    `sort`        TINYINT     NOT NULL DEFAULT 0 COMMENT '排序',

    `create_time` TIMESTAMP   NOT NULL DEFAULT current_timestamp COMMENT '创建时间',
    `update_time` TIMESTAMP   NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp COMMENT '更新时间',

    PRIMARY KEY (`id`),
    UNIQUE (`source`, `value`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COLLATE = utf8_unicode_ci COMMENT ='枚举';

CREATE TABLE `quick_user`
(
    `id`          INT          NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username`    VARCHAR(50)  NOT NULL COMMENT '用户名',
    `email`       VARCHAR(200) NOT NULL COMMENT '邮箱',
    `password`    VARCHAR(200) NOT NULL COMMENT '密码',
    `nick_name`   VARCHAR(50)  NOT NULL DEFAULT '' COMMENT '昵称',
    `is_deleted`  TINYINT      NOT NULL DEFAULT 0 COMMENT '是否删除',
    `create_time` TIMESTAMP    NOT NULL DEFAULT current_timestamp COMMENT '创建时间',
    `update_time` TIMESTAMP    NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp COMMENT '更新时间',

    PRIMARY KEY (`id`),
    UNIQUE (`username`),
    UNIQUE (`email`),
    INDEX (`nick_name`),
    INDEX (`create_time`),
    INDEX (`update_time`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COLLATE = utf8_unicode_ci COMMENT ='用户';

CREATE TABLE `quick_user_info`
(
    `id`           INT           NOT NULL COMMENT '用户ID',
    `true_name`    VARCHAR(50)   NOT NULL DEFAULT '' COMMENT '用户姓名',
    `age`          INT           NOT NULL DEFAULT 0 COMMENT '年龄',
    `sex`          TINYINT       NOT NULL DEFAULT 0 COMMENT '性别',
    `photo`        VARCHAR(200)  NOT NULL DEFAULT '' COMMENT '头像',
    `introduction` VARCHAR(1000) NOT NULL DEFAULT '' COMMENT '简介',
    `create_time`  TIMESTAMP     NOT NULL DEFAULT current_timestamp COMMENT '创建时间',
    `update_time`  TIMESTAMP     NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp COMMENT '更新时间',

    PRIMARY KEY (`id`),
    INDEX (`true_name`),
    INDEX (`age`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COLLATE = utf8_unicode_ci COMMENT ='用户额外信息';

CREATE TABLE `quick_user_type`
(
    `id`             INT          NOT NULL COMMENT '类型ID',
    `user_type_name` VARCHAR(50)  NOT NULL COMMENT '类型名称',
    `remark`         VARCHAR(200) NOT NULL DEFAULT '' COMMENT '备注',
    `is_deleted`     TINYINT      NOT NULL DEFAULT 0 COMMENT '是否删除',
    `create_time`    TIMESTAMP    NOT NULL DEFAULT current_timestamp COMMENT '创建时间',
    `update_time`    TIMESTAMP    NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp COMMENT '更新时间',

    PRIMARY KEY (`id`),
    INDEX (`user_type_name`),
    INDEX (`create_time`),
    INDEX (`update_time`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COLLATE = utf8_unicode_ci COMMENT ='用户类型';

CREATE TABLE `quick_user_type_relation`
(
    `id`           INT       NOT NULL AUTO_INCREMENT COMMENT '用户类型关系ID',
    `user_id`      INT       NOT NULL COMMENT '用户ID',
    `user_type_id` INT       NOT NULL COMMENT '类型ID',
    `status`       INT       NOT NULL DEFAULT 0 COMMENT '状态',
    `is_deleted`   TINYINT   NOT NULL DEFAULT 0 COMMENT '是否删除',
    `create_time`  TIMESTAMP NOT NULL DEFAULT current_timestamp COMMENT '创建时间',
    `update_time`  TIMESTAMP NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp COMMENT '更新时间',

    PRIMARY KEY (`id`),
    UNIQUE relation_index (`user_id`, `user_type_id`),
    INDEX (`create_time`),
    INDEX (`update_time`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COLLATE = utf8_unicode_ci COMMENT ='用户类型关系';

CREATE TABLE `quick_multi_pk`
(
    `key1`        INT         NOT NULL COMMENT '主键1',
    `key2`        INT         NOT NULL COMMENT '主键2',
    `value`       VARCHAR(50) NOT NULL COMMENT '值',
    `status`      INT         NOT NULL DEFAULT 0 COMMENT '状态(1正常， 2提高， 3起飞)',
    `is_deleted`  TINYINT     NOT NULL DEFAULT 0 COMMENT '是否删除',
    `create_time` TIMESTAMP   NOT NULL DEFAULT current_timestamp COMMENT '创建时间',
    `update_time` TIMESTAMP   NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp COMMENT '更新时间',

    PRIMARY KEY (`key1`, `key2`),
    INDEX (`create_time`),
    INDEX (`update_time`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COLLATE = utf8_unicode_ci COMMENT ='多主键测试表';

# CREATE TABLE `no_pk`
# (
#   `key`        INT         NOT NULL
#   COMMENT '键',
#   `value`       VARCHAR(50) NOT NULL
#   COMMENT '值',
#   `is_deleted`  TINYINT     NOT NULL DEFAULT 0
#   COMMENT '是否删除',
#   `create_time` TIMESTAMP   NOT NULL DEFAULT current_timestamp
#   COMMENT '创建时间',
#   `update_time` TIMESTAMP   NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp
#   COMMENT '更新时间',
#
#   INDEX (`key`),
#   INDEX (`create_time`),
#   INDEX (`update_time`)
# )
#   ENGINE = InnoDB
#   DEFAULT CHARSET = utf8
#   COLLATE = utf8_unicode_ci
#   COMMENT ='无主键测试表';

INSERT INTO quick_enum(value, text, source)
VALUES ('sex', '性别', ''),
       ('0', '保密', 'sex'),
       ('1', '男', 'sex'),
       ('2', '女', 'sex');


INSERT INTO quick_user_type (id, user_type_name)
VALUES (1, '超级管理员'),
       (2, '管理员'),
       (3, '普通用户');
INSERT INTO quick_user (username, email, password, nick_name)
VALUES ('root', 'root@example.com', '123456', 'root'),
       ('admin1', 'admin1@example.com', '123456', 'admin1'),
       ('admin2', 'admin2@example.com', '123456', 'admin2'),
       ('admin3', 'admin3@example.com', '123456', 'admin3'),
       ('admin4', 'admin4@example.com', '123456', 'admin4'),
       ('admin5', 'admin5@example.com', '123456', 'admin5'),
       ('user1', 'user1@example.com', '123456', 'user1'),
       ('user2', 'user2@example.com', '123456', 'user2'),
       ('user3', 'user3@example.com', '123456', 'user3'),
       ('user4', 'user4@example.com', '123456', 'user4'),
       ('user5', 'user5@example.com', '123456', 'user5'),
       ('user6', 'user6@example.com', '123456', 'user6'),
       ('user7', 'user7@example.com', '123456', 'user7'),
       ('user8', 'user8@example.com', '123456', 'user8'),
       ('user9', 'user9@example.com', '123456', 'user9'),
       ('user10', 'user10@example.com', '123456', 'user10'),
       ('user11', 'user11@example.com', '123456', 'user11'),
       ('user12', 'user12@example.com', '123456', 'user12'),
       ('user13', 'user13@example.com', '123456', 'user13'),
       ('user14', 'user14@example.com', '123456', 'user14'),
       ('user15', 'user15@example.com', '123456', 'user15'),
       ('user16', 'user16@example.com', '123456', 'user16'),
       ('user17', 'user17@example.com', '123456', 'user17'),
       ('user18', 'user18@example.com', '123456', 'user18'),
       ('user19', 'user19@example.com', '123456', 'user19'),
       ('user20', 'user20@example.com', '123456', 'user20'),
       ('user21', 'user21@example.com', '123456', 'user21'),
       ('user22', 'user22@example.com', '123456', 'user22'),
       ('user23', 'user23@example.com', '123456', 'user23'),
       ('user24', 'user24@example.com', '123456', 'user24'),
       ('user25', 'user25@example.com', '123456', 'user25'),
       ('user26', 'user26@example.com', '123456', 'user26'),
       ('user27', 'user27@example.com', '123456', 'user27'),
       ('user28', 'user28@example.com', '123456', 'user28'),
       ('user29', 'user29@example.com', '123456', 'user29'),
       ('user30', 'user30@example.com', '123456', 'user30');

INSERT INTO quick_user_info (id, true_name, age, sex)
VALUES (1, 'root', 30, 0),
       (2, 'admin1', 25, 1),
       (3, 'admin2', 43, 1),
       (4, 'admin3', 23, 0),
       (5, 'admin4', 24, 0);

INSERT INTO quick_user_type_relation (user_id, user_type_id)
VALUES (1, 1),
       (2, 2),
       (3, 2),
       (4, 2),
       (5, 2),
       (6, 2),
       (7, 3),
       (8, 3),
       (9, 3),
       (10, 3),
       (11, 3),
       (12, 3),
       (13, 3),
       (14, 3),
       (15, 3),
       (16, 3),
       (17, 3),
       (18, 3),
       (19, 3),
       (20, 3),
       (21, 3),
       (22, 3),
       (23, 3),
       (24, 3),
       (25, 3),
       (26, 3),
       (27, 3),
       (28, 3),
       (29, 3),
       (30, 3),
       (31, 3),
       (32, 3),
       (33, 3),
       (34, 3),
       (35, 3),
       (36, 3);

INSERT INTO quick_multi_pk (key1, key2, `value`)
VALUES (1, 30, 'root'),
       (2, 25, 'admin1'),
       (1, 43, 'admin2'),
       (4, 23, 'admin3'),
       (5, 24, 'admin4');