package com.check_res.model;

public class ResourceInfo {
    private String project;
    private String type;
    private String name;
    private String value;

    public ResourceInfo(String type) {
        this.type = type;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "project:" + project + ",type:" + type + ",name:" + name + ", value:" + value;
    }
}
