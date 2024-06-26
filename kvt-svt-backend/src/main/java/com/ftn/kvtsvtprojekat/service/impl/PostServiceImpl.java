package com.ftn.kvtsvtprojekat.service.impl;

import com.ftn.kvtsvtprojekat.model.Group;
import com.ftn.kvtsvtprojekat.model.Post;
import com.ftn.kvtsvtprojekat.model.Post;
import com.ftn.kvtsvtprojekat.model.User;
import com.ftn.kvtsvtprojekat.repository.PostRepository;
import com.ftn.kvtsvtprojekat.service.PostService;
import org.modelmapper.ModelMapper;
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
    public List<Post> findAllByOrderByCreationDateDesc() {
        return postRepository.findAllByOrderByCreationDateDesc();
    }

    @Override
    public List<Post> findAllByOrderByCreationDateAsc() {
        return postRepository.findAllByOrderByCreationDate();
    }

    @Override
    public List<Post> findAllByGroup(Group group) {
        return postRepository.findAllByGroup(group);
    }

    @Override
    public List<Post> findAllByUser(User user) {
        return postRepository.findAllByUser(user);
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
    public void delete(Long id){
        Post post = postRepository.findPostById(id);
        post.setIsDeleted(true);
        postRepository.save(post);
    }
}
