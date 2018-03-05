package org.xi.quick.codegenerator.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.xi.quick.codegenerator.entity.FkSelectField;
import org.xi.quick.codegenerator.entity.ValidStatusField;

import java.util.*;

@Component
@ConfigurationProperties(prefix = "generator")
public class GeneratorConfigProperties {

    private Set<String> emptySet = new HashSet<>();

    public GeneratorConfigProperties() {
        field = new HashMap<>();
        file = new HashMap<>();
        folder = new HashMap<>();
        path = new HashMap<>();

        commonProperties = new HashMap<>();
        dataTypeMap = new HashMap<>();
    }

    private String encoding;

    private Map<String, Set<String>> field;
    private Map<String, Set<String>> file;
    private Map<String, Set<String>> folder;
    private Map<String, String> path;

    private ValidStatusField validStatusField;
    private FkSelectField[] fkSelectFields;

    private Map<String, String> commonProperties;
    private Map<String, String> dataTypeMap;

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public Map<String, Set<String>> getField() {
        return field;
    }

    public void setField(Map<String, Set<String>> field) {
        this.field = field;
    }

    public Map<String, Set<String>> getFile() {
        return file;
    }

    public void setFile(Map<String, Set<String>> file) {
        this.file = file;
    }

    public Map<String, Set<String>> getFolder() {
        return folder;
    }

    public void setFolder(Map<String, Set<String>> folder) {
        this.folder = folder;
    }

    public Map<String, String> getPath() {
        return path;
    }

    public void setPath(Map<String, String> path) {
        this.path = path;
    }

    public ValidStatusField getValidStatusField() {
        return validStatusField;
    }

    public void setValidStatusField(ValidStatusField validStatusField) {
        this.validStatusField = validStatusField;
    }

    public FkSelectField[] getFkSelectFields() {
        return fkSelectFields;
    }

    public void setFkSelectFields(FkSelectField[] fkSelectFields) {
        this.fkSelectFields = fkSelectFields;
    }

    public Map<String, String> getCommonProperties() {
        return commonProperties;
    }

    public void setCommonProperties(Map<String, String> commonProperties) {
        this.commonProperties = commonProperties;
    }

    public Map<String, String> getDataTypeMap() {
        return dataTypeMap;
    }

    public void setDataTypeMap(Map<String, String> dataTypeMap) {
        this.dataTypeMap = dataTypeMap;
    }

    //region 自定义

    /**
     * 获取模版路径
     *
     * @return
     */
    public String getTemplatePath() {
        return path.getOrDefault("template", "");
    }

    /**
     * 获取输出路径
     *
     * @return
     */
    public String getOutPath() {
        return path.getOrDefault("out", "");
    }

    /**
     * 获取忽略的文件夹集合
     *
     * @return
     */
    public Set<String> getIgnoreFolderSet() {
        return folder.getOrDefault("ignore", emptySet);
    }

    /**
     * 获取聚合文件夹集合
     *
     * @return
     */
    public Set<String> getAggregateFolderSet() {
        return folder.getOrDefault("aggregate", emptySet);
    }

    /**
     * 获取仅复制文件夹集合
     *
     * @return
     */
    public Set<String> getJustCopyFolderSet() {
        return folder.getOrDefault("justCopy", emptySet);
    }

    /**
     * 获取忽略的文件集合
     *
     * @return
     */
    public Set<String> getIgnoreFileSet() {
        return file.getOrDefault("ignore", emptySet);
    }

    /**
     * 获取聚合文件集合
     *
     * @return
     */
    public Set<String> getAggregateFileSet() {
        return file.getOrDefault("aggregate", emptySet);
    }

    /**
     * 获取仅复制文件集合
     *
     * @return
     */
    public Set<String> getJustCopyFileSet() {
        return file.getOrDefault("justCopy", emptySet);
    }

    /**
     * 获取不需要的字段集合
     *
     * @return
     */
    public Set<String> getNotRequiredFieldSet() {
        return field.getOrDefault("notRequired", emptySet);
    }

    /**
     * 获取是选择项的字段集合
     *
     * @return
     */
    public Set<String> getSelectFieldSet() {
        return field.getOrDefault("select", emptySet);
    }

    /**
     * 获取图片路径的字段集合
     *
     * @return
     */
    public Set<String> getImgUrlFieldSet() {
        return field.getOrDefault("imgUrl", emptySet);
    }

    /**
     * 获取视频路径的字段集合
     *
     * @return
     */
    public Set<String> getVideoUrlFieldSet() {
        return field.getOrDefault("videoUrl", emptySet);
    }

    /**
     * 获取是文档路径字段集合
     *
     * @return
     */
    public Set<String> getDocUrlFieldSet() {
        return field.getOrDefault("docUrl", emptySet);
    }

    /**
     * 获取页面路径的字段集合
     *
     * @return
     */
    public Set<String> getPageUrlFieldSet() {
        return field.getOrDefault("pageUrl", emptySet);
    }

    /**
     * 获取其他路径的字段集合
     *
     * @return
     */
    public Set<String> getOtherUrlFieldSet() {
        return field.getOrDefault("otherUrl", emptySet);
    }

    /**
     * 获取所有路径的字段集合
     *
     * @return
     */
    public Set<String> getAllUrlFieldSet() {
        Set<String> urlSet = new HashSet<>();
        urlSet.addAll(getImgUrlFieldSet());
        urlSet.addAll(getVideoUrlFieldSet());
        urlSet.addAll(getDocUrlFieldSet());
        urlSet.addAll(getPageUrlFieldSet());
        urlSet.addAll(getOtherUrlFieldSet());
        return urlSet;
    }

    /**
     * 获取内容的字段集合
     *
     * @return
     */
    public Set<String> getContentFieldSet() {
        return field.getOrDefault("content", emptySet);
    }

    /**
     * 获取忽略查询的字段集合
     *
     * @return
     */
    public Set<String> getIgnoreSearchFieldSet() {
        Set<String> fieldSet = new HashSet<>();
        fieldSet.addAll(getAllUrlFieldSet());
        fieldSet.addAll(getContentFieldSet());
        return fieldSet;
    }

    //endregion
}
