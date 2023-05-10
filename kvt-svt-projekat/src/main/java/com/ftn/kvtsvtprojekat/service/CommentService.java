package com.ftn.kvtsvtprojekat.service;

import com.ftn.kvtsvtprojekat.model.Banned;
import com.ftn.kvtsvtprojekat.model.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> findAll();

    Comment findOneById(Long id);

    Comment addComment(Comment comment);

    Comment updateComment(Comment comment);

    Comment deleteComment(Long id);
}
