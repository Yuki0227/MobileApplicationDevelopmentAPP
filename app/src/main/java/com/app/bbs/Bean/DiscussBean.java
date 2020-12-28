package com.app.bbs.Bean;

import java.io.Serializable;

public class DiscussBean implements Serializable {
    public static final String KEY = "ItemInfoDiscuss";

    private String discontent;

    public DiscussBean(String discontent){
        this.discontent=discontent;
    }

    public String getDiscontent() {
        return discontent;
    }

    public void setDiscontent(String discontent) {
        this.discontent = discontent;
    }
}

