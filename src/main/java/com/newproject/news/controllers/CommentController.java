package com.newproject.news.controllers;

import com.newproject.news.entity.Category;
import com.newproject.news.entity.Comments;
import com.newproject.news.entity.News;
import com.newproject.news.entity.User;
import com.newproject.news.services.CommentService;
import com.newproject.news.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class CommentController {
    @Autowired
    CommentService commentService;
    @Autowired
    NewsService newsService;

    @PostMapping("/news/comments/{id}/del")
    public String delCom(@PathVariable(value = "id") Long idC, Model model) {
        commentService.deleteComment(idC);
        return "redirect:/";
    }

    @GetMapping("/news/{id}/addcom")
    public String addCom(@PathVariable(value = "id") Long id, Model model) {
        return "addcom";
    }

    @PostMapping("/news/{id}/addcom")
    public String addCom(@PathVariable(value = "id") Long id, @RequestParam String text, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        News news = newsService.findNewsById(id);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss ");
        Comments comments = new Comments( text, dateFormat.format(new Date()), news,user);
        commentService.addCom(comments);
        newsService.updateNews(news);
        return "redirect:/news/{id}";
    }

}
