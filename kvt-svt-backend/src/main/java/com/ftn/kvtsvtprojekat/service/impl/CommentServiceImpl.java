package com.ftn.kvtsvtprojekat.service.impl;

import com.ftn.kvtsvtprojekat.model.Comment;
import com.ftn.kvtsvtprojekat.model.Comment;
import com.ftn.kvtsvtprojekat.model.Post;
import com.ftn.kvtsvtprojekat.model.User;
import com.ftn.kvtsvtprojekat.repository.CommentRepository;
import com.ftn.kvtsvtprojekat.service.CommentService;
import org.modelmapper.ModelMapper;
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
    public List<Comment> findAllByParentComment(Comment parentComment) {
        return commentRepository.findAllByParentComment(parentComment);
    }
    
    @Override
    public List<Comment> findAllByParentCommentAndIsDeletedFalse(Comment parentComment) {
        return commentRepository.findAllByParentCommentAndIsDeletedFalse(parentComment);
    }

    @Override
    public List<Comment> findAllByUser(User user) {
        return commentRepository.findAllByUser(user);
    }
    
    @Override
    public List<Comment> findAllByUserAndIsDeletedFalse(User user) {
        return commentRepository.findAllByUserAndIsDeletedFalse(user);
    }

    @Override
    public Comment findOneById(Long id){
        return commentRepository.findCommentById(id);
    }

    @Override
    public Comment save(Comment comment){
        return commentRepository.save(comment);
    }

    @Override
    public void delete(Long id){
        Comment comment = commentRepository.findCommentById(id);
        comment.setIsDeleted(true);
        commentRepository.save(comment);
    }

    @Override
    public List<Comment> findByPost(Post post) {
         return commentRepository.findAllByPost(post);

    }
    
    @Override
    public List<Comment> findByPostAndIsDeletedFalse(Post post) {
         return commentRepository.findAllByPostAndIsDeletedFalse(post);

    }

    @Override
    public List<Comment> findByPostOrderByIdDesc(Post post) {
        return commentRepository.findAllByPostOrderByIdDesc(post);
    }
    
    @Override
    public List<Comment> findByPostAndIsDeletedFalseOrderByIdDesc(Post post) {
        return commentRepository.findAllByPostAndIsDeletedFalseOrderByIdDesc(post);
    }

    @Override
    public List<Comment> findByPostOrderByLikes() {
        return commentRepository.findAllByPostOrderByReactionDesc();
    }
    
    @Override
    public List<Comment> findByPostAndIsDeletedFalseOrderByLikes() {
        return commentRepository.findAllByPostAndIsDeletedFalseOrderByReactionDesc();
    }
}
