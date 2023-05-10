package com.ftn.kvtsvtprojekat.service;

import com.ftn.kvtsvtprojekat.model.Reaction;

import java.util.List;

public interface ReactionService {
    List<Reaction> findAll();

    Reaction findOneById(Long id);

    Reaction addReaction(Reaction reaction);

    Reaction updateReaction(Reaction reaction);

    Reaction deleteReaction(Long id);
}
