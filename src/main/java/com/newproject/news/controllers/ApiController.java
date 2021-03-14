package com.newproject.news.controllers;

import com.newproject.news.entity.News;
import com.newproject.news.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApiController {
    @Autowired
    private NewsService newsService;


    @GetMapping("/api")
    public List getAllNews() {
        List<News> news = newsService.findAll();
        System.out.println(news);
        return news;
    }

    @PostMapping("/api/category")
    public List getByCategory(@RequestParam("category") String category) {
        List<News> news = newsService.findByCategory(category);
        return news;
    }

}
