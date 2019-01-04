package org.xi.quick.codegeneratorkt.service

import org.xi.quick.codegeneratorkt.model.FreemarkerModel

interface FreeMarkerService {

    /**
     * 所有模版
     *
     * @return
     */
    fun getTableTemplates(): List<FreemarkerModel>

    /**
     * 所有运行一次的模版
     *
     * @return
     */
    fun getOnceTemplates(): List<FreemarkerModel>

    /**
     * 所有聚合的模版
     *
     * @return
     */
    fun getAggrTemplates(): List<FreemarkerModel>

    /**
     * 所有仅复制的文件
     *
     * @return
     */
    fun getCopyTemplates(): List<FreemarkerModel>
}