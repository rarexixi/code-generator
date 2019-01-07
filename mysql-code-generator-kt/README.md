# MySqlCodeGenerator

mysql代码生成器

## 使用方式

下载源码，使用maven打包，并将jar包重命名为codegen.jar，复制到对应模版目录下（codegen.bat/codegen.sh同级目录）

```
$ mvn package -Dmaven.test.skip=true
$ mv target/mysql-code-generator-0.0.1-SNAPSHOT.jar ./dubbo/codegen.jar
```

修改对应的yml文件的数据库连接及其他设置，然后运行codegen.bat/codegen.sh，根据提示生成代码

示例配置对应的sql为/sql/create_tables.sql

## 模版说明

模版引擎采用freemarker

公共属性在commonProperties，其中other map object包括
```
"dbUrl": 数据库连接串,
"dbUsername": 数据库连接用户名,
"dbPassword": 数据库连接密码,
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
    baseColumns: 基本的公共列（如is_deleted, create_time等）
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
|targetTableName                      |目标表名                   |String                |user_type_rela
|className                            |表对应的类名               |String                |UserTypeRela
|comment                              |表说明                     |String                |用户类型关系表
|columns                              |表列                       |List<ColumnModel>     |
|hasPk                                |是否有主键                 |Boolean               |
|pks                                  |主键列表                   |List<ColumnModel>     |
|hasUniPk                             |主键唯一                   |Boolean               |
|uniPk                                |唯一主键                   |ColumnModel           |
|hasAutoIncUniPk                      |唯一主键是否自增           |Boolean               |
|indexes                              |索引列                     |List<ColumnModel>     |
|searchColumns                        |除了公共列以外的列         |List<ColumnModel>     |
|requiredColumns                      |除了公共列以外的列         |List<ColumnModel>     |
|columnsExceptBase                    |除了公共列以外的列         |List<ColumnModel>     |
|selectColumns                        |选择列                     |List<ColumnModel>     |
|fkSelectColumns                      |外键选择列                 |List<ColumnModel>     |
|validStatusColumn                    |有效性字段                 |ColumnModel           |

### ColumnModel

|FieldName                    |FieldRemark               |type                  |example
|-----------------------------|--------------------------|----------------------|-----------------
|databaseName                 |数据库名                  |String                |user
|tableName                    |表名                      |String                |user_type_rela
|columnName                   |列名                      |String                |user_id
|columnPosition               |列位置                    |Long                  |1
|columnDefault                |默认值                    |String                |0
|nullable                     |是否为空                  |Boolean               |NO
|dataType                     |数据类型                  |String                |int
|charLength                   |字符长度                  |Long                  |32
|byteLength                   |字节长度                  |Long                  |96
|columnType                   |列类型                    |String                |int(11)
|columnKey                    |                          |String                |PRI
|extra                        |                          |String                |auto_increment
|columnComment                |列说明                    |String                |用户ID
|index                        |是否是索引                |Boolean               |
|targetName                   |对应的字段名              |String                |UserId
|targetDataType               |对应的字段类型            |String                |Integer
|autoIncrement                |是否自增长                |Boolean               |
|pk                           |是否是主键                |Boolean               |
|select                       |是否是选择项              |Boolean               |
|selectOptions                |选择项列表                |SelectOption[]        |
|fkSelect                     |是否是逻辑外键选择项      |Boolean               |
|fkSelectColumn               |逻辑外键列                |FkSelectColumn        |
|validStatus                  |是否是有效性字段          |Boolean               |
|validStatusOption            |有效性字段选择项          |ValidStatus           |
|notRequired                  |是否不必须的              |Boolean               |
|imgUrl                       |是否是图片url             |Boolean               |
|videoUrl                     |是否是视频url             |Boolean               |
|docUrl                       |是否文档url               |Boolean               |
|pageUrl                      |是否页面url               |Boolean               |
|otherUrl                     |是否是其他url             |Boolean               |
|url                          |是否是url                 |Boolean               |
|content                      |是否是内容字段            |Boolean               |
|ignoreSearch                 |是否忽略查询              |Boolean               |

#### SelectOption

|FieldName                    |FieldRemark               |type                  |example
|-----------------------------|--------------------------|----------------------|-----------------
|value                        |值                        |String                |1,2
|text                         |展示的值                  |String                 |状态1，状态2

#### FkSelectColumn

|FieldName                    |FieldRemark               |type                  |example
|-----------------------------|--------------------------|----------------------|-----------------
|foreignTableName             |外键表名                  |String                |db_user
|valueName                    |值的列名                  |String                |Id
|textName                     |展示的列名                |String                |Name
|foreignTargetTableName       |外键目标表名              |String                |user
|foreignClassName             |外键类名                  |String                |User

#### ValidStatus

|FieldName                    |FieldRemark               |type                  |example
|-----------------------------|--------------------------|----------------------|-----------------
|valid                        |数据库名                  |String                |0 (未删除)
|invalid                      |数据库名                  |String                |1 (已删除)







