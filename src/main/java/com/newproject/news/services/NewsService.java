package com.newproject.news.services;

import com.newproject.news.entity.Category;
import com.newproject.news.entity.News;
import com.newproject.news.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;


    @Transactional
    public void addNews(News news) {
        newsRepository.save(news);
    }

    @Transactional
    public List<News> findAll() {
        return newsRepository.findAll();
    }
    @Transactional
    public List<News> findByCategory(String category) {
        return newsRepository.findByCategory(category);
    }

    @Transactional(readOnly=true)
    public Optional<News> findNews(long id) {
        return newsRepository.findById(id);
    }

    @Transactional(readOnly=true)
    public Page<News> findAll(Pageable pageable) {
        return newsRepository.findAll(pageable);
    }

    @Transactional
    public News findNewsById(long id) {
        return newsRepository.findById(id).get();
    }

    @Transactional
    public void deleteNews(long id) {
    newsRepository.deleteById(id);
    }

    @Transactional(readOnly=true)
    public Boolean findNewsId(long id){
        return  newsRepository.findById(id).isPresent();
    }

    @Transactional
    public void updateNews(News news) {
        newsRepository.save(news);
    }
}
