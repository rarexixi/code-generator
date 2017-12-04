# MysqlCodeGenerator
mysql code generator

## TableModel

|FieldName                |FieldRemark            |example
|-------------------------|-----------------------|-----------------
|databaseName             |数据库名                 |user
|tableName                |表名                    |user
|tableComment             |表说明                   |用户表
|columns                  |表字段列表                |
|tableClassName           |表对应的JAVA类名          |User                    
|tableClassNameFirstLower |表对应的JAVA类名首字母小写  |user                            
|hasPrimaryKey            |是否有主键                |true              
|hasSinglePrimaryKey      |主键是否只有一个字段        |true                      
|firstPrimaryKey          |第一个主键                |
|primaryKey               |主键列表                  |
|primaryKeyParameters     |主键对应的JAVA参数         |单个(Integer id)，多个(Integer id1, Integer id2)
|primaryKeyParameterValues|主键对应的JAVA参数值       |单个(id) ，多个(id1, id2)
|hasIsActive              |是否有is_active字段       |true

## ColumnModel

|FieldName                    |FieldRemark            |example
|-------------------------    |-----------------------|-----------------
|databaseName                 |数据库名                 |                                                                  
|tableName                    |表名                     |                                                             
|columnName                   |列名                     |                                                              
|columnPosition               |列位置                   |                                                       
|columnDefault                |默认值                   |                                                      
|isNullable                   |是否为空                 |                                                                     
|dataType                     |数据类型                 |                                                                   
|charLength                   |字符长度                 |                                                                      
|byteLength                   |字节长度                 |                                                                     
|columnType                   |列类型                   |                                                                    
|columnKey                    |                        |                                                                
|extra                        |                        |                                                            
|columnComment                |列说明                   |                                                      
|columnFieldName              |对应的JAVA字段名          |                                                              
|columnFieldNameFirstLower    |对应的JAVA字段名          |                                                                     
|columnFieldType              |对应的JAVA字段类型        |                                                              
|autoIncrement                |是否自增长                |                                                        
|primaryKey                   |是否是主键                |                           


