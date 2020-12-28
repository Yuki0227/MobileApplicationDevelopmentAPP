package com.app.bbs.Bean;

import android.annotation.SuppressLint;

import java.io.Serializable;

public class ItemBean implements Serializable {

    public static final String KEY = "ItemInfo";
    private String title;
    private String content;

    public ItemBean(String title, String content) {
        this.title=title;
        this.content=content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
