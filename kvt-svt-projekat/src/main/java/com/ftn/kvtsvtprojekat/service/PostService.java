package com.ftn.kvtsvtprojekat.service;

import com.ftn.kvtsvtprojekat.model.Post;

import java.util.List;

public interface PostService {
    List<Post> findAll();

    Post findOneById(Long id);

    Post addPost(Post post);

    Post updatePost(Post post);

    Post deletePost(Long id);
}
