package com.ftn.kvtsvtprojekat.service;

import com.ftn.kvtsvtprojekat.model.Banned;
import com.ftn.kvtsvtprojekat.model.Image;

import java.util.List;

public interface ImageService {
    List<Image> findAll();

    Image findOneById(Long id);

    Image save(Image image);

    Image delete(Long id);
}
