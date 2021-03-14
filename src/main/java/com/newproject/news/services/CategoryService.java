package com.newproject.news.services;

import com.newproject.news.entity.Category;
import com.newproject.news.entity.News;
import com.newproject.news.repository.CategoryRepository;
import com.newproject.news.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    @Transactional(readOnly=true)
    public List<Category> findCategory() {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly=true)
    public Category findCategory(long id) {
        return categoryRepository.findById(id).get();
    }
}
