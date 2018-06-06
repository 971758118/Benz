package com.benz.find.benz.entity;

/**
 * Created by Jiaxu on 2018-06-06
 */

public class DuanziEntity {
    private String id;
    private String content;
    private String url;
    private String pv;
    private String layout;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPv() {
        return pv;
    }

    public void setPv(String pv) {
        this.pv = pv;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    @Override
    public String toString() {
        return "DuanziEntity{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", url='" + url + '\'' +
                ", pv='" + pv + '\'' +
                ", layout='" + layout + '\'' +
                '}';
    }
}
