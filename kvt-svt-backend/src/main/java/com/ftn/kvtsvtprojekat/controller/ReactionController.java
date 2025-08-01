package com.ftn.kvtsvtprojekat.controller;

import com.ftn.kvtsvtprojekat.model.Comment;
import com.ftn.kvtsvtprojekat.model.Reaction;
import com.ftn.kvtsvtprojekat.model.Post;
import com.ftn.kvtsvtprojekat.model.dto.ReactionDTO;
import com.ftn.kvtsvtprojekat.model.enums.ReactionType;
import com.ftn.kvtsvtprojekat.service.CommentService;
import com.ftn.kvtsvtprojekat.service.PostService;
import com.ftn.kvtsvtprojekat.service.ReactionService;
import com.ftn.kvtsvtprojekat.indexservice.PostIndexingService;
import com.ftn.kvtsvtprojekat.indexservice.GroupIndexingService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/reaction")
public class ReactionController {
    private final ModelMapper modelMapper;
    private final ReactionService reactionService;
    private final PostService postService;
    private final CommentService commentService;
    private final PostIndexingService postIndexingService;
    private final GroupIndexingService groupIndexingService;

    public ReactionController(ModelMapper modelMapper, ReactionService reactionService, PostService postService, CommentService commentService, PostIndexingService postIndexingService, GroupIndexingService groupIndexingService) {
        this.modelMapper = modelMapper;
        this.reactionService = reactionService;
        this.postService = postService;
        this.commentService = commentService;
        this.postIndexingService = postIndexingService;
        this.groupIndexingService = groupIndexingService;
    }

    @GetMapping("/byPost/{id}")
    public ResponseEntity<List<ReactionDTO>> getReactionsForPost(@PathVariable("id") Long postId) {

        Post post = postService.findOneById(postId);
        List<Reaction> reactions = reactionService.findAllByPost(post);
        List<ReactionDTO> reactionsDTO = new ArrayList<>();
        for (Reaction reaction : reactions) {
            if(!reaction.getIsDeleted()) {
                ReactionDTO reactionDTO = modelMapper.map(reaction, ReactionDTO.class);
                reactionsDTO.add(reactionDTO);
            }
        }
        return new ResponseEntity<>(reactionsDTO, HttpStatus.OK);
    }

    @GetMapping("/byComment/{id}")
    public ResponseEntity<List<ReactionDTO>> getReactionsForComment(@PathVariable("id") Long commentId) {

        Comment comment = commentService.findOneById(commentId);
        List<Reaction> reactions = reactionService.findAllByComment(comment);
        List<ReactionDTO> reactionsDTO = new ArrayList<>();
        for (Reaction reaction : reactions) {
            if(!reaction.getIsDeleted()) {
                ReactionDTO reactionDTO = modelMapper.map(reaction, ReactionDTO.class);
                reactionsDTO.add(reactionDTO);
            }
        }

        return new ResponseEntity<>(reactionsDTO, HttpStatus.OK);
    }

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
        reaction.setIsDeleted(false);
        reaction.setReactionTime(LocalDateTime.now());
        
        // Update Elasticsearch like count if this is a post reaction
        if (reactionDTO.getPostId() != null) {
            try {
                System.out.println("Updating like count for post: " + reactionDTO.getPostId());
                postIndexingService.updateLikeCount(reactionDTO.getPostId().toString());
                
                // Update group like count and number of posts with likes if post belongs to a group
                if (reactionDTO.getGroupId() != null) {
                    System.out.println("Updating like count for group: " + reactionDTO.getGroupId());
                    groupIndexingService.updateLikeCount(reactionDTO.getGroupId().toString());
                }
            } catch (Exception e) {
                // Log error but don't fail the request
                System.err.println("Failed to update Elasticsearch like count: " + e.getMessage());
            }
        }
        
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

    @PutMapping(value = "/delete")
    public ResponseEntity<Void> deleteReaction(@RequestBody ReactionDTO reactionDTO) {
        if(reactionDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(reactionDTO.getPostId() != null) {
            Reaction reaction = reactionService.findOneByPostIdAndUserId(reactionDTO.getPostId(), reactionDTO.getUserId());
            if (reaction != null) {
                reactionService.delete(reaction.getId());
                
                // Update Elasticsearch like count
                try {
                    postIndexingService.deleteLikeCount(reactionDTO.getPostId().toString());
                    
                    // Update group like count if post belongs to a group
                    if (reactionDTO.getGroupId() != null) {
                        groupIndexingService.deleteLikeCount(reactionDTO.getGroupId().toString());
                    }
                } catch (Exception e) {
                    // Log error but don't fail the request
                    System.err.println("Failed to update Elasticsearch like count: " + e.getMessage());
                }
                
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
            }
        } else if (reactionDTO.getCommentId() != null) {
            Reaction reaction = reactionService.findOneByCommentIdAndUserId(reactionDTO.getCommentId(), reactionDTO.getUserId());
            if (reaction != null) {
                reactionService.delete(reaction.getId());
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
            }
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
