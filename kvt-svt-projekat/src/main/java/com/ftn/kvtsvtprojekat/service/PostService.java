package com.ftn.kvtsvtprojekat.service;

import com.ftn.kvtsvtprojekat.model.Post;

import java.util.List;

public interface PostService {
    List<Post> findAll();

    Post findOneById(Long id);

    Post save(Post post);

    Post delete(Long id);
}
