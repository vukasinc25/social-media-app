package com.ftn.kvtsvtprojekat.service;

import com.ftn.kvtsvtprojekat.model.Comment;
import com.ftn.kvtsvtprojekat.model.Post;
import com.ftn.kvtsvtprojekat.model.Reaction;

import java.util.List;

public interface ReactionService {
    List<Reaction> findAll();

    List<Reaction> findAllByPost(Post post);

    List<Reaction> findAllByComment(Comment comment);

    Reaction findOneById(Long id);

    Reaction save(Reaction reaction);

    void delete(Long id);
}
