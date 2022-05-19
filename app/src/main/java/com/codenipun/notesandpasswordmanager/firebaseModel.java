package com.codenipun.notesandpasswordmanager;

public class firebaseModel {
    String title, content;

    public firebaseModel(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public firebaseModel(){}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
