package org.xi.quick.codegenerator.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.xi.quick.codegenerator.entity.ValidStatusField;

import java.util.*;

@Component
@ConfigurationProperties(prefix = "generator")
public class GeneratorConfigProperties {

    public GeneratorConfigProperties() {
        field = new HashMap<>();
        file = new HashMap<>();
        folder = new HashMap<>();
        path = new HashMap<>();
    }

    private String encoding;

    private Map<String, Set<String>> field;
    private Map<String, Set<String>> file;
    private Map<String, Set<String>> folder;
    private Map<String, String> path;

    private ValidStatusField validStatusField;

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

    //region 自定义

    public String getTemplatePath() {
        return path.getOrDefault("template", "");
    }

    public String getOutPath() {
        return path.getOrDefault("out", "");
    }

    public String getCommonPropertiesPath() {
        return path.getOrDefault("commonProperties", "");
    }

    public String getDataTypePropertiesPath() {
        return path.getOrDefault("dataTypeProperties", "");
    }

    public Set<String> getIgnoredFolderSet() {
        return folder.getOrDefault("ignored", null);
    }

    public Set<String> getAggregateFileSet() {
        return file.getOrDefault("aggregate", null);
    }

    public Set<String> getJustCopyFileSet() {
        return file.getOrDefault("notRequired", null);
    }

    /**
     * 获取不需要的字段集合
     *
     * @return
     */
    public Set<String> getNotRequiredFieldSet() {
        return field.getOrDefault("notRequired", null);
    }

    //endregion
}
