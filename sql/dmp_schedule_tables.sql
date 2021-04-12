create table dmp_schedule_project
(
    `id`                 int unsigned auto_increment comment '项目ID' primary key,
    `project_code`       varchar(64)              not null comment '项目编码(Azkaban项目名)',
    `project_name`       varchar(64)              not null comment '项目名称',
    `schedule_time_type` tinyint       default 0  not null comment '调度时间类型(0 天, 1 小时)',
    `dept_id`            int unsigned             not null comment '责任部门',
    `azkaban_project_id` int unsigned  default 0  not null comment 'Azkaban项目ID',
    `remark`             varchar(256)  default '' not null comment '备注',
    `ext_info`           varchar(1024) default '' not null comment '扩展信息',

    unique uniq_schedule_project_code (`project_code`),
    index idx_schedule_project_azkaban (`azkaban_project_id`),
    index idx_schedule_project_name (`project_name`)
) comment '调度项目';

create table dmp_schedule_type
(
    `id`        int unsigned auto_increment comment '调度类型ID' primary key,
    `type_code` varchar(64)              not null comment '调度类型编码',
    `type_name` varchar(64)              not null comment '调度类型名称',
    `icon`      varchar(256)  default '' not null comment '图标',
    `color`     varchar(32)   default '' not null comment '颜色',
    `sort`      int           default 0  not null comment '排序',
    `ext_info`  varchar(1024) default '' not null comment '扩展信息',

    unique uniq_schedule_type_code (`type_code`),
    index idx_schedule_type_name (`type_name`)
) comment '调度类型';

create table dmp_schedule_job
(
    `id`               int unsigned auto_increment comment '作业ID' primary key,
    `job_key`          char(32)                 not null comment '作业键(uuid，自动生成的唯一键)',
    `job_code`         varchar(64)              not null comment '作业编码(Azkaban作业名)',
    `job_name`         varchar(64)              not null comment '作业名称',
    `project_id`       int unsigned             not null comment '项目ID',
    `job_type`         tinyint       default 1  not null comment '作业类型 (1 command, 2 flow)',
    `flow_id`          int unsigned  default 0  not null comment 'Flow ID',
    `schedule_type_id` int unsigned             not null comment '调度类型ID',
    `strategy`         tinyint       default 10 not null comment '失败策略 (10 失败中止, 20 失败重试, 30 失败忽略, 40 成功为空中止, 50 成功为空重试, 60 成功为空忽略)',
    `owner`            varchar(64)   default '' not null comment '作业所有人',
    `retry_times`      tinyint       default 0  not null comment '重试次数',
    `retry_interval`   tinyint       default 0  not null comment '重试间隔(秒)',
    `publish_version`  bigint        default 0  not null comment '发布版本',
    `remark`           varchar(256)  default '' not null comment '备注',
    `ext_info`         varchar(1024) default '' not null comment '扩展信息',

    unique uniq_schedule_project_job (`project_id`, `job_code`),
    index idx_schedule_job_code (`job_key`),
    index idx_schedule_job_code (`job_code`),
    index idx_schedule_job_name (`job_name`),
    index idx_schedule_job_flow (`flow_id`),
    index idx_schedule_job_type (`schedule_type_id`),
    index idx_schedule_job_owner (`owner`)
) comment '调度作业';

create table dmp_schedule_job_alarm
(
    `job_id`         int unsigned             not null comment '调度作业ID' primary key,
    `user_ids`       varchar(128)             not null comment '收件人ID (多个英文逗号隔开)',
    `fail_alarm`     tinyint       default 0  not null comment '失败报警',
    `empty_alarm`    tinyint       default 0  not null comment '为空报警',
    `overtime_alarm` tinyint       default 0  not null comment '超时报警',
    `timeout`        int           default 0  not null comment '超时时长 (分钟)',
    `send_email`     tinyint       default 0  not null comment '是否发送邮件',
    `send_im`        tinyint       default 0  not null comment '是否发送程序通知',
    `send_vms`       tinyint       default 0  not null comment '是否发送语音短信',
    `ext_info`       varchar(1024) default '' not null comment '扩展信息'
)
    engine = InnoDB
    default charset = utf8
    collate = utf8_unicode_ci
    comment '作业报警';

create table dmp_schedule_job_config
(
    `job_id` int unsigned not null comment '调度作业ID' primary key,
    `config` mediumtext   null comment '作业配置'
) comment '调度作业配置';

create table dmp_schedule_job_relation
(
    `job_from` int unsigned not null comment '来源',
    `job_to`   int unsigned not null comment '目标',
    primary key (`job_from`, `job_to`)
) comment '调度作业依赖';

drop table if exists dmp_schedule_job_exec_history;
create table dmp_schedule_job_exec_history
(
    `id`           int unsigned auto_increment comment '调度历史ID' primary key,
    `job_id`       int unsigned                       not null comment '调度作业ID',
    `env`          int unsigned                       not null comment '环境',
    `execute_date` date                               not null comment '执行日期',
    `execute_hour` tinyint  default 23                not null comment '执行小时',
    `submit_time`  datetime default current_timestamp not null comment '任务提交时间',
    `start_time`   datetime                           not null comment '开始执行时间',
    `finish_time`  datetime                           null comment '执行完成时间',
    `status`       tinyint  default 10                not null comment '状态 (10 正在执行, 20 执行成功, 21 成功为空, 90 执行失败)',

    unique uniq_schedule_history_date_hour (`job_id`, `execute_date`, `execute_hour`, env)
)
    engine = InnoDB
    default charset = utf8
    collate = utf8_unicode_ci
    comment '调度作业执行历史';

drop table if exists dmp_schedule_execution;
create table dmp_schedule_execution
(
    `exec_id`          int                                     not null comment 'Azkaban执行ID',
    `env`              int unsigned                            not null comment '环境',
    `project_id`       int                                     not null comment '调度项目ID',
    `flow_id`          int           default 0                 not null comment 'Flow ID (默认0 finish)',
    `params`           varchar(1024) default ''                not null comment '执行参数JSON',

    `execute_user`     varchar(64)   default ''                not null comment '执行人',
    `execute_username` varchar(64)   default ''                not null comment '执行人',
    `execute_time`     datetime      default CURRENT_TIMESTAMP not null comment '执行时间',

    primary key (exec_id, env),
    index idx_execution_project_id (project_id),
    index idx_execution_flow (flow_id),
    index idx_execution_env (env)
)
    engine = InnoDB
    default charset = utf8
    collate = utf8_unicode_ci
    comment '调度执行';

create table `dmp_schedule_watchkeeper`
(
    `id`              int unsigned auto_increment comment '值班ID' primary key,
    `user_id`         int unsigned                          not null comment '用户ID',
    `start_time`      datetime                              not null comment '开始时间',
    `end_time`        datetime                              not null comment '结束时间',

    `deleted`         tinyint     default 0                 not null comment '是否删除(0否, 1是)',
    `create_user`     varchar(64) default ''                not null comment '创建人',
    `create_username` varchar(64) default ''                not null comment '创建人',
    `update_user`     varchar(64) default ''                not null comment '修改人',
    `update_username` varchar(64) default ''                not null comment '修改人',
    `create_time`     datetime    default current_timestamp not null comment '创建时间',
    `update_time`     datetime    default current_timestamp not null on update current_timestamp comment '更新时间',

    index idx_schedule_watchkeeper_user_id (`user_id`),
    index idx_schedule_watchkeeper_start_time (`start_time`),
    index idx_schedule_watchkeeper_end_time (`end_time`)
)
    engine = InnoDB
    default charset = utf8
    collate = utf8_unicode_ci
    comment ='调度值班人';


create table `dmp_schedule_operator_privilege_project`
(
    `operator_id`     int unsigned     not null comment '操作者ID',
    `operator_type`   tinyint unsigned not null comment '操作者类型(1用户 2部门 3角色)',
    `project_id`      int unsigned     not null comment '调度项目ID',
    `privilege_value` tinyint unsigned not null comment '权限值 (0可读 1可编辑 2可执行 3可配置调度)',
    `expires`         datetime         null comment '过期时间',

    primary key (`operator_id`, `operator_type`, `project_id`, `privilege_value`),
    index idx_meta_role_privilege_database_data_id (`project_id`)
) comment ='调度项目权限';


SELECT p.`id`,
       p.`project_code`,
       p.`project_name`,
       p.`schedule_time_type`,
       p.`owner`,
       p.`azkaban_project_id`,
       p.`remark`,
       p.`ext_info`
FROM `dmp_schedule_project` p
         JOIN (
    SELECT `project_id`,
           GROUP_CONCAT(DISTINCT `privilege_value`)
    FROM `dmp_schedule_operator_privilege_project`
    WHERE ((`operator_id` = ${user_id} AND `operator_type` = 1)
        OR (`operator_id` = ${dept_id} AND `operator_type` = 2)
        OR (`operator_id` IN ${role_ids} AND `operator_type` = 3))
      AND (`expires` IS NULL OR `expires` > now())
    GROUP BY `project_id`
) opp ON p.`id` = opp.`project_id`;


insert into dmp_schedule_type(id, type_code, type_name, icon, color, sort)
values (1, 'dataflow', 'dataflow', '', 'rgb(100, 192, 187)', 1),
       (2, 'datax_import', 'datax导入', '', 'rgb(126, 124, 199)', 2),
       (3, 'datax_export', 'datax导出', '', 'rgb(139, 187, 115)', 3),
       (4, 'shell_spark', 'spark', '', 'rgb(197, 157, 219)', 4),
       (5, 'shell_hive', 'hive', '', 'rgb(100, 161, 216)', 5),
       (6, 'shell_mysql', 'mysql', '', 'rgb(145, 153, 190)', 6),
       (7, 'shell_java', 'java', '', 'rgb(166, 156, 147)', 7),
       (8, 'shell_python', 'python', '', 'rgb(65, 127, 177)', 8),
       (9, 'shell_custom', 'shell', '', 'rgb(245, 108, 108)', 9),
       (10, 'ares_email', 'email', '', 'rgb(232, 165, 241)', 10);