package com.ftn.kvtsvtprojekat.service;

import com.ftn.kvtsvtprojekat.model.Banned;
import com.ftn.kvtsvtprojekat.model.Image;
import com.ftn.kvtsvtprojekat.model.Post;
import com.ftn.kvtsvtprojekat.model.User;

import java.util.List;

public interface ImageService {
    List<Image> findAll();

    List<Image> findAllByPost(Post post);

    Image findOneByUser(User user);

    Image findOneById(Long id);

    Image save(Image image);

    void delete(Long id);
}
