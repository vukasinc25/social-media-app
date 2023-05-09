package com.ftn.kvtsvtprojekat.service.impl;

import com.ftn.kvtsvtprojekat.model.Image;
import com.ftn.kvtsvtprojekat.repository.ImageRepository;
import com.ftn.kvtsvtprojekat.repository.ImageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl {
    
    public final ImageRepository imageRepository;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }
    
    public List<Image> findAll(){
        return imageRepository.findAll();
    }

    public Image findOneById(Long id){
        return imageRepository.findImageById(id);
    }

    public Image addImage(Image image){
        return imageRepository.save(image);
    }

    public Image updateImage(Image image){
        return imageRepository.save(image);
    }

    public Image deleteImage(Long id){
        return imageRepository.deleteImageById(id);
    }
}
