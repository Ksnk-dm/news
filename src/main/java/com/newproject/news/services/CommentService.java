package com.newproject.news.services;

import com.newproject.news.entity.Comments;
import com.newproject.news.entity.News;
import com.newproject.news.repository.ComentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CommentService {
    @Autowired
    private ComentRepository comentRepository;

    @Transactional
    public void addCom(Comments comments) {
        comentRepository.save(comments);
    }

    @Transactional(readOnly=true)
    public Page<Comments> findByNews(News news, Pageable pageable) {
        return comentRepository.findByNews(news,pageable);
    }

    @Transactional(readOnly=true)
    public Page<Comments> findAll(Pageable pageable) {
        return comentRepository.findAll(pageable);
    }

    @Transactional
    public void deleteComment(long id) {
        comentRepository.deleteById(id);
    }
}
