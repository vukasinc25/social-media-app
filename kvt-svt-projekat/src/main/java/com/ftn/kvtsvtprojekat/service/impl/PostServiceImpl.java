package com.ftn.kvtsvtprojekat.service.impl;

import com.ftn.kvtsvtprojekat.model.Post;
import com.ftn.kvtsvtprojekat.repository.PostRepository;
import com.ftn.kvtsvtprojekat.repository.PostRepository;
import com.ftn.kvtsvtprojekat.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    public final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> findAll(){
        return postRepository.findAll();
    }

    public Post findOneById(Long id){
        return postRepository.findPostById(id);
    }

    public Post addPost(Post post){
        return postRepository.save(post);
    }

    public Post updatePost(Post post){
        return postRepository.save(post);
    }

    public Post deletePost(Long id){
        return postRepository.deletePostById(id);
    }
}
