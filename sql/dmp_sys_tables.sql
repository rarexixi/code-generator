-- 系统模块
create table `dmp_sys_module`
(
    `id`          int unsigned                           not null auto_increment comment '模块ID',
    `module_code` varchar(64)                            not null comment '模块编码',
    `module_name` varchar(64)                            not null comment '模块名称',
    `url`         varchar(256) default ''                not null comment '模块URL',
    `icon`        varchar(64)  default ''                not null comment '菜单图标',
    `icon_type`   tinyint      default 0                 not null comment '图标类型',
    `remark`      varchar(128) default ''                not null comment '备注',

    `deleted`     tinyint      default 0                 not null comment '是否删除 (0否 1是)',
    `create_user` varchar(64)  default ''                not null comment '创建人',
    `update_user` varchar(64)  default ''                not null comment '修改人',
    `create_time` datetime     default current_timestamp not null comment '创建时间',
    `update_time` datetime     default current_timestamp not null on update current_timestamp comment '更新时间',

    primary key (`id`),
    unique uniq_sys_module_code (`module_code`)
)
    engine = InnoDB
    default charset = utf8
    collate = utf8_unicode_ci
    comment ='系统模块';

-- 部门
create table `dmp_sys_dept`
(
    `id`          int unsigned                           not null auto_increment comment '部门ID',
    `parent_id`   int          default 0                 not null comment '上级部门ID',
    `dept_code`   varchar(64)                            not null comment '部门编码',
    `dept_name`   varchar(64)                            not null comment '部门名称',
    `full_code`   varchar(256)                           not null comment '完整部门编码',
    `remark`      varchar(128) default ''                not null comment '备注',

    `deleted`     tinyint      default 0                 not null comment '是否删除 (0否 1是)',
    `create_user` varchar(64)  default ''                not null comment '创建人',
    `update_user` varchar(64)  default ''                not null comment '修改人',
    `create_time` datetime     default current_timestamp not null comment '创建时间',
    `update_time` datetime     default current_timestamp not null on update current_timestamp comment '更新时间',

    primary key (`id`),
    unique uniq_sys_dept_parent_id_code (`parent_id`, `dept_code`),
    unique uniq_sys_dept_full_code (`full_code`)
)
    engine = InnoDB
    default charset = utf8
    collate = utf8_unicode_ci
    comment ='系统部门';

-- 系统用户
create table `dmp_sys_user`
(
    `id`          int unsigned                          not null comment '用户ID',
    `username`    varchar(64)                           not null comment '用户名',
    `password`    varchar(256)                          not null comment '密码',
    `real_name`   varchar(16)                           not null comment '姓名',
    `email`       varchar(64)                           not null comment '邮箱',
    `mobile`      varchar(16)                           not null comment '手机号',
    `dept_id`     int unsigned                          not null comment '部门ID',

    `deleted`     tinyint     default 0                 not null comment '是否删除 (0否 1是)',
    `create_user` varchar(64) default ''                not null comment '创建人',
    `update_user` varchar(64) default ''                not null comment '修改人',
    `create_time` datetime    default current_timestamp not null comment '创建时间',
    `update_time` datetime    default current_timestamp not null on update current_timestamp comment '更新时间',

    primary key (`id`),
    index idx_sys_user_username (`username`),
    index idx_sys_user_real_name (`real_name`),
    index idx_sys_user_email (`email`),
    index idx_sys_user_mobile (`mobile`)
)
    engine = InnoDB
    default charset = utf8
    collate = utf8_unicode_ci
    comment ='系统用户';

-- 系统角色
create table `dmp_sys_role`
(
    `id`          int unsigned                           not null auto_increment comment '角色ID',
    `role_code`   varchar(64)                            not null comment '角色编码',
    `role_name`   varchar(64)                            not null comment '角色名称',
    `type`        tinyint                                not null comment '类型 (1系统角色 2数据角色)',
    `built_in`    int          default 0                 not null comment '是否内置角色 (0否 1是)',
    `remark`      varchar(128) default ''                not null comment '备注',

    `deleted`     tinyint      default 0                 not null comment '是否删除 (0否 1是)',
    `create_user` varchar(64)  default ''                not null comment '创建人',
    `update_user` varchar(64)  default ''                not null comment '修改人',
    `create_time` datetime     default current_timestamp not null comment '创建时间',
    `update_time` datetime     default current_timestamp not null on update current_timestamp comment '更新时间',

    primary key (`id`),
    unique uniq_sys_role_code (`role_code`)
)
    engine = InnoDB
    default charset = utf8
    collate = utf8_unicode_ci
    comment ='系统角色';

-- 系统权限
create table `dmp_sys_permission`
(
    `id`              int unsigned                           not null auto_increment comment '权限ID',
    `parent_id`       int          default 0                 not null comment '权限分组ID',
    `permission_code` varchar(64)                            not null comment '权限编码',
    `permission_name` varchar(64)                            not null comment '权限名称',
    `full_code`       varchar(256)                           not null comment '完整权限编码',
    `type`            tinyint      default 0                 not null comment '类型 (0权限分组 1权限)',
    `remark`          varchar(128) default ''                not null comment '备注',

    `deleted`         tinyint      default 0                 not null comment '是否删除 (0否 1是)',
    `create_user`     varchar(64)  default ''                not null comment '创建人',
    `update_user`     varchar(64)  default ''                not null comment '修改人',
    `create_time`     datetime     default current_timestamp not null comment '创建时间',
    `update_time`     datetime     default current_timestamp not null on update current_timestamp comment '更新时间',

    primary key (`id`),
    unique uniq_sys_permission_parent_id_code (`parent_id`, `permission_code`),
    unique uniq_sys_permission_full_code (`full_code`)
)
    engine = InnoDB
    default charset = utf8
    collate = utf8_unicode_ci
    comment ='系统权限';

-- 系统菜单
create table `dmp_sys_menu`
(
    `id`          int unsigned                           not null auto_increment comment '菜单ID',
    `module_id`   int          default 0                 not null comment '模块ID',
    `parent_id`   int          default 0                 not null comment '父菜单ID',
    `menu_code`   varchar(64)  default ''                not null comment '菜单编码',
    `menu_name`   varchar(64)                            not null comment '菜单名称',
    `full_code`   varchar(256)                           not null comment '完整菜单编码',
    `type`        tinyint      default 0                 not null comment '类型 (0目录 1菜单)',
    `path`        varchar(256) default ''                not null comment '菜单路径',
    `path_type`   tinyint      default 0                 not null comment '路径类型(0vue路由 1相对路径 2绝对路径)',
    `icon`        varchar(256) default ''                not null comment '菜单图标',
    `icon_type`   tinyint      default 0                 not null comment '图标类型',
    `sort`        int          default 0                 not null comment '排序',
    `remark`      varchar(128) default ''                not null comment '备注',

    `deleted`     tinyint      default 0                 not null comment '是否删除 (0否 1是)',
    `create_user` varchar(64)  default ''                not null comment '创建人',
    `update_user` varchar(64)  default ''                not null comment '修改人',
    `create_time` datetime     default current_timestamp not null comment '创建时间',
    `update_time` datetime     default current_timestamp not null on update current_timestamp comment '更新时间',

    primary key (`id`),
    unique uniq_sys_menu_parent_id_code (`parent_id`, `menu_code`),
    unique uniq_sys_menu_full_code (`full_code`),
    index idx_sys_menu_module (`module_id`)
)
    engine = InnoDB
    default charset = utf8
    collate = utf8_unicode_ci
    comment ='系统菜单';

-- 用户-角色
create table `dmp_sys_user_role`
(
    `user_id` int unsigned not null comment '用户ID',
    `role_id` int unsigned not null comment '角色ID',
    `expires` datetime     null comment '过期时间',
    primary key (`user_id`, `role_id`)
)
    engine = InnoDB
    default charset = utf8
    collate = utf8_unicode_ci
    comment ='用户-角色';

-- 菜单-权限
create table `dmp_sys_menu_permission`
(
    `menu_id`       int unsigned not null comment '菜单ID',
    `permission_id` int unsigned not null comment '权限ID',
    primary key (`menu_id`, `permission_id`)
)
    engine = InnoDB
    default charset = utf8
    collate = utf8_unicode_ci
    comment ='菜单-权限';

-- 操作者-模块
create table `dmp_sys_role_module`
(
    `operator_id`   int unsigned     not null comment '操作者ID',
    `operator_type` tinyint unsigned not null comment '操作者类型(1用户 2部门 3角色)',
    `module_id`     int unsigned     not null comment '模块ID',
    `expires`       datetime         null comment '过期时间',
    primary key (`operator_id`, `module_id`)
)
    engine = InnoDB
    default charset = utf8
    collate = utf8_unicode_ci
    comment ='操作者-模块';

-- 操作者-菜单
create table `dmp_sys_role_menu`
(
    `operator_id`   int unsigned     not null comment '操作者ID',
    `operator_type` tinyint unsigned not null comment '操作者类型(1用户 2部门 3角色)',
    `menu_id`       int unsigned     not null comment '菜单ID',
    `expires`       datetime         null comment '过期时间',
    primary key (`operator_id`, `menu_id`)
)
    engine = InnoDB
    default charset = utf8
    collate = utf8_unicode_ci
    comment ='操作者-菜单';

-- 操作者-权限
create table `dmp_sys_role_permission`
(
    `operator_id`   int unsigned     not null comment '操作者ID',
    `operator_type` tinyint unsigned not null comment '操作者类型(1用户 2部门 3角色)',
    `permission_id` int unsigned     not null comment '权限ID',
    `expires`       datetime         null comment '过期时间',
    primary key (`operator_id`, `permission_id`)
)
    engine = InnoDB
    default charset = utf8
    collate = utf8_unicode_ci
    comment ='操作者-权限';

-- 系统选项
create table `dmp_sys_option`
(
    `id`           int unsigned                            not null auto_increment comment '枚举ID',
    `parent_id`    int           default 0                 not null comment '父ID',
    `option_code`  varchar(64)                             not null comment '枚举编码',
    `option_value` varchar(64)                             not null comment '枚举值',
    `option_text`  varchar(64)   default ''                not null comment '展示文字',
    `full_code`    varchar(256)                            not null comment '完整枚举编码',
    `sort`         int           default 0                 not null comment '排序',
    `type`         tinyint       default 0                 not null comment '类型 (0分组 1选项)',
    `built_in`     tinyint       default 0                 not null comment '是否系统内置 (0否 1是)',
    `ext_info`     varchar(1024) default ''                not null comment '扩展信息',

    `deleted`      tinyint       default 0                 not null comment '是否删除 (0否 1是)',
    `create_user`  varchar(64)   default ''                not null comment '创建人',
    `update_user`  varchar(64)   default ''                not null comment '修改人',
    `create_time`  datetime      default current_timestamp not null comment '创建时间',
    `update_time`  datetime      default current_timestamp not null on update current_timestamp comment '更新时间',

    primary key (`id`),
    unique uniq_sys_option_code (`option_code`),
    unique uniq_sys_option_parent_id_value (`parent_id`, `option_value`),
    unique uniq_sys_option_full_code (`full_code`)
)
    engine = InnoDB
    default charset = utf8
    collate = utf8_unicode_ci
    comment ='系统选项';

-- 系统配置
create table `dmp_sys_config`
(
    `config_key`   varchar(64)                            not null comment '配置键',
    `config_value` text                                   null comment '配置值',
    `type`         tinyint      default 0                 not null comment '配置类型 (0后台配置 1页面配置)',
    `remark`       varchar(512) default ''                not null comment '备注',

    `deleted`      tinyint      default 0                 not null comment '是否删除 (0否 1是)',
    `create_user`  varchar(64)  default ''                not null comment '创建人',
    `update_user`  varchar(64)  default ''                not null comment '修改人',
    `create_time`  datetime     default current_timestamp not null comment '创建时间',
    `update_time`  datetime     default current_timestamp not null on update current_timestamp comment '更新时间',

    primary key (`config_key`)
)
    engine = InnoDB
    default charset = utf8
    collate = utf8_unicode_ci
    comment ='系统配置';

