package com.ftn.kvtsvtprojekat.service.impl;

import com.ftn.kvtsvtprojekat.model.Image;
import com.ftn.kvtsvtprojekat.model.Image;
import com.ftn.kvtsvtprojekat.repository.ImageRepository;
import com.ftn.kvtsvtprojekat.service.ImageService;
import org.modelmapper.ModelMapper;
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
    public Image save(Image image){
        return imageRepository.save(image);
    }

    @Override
    public void delete(Long id){
        Image image = imageRepository.findImageById(id);
        image.setIsDeleted(true);
        imageRepository.save(image);
    }
}
