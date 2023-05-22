package com.ftn.kvtsvtprojekat.service;

import com.ftn.kvtsvtprojekat.model.Post;
import com.ftn.kvtsvtprojekat.model.Reaction;

import java.util.List;

public interface ReactionService {
    List<Reaction> findAll();

    List<Reaction> findAllByPost(Post post);

    Reaction findOneById(Long id);

    Reaction save(Reaction reaction);

    void delete(Long id);
}
