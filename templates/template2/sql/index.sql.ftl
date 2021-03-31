insert into dmp_sys_permission(permission_id, parent_id, permission_code, permission_name, type)
values
<#list tableModels as table>
<#include "/properties.ftl">
<#assign index = (table?index) * 8>
       (${index + 1}, 0, '${tablePath}', '${tableComment}', 0)
       (${index + 2}, ${index + 1}, '${tablePath}:add', '添加${tableComment}', 1)
       (${index + 3}, ${index + 1}, '${tablePath}:del', '删除${tableComment}', 1)
       (${index + 4}, ${index + 1}, '${tablePath}:enable', '启用${tableComment}', 1)
       (${index + 5}, ${index + 1}, '${tablePath}:disable', '禁用${tableComment}', 1)
       (${index + 6}, ${index + 1}, '${tablePath}:update', '更新${tableComment}', 1)
       (${index + 7}, ${index + 1}, '${tablePath}:view', '查看${tableComment}', 1)
       (${index + 8}, ${index + 1}, '${tablePath}:export', '导出${tableComment}', 1)
</#list>
