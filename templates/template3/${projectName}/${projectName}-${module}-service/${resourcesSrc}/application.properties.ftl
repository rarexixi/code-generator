<#macro mapperEl$ value>${r"${"}${value}}</#macro>
server.port=${servicePort}
spring.application.name=${projectName}-${module}-service
spring.jackson.date-format=java.text.SimpleDateFormat

spring.servlet.multipart.max-file-size=200MB
spring.servlet.multipart.max-request-size=256MB

spring.http.encoding.charset=UTF-8
spring.http.encoding.enabled=true
spring.http.encoding.force=true

# region datasoruce
spring.datasource.url=${dbUrl}
spring.datasource.username=${dbUsername}
spring.datasource.password=${dbPassword}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
# endregion
#
# region mybatis
mybatis.type-aliases-package=${modulePackage}.presentation.mapper
mybatis.mapper-locations=classpath:mapper/*.xml
mybatis.configuration.cache-enabled=false
mybatis.configuration.lazy-loading-enabled=false
mybatis.configuration.multiple-result-sets-enabled=true
mybatis.configuration.use-column-label=true
mybatis.configuration.use-generated-keys=false
mybatis.configuration.default-executor-type=simple
mybatis.configuration.default-statement-timeout=25000
mybatis.configuration.map-underscore-to-camel-case=true
mybatis.configuration.call-setters-on-nulls=true
# endregion
#
# region pagehelper
pagehelper.reasonable=true
pagehelper.params="count=countSql"
pagehelper.support-methods-arguments=true
# endregion

dmp.module=${projectName}-${module}
dmp.auth.anonymous-urls=/actuator/info

# /actuator/info 显示信息
info.app.name=<@mapperEl$ 'spring.application.name'/>