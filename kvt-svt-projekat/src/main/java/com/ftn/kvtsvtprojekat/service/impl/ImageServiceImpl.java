package com.ftn.kvtsvtprojekat.service.impl;

import com.ftn.kvtsvtprojekat.model.Image;
import com.ftn.kvtsvtprojekat.repository.ImageRepository;
import com.ftn.kvtsvtprojekat.repository.ImageRepository;
import com.ftn.kvtsvtprojekat.service.ImageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    
    public final ImageRepository imageRepository;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public List<Image> findAll(){
        return imageRepository.findAll();
    }

    @Override
    public Image findOneById(Long id){
        return imageRepository.findImageById(id);
    }

    @Override
    public Image addImage(Image image){
        return imageRepository.save(image);
    }

    @Override
    public Image updateImage(Image image){
        return imageRepository.save(image);
    }

    @Override
    public Image deleteImage(Long id){
        return imageRepository.deleteImageById(id);
    }
}
