package org.xi.quick.codegenerator.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@ConfigurationProperties(prefix = "generator.field")
public class FieldProperties {

    /**
     * 不需要填写的字段集合
     */
    private Set<String> notRequired;

    /**
     * 选择项的字段集合
     */
    private Set<String> select;

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

    public Set<String> getNotRequired() {
        return notRequired;
    }

    public void setNotRequired(Set<String> notRequired) {
        this.notRequired = notRequired;
    }

    public Set<String> getSelect() {
        return select;
    }

    public void setSelect(Set<String> select) {
        this.select = select;
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
}
