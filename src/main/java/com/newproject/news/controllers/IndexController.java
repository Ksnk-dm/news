package com.newproject.news.controllers;

import com.newproject.news.entity.Category;
import com.newproject.news.entity.News;
import com.newproject.news.repository.NewsRepository;
import com.newproject.news.services.CategoryService;
import com.newproject.news.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class IndexController {

    @Autowired
    private NewsService newsService;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private NewsRepository newsRepository;

    @Value("${site.indexSetting.mainCategory}")
    private String mainCategory;
    @Value("${site.indexSetting.blockCategory}")
    private String blockCategory;

    @GetMapping("/")
    public String viewIndexPage(Model model, HttpServletRequest request) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMM yyyy");
        List<News> oneNews = newsService.findByCategory(mainCategory);
        List<News> category = newsService.findByCategory(blockCategory);
        Pageable sortedById = PageRequest.of(0, 4, Sort.by(Sort.Direction.DESC, "id"));
        Pageable sortedById2 = PageRequest.of(1, 3, Sort.by(Sort.Direction.DESC, "id"));
        List<Category> categories = categoryService.findCategory();
        Collections.reverse(category);
        model.addAttribute("all", newsService.findAll());
        if(oneNews.isEmpty()){
            model.addAttribute("topNews", oneNews);
        } else {
            model.addAttribute("topNews", oneNews.get(oneNews.size() - 1));
        }
        model.addAttribute("allNews", newsService.findAll(sortedById));
        model.addAttribute("allNews2", newsService.findAll(sortedById2));
        model.addAttribute("category", categories);
        model.addAttribute("category2",category);
        model.addAttribute("date", dateFormat.format(new Date()));
        return "index";
    }




}
