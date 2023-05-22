package com.ftn.kvtsvtprojekat.controller;

import com.ftn.kvtsvtprojekat.model.Group;
import com.ftn.kvtsvtprojekat.model.Post;
import com.ftn.kvtsvtprojekat.model.dto.PostDTO;
import com.ftn.kvtsvtprojekat.service.GroupService;
import com.ftn.kvtsvtprojekat.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;
    private final GroupService groupService;
    public final ModelMapper modelMapper;

    public PostController(PostService postService, GroupService groupService, ModelMapper modelMapper) {
        this.postService = postService;
        this.groupService = groupService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/byGroup/{id}")
    public ResponseEntity<List<PostDTO>> getPosts(@PathVariable("id") Long groupId) {

        Group group = groupService.findOneById(groupId);
        List<Post> posts = postService.findAllByGroup(group);
        List<PostDTO> postsDTO = new ArrayList<>();
        for (Post post : posts) {
            PostDTO postDTO = modelMapper.map(post, PostDTO.class);
            postsDTO.add(postDTO);
        }

        return new ResponseEntity<>(postsDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> getPostsByGroup() {

        List<Post> posts = postService.findAll();
        List<PostDTO> postsDTO = new ArrayList<>();
        for (Post post : posts) {
            PostDTO postDTO = modelMapper.map(post, PostDTO.class);
            postsDTO.add(postDTO);
        }

        return new ResponseEntity<>(postsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PostDTO> getPost(@PathVariable("id") Long id) {
        Post post = postService.findOneById(id);

        if (post == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        PostDTO postDTO = modelMapper.map(post, PostDTO.class);

        return status(HttpStatus.OK).body(postDTO);
    }

    @PostMapping(value = "/create", consumes = "application/json")
    public ResponseEntity<Post> createPost(@Valid @RequestBody PostDTO postDTO) {
        Post post = modelMapper.map(postDTO, Post.class);
        postService.save(post);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<PostDTO> updatePost(@PathVariable("id") Long id, @RequestBody PostDTO postDTO) {
        Post post = postService.findOneById(id);
        if(post == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        post.setContent(postDTO.getContent());

        postService.save(post);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        if(id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Post post = postService.findOneById(id);

        if (post != null) {
            postService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }

}
