# MysqlCodeGenerator
mysql code generator

模版引擎采用freemarker
公共属性在commonProperties
其中other map object
包括
```
"now", new Date(),
"dbUrl", 数据库连接串,
"dbUsername", 数据库连接用户名,
"dbPassword", 数据库连接密码,
"validStatusField", 有效字段（类型是ValidStatusField）,
commonProperties配置的map
```
对于表，页面model为
```
{
    table: object,
    other map object
}
```
对于基本文件，页面model为
```
{
    other map object
}
```
对于聚合文件，页面model为
```
{
    tableModels: table对象列表,
    other map object
}
```

## TableModel

|FieldName                            |FieldRemark                |type                  |example
|-------------------------------------|---------------------------|----------------------|--------------------
|databaseName                         |数据库名                   |String                |user
|tableName                            |表名                       |String                |user_type_rela
|tableComment                         |表说明                     |String                |用户类型关系表
|validStatusField                     |配置的有效性字段           |ValidStatusField      |
|columns                              |表字段列表                 |String                |
|statistics                           |索引列表                   |String                |
|tableClassName                       |表对应的JAVA类名           |String                |UserTypeRela
|tableClassNameFirstLower             |表对应的JAVA类名首字母小写 |String                |userTypeRela
|hasPrimaryKey                        |是否有主键                 |Boolean               |
|primaryKey                           |主键列表                   |List<ColumnModel>     |
|hasAutoIncrementUniquePrimaryKey     |是否有唯一自增主键         |Boolean               |
|uniquePrimaryKey                     |获取唯一主键               |ColumnModel           |
|primaryKeyParameters                 |主键对应的JAVA参数         |String                |单个(Integer id)，多个(Integer userId, Integer userTypeId)
|primaryKeyParameterValues            |主键对应的JAVA参数值       |String                |单个(id) ，多个(userId, userTypeId)
|primaryKeyParameters                 |旧主键对应的JAVA参数       |String                |单个(Integer oldId)，多个(Integer oldUserId, Integer oldUserTypeId)
|primaryKeyParameterValues            |旧主键对应的JAVA参数值     |String                |单个(oldId) ，多个(oldUserId, oldUserTypeId)
|validStatusColumn                    |有效性字段                 |ColumnModel           |

## ColumnModel

|FieldName                    |FieldRemark               |type                  |example
|-----------------------------|--------------------------|----------------------|-----------------
|databaseName                 |数据库名                  |String                |user
|tableName                    |表名                      |String                |user_type_rela
|columnName                   |列名                      |String                |user_id
|columnPosition               |列位置                    |Long                  |1
|columnDefault                |默认值                    |String                |0
|isNullable                   |是否为空                  |String                |NO
|dataType                     |数据类型                  |String                |int
|charLength                   |字符长度                  |Long                  |32
|byteLength                   |字节长度                  |Long                  |96
|columnType                   |列类型                    |String                |int(11)
|columnKey                    |                          |String                |PRI
|extra                        |                          |String                |auto_increment
|columnComment                |列说明                    |String                |用户ID
|index                        |是否是索引                |Boolean               |
|columnFieldName              |对应的JAVA字段名          |String                |UserId
|columnFieldType              |对应的JAVA字段类型        |String                |Integer
|fkSelectField                |逻辑外键选择项            |Boolean               |
|fkSelect                     |是否是逻辑外键选择项      |Boolean               |
|autoIncrement                |是否自增长                |Boolean               |
|primaryKey                   |是否是主键                |Boolean               |
|validStatus                  |是否是有效性字段          |Boolean               |
|select                       |是否是选择项              |Boolean               |
|notRequired                  |是否不必须的              |Boolean               |
|imgUrl                       |是否是图片url             |Boolean               |
|videoUrl                     |是否是视频url             |Boolean               |
|docUrl                       |是否文档url               |Boolean               |
|pageUrl                      |是否页面url               |Boolean               |
|otherUrl                     |是否是其他url             |Boolean               |
|url                          |是否是url                 |Boolean               |
|content                      |是否是内容字段            |Boolean               |
|ignoreSearch                 |是否忽略查询              |Boolean               |


