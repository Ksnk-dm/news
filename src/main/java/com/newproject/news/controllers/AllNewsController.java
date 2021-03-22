package com.newproject.news.controllers;


import com.newproject.news.services.CategoryService;
import com.newproject.news.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class AllNewsController {
    @Autowired
    private NewsService newsService;
    @Autowired
    private CategoryService categoryService;
    @GetMapping("news/allnews")
    public String allNews(Model model, HttpServletRequest request){
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMM yyyy");
        int page = 0;
        int size = 25;
        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }

        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }
        Pageable sortedById = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("date", dateFormat.format(new Date()));
        model.addAttribute("news", newsService.findAll(sortedById));
        model.addAttribute("category", categoryService.findCategory());
        return "blog";
    }
}
