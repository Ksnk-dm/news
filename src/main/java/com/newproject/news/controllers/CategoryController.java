package com.newproject.news.controllers;

import com.newproject.news.entity.Category;
import com.newproject.news.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CategoryController {


    @Autowired
    private CategoryService categoryService;
    @GetMapping("/news/addCategory")
    public String addCategory(Model model){
        return "addcategory";
    }

    @PostMapping("/news/addCategory")
    public String addNews(@RequestParam String name, Model model) {
        Category category = new Category(name);
        categoryService.addCategory(category);
        return "redirect:/";
    }
}
