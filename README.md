# MysqlCodeGenerator
mysql code generator

模版引擎采用freemarker
公共属性在common.properties

## TableModel

|FieldName                |FieldRemark            |example
|-------------------------|-----------------------|-----------------
|databaseName             |数据库名                 |user
|tableName                |表名                     |user_type_rela
|tableComment             |表说明                   |用户类型关系表
|validStatusField         |配置的有效性字段           |
|columns                  |表字段列表                |
|statistics               |索引列表                  |
|tableClassName           |表对应的JAVA类名          |UserTypeRela                    
|tableClassNameFirstLower |表对应的JAVA类名首字母小写 |userTypeRela                            
|hasPrimaryKey            |是否有主键                |true              
|primaryKey               |主键列表                  |
|hasAutoIncrementUniquePrimaryKey |是否有唯一自增主键  |
|uniquePrimaryKey         |获取唯一主键               |
|primaryKeyParameters     |主键对应的JAVA参数         |单个(Integer id)，多个(Integer userId, Integer userTypeId)
|primaryKeyParameterValues|主键对应的JAVA参数值       |单个(id) ，多个(userId, userTypeId)
|primaryKeyParameters     |旧主键对应的JAVA参数         |单个(Integer oldId)，多个(Integer oldUserId, Integer oldUserTypeId)
|primaryKeyParameterValues|旧主键对应的JAVA参数值       |单个(oldId) ，多个(oldUserId, oldUserTypeId)
|validStatusColumn        |有效性字段                |

## ColumnModel

|FieldName                    |FieldRemark            |example
|-------------------------    |-----------------------|-----------------
|databaseName                 |数据库名                |user                                                                  
|tableName                    |表名                    |user_type_rela                                                         
|columnName                   |列名                    |user_id                                                              
|columnPosition               |列位置                  |1                                                       
|columnDefault                |默认值                  |0                                                      
|isNullable                   |是否为空                |NO                                                                    
|dataType                     |数据类型                |int                                                                   
|charLength                   |字符长度                |32                                                                      
|byteLength                   |字节长度                |96                                                                     
|columnType                   |列类型                  |int(11)                                                                
|columnKey                    |                       |PRI                                                                
|extra                        |                       |auto_increment                                                         
|columnComment                |列说明                  |用户ID     
|notRequired                  |不必须的                 |
|validStatus                  |是有效性字段             |
|columnFieldName              |对应的JAVA字段名         |UserId                                                              
|columnFieldNameFirstLower    |对应的JAVA字段名首字母小写|userId                                                                   
|columnFieldType              |对应的JAVA字段类型       |Integer                                                              
|autoIncrement                |是否自增长              |false                                                        
|primaryKey                   |是否是主键              |true     
|endWithId                    |数据库字段是否以_id结尾  |
|index                        |是否是索引              |


