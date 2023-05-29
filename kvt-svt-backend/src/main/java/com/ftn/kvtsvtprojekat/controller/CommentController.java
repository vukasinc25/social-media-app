package com.ftn.kvtsvtprojekat.controller;

import com.ftn.kvtsvtprojekat.model.Comment;
import com.ftn.kvtsvtprojekat.model.Post;
import com.ftn.kvtsvtprojekat.model.User;
import com.ftn.kvtsvtprojekat.model.dto.CommentDTO;
import com.ftn.kvtsvtprojekat.repository.CommentRepository;
import com.ftn.kvtsvtprojekat.service.CommentService;
import com.ftn.kvtsvtprojekat.service.PostService;
import com.ftn.kvtsvtprojekat.service.UserService;
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

    public CommentController(CommentService commentService, PostService postService, UserService userService, ModelMapper modelMapper, CommentRepository commentRepository) {
        this.commentService = commentService;
        this.postService = postService;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.commentRepository = commentRepository;
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<List<CommentDTO>> getChildComments(@PathVariable("id") Long parentId) {

        Comment commentParent = commentService.findOneById(parentId);
        List<Comment> comments = commentService.findAllByParentComment(commentParent);
        List<CommentDTO> commentsDTO = new ArrayList<>();
        for (Comment comment : comments) {
            if(!comment.getIsDeleted()){
                CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);
                commentsDTO.add(commentDTO);
            }
        }

        return new ResponseEntity<>(commentsDTO, HttpStatus.OK);
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
        List<Comment> comments = commentService.findAllByUser(user);
        List<CommentDTO> commentsDTO = new ArrayList<>();
        for (Comment comment : comments) {
            if(!comment.getIsDeleted()){
                CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);
                commentsDTO.add(commentDTO);
            }
        }

        return new ResponseEntity<>(commentsDTO, HttpStatus.OK);
    }

    @GetMapping("/byPost/{id}")
    public ResponseEntity<List<CommentDTO>> getCommentsForPost(@PathVariable("id") Long postId) {

        Post post = postService.findOneById(postId);
        List<Comment> comments = commentService.findByPost(post);
        return getListResponseEntity(comments);
    }

    @GetMapping("/byPostDesc/{id}")
    public ResponseEntity<List<CommentDTO>> getCommentsForPostDesc(@PathVariable("id") Long postId) {

        Post post = postService.findOneById(postId);
        List<Comment> comments = commentService.findByPostOrderByIdDesc(post);
        return getListResponseEntity(comments);
    }

    @GetMapping("/byPostReaction/{id}")
    public ResponseEntity<List<CommentDTO>> getCommentsForPostByReaction(@PathVariable("id") Long postId) {

        if(postId == 1){
            List<Comment> comments = commentService.findByPostOrderByLikes();
            return getListResponseEntity(comments);
        } else if (postId == 2) {
            List<Comment> comments = commentRepository.findAllByPostOrderByReactionLikeAsc();
            return getListResponseEntity(comments);
        } else if (postId == 3) {
            List<Comment> comments = commentRepository.findAllByPostOrderByReactionDislikeDesc();
            return getListResponseEntity(comments);
        }
        else if (postId == 4) {
            List<Comment> comments = commentRepository.findAllByPostOrderByReactionDislikeAsc();
            return getListResponseEntity(comments);
        } else if (postId == 5) {
            List<Comment> comments = commentRepository.findAllByPostOrderByReactionHeartDesc();
            return getListResponseEntity(comments);
        } else if (postId == 6) {
            List<Comment> comments = commentRepository.findAllByPostOrderByReactionHeartAsc();
            return getListResponseEntity(comments);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

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

        if (comment == null) {
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
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }
}
