package com.ftn.kvtsvtprojekat.controller;

import com.ftn.kvtsvtprojekat.model.Comment;
import com.ftn.kvtsvtprojekat.model.Post;
import com.ftn.kvtsvtprojekat.model.User;
import com.ftn.kvtsvtprojekat.model.dto.CommentDTO;
import com.ftn.kvtsvtprojekat.repository.CommentRepository;
import com.ftn.kvtsvtprojekat.service.CommentService;
import com.ftn.kvtsvtprojekat.service.PostService;
import com.ftn.kvtsvtprojekat.service.UserService;
import com.ftn.kvtsvtprojekat.indexservice.PostIndexingService;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    public final CommentService commentService;
    public final PostService postService;
    public final UserService userService;
    public final ModelMapper modelMapper;
    public final CommentRepository commentRepository;
    public final PostIndexingService postIndexingService;

    public CommentController(CommentService commentService, PostService postService, UserService userService, ModelMapper modelMapper, CommentRepository commentRepository, PostIndexingService postIndexingService) {
        this.commentService = commentService;
        this.postService = postService;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.commentRepository = commentRepository;
        this.postIndexingService = postIndexingService;
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<List<CommentDTO>> getChildComments(@PathVariable("id") Long parentId) {

        Comment commentParent = commentService.findOneById(parentId);
        List<Comment> comments = commentService.findAllByParentCommentAndIsDeletedFalse(commentParent);
        return getListResponseEntity(comments);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CommentDTO>> getComments() {

        List<Comment> comments = commentService.findAll();
        List<CommentDTO> commentsDTO = new ArrayList<>();
        for (Comment comment : comments) {
            if(!comment.getIsDeleted()){
                CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);
                commentsDTO.add(commentDTO);
            }
        }

        return new ResponseEntity<>(commentsDTO, HttpStatus.OK);
    }

    @GetMapping("/deleted")
    public ResponseEntity<List<CommentDTO>> getCommentsDeleted() {

        List<Comment> comments = commentService.findAll();
        List<CommentDTO> commentsDTO = new ArrayList<>();
        for (Comment comment : comments) {
            if(comment.getIsDeleted()){
                CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);
                commentsDTO.add(commentDTO);
            }
        }

        return new ResponseEntity<>(commentsDTO, HttpStatus.OK);
    }

    @GetMapping("/byUser/{id}")
    public ResponseEntity<List<CommentDTO>> getCommentsByUser(@PathVariable("id") Long userId) {

        User user = userService.findOneById(userId);
        List<Comment> comments = commentService.findAllByUserAndIsDeletedFalse(user);
        return getListResponseEntity(comments);
    }

    @GetMapping("/byPost/{id}")
    public ResponseEntity<List<CommentDTO>> getCommentsForPost(@PathVariable("id") Long postId) {

        Post post = postService.findOneById(postId);
        List<Comment> comments = commentService.findByPostAndIsDeletedFalse(post);
        return getListResponseEntity(comments);
    }

    @GetMapping("/byPostDesc/{id}")
    public ResponseEntity<List<CommentDTO>> getCommentsForPostDesc(@PathVariable("id") Long postId) {

        Post post = postService.findOneById(postId);
        List<Comment> comments = commentService.findByPostAndIsDeletedFalseOrderByIdDesc(post);
        return getListResponseEntity(comments);
    }

    @GetMapping("/byPostReaction/{id}")
    public ResponseEntity<List<CommentDTO>> getCommentsForPostByReaction(@PathVariable("id") Long postId) {

        List<Comment> comments;
        if(postId == 1){
            comments = commentService.findByPostAndIsDeletedFalseOrderByLikes();
        } else if (postId == 2) {
            comments = commentRepository.findAllByPostAndIsDeletedFalseOrderByReactionLikeAsc();
        } else if (postId == 3) {
            comments = commentRepository.findAllByPostAndIsDeletedFalseOrderByReactionDislikeDesc();
        }
        else if (postId == 4) {
            comments = commentRepository.findAllByPostAndIsDeletedFalseOrderByReactionDislikeAsc();
        } else if (postId == 5) {
            comments = commentRepository.findAllByPostAndIsDeletedFalseOrderByReactionHeartDesc();
        } else if (postId == 6) {
            comments = commentRepository.findAllByPostAndIsDeletedFalseOrderByReactionHeartAsc();
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        return getListResponseEntity(comments);
    }

    @NotNull
    private ResponseEntity<List<CommentDTO>> getListResponseEntity(List<Comment> comments) {
        List<CommentDTO> commentsDTO = new ArrayList<>();
        for (Comment comment : comments) {
            CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);
            commentsDTO.add(commentDTO);
        }
        return new ResponseEntity<>(commentsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CommentDTO> getComment(@PathVariable("id") Long id) {
        Comment comment = commentService.findOneById(id);

        if (comment == null || comment.getIsDeleted()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);

        return status(HttpStatus.OK).body(commentDTO);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Comment> createComment(@Valid @RequestBody CommentDTO commentDTO) {

        Comment comment = modelMapper.map(commentDTO, Comment.class);
        Comment comment2 = commentService.findOneById(commentDTO.getParentCommentId());
        comment.setParentComment(comment2);
        comment.setIsDeleted(false);
        commentService.save(comment);
        
        // Update Elasticsearch comment count and add comment text to index
        if (comment.getPost() != null && comment.getPost().getId() != null) {
            try {
                postIndexingService.updateCommentCount(comment.getPost().getId().toString());
                postIndexingService.createCommentTextIndex(comment);
            } catch (Exception e) {
                // Log error but don't fail the request
                System.err.println("Failed to update Elasticsearch comment index: " + e.getMessage());
            }
        }
        
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable("id") Long id, @RequestBody CommentDTO commentDTO) {
        Comment comment = commentService.findOneById(id);
        if(comment == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        comment.setText(commentDTO.getText());
        comment.setIsDeleted(commentDTO.getIsDeleted());

        commentService.save(comment);
        
        // Update Elasticsearch comment text index
        if (comment.getPost() != null && comment.getPost().getId() != null) {
            try {
                postIndexingService.updateCommentTextIndex(comment);
            } catch (Exception e) {
                // Log error but don't fail the request
                System.err.println("Failed to update Elasticsearch comment text index: " + e.getMessage());
            }
        }
        
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        if(id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Comment comment = commentService.findOneById(id);

        if (comment != null) {
            comment.setIsDeleted(true);
            commentService.save(comment);
            
            // Update Elasticsearch comment count and remove comment text from index
            if (comment.getPost() != null && comment.getPost().getId() != null) {
                try {
                    postIndexingService.deleteCommentCount(comment.getPost().getId().toString());
                    postIndexingService.deleteCommentTextIndex(comment);
                } catch (Exception e) {
                    // Log error but don't fail the request
                    System.err.println("Failed to update Elasticsearch comment index: " + e.getMessage());
                }
            }
            
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }
}
