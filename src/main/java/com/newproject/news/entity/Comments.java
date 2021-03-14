package com.newproject.news.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String text;
    private String date;
    @ManyToOne
    @JoinColumn(name="news_id")
    private News news;
    @OneToOne
    private User user;

    public Comments() {
    }


    public Comments(String text, String date, News news, User user) {
        this.text = text;
        this.date = date;
        this.news = news;
        this.user = user;
    }

    public Comments(String text, String date, News news) {
        this.text = text;
        this.date = date;
        this.news = news;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
