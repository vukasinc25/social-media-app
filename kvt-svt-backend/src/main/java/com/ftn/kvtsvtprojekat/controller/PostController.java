package com.ftn.kvtsvtprojekat.controller;

import com.ftn.kvtsvtprojekat.model.Comment;
import com.ftn.kvtsvtprojekat.model.Group;
import com.ftn.kvtsvtprojekat.model.Post;
import com.ftn.kvtsvtprojekat.model.User;
import com.ftn.kvtsvtprojekat.model.dto.CommentDTO;
import com.ftn.kvtsvtprojekat.model.dto.PostDTO;
import com.ftn.kvtsvtprojekat.service.GroupService;
import com.ftn.kvtsvtprojekat.service.PostService;
import com.ftn.kvtsvtprojekat.service.UserService;
import com.ftn.kvtsvtprojekat.indexservice.PostIndexingService;
import com.ftn.kvtsvtprojekat.indexservice.FileService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;
    private final GroupService groupService;
    public final ModelMapper modelMapper;
    private final UserService userService;
    private final PostIndexingService postIndexingService;
    private final FileService fileService;

    public PostController(PostService postService, GroupService groupService, ModelMapper modelMapper, UserService userService, PostIndexingService postIndexingService, FileService fileService) {
        this.postService = postService;
        this.groupService = groupService;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.postIndexingService = postIndexingService;
        this.fileService = fileService;
    }

    @GetMapping("/byGroup/{id}")
    public ResponseEntity<List<PostDTO>> getPostsByGroup(@PathVariable("id") Long groupId) {

        Group group = groupService.findOneById(groupId);
        List<Post> posts = postService.findAllByGroup(group);
        List<PostDTO> postsDTO = new ArrayList<>();
        for (Post post : posts) {
            if(!post.getIsDeleted()) {
                PostDTO postDTO = modelMapper.map(post, PostDTO.class);
                postsDTO.add(postDTO);
            }
        }

        return new ResponseEntity<>(postsDTO, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostDTO>> getPosts() {

        List<Post> posts = postService.findAll();
        List<PostDTO> postsDTO = new ArrayList<>();
        for (Post post : posts) {
            if(!post.getIsDeleted()) {
                PostDTO postDTO = modelMapper.map(post, PostDTO.class);
                postsDTO.add(postDTO);
            }
        }
        return new ResponseEntity<>(postsDTO, HttpStatus.OK);
    }

    @GetMapping("/allDesc")
    public ResponseEntity<List<PostDTO>> getPostsDescending() {

        List<Post> posts = postService.findAllByOrderByCreationDateDesc();
        List<PostDTO> postsDTO = new ArrayList<>();
        for (Post post : posts) {
            if(!post.getIsDeleted()) {
                PostDTO postDTO = modelMapper.map(post, PostDTO.class);
                postsDTO.add(postDTO);
            }
        }
        return new ResponseEntity<>(postsDTO, HttpStatus.OK);
    }

    @GetMapping("/allAsc")
    public ResponseEntity<List<PostDTO>> getPostsAscending() {

        List<Post> posts = postService.findAllByOrderByCreationDateAsc();
        List<PostDTO> postsDTO = new ArrayList<>();
        for (Post post : posts) {
            if(!post.getIsDeleted()) {
                PostDTO postDTO = modelMapper.map(post, PostDTO.class);
                postsDTO.add(postDTO);
            }
        }
        return new ResponseEntity<>(postsDTO, HttpStatus.OK);
    }

    @GetMapping("/byUser/{id}")
    public ResponseEntity<List<PostDTO>> getCommentsByUser(@PathVariable("id") Long userId) {

        User user = userService.findOneById(userId);
        List<Post> posts = postService.findAllByUser(user);
        List<PostDTO> postsDTO = new ArrayList<>();
        for (Post post : posts) {
            if(!post.getIsDeleted()){
                PostDTO postDTO = modelMapper.map(post, PostDTO.class);
                postsDTO.add(postDTO);
            }
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

    @PostMapping(value = "/create", consumes = {"multipart/form-data"})
    public ResponseEntity<Post> createPost(
            @RequestPart("post") PostDTO postDTO,
            @RequestPart(value = "pdfFile", required = false) MultipartFile pdfFile
    ) {
        postDTO.setIsDeleted(false);
        Post post = modelMapper.map(postDTO, Post.class);
        LocalDateTime time = LocalDateTime.now();
        post.setCreationDate(time);

        // If PDF is provided, upload to MinIO and set file name
        if (pdfFile != null && !pdfFile.isEmpty()) {
            String serverFilename = fileService.store(pdfFile, java.util.UUID.randomUUID().toString());
            post.setPdfFile(serverFilename);
        }

        postService.save(post);

        // Index in Elasticsearch (pass pdfFile if present, else null)
        postIndexingService.indexPost(pdfFile, post);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<PostDTO> updatePost(@PathVariable("id") Long id, @RequestBody PostDTO postDTO) {
        Post post = postService.findOneById(id);
        if(post == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        post.setTitle(postDTO.getTitle());
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
            post.setIsDeleted(true);
            postService.save(post);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @PostMapping("/recreate-index")
    public ResponseEntity<Void> recreateIndex() {
        postIndexingService.recreateIndex();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
