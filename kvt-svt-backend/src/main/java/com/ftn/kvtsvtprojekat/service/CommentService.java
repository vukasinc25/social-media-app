package com.ftn.kvtsvtprojekat.service;

import com.ftn.kvtsvtprojekat.model.Banned;
import com.ftn.kvtsvtprojekat.model.Comment;
import com.ftn.kvtsvtprojekat.model.Post;
import com.ftn.kvtsvtprojekat.model.User;

import java.util.List;

public interface CommentService {
    List<Comment> findAll();

    List<Comment> findAllByParentComment(Comment parentComment);
    
    List<Comment> findAllByParentCommentAndIsDeletedFalse(Comment parentComment);

    List<Comment> findAllByUser(User user);
    
    List<Comment> findAllByUserAndIsDeletedFalse(User user);

    Comment findOneById(Long id);

    Comment save(Comment comment);

    void delete(Long id);

    List<Comment> findByPost(Post post);
    
    List<Comment> findByPostAndIsDeletedFalse(Post post);

    List<Comment> findByPostOrderByIdDesc(Post post);
    
    List<Comment> findByPostAndIsDeletedFalseOrderByIdDesc(Post post);

    List<Comment> findByPostOrderByLikes();
    
    List<Comment> findByPostAndIsDeletedFalseOrderByLikes();
}
