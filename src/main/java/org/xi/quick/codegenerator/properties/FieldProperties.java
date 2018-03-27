package org.xi.quick.codegenerator.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.xi.quick.codegenerator.entity.SelectField;

import java.util.HashSet;
import java.util.Set;

@Component
@ConfigurationProperties(prefix = "generator.field")
public class FieldProperties {

    public FieldProperties() {
        notRequired = new HashSet<>();
        imgUrl = new HashSet<>();
        videoUrl = new HashSet<>();
        docUrl = new HashSet<>();
        pageUrl = new HashSet<>();
        otherUrl = new HashSet<>();
        content = new HashSet<>();
        select = new SelectField[0];
    }

    /**
     * 不需要填写的字段集合
     */
    private Set<String> notRequired;

    /**
     * 选择项的字段集合
     */
    private SelectField[] select;

    /**
     * 图片路径的字段集合
     */
    private Set<String> imgUrl;

    /**
     * 视频路径的字段集合
     */
    private Set<String> videoUrl;

    /**
     * 文档路径字段集合
     */
    private Set<String> docUrl;

    /**
     * 页面路径的字段集合
     */
    private Set<String> pageUrl;

    /**
     * 其他路径的字段集合
     */
    private Set<String> otherUrl;

    /**
     * 内容类型字段集合
     */
    private Set<String> content;

    /**
     * 选择项的字段名集合
     */
    private Set<String> selectFieldNameSet;

    public Set<String> getNotRequired() {
        return notRequired;
    }

    public void setNotRequired(Set<String> notRequired) {
        this.notRequired = notRequired;
    }

    public Set<String> getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(Set<String> imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Set<String> getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(Set<String> videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Set<String> getDocUrl() {
        return docUrl;
    }

    public void setDocUrl(Set<String> docUrl) {
        this.docUrl = docUrl;
    }

    public Set<String> getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(Set<String> pageUrl) {
        this.pageUrl = pageUrl;
    }

    public Set<String> getOtherUrl() {
        return otherUrl;
    }

    public void setOtherUrl(Set<String> otherUrl) {
        this.otherUrl = otherUrl;
    }

    public Set<String> getContent() {
        return content;
    }

    public void setContent(Set<String> content) {
        this.content = content;
    }

    public SelectField[] getSelect() {
        return select;
    }

    public void setSelect(SelectField[] select) {
        this.select = select;
    }
}
