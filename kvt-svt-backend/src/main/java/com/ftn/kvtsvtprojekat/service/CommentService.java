package com.ftn.kvtsvtprojekat.service;

import com.ftn.kvtsvtprojekat.model.Banned;
import com.ftn.kvtsvtprojekat.model.Comment;
import com.ftn.kvtsvtprojekat.model.Post;

import java.util.List;

public interface CommentService {
    List<Comment> findAll();

    Comment findOneById(Long id);

    Comment save(Comment comment);

    void delete(Long id);

    List<Comment> findByPost(Post post);
}
