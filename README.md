# MysqlCodeGenerator

mysql代码生成器，示例有dubbo，spring-boot，spring-cloud三套模版，模版页面采用bootstrap V4布局

## 使用方式

下载源码，使用maven打包，并将jar包重命名为codegen.jar，复制到对应模版目录下（codegen.bat/codegen.sh同级目录）

```
$ mvn package -Dmaven.test.skip=true
$ mv target/mysql-code-generator-0.0.1-SNAPSHOT.jar ./dubbo/codegen.jar
```

修改对应的yml文件的数据库连接及其他设置，然后运行codegen.bat/codegen.sh，根据提示生成代码，一般gen base，gen aggregate，gen *即可

示例配置对应的sql为/sql/create_tables.sql

## 模版说明

模版引擎采用freemarker

公共属性在commonProperties，其中other map object包括
```
"now": new Date(),
"dbUrl": 数据库连接串,
"dbUsername": 数据库连接用户名,
"dbPassword": 数据库连接密码,
"validStatusField": 有效字段（类型是ValidStatusField）,
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

### TableModel

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
|validStatusColumn                    |有效性字段                 |ColumnModel           |

### ColumnModel

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
|fkSelectField                |逻辑外键选择项            |FkSelectField         |
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

