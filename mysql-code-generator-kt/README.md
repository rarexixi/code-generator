# MySqlCodeGenerator

mysql代码生成器

## 使用方式

下载源码，使用maven打包，并将jar包重命名为codegen.jar，复制到对应模版目录下（codegen.bat/codegen.sh同级目录）

```
$ cd ${project-path}/mysql-code-generator-kt
$ mvn package -Dmaven.test.skip=true
$ mv target/mysql-code-generator-kt-xxx.jar ./codegen.jar
```

复制项目中的```application-xxx.yml```到jar包同级目录

按需要配置```application-xxx.yml```（一般是数据库链接，和generator下的配置，配置说明见下文）,```指定模版输入输出路径，注意windows路径"\"替换linux/mac下的"/"```

然后执行以下命令运行，```xxx```是上面配置文件```application-xxx.yml``` 横杠后面的xxx，文件名自定义即可

```sbtshell
$ java -jar codegen.jar --spring.profiles.active=xxx
```

### 配置文件说明
语法不懂可以访问 [链接](https://www.jianshu.com/p/97222440cd08)


```yaml
generator:                                                  # 参见 org.xi.quick.codegeneratorkt.configuration.properties.GeneratorProperties
  database-name: database_name                              # 数据库名
  encoding: UTF-8                                           # 代码字符集 （默认UTF-8）
  table-name-match-regex: '(?<=${prefix}).*(?=${suffix})'   # 获取实际表名的正则表达式，将${prefix}替换为表名前缀，将${suffix}替换为表名后缀
  paths:
    template: ./template                                    # 模版路径 (以“/”结尾，windows环境下以“\”结尾)
    out: ./out/                                             # 输出路径（同上）
  files:
    ignore: path1[, path2...]                               # 要忽略的文件（不会生成到对应目录，模板下暂时是 "include/"，该行可注释）
    aggregate: path1[, path2...]                            # 聚合文件（用于生成索引之类页面，该行可注释，一般是索引的页面，或者所有的列表）
    copy: path1[, path2...]                                 # 仅复制的文件（不做任何修改，该行可注释，一般是公共js文件，css文件）
  columns:
    base:
      column-name-set: column-name[, column-name2...]       # 公共的列名，用来生成BaseEntity （多个列名用英文逗号隔开","，下同）
      table-name: table-name                                # 具体表名，用来获取字段信息，必填！！！
    not-required:
      - column-name-set: column-name[, column-name2...]
        table-name: table-name                              # 具体表名，可选，不写代表所有表
    img-url:
      - column-name-set: column-name[, column-name2...]     # 图片字段名列表（多个用英文逗号隔开）图片字段列表（多个用英文逗号隔开）
        table-name: table-name                              # 具体表名，可选，不写代表所有表
    video-url:
      - column-name-set: column-name[, column-name2...]     # 视频url字段名列表（多个用英文逗号隔开）
        table-name: table-name                              # 具体表名，可选，不写代表所有表
    doc-url:
      - column-name-set: column-name[, column-name2...]     # 文档url字段名列表（多个用英文逗号隔开）
        table-name: table-name                              # 具体表名，可选，不写代表所有表
    page-url:
      - column-name-set: column-name[, column-name2...]     # 页面url字段名列表（多个用英文逗号隔开）
        table-name: table-name                              # 具体表名，可选，不写代表所有表
    other-url:
      - column-name-set: column-name[, column-name2...]     # 其他url字段名列表（多个用英文逗号隔开）
        table-name: table-name                              # 具体表名，可选，不写代表所有表
    content:
      - column-name-set: column-name[, column-name2...]     # 内容字段名列表（多个用英文逗号隔开，用于生成前端textarea，并且忽略查询）
        table-name: table-name                              # 具体表名，可选，不写代表所有表
    select:
      - column-name-set: status                             # 字段名列表（多个用英文逗号隔开）
        table-name: multi_pk                                # 具体表名，可选，不写代表所有表
        options:                                            # 下拉列表（数组，用来生成 <option value ="value">text</option>)
          - value: value1
            text: text1 
          - value: value2
            text: text2
    fk-select:
      - column-name-set:  column-name[, column-name2...]
        table-name: table-name                              # 具体表名，可选，不写代表所有表
        fk:                                                 # 下拉列表（数组，用来生成 <option value ="${value-column-name}">${text-column-name}</option>)
          foreign-table-name: foreign-table-name            # 外键具体表名
          value-column-name: value-column-name              
          text-column-name: text-column-name                 
    valid-status:
      - column-name-set: column-name[, column-name2...]
        table-name: table-name                              # 具体表名，可选，不写代表所有表
        status:
          valid: 0                                          # 有效值
          invalid: 1                                        # 无效值
  common-properties:                                        # 公共自定义属性（键值对，用于生成文件路径的替换，以及模板文件相应字段的替换）
    key1: value1
    key2: value2
    ...
  data-type-map:                                            # 数据映射关系（键值对，用于确定java字段类型）
    dataType1: javaType1
    dataType2: javaType2
    ...
```


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







