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

    @Override
    public List<Post> findAll(){
        return postRepository.findAll();
    }

    @Override
    public Post findOneById(Long id){
        return postRepository.findPostById(id);
    }

    @Override
    public Post addPost(Post post){
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(Post post){
        return postRepository.save(post);
    }

    @Override
    public Post deletePost(Long id){
        return postRepository.deletePostById(id);
    }
}
