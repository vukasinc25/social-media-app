package com.ftn.kvtsvtprojekat.service.impl;

import com.ftn.kvtsvtprojekat.model.Reaction;
import com.ftn.kvtsvtprojekat.repository.ReactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReactionServiceImpl {
    
    public final ReactionRepository reactionRepository;

    public ReactionServiceImpl(ReactionRepository reactionRepository) {
        this.reactionRepository = reactionRepository;
    }

    public List<Reaction> findAll(){
        return reactionRepository.findAll();
    }

    public Reaction findOneById(Long id){
        return reactionRepository.findReactionById(id);
    }

    public Reaction addReaction(Reaction reaction){
        return reactionRepository.save(reaction);
    }

    public Reaction updateReaction(Reaction reaction){
        return reactionRepository.save(reaction);
    }

    public Reaction deleteReaction(Long id){
        return reactionRepository.deleteReactionById(id);
    }
}
