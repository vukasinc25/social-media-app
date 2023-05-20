package com.ftn.kvtsvtprojekat.service.impl;

import com.ftn.kvtsvtprojekat.model.Post;
import com.ftn.kvtsvtprojekat.repository.PostRepository;
import com.ftn.kvtsvtprojekat.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    public final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
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
    public Post save(Post post){
        return postRepository.save(post);
    }

    @Override
    public Post delete(Long id){
        return postRepository.deleteById(id);
    }
}
