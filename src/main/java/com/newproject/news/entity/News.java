package com.newproject.news.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class News {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
    private String title;
    private String anonce;
    private String img;
    @Column(length = 2048)
    private String fullText;
    private String date;
    private String video;
    @ManyToOne
    @JoinColumn(name="category_id")
    @JsonIgnore
    private Category category;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy="news", orphanRemoval=true)
    private List<Comments> comments = new ArrayList<Comments>();
    private String url;


    public News() {
    }

    public News(String title, String anonce, String fullText, String date, String video, Category category, String url) {
        this.title = title;
        this.anonce = anonce;
        this.fullText = fullText;
        this.date = date;
        this.video = video;
        this.category = category;
        this.url = url;
    }

    public News(String title, String anonce, String img, String fullText, String date, String video, Category category) {
        this.title = title;
        this.anonce = anonce;
        this.img = img;
        this.fullText = fullText;
        this.date = date;
        this.video = video;
        this.category = category;
    }



    public News(String title, String anonce, String img, String fullText, String date, Category category) {
        this.title = title;
        this.anonce = anonce;
        this.img = img;
        this.fullText = fullText;
        this.date = date;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnonce() {
        return anonce;
    }

    public void setAnonce(String anonce) {
        this.anonce = anonce;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return Objects.equals(id, news.id) && Objects.equals(title, news.title) && Objects.equals(anonce, news.anonce) && Objects.equals(img, news.img) && Objects.equals(fullText, news.fullText) && Objects.equals(date, news.date) && Objects.equals(video, news.video) && Objects.equals(category, news.category) && Objects.equals(comments, news.comments) && Objects.equals(url, news.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, anonce, img, fullText, date, video, category, comments, url);
    }
}
