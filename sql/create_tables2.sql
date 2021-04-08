create table dmp_sys_config
(
    `key`       varchar(64)                            not null comment '配置键'
        primary key,
    value       text                                   null comment '配置值',
    type        tinyint      default 0                 not null comment '配置类型 (0后台配置,1页面配置)',
    remark      varchar(512) default ''                not null comment '备注',
    deleted     tinyint      default 0                 not null comment '是否删除',
    create_user varchar(64)  default ''                not null comment '创建人',
    update_user varchar(64)  default ''                not null comment '修改人',
    create_time datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '系统配置' collate = utf8_unicode_ci;

create table dmp_sys_data_role
(
    id          int unsigned auto_increment comment '角色ID'
        primary key,
    role_code   varchar(64)                             not null comment '数据角色编码',
    role_name   varchar(64)                             not null comment '数据角色名称',
    remark      varchar(256)  default ''                not null comment '备注',
    ext_info    varchar(1024) default ''                not null comment '扩展信息',
    deleted     tinyint       default 0                 not null comment '是否删除(0否, 1是)',
    create_user varchar(64)   default ''                not null comment '创建人',
    update_user varchar(64)   default ''                not null comment '修改人',
    create_time datetime      default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime      default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uniq_data_role_code_module
        unique (role_code),
    constraint uniq_data_role_name_module
        unique (role_name)
)
    comment '数据角色' collate = utf8_unicode_ci;

create table dmp_sys_menu
(
    id          int unsigned auto_increment comment '菜单ID'
        primary key,
    module_id   int          default 0                 not null comment '模块ID',
    parent_id   int          default 0                 not null comment '父菜单ID，一级菜单为0',
    menu_code   varchar(64)  default ''                not null comment '菜单编码',
    menu_name   varchar(64)                            not null comment '菜单名称',
    type        tinyint      default 0                 not null comment '类型 (0目录, 1菜单)',
    path        varchar(256) default ''                not null comment '菜单路径',
    path_type   tinyint      default 0                 not null comment '路径类型(0vue路由, 1相对路径, 2绝对路径)',
    icon        varchar(64)  default ''                not null comment '菜单图标',
    icon_type   tinyint      default 0                 not null comment '图标类型',
    sort        int          default 0                 not null comment '排序',
    remark      varchar(128) default ''                not null comment '备注',
    deleted     tinyint      default 0                 not null comment '是否删除',
    create_user varchar(64)  default ''                not null comment '创建人',
    update_user varchar(64)  default ''                not null comment '修改人',
    create_time datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uniq_sys_menu_parent_id_code
        unique (parent_id, menu_code)
)
    comment '系统菜单' collate = utf8_unicode_ci;

create index idx_sys_menu_module
    on dmp_sys_menu (module_id);

create index idx_sys_menu_sort
    on dmp_sys_menu (sort);

create table dmp_sys_menu_permission
(
    menu_id       int unsigned not null comment '菜单ID',
    permission_id int unsigned not null comment '权限ID',
    primary key (menu_id, permission_id)
)
    comment '菜单-权限' collate = utf8_unicode_ci;

create table dmp_sys_module
(
    id          int unsigned auto_increment comment '模块ID'
        primary key,
    module_code varchar(64)                            not null comment '模块编码',
    module_name varchar(64)                            not null comment '模块名称',
    url         varchar(256) default ''                not null comment '模块URL',
    icon        varchar(64)  default ''                not null comment '菜单图标',
    icon_type   tinyint      default 0                 not null comment '图标类型',
    remark      varchar(128) default ''                not null comment '备注',
    deleted     tinyint      default 0                 not null comment '是否删除',
    create_user varchar(64)  default ''                not null comment '创建人',
    update_user varchar(64)  default ''                not null comment '修改人',
    create_time datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uniq_sys_module_code
        unique (module_code)
)
    comment '系统模块' collate = utf8_unicode_ci;

create table dmp_sys_option
(
    id          int unsigned auto_increment comment '枚举ID'
        primary key,
    option_code varchar(128)                            not null comment '编码',
    value       varchar(64)                             not null comment '值',
    text        varchar(64)   default ''                not null comment '展示文字',
    sort        int           default 0                 not null comment '排序',
    parent_id   int           default 0                 not null comment '父ID',
    type        tinyint       default 0                 not null comment '类型 (0分组,1选项)',
    built_in    tinyint       default 0                 not null comment '是否系统内置 (0否,1是)',
    ext_info    varchar(1024) default ''                not null comment '扩展信息',
    deleted     tinyint       default 0                 not null comment '是否删除',
    create_user varchar(64)   default ''                not null comment '创建人',
    update_user varchar(64)   default ''                not null comment '修改人',
    create_time datetime      default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime      default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uniq_sys_option_code
        unique (option_code),
    constraint uniq_sys_option_parent_id_value
        unique (parent_id, value)
)
    comment '系统选项' collate = utf8_unicode_ci;

create table dmp_sys_permission
(
    id              int unsigned auto_increment comment '权限ID'
        primary key,
    module_id       int unsigned                           not null comment '模块ID',
    parent_id       int          default 0                 not null comment '分组ID，一级分组/权限为0',
    permission_code varchar(64)                            not null comment '权限编码',
    permission_name varchar(64)                            not null comment '权限名称',
    type            tinyint      default 0                 not null comment '类型 (0权限分组,1权限)',
    remark          varchar(128) default ''                not null comment '备注',
    deleted         tinyint      default 0                 not null comment '是否删除',
    create_user     varchar(64)  default ''                not null comment '创建人',
    update_user     varchar(64)  default ''                not null comment '修改人',
    create_time     datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time     datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uniq_sys_permission_parent_id_code
        unique (parent_id, permission_code)
)
    comment '系统权限' collate = utf8_unicode_ci;

create index idx_sys_permission_module
    on dmp_sys_permission (module_id);

create table dmp_sys_role
(
    id          int unsigned auto_increment comment '角色ID'
        primary key,
    role_code   varchar(64)                            not null comment '角色编码',
    role_name   varchar(64)                            not null comment '角色名称',
    built_in    int          default 0                 not null comment '是否内置角色 (0否, 1是)',
    remark      varchar(128) default ''                not null comment '备注',
    deleted     tinyint      default 0                 not null comment '是否删除',
    create_user varchar(64)  default ''                not null comment '创建人',
    update_user varchar(64)  default ''                not null comment '修改人',
    create_time datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uniq_sys_role_code
        unique (role_code)
)
    comment '系统角色' collate = utf8_unicode_ci;

create table dmp_sys_role_menu
(
    role_id int unsigned not null comment '角色ID',
    menu_id int unsigned not null comment '菜单ID',
    expires datetime     null comment '过期时间',
    primary key (role_id, menu_id)
)
    comment '角色-菜单' collate = utf8_unicode_ci;

create table dmp_sys_role_module
(
    role_id   int unsigned not null comment '角色ID',
    module_id int unsigned not null comment '模块ID',
    expires   datetime     null comment '过期时间',
    primary key (role_id, module_id)
)
    comment '角色-模块' collate = utf8_unicode_ci;

create table dmp_sys_role_permission
(
    role_id       int unsigned not null comment '角色ID',
    permission_id int unsigned not null comment '权限ID',
    expires       datetime     null comment '过期时间',
    primary key (role_id, permission_id)
)
    comment '角色-权限' collate = utf8_unicode_ci;

create table dmp_sys_user
(
    id          int unsigned                          not null comment '用户ID'
        primary key,
    job_number  varchar(16)                           not null comment '工号',
    username    varchar(64)                           not null comment '用户名',
    real_name   varchar(16)                           not null comment '姓名',
    email       varchar(64)                           not null comment '邮箱',
    mobile      varchar(16)                           not null comment '手机号',
    deleted     tinyint     default 0                 not null comment '是否删除',
    create_user varchar(64) default ''                not null comment '创建人',
    update_user varchar(64) default ''                not null comment '修改人',
    create_time datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '系统用户' collate = utf8_unicode_ci;

create index idx_sys_user_email
    on dmp_sys_user (email);

create index idx_sys_user_job_number
    on dmp_sys_user (job_number);

create index idx_sys_user_mobile
    on dmp_sys_user (mobile);

create index idx_sys_user_real_name
    on dmp_sys_user (real_name);

create index idx_sys_user_username
    on dmp_sys_user (username);

create table dmp_sys_user_data_role
(
    user_id      int               not null comment '用户ID',
    data_role_id int unsigned      not null comment '数据角色ID',
    main_role    tinyint default 0 not null comment '是否主体角色(0否, 1是)',
    expires      datetime          null comment '过期时间',
    primary key (user_id, data_role_id)
)
    comment '用户-数据角色' collate = utf8_unicode_ci;

create table dmp_sys_user_role
(
    user_id int unsigned not null comment '用户ID',
    role_id int unsigned not null comment '角色ID',
    expires datetime     null comment '过期时间',
    primary key (user_id, role_id)
)
    comment '用户-角色' collate = utf8_unicode_ci;

