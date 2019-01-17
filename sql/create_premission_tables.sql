drop database `quick`;

create database `quick`
  default character set utf8mb4
  collate utf8mb4_general_ci;

use `quick`;
drop table if exists `sys_user`;
drop table if exists `sys_role`;
drop table if exists `sys_permission`;
drop table if exists `sys_user_role`;
drop table if exists `sys_role_permission`;

create table `sys_user`
(
  `id`          int          not null auto_increment comment '用户ID',
  `username`    varchar(64)  not null comment '用户名',
  `password`    varchar(128) not null comment '密码',
  `email`       varchar(128) not null default '' comment '邮箱',
  `phone`       varchar(16)  not null default '' comment '手机',
  `is_deleted`  tinyint      not null default 0 comment '是否删除',
  `create_time` datetime     not null default current_timestamp comment '创建时间',
  `update_time` datetime     not null default current_timestamp on update current_timestamp comment '更新时间',

  primary key (`id`),
  unique (`username`),
  unique (`email`),
  unique (`phone`),
  index (`create_time`),
  index (`update_time`)
)
  engine = InnoDB
  default charset = utf8
  collate = utf8_unicode_ci
  comment ='用户';

create table `sys_role`
(
  `id`          int         not null auto_increment comment '类型ID',
  `role_code`   varchar(64) not null comment '角色编码',
  `role_name`   varchar(64) not null comment '角色名称',
  `is_deleted`  tinyint     not null default 0 comment '是否删除',
  `create_time` datetime    not null default current_timestamp comment '创建时间',
  `update_time` datetime    not null default current_timestamp on update current_timestamp comment '更新时间',

  primary key (`id`),
  unique (`role_code`),
  index (`create_time`),
  index (`update_time`)
)
  engine = InnoDB
  default charset = utf8
  collate = utf8_unicode_ci
  comment ='角色';

create table `sys_permission`
(
  `id`              int          not null auto_increment comment '权限ID',
  `permission_code` varchar(128) not null comment '权限编码',
  `permission_name` varchar(64)  not null comment '权限名称',
  `is_deleted`      tinyint      not null default 0 comment '是否删除',
  `create_time`     datetime     not null default current_timestamp comment '创建时间',
  `update_time`     datetime     not null default current_timestamp on update current_timestamp comment '更新时间',

  primary key (`id`),
  unique (`permission_name`),
  index (`create_time`),
  index (`update_time`)
)
  engine = InnoDB
  default charset = utf8
  collate = utf8_unicode_ci
  comment ='权限';

create table `sys_user_role`
(
  `id`          int         not null auto_increment comment '用户类型关系ID',
  `user_id`  int         not null comment '用户ID',
  `role_code`   varchar(64) not null comment '角色编码',
  `is_deleted`  tinyint     not null default 0 comment '是否删除',
  `create_time` datetime    not null default current_timestamp comment '创建时间',
  `update_time` datetime    not null default current_timestamp on update current_timestamp comment '更新时间',

  primary key (`id`),
  unique (`user_id`, `role_code`),
  index (`create_time`),
  index (`update_time`)
)
  engine = InnoDB
  default charset = utf8
  collate = utf8_unicode_ci
  comment ='用户角色关系';

create table `sys_role_permission`
(
  `id`              int          not null auto_increment comment '用户类型关系ID',
  `role_code`       varchar(64)  not null comment '角色编码',
  `permission_code` varchar(128) not null comment '权限编码',
  `is_deleted`      tinyint      not null default 0 comment '是否删除',
  `create_time`     datetime     not null default current_timestamp comment '创建时间',
  `update_time`     datetime     not null default current_timestamp on update current_timestamp comment '更新时间',

  primary key (`id`),
  unique (`role_code`, `permission_code`),
  index (`create_time`),
  index (`update_time`)
)
  engine = InnoDB
  default charset = utf8
  collate = utf8_unicode_ci
  comment ='角色权限关系';


insert into sys_role (id, role_code, role_name)
values (1, 'root', '超级管理员'),
       (2, 'administrator', '管理员'),
       (3, 'user', '普通用户');

insert into sys_user (id, username, password, email, phone)
VALUES (1, 'root', 'root', 'root@sina.com', '13266660001'),
       (2, 'admin1', '123456', 'admin1@example.com', '13266660002'),
       (3, 'admin2', '123456', 'admin2@example.com', '13266660003'),
       (4, 'admin3', '123456', 'admin3@example.com', '13266660004'),
       (5, 'admin4', '123456', 'admin4@example.com', '13266660005'),
       (6, 'admin5', '123456', 'admin5@example.com', '13266660006'),
       (7, 'user1', '123456', 'user1@example.com', '13266660007'),
       (8, 'user2', '123456', 'user2@example.com', '13266660008'),
       (9, 'user3', '123456', 'user3@example.com', '13266660009'),
       (10, 'user4', '123456', 'user4@example.com', '13266660010'),
       (11, 'user5', '123456', 'user5@example.com', '13266660011'),
       (12, 'user6', '123456', 'user6@example.com', '13266660012'),
       (13, 'user7', '123456', 'user7@example.com', '13266660013'),
       (14, 'user8', '123456', 'user8@example.com', '13266660014'),
       (15, 'user9', '123456', 'user9@example.com', '13266660015'),
       (16, 'user10', '123456', 'user10@example.com', '13266660016'),
       (17, 'user11', '123456', 'user11@example.com', '13266660017'),
       (18, 'user12', '123456', 'user12@example.com', '13266660018'),
       (19, 'user13', '123456', 'user13@example.com', '13266660019'),
       (20, 'user14', '123456', 'user14@example.com', '13266660020'),
       (21, 'user15', '123456', 'user15@example.com', '13266660021'),
       (22, 'user16', '123456', 'user16@example.com', '13266660022'),
       (23, 'user17', '123456', 'user17@example.com', '13266660023'),
       (24, 'user18', '123456', 'user18@example.com', '13266660024'),
       (25, 'user19', '123456', 'user19@example.com', '13266660025'),
       (26, 'user20', '123456', 'user20@example.com', '13266660026'),
       (27, 'user21', '123456', 'user21@example.com', '13266660027'),
       (28, 'user22', '123456', 'user22@example.com', '13266660028'),
       (29, 'user23', '123456', 'user23@example.com', '13266660029'),
       (30, 'user24', '123456', 'user24@example.com', '13266660030'),
       (31, 'user25', '123456', 'user25@example.com', '13266660031'),
       (32, 'user26', '123456', 'user26@example.com', '13266660032'),
       (33, 'user27', '123456', 'user27@example.com', '13266660033'),
       (34, 'user28', '123456', 'user28@example.com', '13266660034'),
       (35, 'user29', '123456', 'user29@example.com', '13266660035'),
       (36, 'user30', '123456', 'user30@example.com', '13266660036');

insert into sys_user_role(user_id, role_code)
values (1, 'root'),
       (2, 'administrator'),
       (3, 'administrator'),
       (4, 'administrator'),
       (5, 'administrator'),
       (6, 'administrator'),
       (7, 'user'),
       (8, 'user'),
       (9, 'user'),
       (10, 'user'),
       (11, 'user'),
       (12, 'user'),
       (13, 'user'),
       (14, 'user'),
       (15, 'user'),
       (16, 'user'),
       (17, 'user'),
       (18, 'user'),
       (19, 'user'),
       (20, 'user'),
       (21, 'user'),
       (22, 'user'),
       (23, 'user'),
       (24, 'user'),
       (25, 'user'),
       (26, 'user'),
       (27, 'user'),
       (28, 'user'),
       (29, 'user'),
       (30, 'user'),
       (31, 'user'),
       (32, 'user'),
       (33, 'user'),
       (34, 'user'),
       (35, 'user'),
       (36, 'user');
