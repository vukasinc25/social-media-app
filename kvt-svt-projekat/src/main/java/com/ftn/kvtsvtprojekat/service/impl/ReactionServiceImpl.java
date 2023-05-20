package com.ftn.kvtsvtprojekat.service.impl;

import com.ftn.kvtsvtprojekat.model.Reaction;
import com.ftn.kvtsvtprojekat.repository.ReactionRepository;
import com.ftn.kvtsvtprojekat.service.ReactionService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReactionServiceImpl implements ReactionService {
    
    public final ReactionRepository reactionRepository;
    private final ModelMapper modelMapper;


    public ReactionServiceImpl(ReactionRepository reactionRepository, ModelMapper modelMapper) {
        this.reactionRepository = reactionRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Reaction> findAll(){
        return reactionRepository.findAll();
    }

    @Override
    public Reaction findOneById(Long id){
        return reactionRepository.findReactionById(id);
    }

    @Override
    public Reaction save(Reaction reaction){
        return reactionRepository.save(reaction);
    }

    @Override
    public Reaction delete(Long id){
        return reactionRepository.deleteReactionById(id);
    }
}
