<#include "/include/table/properties.ftl">
<#macro mapperEl value>${r"#{"}${value}}</#macro>
<#macro mapperEl$ value>${r"${"}${value}}</#macro>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//ibatis.apache.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="${modulePackage}.presentation.mapper.${className}Mapper">

    <resultMap id="BaseResultMap" type="${modulePackage}.presentation.entity.${className}Entity">
        <#list table.columns as column>
        <#include "/include/column/properties.ftl">
        <result property="${fieldName}" column="${column.columnName}"/>
        </#list>
    </resultMap>
    <resultMap id="ExtResultMap" extends="BaseResultMap" type="${modulePackage}.presentation.entity.${className}EntityExt">
        <#list table.fkSelectColumns as column>
        <#include "/include/column/properties.ftl">
        <result property="${fieldNameExceptKey}Text" column="${columnExceptKey}_text"/>
        </#list>
    </resultMap>

    <sql id="tableName">`${table.tableName}`</sql>

    <!--插入${tableComment}-->
    <insert id="insert"<#if table.hasAutoIncUniPk> useGeneratedKeys="true" keyProperty="entity.${table.uniPk.targetName?uncap_first}"</#if>>
        insert into <include refid="tableName"/>
        <trim prefix="(" suffix=")" suffixOverrides=",">
        <#list table.columns as column>
        <#include "/include/column/properties.ftl">
            <if test="entity.${fieldName} != null">`${column.columnName}`,</if>
        </#list>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides=",">
        <#list table.columns as column>
        <#include "/include/column/properties.ftl">
            <if test="entity.${fieldName} != null"><@mapperEl 'entity.' + fieldName/>,</if>
        </#list>
        </trim>
    </insert>

    <!--todo 插入具体的列-->
    <!--批量插入${tableComment}-->
    <insert id="batchInsert"<#if table.hasAutoIncUniPk> useGeneratedKeys="true" keyProperty="${table.uniPk.targetName?uncap_first}"</#if>>
        insert into <include refid="tableName"/>
        (
        <#list table.columns as column>
        <#include "/include/column/properties.ftl">
            `${column.columnName}`<#if column?has_next>,</#if>
        </#list>
        )
        values
        <foreach collection="list" item="item" separator=",">
        (
        <#list table.columns as column>
        <#include "/include/column/properties.ftl">
            <@mapperEl 'item.' + fieldName/><#if column?has_next>,</#if>
        </#list>
        )
        </foreach>
    </insert>

    <!--根据主键条件删除${tableComment}-->
    <delete id="delete">
        DELETE FROM <include refid="tableName"/>
        <where>
        <#if (table.hasUniPk)>
            <choose>
                <when test="condition.${uniPkFieldName} != null">
                    `${uniPk.columnName}` = <@mapperEl 'condition.' + uniPkFieldName/>
                </when>
                <when test="condition.${uniPkFieldName}s != null">
                    `${uniPk.columnName}` in <foreach collection="condition.${uniPkFieldName}s" item="it" open="(" close=")" separator=","><@mapperEl 'it'/></foreach>
                </when>
                <otherwise>
                    1!=1
                </otherwise>
            </choose>
        <#else>
        <#list pks as column>
            <#include "/include/column/properties.ftl">
            <#if (column_index > 0)>AND </#if>`${column.columnName}` = <@mapperEl 'condition.' + fieldName/>
        </#list>
        </#if>
        </where>
    </delete>

    <!--根据主键条件更新${tableComment}-->
    <update id="update">
        UPDATE <include refid="tableName"/>
        <set>
            <#list table.columns as column>
            <#include "/include/column/properties.ftl">
            <#if (column.pk && column.autoIncrement)>
            <#else>
            <if test="entity.${fieldName} != null">
                `${column.columnName}` = <@mapperEl 'entity.' + fieldName/><#if column?has_next>,</#if>
            </if>
            </#if>
            </#list>
        </set>
        <where>
        <#if (table.hasUniPk)>
            <choose>
                <when test="condition.${uniPkFieldName} != null">
                    `${uniPk.columnName}` = <@mapperEl 'condition.' + uniPkFieldName/>
                </when>
                <when test="condition.${uniPkFieldName}s != null">
                    `${uniPk.columnName}` in <foreach collection="condition.${uniPkFieldName}s" item="it" open="(" close=")" separator=","><@mapperEl 'it'/></foreach>
                </when>
                <otherwise>
                    1!=1
                </otherwise>
            </choose>
        <#else>
        <#list pks as column>
            <#include "/include/column/properties.ftl">
            <#if (column_index > 0)>AND </#if>`${column.columnName}` = <@mapperEl 'condition.' + fieldName/>
        </#list>
        </#if>
        </where>
    </update>

    <!--根据主键获取${tableComment}详情-->
    <select id="detail" resultMap="ExtResultMap">
        SELECT DISTINCT
        <#assign fkIndex = 0>
        <#list table.columns as column>
        <#include "/include/column/properties.ftl">
        MT.`${column.columnName}`<#if column?has_next>,</#if>
        <#if (column.fkSelect)>
        <#assign tmpName = ('T' + fkIndex)>
        <#assign fkIndex = (1 + fkIndex)>
        `${tmpName}`.`${column.fkSelectColumn.textColumnName}` `${columnExceptKey}_text`<#if column?has_next>,</#if>
        </#if>
        </#list>
        FROM
        <include refid="tableName"/> MT
        <#if (table.fkSelectColumns?size > 0)>
            <#list table.fkSelectColumns as column>
            <#assign tmpName = ('T' + column?index)>
            LEFT JOIN (SELECT `${column.fkSelectColumn.valueColumnName}`, `${column.fkSelectColumn.textColumnName}` FROM `${column.fkSelectColumn.foreignTableName}`<#list column.fkSelectColumn.conditions as condition><#if condition?is_first> WHERE </#if>${condition.field} = '${condition.value}'<#if condition?has_next> AND </#if></#list>) ${tmpName} ON MT.${column.columnName} = ${tmpName}.`${column.fkSelectColumn.valueColumnName}`
            </#list>
        </#if>
        <where>
        <#list pks as column>
            <#include "/include/column/properties.ftl">
            <#if (column_index > 0)>AND </#if>MT.`${column.columnName}` = <@mapperEl fieldName/>
        </#list>
        </where>
    </select>

    <sql id="where">
        <where>
            <if test="condition == null">1!=1</if>
            <if test="condition != null">
                <trim prefixOverrides="AND|OR">
                <#list table.columns as column>
                <#include "/include/column/properties.ftl">
                <#if (column.ignoreSearch || isContent)>
                <#else>
                    <#if (canBeEqual)>
                    <if test="condition.${fieldName} != null<#if (isString)> and condition.${fieldName} != ''</#if>">
                        AND MT.`${column.columnName}` = <@mapperEl 'condition.' + fieldName/>
                    </if>
                    </#if>
                    <#if (canBeList)>
                    <if test="condition.${fieldName}In != null and condition.${fieldName}In.size() > 0">
                        AND MT.`${column.columnName}` IN
                        <foreach collection="condition.${fieldName}In" item="it" open="(" close=")" separator=","><@mapperEl 'it'/></foreach>
                    </if>
                    <if test="condition.${fieldName}NotIn != null and condition.${fieldName}NotIn.size() > 0">
                        AND MT.`${column.columnName}` NOT IN
                        <foreach collection="condition.${fieldName}NotIn" item="it" open="(" close=")" separator=","><@mapperEl 'it'/></foreach>
                    </if>
                    </#if>
                    <#if (canBeRange)>
                    <if test="condition.${fieldName}Min != null">
                        <![CDATA[
                        AND MT.`${column.columnName}` >= <@mapperEl 'condition.' + fieldName + 'Min'/>
                        ]]>
                    </if>
                    <if test="condition.${fieldName}Max != null">
                        <![CDATA[
                        AND MT.`${column.columnName}` <= <@mapperEl 'condition.' + fieldName + 'Max'/>
                        ]]>
                    </if>
                    </#if>
                    <#if (canBeNull)>
                    <if test="condition.${fieldName}IsNotNull != null and condition.${fieldName}IsNotNull == true">
                        AND MT.`${column.columnName}` IS NOT NULL
                    </if>
                    <if test="condition.${fieldName}IsNull != null and condition.${fieldName}IsNull == true">
                        AND MT.`${column.columnName}` IS NULL
                    </if>
                    </#if>
                    <#if (isString)>
                    <if test="condition.${fieldName}IsNotEmpty != null and condition.${fieldName}IsNotEmpty == true">
                        AND MT.`${column.columnName}` IS NOT NULL AND `${column.columnName}` != ''
                    </if>
                    <if test="condition.${fieldName}IsEmpty != null and condition.${fieldName}IsEmpty == true">
                        AND (MT.`${column.columnName}` IS NULL OR MT.`${column.columnName}` = '')
                    </if>
                    <if test="condition.${fieldName}StartWith != null and condition.${fieldName}StartWith != ''">
                        AND MT.`${column.columnName}` LIKE concat(<@mapperEl 'condition.' + fieldName + 'StartWith'/>, '%')
                    </if>
                    <if test="condition.${fieldName}EndWith != null and condition.${fieldName}EndWith != ''">
                        AND MT.`${column.columnName}` LIKE concat('%', <@mapperEl 'condition.' + fieldName + 'EndWith'/>)
                    </if>
                    <if test="condition.${fieldName}Contains != null and condition.${fieldName}Contains != ''">
                        AND MT.`${column.columnName}` LIKE concat('%', <@mapperEl 'condition.' + fieldName + 'Contains'/>, '%')
                    </if>
                    </#if>
                </#if>
                </#list>
                </trim>
            </if>
        </where>
    </sql>
    <select id="select" resultMap="ExtResultMap">
        SELECT
        <if test="condition.columns != null">
            <foreach collection="condition.columns" item="it" separator=",">MT.`<@mapperEl$ 'it'/>`</foreach>
        </if>
        <if test="condition.columns == null">
            <#list table.columns as column>
            MT.`${column.columnName}`<#if column?has_next>,</#if>
            </#list>
        </if>
        FROM <include refid="tableName"/> MT
        <include refid="where"/>
        <if test="condition.orderBy != null">
            ORDER BY <foreach collection="condition.orderBy" index="key" item="val" separator=",">MT.<@mapperEl$ 'key'/> <@mapperEl$ 'val'/></foreach>
        </if>
    </select>

    <select id="count" resultType="java.lang.Integer">
        SELECT COUNT(*)
        FROM <include refid="tableName"/> MT
        <include refid="where"/>
    </select>
</mapper>
