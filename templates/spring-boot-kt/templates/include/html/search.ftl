        <el-form ref="searchParams" :model="searchParams" :label-position="'left'" label-width="120px">
            <el-row :gutter="10">
<#list table.indexes as column>
                <#include "/include/column/properties.ftl">
                <#if column.validStatus>
                <#-- <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
                    <el-form-item label="${columnComment}">
                        <el-select v-model="searchParams.${fieldName}" placeholder="全部">
                            <el-option v-for="item in ${fieldName}SelectList" :value="item.value" :label="item.text"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col> -->
                <#elseif column.fkSelect>
                <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
                    <el-form-item label="${columnComment}">
                        <el-select v-model="searchParams.${fieldName}" placeholder="全部">
                            <el-option value="" label="全部"></el-option>
                            <el-option v-for="item in ${fieldNameExceptKey}SelectList" :value="item.${column.fkSelectColumn.valueName?uncap_first}" :label="item.${column.fkSelectColumn.textName?uncap_first}"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <#elseif column.select>
                <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
                    <el-form-item label="${columnComment}">
                        <el-select v-model="searchParams.${fieldName}" placeholder="全部">
                            <el-option value="" label="全部"></el-option>
                            <el-option v-for="item in ${fieldNameExceptKey}SelectList" :value="item.value" :label="item.text"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <#elseif column.pk>
                <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
                    <el-form-item label="${columnComment}">
                        <el-input v-model="searchParams.${fieldName}" type="text"></el-input>
                    </el-form-item>
                </el-col>
                <#elseif (isInteger)>
                <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
                    <el-form-item label="${columnComment}">
                        <el-input v-model="searchParams.${fieldName}" type="text"></el-input>
                    </el-form-item>
                </el-col>
                <#elseif (isDecimal)>
                <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
                    <el-form-item label="${columnComment}">
                        <el-input v-model="searchParams.${fieldName}Min"></el-input>
                        <el-input v-model="searchParams.${fieldName}Max"></el-input>
                    </el-form-item>
                </el-col>
                <#elseif (isString)>
                <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
                    <el-form-item label="${columnComment}">
                        <el-input v-model="searchParams.${fieldName}StartWith"></el-input>
                    </el-form-item>
                </el-col>
                <#elseif (isDate || isDateTime)>
                <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
                    <el-form-item label="${columnComment}">
                        <el-date-picker
                                v-model="searchParams.${fieldName}Range"
                                type="daterange"
                                :default-time="['00:00:00', '23:59:59']"
                                range-separator="至"
                                start-placeholder="开始日期"
                                end-placeholder="结束日期">
                        </el-date-picker>
                    </el-form-item>
                </el-col>
                <#elseif (isTime)>
                <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="6">
                    <el-form-item label="${columnComment}">
                        <el-time-picker
                            is-range
                            v-model="searchParams.${fieldName}Range"
                            range-separator="至"
                            start-placeholder="开始时间"
                            end-placeholder="结束时间"
                            placeholder="选择时间范围">
                        </el-time-picker>
                    </el-form-item>
                </el-col>
                </#if>
</#list>
                <el-col :span="24">
                    <el-form-item>
                        <el-button @click="search" type="primary" icon="el-icon-search">搜索</el-button>
                        <el-button @click="resetSearch" type="warning" icon="el-icon-refresh">重置</el-button>
                        <el-button @click="exportExcel" type="success" icon="el-icon-download">导出</el-button>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>

<#if table.validStatusColumn??>
        <el-tabs v-model="searchParams.${table.validStatusColumn.targetName?uncap_first}" @tab-click="search">
            <el-tab-pane name="null" label="全部"></el-tab-pane>
            <el-tab-pane name="${table.validStatusColumn.validStatusOption.valid}" label="有效"></el-tab-pane>
            <el-tab-pane name="${table.validStatusColumn.validStatusOption.invalid}" label="无效"></el-tab-pane>
        </el-tabs>
</#if>