package com.ftn.kvtsvtprojekat.service;

import com.ftn.kvtsvtprojekat.model.Reaction;

import java.util.List;

public interface ReactionService {
    List<Reaction> findAll();

    Reaction findOneById(Long id);

    Reaction save(Reaction reaction);

    Reaction delete(Long id);
}
