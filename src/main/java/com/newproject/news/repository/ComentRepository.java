package com.newproject.news.repository;

import com.newproject.news.entity.Category;
import com.newproject.news.entity.Comments;
import com.newproject.news.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ComentRepository extends JpaRepository<Comments, Long> {
    @Query("SELECT c FROM Comments c WHERE c.news = :comments")
    Page<Comments> findByNews(@Param("comments") News news, Pageable pageable);
}
