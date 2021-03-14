package com.newproject.news.repository;

import com.newproject.news.entity.Comments;
import com.newproject.news.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {
    @Query("SELECT c FROM News c WHERE c.category.name = :category ")
    List<News> findByCategory(@Param("category") String category);
}
