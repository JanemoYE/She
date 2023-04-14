package com.aboutlive.aboutlive.Entity;

public class Information {
    private int id;
    private String userKey;
    private String content;
    private String type;
    private String sendUserKey;
    private int read;
    private String articleId;

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSendUserKey() {
        return sendUserKey;
    }

    public void setSendUserKey(String sendUserKey) {
        this.sendUserKey = sendUserKey;
    }
}
