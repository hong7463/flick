package com.haisenhong.flicker.data.models.responses;

/**
 * Created by hison7463 on 10/13/16.
 */

public class Youtube {

    private String name;
    private String size;
    private String source;
    private String type;

    public Youtube() {
    }

    public Youtube(String name, String size, String source, String type) {
        this.name = name;
        this.size = size;
        this.source = source;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Youtube{" +
                "name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", source='" + source + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
