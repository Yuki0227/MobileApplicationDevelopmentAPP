package com.app.bbs.entity;


import java.io.Serializable;

public class ArticleReviewView implements Serializable {

    private long id;
    private long articleId;
    private String author;
    private String body;

    public void setId(long id) {
        this.id = id;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getId() {
        return id;
    }

    public long getArticleId() {
        return articleId;
    }

    public String getAuthor() {
        return author;
    }

    public String getBody() {
        return body;
    }
}
