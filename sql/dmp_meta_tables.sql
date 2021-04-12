create table dmp_meta_datasource
(
    `id`              int unsigned auto_increment comment '数据源ID' primary key,
    `datasource_name` varchar(64)                             not null comment '数据源名',
    `database_type`   int                                     not null comment '数据库类型(1hive 2mysql 3tidb)',
    `host`            varchar(128)                            not null comment '主机名',
    `port`            int                                     not null comment '端口号',
    `remark`          varchar(256)  default ''                not null comment '备注',
    `ext_info`        varchar(1024) default ''                not null comment '扩展信息',

    `deleted`         tinyint       default 0                 not null comment '是否删除',
    `create_user`     varchar(64)   default ''                not null comment '创建人',
    `create_username` varchar(64)   default ''                not null comment '创建人',
    `update_user`     varchar(64)   default ''                not null comment '修改人',
    `update_username` varchar(64)   default ''                not null comment '修改人',
    `create_time`     datetime      default current_timestamp not null comment '创建时间',
    `update_time`     datetime      default current_timestamp not null on update current_timestamp comment '更新时间',

    unique uniq_datasource_name (`datasource_name`),
    index idx_datasource_host (`host`)
)
    comment '数据源'
    engine = InnoDB
    default charset = utf8
    collate = utf8_unicode_ci;

create table dmp_meta_database_user
(
    `id`            int unsigned auto_increment comment '数据库ID' primary key,
    `datasource_id` int unsigned            not null comment '数据源ID',
    `username`      varchar(64)             not null comment '用户名',
    `password`      varchar(256) default '' not null comment '密码',
    `remark`        varchar(256) default '' not null comment '备注',

    unique uniq_database_username (`datasource_id`, `username`)
)
    comment '数据库用户'
    engine = InnoDB
    default charset = utf8
    collate = utf8_unicode_ci;

create table dmp_meta_database
(
    `id`              int unsigned auto_increment comment '数据库ID' primary key,
    `datasource_id`   int unsigned                            not null comment '数据源ID',
    `database_name`   varchar(64)                             not null comment '数据库名',
    `dept_id`         int unsigned                            not null comment '责任部门',
    `sync_type`       tinyint       default 0                 not null comment '同步方式 (0手动同步 10DMP元数据执行 20定时主动同步所有表 21定时主动同步元数据中的表 30通过回调同步)',
    `mark_only`       tinyint       default 0                 not null comment '是否仅标记',
    `remark`          varchar(256)  default ''                not null comment '备注',
    `ext_info`        varchar(1024) default ''                not null comment '扩展信息',

    `deleted`         tinyint       default 0                 not null comment '是否删除',
    `create_user`     varchar(64)   default ''                not null comment '创建人',
    `create_username` varchar(64)   default ''                not null comment '创建人',
    `update_user`     varchar(64)   default ''                not null comment '修改人',
    `update_username` varchar(64)   default ''                not null comment '修改人',
    `create_time`     datetime      default current_timestamp not null comment '创建时间',
    `update_time`     datetime      default current_timestamp not null on update current_timestamp comment '更新时间',

    unique uniq_database_name (`datasource_id`, `database_name`)
)
    comment '数据库'
    engine = InnoDB
    default charset = utf8
    collate = utf8_unicode_ci;

create table dmp_meta_mapping_database
(
    `id`                    int unsigned auto_increment comment '映射数据库ID' primary key,
    `database_user_id`      int                                     not null comment '数据库用户ID',
    `database_id`           int                                     not null comment '数据库ID',
    `mapping_database_name` varchar(64)                             not null comment '使用库名',
    `database_comment`      varchar(256)  default ''                not null comment '数据库注释',
    `max_privilege`         tinyint       default 0                 not null comment '最大权限 (0只读 1读写 2DDL)',
    `params`                varchar(256)  default ''                not null comment '连接参数',
    `remark`                varchar(256)  default ''                not null comment '备注',
    `ext_info`              varchar(1024) default ''                not null comment '扩展信息',

    `deleted`               tinyint       default 0                 not null comment '是否删除',
    `create_user`           varchar(64)   default ''                not null comment '创建人',
    `create_username`       varchar(64)   default ''                not null comment '创建人',
    `update_user`           varchar(64)   default ''                not null comment '修改人',
    `update_username`       varchar(64)   default ''                not null comment '修改人',
    `create_time`           datetime      default current_timestamp not null comment '创建时间',
    `update_time`           datetime      default current_timestamp not null on update current_timestamp comment '更新时间',

    unique uniq_mapping_database_name (`mapping_database_name`),
    index idx_mapping_database_user_id (`database_user_id`),
    index idx_mapping_database_database_id (`database_id`)
)
    comment '映射数据库'
    engine = InnoDB
    default charset = utf8
    collate = utf8_unicode_ci;

create table dmp_meta_classification
(
    `id`              int unsigned auto_increment comment '分类ID' primary key,
    `parent_id`       int unsigned  default 0                 not null comment '父分类ID',
    `code`            varchar(32)                             not null comment '分类编码',
    `name`            varchar(64)                             not null comment '分类名称',
    `full_code`       varchar(128)                            not null comment '完整分类编码',
    `type`            tinyint unsigned                        not null comment '类型(0分组 1主题 2集市)',
    `remark`          varchar(256)  default ''                not null comment '备注',
    `ext_info`        varchar(1024) default ''                not null comment '扩展信息',

    `deleted`         tinyint       default 0                 not null comment '是否删除',
    `create_user`     varchar(64)   default ''                not null comment '创建人',
    `create_username` varchar(64)   default ''                not null comment '创建人',
    `update_user`     varchar(64)   default ''                not null comment '修改人',
    `update_username` varchar(64)   default ''                not null comment '修改人',
    `create_time`     datetime      default current_timestamp not null comment '创建时间',
    `update_time`     datetime      default current_timestamp not null on update current_timestamp comment '更新时间',

    unique uniq_classification_parent_id_code (`parent_id`, `code`),
    index idx_classification_code (`code`),
    index idx_classification_full_code (`full_code`)
)
    comment '表分类'
    engine = InnoDB
    default charset = utf8
    collate = utf8_unicode_ci;

create table dmp_meta_table
(
    `id`                  int unsigned auto_increment comment '表ID' primary key,
    `mapping_database_id` int unsigned                            not null comment '数据库ID',
    `table_uuid`          char(32)                                not null comment '表唯一标识',
    `table_name`          varchar(64)                             not null comment '表名',
    `table_comment`       varchar(256)  default ''                not null comment '表注释',
    `properties`          varchar(1024) default ''                not null comment '表信息',
    `sync_mode`           tinyint       default 0                 not null comment '同步方式 (0实时 1小时增量 2小时全量 3日增量 4日全量 5周增量 6周全量)',
    `owner`               varchar(64)   default ''                not null comment '负责人',
    `temporary`           tinyint       default 0                 not null comment '是否临时表 (0否 1是)',
    `expires`             datetime                                null comment '过期时间',
    `internal`            tinyint       default 0                 not null comment '是否内部表 (0否 1是)',
    `publish_version`     bigint                                  not null comment '发布版本',
    `remark`              varchar(256)  default ''                not null comment '备注',
    `ext_info`            varchar(1024) default ''                not null comment '扩展信息',

    `deleted`             tinyint       default 0                 not null comment '是否删除',
    `create_user`         varchar(64)   default ''                not null comment '创建人',
    `create_username`     varchar(64)   default ''                not null comment '创建人',
    `update_user`         varchar(64)   default ''                not null comment '修改人',
    `update_username`     varchar(64)   default ''                not null comment '修改人',
    `create_time`         datetime      default current_timestamp not null comment '创建时间',
    `update_time`         datetime      default current_timestamp not null on update current_timestamp comment '更新时间',

    unique uniq_table_mapping_database_table_env (`mapping_database_id`, `table_name`),
    index idx_table_name (`table_name`),
    index idx_table_owner (`owner`)
)
    comment '表'
    engine = InnoDB
    default charset = utf8
    collate = utf8_unicode_ci;

create table dmp_meta_table_history
(
    `id`              int unsigned comment '日志ID' primary key,
    `table_id`        int unsigned                          not null comment '表ID',
    `table_structure` mediumtext                            null comment '表结构 (json，包含字段列表信息，索引信息等)',
    `create_user`     varchar(64) default ''                not null comment '操作人',
    `create_username` varchar(64) default ''                not null comment '操作人',
    `create_time`     datetime    default current_timestamp not null comment '创建时间',

    index idx_table_change_history_table_id (`table_id`)
)
    comment '表操作历史'
    engine = InnoDB
    default charset = utf8
    collate = utf8_unicode_ci;

create table dmp_meta_table_classification
(
    `table_id`          int unsigned      not null comment '表ID',
    `classification_id` int unsigned      not null comment '分类ID',
    `main`              tinyint default 0 not null comment '是否主体',

    primary key (`table_id`, `classification_id`)
)
    comment '表分类'
    engine = InnoDB
    default charset = utf8
    collate = utf8_unicode_ci;


create table dmp_meta_table_column
(
    `id`               int unsigned auto_increment comment '字段ID' primary key,
    `table_id`         int unsigned             not null comment '表ID',
    `column_name`      varchar(64)              not null comment '字段名',
    `data_type`        varchar(32)              not null comment '字段类型',
    `partition_column` tinyint       default 0  not null comment '是否分区字段(0否 1是)',
    `auto_increment`   tinyint       default 0  not null comment '是否自增字段(0否 1是)',
    `column_comment`   varchar(4096) default '' not null comment '字段注释',
    `default_value`    varchar(32)   default '' not null comment '默认值',
    `nullable`         tinyint       default 0  not null comment '是否可空(0否 1是)',
    `column_position`  int           default 0  not null comment '字段位置',
    `private_level`    tinyint       default 1  not null comment '敏感级别',
    `encrypted`        tinyint       default 0  not null comment '是否加密',
    `publish_version`  bigint                   not null comment '发布版本',
    `remark`           varchar(256)  default '' not null comment '备注',
    `ext_info`         varchar(1024) default '' not null comment '扩展信息',

    unique uniq_column_table_id_column_name (`table_id`, `column_name`),
    index idx_column_name (`column_name`)
)
    comment '字段'
    engine = InnoDB
    default charset = utf8
    collate = utf8_unicode_ci;

create table dmp_meta_table_key
(
    `id`              int unsigned auto_increment comment '索引ID' primary key,
    `table_id`        int unsigned             not null comment '表ID',
    `key_name`        varchar(64)              not null comment '索引名',
    `columns`         varchar(128)             not null comment '包含的字段',
    `type`            tinyint       default 0  not null comment '索引类型(0index 1unique 2primary key)',
    `remark`          varchar(256)  default '' not null comment '备注',
    `ext_info`        varchar(1024) default '' not null comment '扩展信息',
    `publish_version` bigint                   not null comment '发布版本',

    unique uniq_key_table_id_key_name_env (`table_id`, `key_name`)
)
    comment '索引'
    engine = InnoDB
    default charset = utf8
    collate = utf8_unicode_ci;

create table dmp_meta_table_relation
(
    `table_from` int unsigned not null comment '表来源',
    `table_to`   int unsigned not null comment '表去向',

    primary key (`table_from`, `table_to`)
)
    comment '表关系'
    engine = InnoDB
    default charset = utf8
    collate = utf8_unicode_ci;

create table dmp_meta_table_column_relation
(
    `column_from` int unsigned not null comment '字段来源',
    `column_to`   int unsigned not null comment '字段去向',

    primary key (`column_from`, `column_to`)
)
    comment '字段关系'
    engine = InnoDB
    default charset = utf8
    collate = utf8_unicode_ci;


# 权限 13 需要操作的数据库有执行DDL的权限
create table `dmp_meta_operator_privilege_database`
(
    `operator_id`     int unsigned     not null comment '操作者ID',
    `operator_type`   tinyint unsigned not null comment '操作者类型(1用户 2部门 3角色)',
    `database_id`     int unsigned     not null comment '数据库ID',
    `privilege_value` tinyint unsigned not null comment '权限值 (库: 0只读 1编辑 2删除, 库中表: 10新建 11编辑 12删除 13保存到数据库)',
    `expires`         datetime         null comment '过期时间',

    primary key (`operator_id`, `operator_type`, `database_id`, `privilege_value`),
    index idx_meta_role_privilege_database_data_id (`database_id`)
)
    comment ='数据库操作权限'
    engine = InnoDB
    default charset = utf8
    collate = utf8_unicode_ci;

# 权限 13 需要操作的数据库有执行DDL的权限
create table `dmp_meta_operator_privilege_table`
(
    `operator_id`     int unsigned     not null comment '操作者ID',
    `operator_type`   tinyint unsigned not null comment '操作者类型(1用户 2部门 3角色)',
    `table_id`        int unsigned     not null comment '表ID',
    `privilege_value` tinyint unsigned not null comment '权限值 (11编辑 12删除 13保存到数据库)',
    `expires`         datetime         null comment '过期时间',

    primary key (`operator_id`, `operator_type`, `table_id`, `privilege_value`),
    index idx_meta_role_privilege_table_data_id (`table_id`)
)
    comment ='表操作权限'
    engine = InnoDB
    default charset = utf8
    collate = utf8_unicode_ci;

# 表访问权限
create table `dmp_meta_access_privilege_table`
(
    `operator_id`         int unsigned     not null comment '操作者ID',
    `operator_type`       tinyint unsigned not null comment '操作者类型(1用户 2部门 3角色)',
    `mapping_database_id` int unsigned     not null comment '映射数据库ID',
    `table_id`            int unsigned     not null comment '表ID (0代表库下所有表)',
    `privilege_value`     tinyint unsigned not null comment '权限值 (0只读 1读写)',
    `expires`             datetime         null comment '过期时间',

    primary key (`operator_id`, `operator_type`, `table_id`, `privilege_value`),
    index idx_meta_role_privilege_table_data_id (`table_id`)
)
    comment ='表访问权限'
    engine = InnoDB
    default charset = utf8
    collate = utf8_unicode_ci;

# 查询有权限的数据库
SELECT d.*
FROM `dmp_meta_database` d
         JOIN (
    SELECT `database_id`,
           GROUP_CONCAT(DISTINCT `privilege_value`)
    FROM `dmp_meta_operator_privilege_database`
    WHERE ((`operator_id` = ${user_id} AND `operator_type` = 1)
        OR (`operator_id` = ${dept_id} AND `operator_type` = 2)
        OR (`operator_id` IN ${role_ids} AND `operator_type` = 3))
      AND `privilege_value` IN ${privilege_values}
      AND (`expires` IS NULL OR `expires` > now())
    GROUP BY `database_id`
) opd ON d.`id` = opd.`database_id`;

# 查询有权限的表
SELECT t.*
FROM `dmp_meta_table` t
         JOIN (
    SELECT `table_id`,
           GROUP_CONCAT(DISTINCT `privilege_value`)
    FROM `dmp_meta_operator_privilege_table`
    WHERE ((`operator_id` = ${user_id} AND `operator_type` = 1)
        OR (`operator_id` = ${dept_id} AND `operator_type` = 2)
        OR (`operator_id` IN ${role_ids} AND `operator_type` = 3))
      AND `privilege_value` IN ${privilege_values}
      AND (`expires` IS NULL OR `expires` > now())
    GROUP BY `table_id`
) opd ON t.`id` = opd.`table_id`;

# 查询有权限数据库的表
SELECT t.*
FROM `dmp_meta_table` t
         JOIN (
    SELECT `database_id`,
           GROUP_CONCAT(DISTINCT `privilege_value`)
    FROM `dmp_meta_operator_privilege_database`
    WHERE ((`operator_id` = ${user_id} AND `operator_type` = 1)
        OR (`operator_id` = ${dept_id} AND `operator_type` = 2)
        OR (`operator_id` IN ${role_ids} AND `operator_type` = 3))
      AND `privilege_value` IN ${privilege_values}
      AND (`expires` IS NULL OR `expires` > now())
    GROUP BY `database_id`
) opd ON t.`database_id` = opd.`database_id`;