package org.xi.quick.codegenerator.model;

import freemarker.template.Template;

/**
 * @author 郗世豪（rarexixi@outlook.com）
 * @date 2017/11/27 12:16
 */
public class FreemarkerModel {

    public FreemarkerModel(String relativePath, Template template) {
        this.relativePath = relativePath;
        this.template = template;
    }

    private String relativePath;
    private Template template;

    public String getRelativePath() {
        return relativePath;
    }

    public Template getTemplate() {
        return template;
    }
}
