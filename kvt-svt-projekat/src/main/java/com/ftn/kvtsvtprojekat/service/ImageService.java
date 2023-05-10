package com.ftn.kvtsvtprojekat.service;

import com.ftn.kvtsvtprojekat.model.Banned;
import com.ftn.kvtsvtprojekat.model.Image;

import java.util.List;

public interface ImageService {
    List<Image> findAll();

    Image findOneById(Long id);

    Image addImage(Image image);

    Image updateImage(Image image);

    Image deleteImage(Long id);
}
