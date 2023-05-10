package com.ftn.kvtsvtprojekat.service.impl;

import com.ftn.kvtsvtprojekat.model.Comment;
import com.ftn.kvtsvtprojekat.repository.CommentRepository;
import com.ftn.kvtsvtprojekat.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    
    public final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> findAll(){
        return commentRepository.findAll();
    }

    @Override
    public Comment findOneById(Long id){
        return commentRepository.findCommentById(id);
    }

    @Override
    public Comment addComment(Comment comment){
        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(Comment comment){
        return commentRepository.save(comment);
    }

    @Override
    public Comment deleteComment(Long id){
        return commentRepository.deleteCommentById(id);
    }
}
