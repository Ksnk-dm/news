package com.newproject.news.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class News {
    @Id
    @GeneratedValue()
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
    @OneToMany(mappedBy="news", cascade=CascadeType.REMOVE, orphanRemoval=true)
    private List<Comments> comments = new ArrayList<Comments>();


    public News() {
    }

    public News(String title) {
        this.title = title;
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

    public News(String title, String anonce, String img, String fullText, String date) {
        this.title = title;
        this.anonce = anonce;
        this.img = img;
        this.fullText = fullText;
        this.date = date;
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




}
