package com.newproject.news.entity;


import javax.persistence.*;
import java.util.Objects;


@Entity
public class Comments {
    @Id
    @GeneratedValue
    private Long id ;
    private String text;
    private String date;
    @ManyToOne
    @JoinColumn(name="news_id")
    private News news;
    @ManyToOne
    @JoinColumn(name="user_id")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comments comments = (Comments) o;
        return Objects.equals(id, comments.id) && Objects.equals(text, comments.text) && Objects.equals(date, comments.date) && Objects.equals(news, comments.news) && Objects.equals(user, comments.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, date, news, user);
    }

    @Override
    public String toString() {
        return "Comments{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", date='" + date + '\'' +
                ", news=" + news +
                ", user=" + user +
                '}';
    }
}
