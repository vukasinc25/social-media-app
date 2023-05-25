package com.ftn.kvtsvtprojekat.controller;

import com.ftn.kvtsvtprojekat.model.Comment;
import com.ftn.kvtsvtprojekat.model.Reaction;
import com.ftn.kvtsvtprojekat.model.Post;
import com.ftn.kvtsvtprojekat.model.dto.ReactionDTO;
import com.ftn.kvtsvtprojekat.model.enums.ReactionType;
import com.ftn.kvtsvtprojekat.service.CommentService;
import com.ftn.kvtsvtprojekat.service.PostService;
import com.ftn.kvtsvtprojekat.service.ReactionService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/reaction")
public class ReactionController {
    private final ModelMapper modelMapper;
    private final ReactionService reactionService;
    private final PostService postService;
//    private final CommentService commentService;

    public ReactionController(ModelMapper modelMapper, ReactionService reactionService, PostService postService, CommentService commentService) {
        this.modelMapper = modelMapper;
        this.reactionService = reactionService;
        this.postService = postService;
//        this.commentService = commentService;
    }

    @GetMapping("/byPost/{id}")
    public ResponseEntity<List<ReactionDTO>> getReactionsForPost(@PathVariable("id") Long postId) {

        Post post = postService.findOneById(postId);
        List<Reaction> reactions = reactionService.findAllByPost(post);
        List<ReactionDTO> reactionsDTO = new ArrayList<>();
        for (Reaction reaction : reactions) {
            ReactionDTO reactionDTO = modelMapper.map(reaction, ReactionDTO.class);
            reactionsDTO.add(reactionDTO);
        }

        return new ResponseEntity<>(reactionsDTO, HttpStatus.OK);
    }

//    @GetMapping("/byComment/{id}")
//    public ResponseEntity<List<ReactionDTO>> getReactionsForComment(@PathVariable("id") Long commentId) {
//
//        Comment comment = commentService.findOneById(commentId);
//        List<Reaction> reactions = reactionService.findAllBy(comment);
//        List<ReactionDTO> reactionsDTO = new ArrayList<>();
//        for (Reaction reaction : reactions) {
//            ReactionDTO reactionDTO = modelMapper.map(reaction, ReactionDTO.class);
//            reactionsDTO.add(reactionDTO);
//        }
//
//        return new ResponseEntity<>(reactionsDTO, HttpStatus.OK);
//    }

    //TODO Sve reakcije po komentaru

    @GetMapping(value = "/{id}")
    public ResponseEntity<ReactionDTO> getReaction(@PathVariable("id") Long id) {
        Reaction reaction = reactionService.findOneById(id);

        if (reaction == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ReactionDTO reactionDTO = modelMapper.map(reaction, ReactionDTO.class);

        return status(HttpStatus.OK).body(reactionDTO);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Reaction> createReaction(@Valid @RequestBody ReactionDTO reactionDTO) {

        Reaction reaction = modelMapper.map(reactionDTO, Reaction.class);
        reactionService.save(reaction);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ReactionDTO> updateReaction(@PathVariable("id") Long id, @RequestBody ReactionDTO reactionDTO) {
        Reaction reaction = reactionService.findOneById(id);
        if(reaction == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        reaction.setReactionType(ReactionType.valueOf(reactionDTO.getReactionType()));

        reactionService.save(reaction);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteReaction(@PathVariable Long id) {
        if(id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Reaction reaction = reactionService.findOneById(id);

        if (reaction != null) {
            reaction.setIsDeleted(true);
            reactionService.save(reaction);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }
}
