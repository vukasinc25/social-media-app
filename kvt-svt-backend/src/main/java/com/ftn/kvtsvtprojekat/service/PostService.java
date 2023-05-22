package com.ftn.kvtsvtprojekat.service;

import com.ftn.kvtsvtprojekat.model.Group;
import com.ftn.kvtsvtprojekat.model.Post;

import java.util.List;

public interface PostService {
    List<Post> findAll();

    List<Post> findAllByGroup(Group group);

    Post findOneById(Long id);

    Post save(Post post);

    void delete(Long id);
}
