package com.ftn.kvtsvtprojekat.service.impl;

import com.ftn.kvtsvtprojekat.model.Comment;
import com.ftn.kvtsvtprojekat.model.Post;
import com.ftn.kvtsvtprojekat.model.Reaction;
import com.ftn.kvtsvtprojekat.model.Reaction;
import com.ftn.kvtsvtprojekat.repository.ReactionRepository;
import com.ftn.kvtsvtprojekat.service.ReactionService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReactionServiceImpl implements ReactionService {
    
    public final ReactionRepository reactionRepository;
    
    public ReactionServiceImpl(ReactionRepository reactionRepository, ModelMapper modelMapper) {
        this.reactionRepository = reactionRepository;
    }

    @Override
    public List<Reaction> findAll(){
        return reactionRepository.findAll();
    }

    @Override
    public List<Reaction> findAllByPost(Post post) {
        return reactionRepository.findAllByPost(post);
    }

    @Override
    public List<Reaction> findAllByComment(Comment comment) {
        return reactionRepository.findAllByComment(comment);
    }

    @Override
    public Reaction findOneById(Long id){
        return reactionRepository.findReactionById(id);
    }

    @Override
    public Reaction findOneByPostIdAndUserId(Long postId, Long userId) {
        return reactionRepository.findOneByPostIdAndUserId(postId, userId);
    }

    @Override
    public Reaction findOneByCommentIdAndUserId(Long commentId, Long userId) {
        return reactionRepository.findOneByCommentIdAndUserId(commentId, userId);
    }

    @Override
    public Reaction save(Reaction reaction){
        return reactionRepository.save(reaction);
    }

    @Override
    public void delete(Long id){
        Reaction reaction = reactionRepository.findReactionById(id);
        reactionRepository.delete(reaction);
    }
}
