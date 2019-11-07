drop database if exists `permission`;

create database `permission`
    default character set utf8mb4
    collate utf8mb4_general_ci;

use `permission`;

drop table if exists `sys_user`;
create table `sys_user`
(
    `user_id`    bigint                             not null auto_increment comment '用户ID',
    `username`   varchar(64)                        not null comment '用户名',
    `password`   varchar(128)                       not null comment '密码',
    `salt`       varchar(16)                        not null comment '盐',
    `email`      varchar(128)                       not null comment '邮箱',
    `mobile`     varchar(128)                       not null comment '手机号',

    `deleted`    tinyint  default 0                 not null comment '是否删除',
    `created_by` int      default 0                 not null comment '创建人',
    `updated_by` int      default 0                 not null comment '修改人',
    `created_at` datetime default current_timestamp not null comment '创建时间',
    `updated_at` datetime default current_timestamp not null on update current_timestamp comment '更新时间',

    primary key (`user_id`),
    unique (`username`),
    unique (`email`),
    unique (`mobile`)
) engine = InnoDB
  default charset = utf8mb4
  collate = utf8_unicode_ci
  auto_increment = 100000000
    comment ='系统用户';

drop table if exists `sys_role`;
create table `sys_role`
(
    `role_id`    bigint                                 not null auto_increment comment '角色ID',
    `role_code`  varchar(64)                            not null comment '角色编码',
    `role_name`  varchar(64)                            not null comment '角色名称',
    `remark`     varchar(128) default ''                not null comment '备注',

    `deleted`    tinyint      default 0                 not null comment '是否删除',
    `created_by` int          default 0                 not null comment '创建人',
    `updated_by` int          default 0                 not null comment '修改人',
    `created_at` datetime     default current_timestamp not null comment '创建时间',
    `updated_at` datetime     default current_timestamp not null on update current_timestamp comment '更新时间',

    primary key (`role_id`)
) engine = InnoDB
  default charset = utf8mb4
  collate = utf8_unicode_ci
  auto_increment = 100000000
    comment ='角色';

drop table if exists `sys_permission`;
create table `sys_permission`
(
    `permission_id`   bigint                                 not null auto_increment comment '权限ID',
    `parent_id`       bigint       default 0                 not null comment '模块ID，一级模块为0',
    `permission_code` varchar(64)                            not null comment '权限编码',
    `permission_name` varchar(64)                            not null comment '权限名称',
    `type`            tinyint      default 0                 not null comment '类型 (0模块,1权限)',
    `remark`          varchar(128) default ''                not null comment '备注',

    `deleted`         tinyint      default 0                 not null comment '是否删除',
    `created_by`      int          default 0                 not null comment '创建人',
    `updated_by`      int          default 0                 not null comment '修改人',
    `created_at`      datetime     default current_timestamp not null comment '创建时间',
    `updated_at`      datetime     default current_timestamp not null on update current_timestamp comment '更新时间',
    primary key (`permission_id`)
) engine = InnoDB
  default charset = utf8mb4
  collate = utf8_unicode_ci
  auto_increment = 100000000
    comment ='权限';

drop table if exists `sys_menu`;
create table `sys_menu`
(
    `menu_id`    bigint                                 not null auto_increment comment '按钮ID',
    `parent_id`  bigint       default 0                 not null comment '父菜单ID，一级菜单为0',
    `menu_code`  varchar(64)  default ''                not null comment '菜单编码',
    `menu_name`  varchar(64)                            not null comment '菜单名称',
    `url`        varchar(256) default ''                not null comment '菜单URL',
    `type`       tinyint      default 0                 not null comment '类型 (0目录,1菜单)',
    `icon`       varchar(64)  default ''                not null comment '菜单图标',
    `sort`       int          default 0                 not null comment '排序',
    `remark`     varchar(128) default ''                not null comment '备注',

    `deleted`    tinyint      default 0                 not null comment '是否删除',
    `created_by` int          default 0                 not null comment '创建人',
    `updated_by` int          default 0                 not null comment '修改人',
    `created_at` datetime     default current_timestamp not null comment '创建时间',
    `updated_at` datetime     default current_timestamp not null on update current_timestamp comment '更新时间',

    primary key (`menu_id`),
    index (`sort`),
    index (`created_at`),
    index (`updated_at`)
) engine = InnoDB
  default charset = utf8mb4
  collate = utf8_unicode_ci
  auto_increment = 100000000
    comment ='菜单';

drop table if exists `sys_user_role`;
create table `sys_user_role`
(
    `user_id` bigint not null comment '用户ID',
    `role_id` bigint not null comment '角色ID',
    primary key (`user_id`, `role_id`)
) engine = InnoDB
  default charset = utf8mb4
  collate = utf8_unicode_ci
    comment ='用户角色';

drop table if exists `sys_role_menu`;
create table `sys_role_menu`
(
    `role_id` bigint not null comment '角色ID',
    `menu_id` bigint not null comment '菜单ID',
    primary key (`role_id`, `menu_id`)
) engine = InnoDB
  default charset = utf8mb4
  collate = utf8_unicode_ci
    comment ='角色菜单';

drop table if exists `sys_role_permission`;
create table `sys_role_permission`
(
    `role_id`       bigint not null comment '角色ID',
    `permission_id` bigint not null comment '权限ID',
    primary key (`role_id`, `permission_id`)
) engine = InnoDB
  default charset = utf8mb4
  collate = utf8_unicode_ci
    comment ='角色权限';

drop table if exists `sys_menu_permission`;
create table `sys_menu_permission`
(
    `menu_id`       bigint not null comment '菜单ID',
    `permission_id` bigint not null comment '权限ID',
    primary key (`menu_id`, `permission_id`)
) engine = InnoDB
  default charset = utf8mb4
  collate = utf8_unicode_ci
    comment ='菜单权限';

drop table if exists `sys_enum`;
create table `sys_enum`
(
    `id`             bigint                                  not null auto_increment comment '枚举ID',
    `value`          varchar(64)                             not null comment '值',
    `text`           varchar(64)   default ''                not null comment '展示文字',
    `parent_id`      bigint        default 0                 not null comment '父ID',
    `source`         varchar(64)                             not null comment '分组',
    `type`           tinyint       default 0                 not null comment '类型 (0分组,1选项)',
    `system_related` tinyint       default 0                 not null comment '是否系统相关 (0否,1是)',
    `ext`            varchar(1024) default ''                not null comment '扩展信息',

    `deleted`        tinyint       default 0                 not null comment '是否删除',
    `created_by`     int           default 0                 not null comment '创建人',
    `updated_by`     int           default 0                 not null comment '修改人',
    `created_at`     datetime      default current_timestamp not null comment '创建时间',
    `updated_at`     datetime      default current_timestamp not null on update current_timestamp comment '更新时间',

    primary key (`id`),
    unique (`value`, `source`),
    index (`source`)
) engine = InnoDB
  default charset = utf8mb4
  collate = utf8_unicode_ci
    comment ='系统枚举';

drop table if exists `sys_config`;
create table `sys_config`
(
    `key`        varchar(64)                             not null comment '配置键',
    `value`      varchar(2048) default ''                not null comment '配置值',
    `remark`     varchar(512)  default ''                not null comment '备注',

    `deleted`    tinyint       default 0                 not null comment '是否删除',
    `created_by` int           default 0                 not null comment '创建人',
    `updated_by` int           default 0                 not null comment '修改人',
    `created_at` datetime      default current_timestamp not null comment '创建时间',
    `updated_at` datetime      default current_timestamp not null on update current_timestamp comment '更新时间',

    primary key (`key`)
) engine = InnoDB
  default charset = utf8mb4
  collate = utf8_unicode_ci
    comment ='系统配置';

drop table if exists `sys_user_token`;
create table `sys_user_token`
(
    `user_id`    bigint                             not null,
    `token`      varchar(128)                       not null comment 'token',
    `expire_at`  datetime                           not null comment '过期时间',
    `updated_at` datetime default current_timestamp not null on update current_timestamp comment '更新时间',
    primary key (`user_id`),
    unique `token` (`token`)
) engine = InnoDB
  default charset = utf8mb4
  collate = utf8_unicode_ci
  auto_increment = 100000000
    comment ='系统用户Token';

drop table if exists `sys_captcha`;
create table `sys_captcha`
(
    `account`   varchar(128) not null comment '系统用户账号 (用户名/手机号/邮箱)',
    `code`      varchar(8)   not null comment '验证码',
    `expire_at` datetime     not null comment '过期时间',
    primary key (`account`)
) engine = InnoDB
  default charset = utf8mb4
  collate = utf8_unicode_ci
    comment ='系统验证码';

insert into sys_user(user_id, username, password, salt, email, mobile)
values (100000000, 'administrator', '123456', '123456', 'admin@123.com', '13233333333');

insert into sys_role(role_id, role_code, role_name)
values (100000000, 'administrator', '超级管理员')

insert into sys_menu(menu_id, parent_id, menu_code, menu_name, url, type, icon, sort)
values (100000000, 0, '', '权限管理', '', 1, 'people', 1),
       (100000001, 100000000, 'sys-role', '角色', '/sys/sysrole/index.html', 1, '', 1),
       (100000002, 100000000, 'sys-user', '系统用户', '/sys/sysuser/index.html', 1, '', 2),
       (100000003, 100000000, 'sys-menu', '菜单', '/sys/sysmenu/index.html', 1, '', 3),
       (100000004, 100000000, 'sys-permission', '权限', '/sys/syspermission/index.html', 1, '', 4),
       (100000005, 0, '', '系统配置', '', 1, 'build', 1),
       (100000006, 100000005, 'sys-config', '系统配置', '/sys/sysconfig/index.htm', 1, '', 1),
       (100000007, 100000005, 'sys-enum', '系统枚举', '/sys/sysenum/index.html', 1, '', 2);

insert into sys_enum(id, value, text, parent_id, source, type, system_related)
values (10000000, 'boolean', 'boolean', 0, '', 0, 1),
       (10000001, '0', '否', 10000000, 'boolean', 1, 1),
       (10000002, '1', '是', 10000000, 'boolean', 1, 1),
       (10000003, 'sys_enum_type', '枚举类型', 0, '', 0, 1),
       (10000004, '0', '分组', 10000003, 'sys_enum_type', 1, 1),
       (10000005, '1', '选项', 10000003, 'sys_enum_type', 1, 1),
       (10000006, 'sys_menu_type', '菜单类型', 0, '', 0, 1),
       (10000007, '0', '目录', 10000006, 'sys_menu_type', 1, 1),
       (10000008, '1', '菜单', 10000006, 'sys_menu_type', 1, 1),
       (10000009, 'sys_permission_type', '权限类型', 0, '', 0, 1),
       (10000010, '0', '模块', 10000009, 'sys_permission_type', 1, 1),
       (10000011, '1', '权限', 10000009, 'sys_permission_type', 1, 1);
