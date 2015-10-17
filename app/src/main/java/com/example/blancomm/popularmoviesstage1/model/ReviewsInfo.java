package com.example.blancomm.popularmoviesstage1.model;

/**
 * Created by manuel on 17/10/15.
 */
public class ReviewsInfo {

    private String id;
    private String author;
    private String content;
    private String url;

    public ReviewsInfo(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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
}
