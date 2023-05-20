package com.ftn.kvtsvtprojekat.service.impl;

import com.ftn.kvtsvtprojekat.model.Image;
import com.ftn.kvtsvtprojekat.repository.ImageRepository;
import com.ftn.kvtsvtprojekat.service.ImageService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    
    public final ImageRepository imageRepository;
    private final ModelMapper modelMapper;

    public ImageServiceImpl(ImageRepository imageRepository, ModelMapper modelMapper) {
        this.imageRepository = imageRepository;
        this.modelMapper = modelMapper;
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
    public Image delete(Long id){
        return imageRepository.deleteImageById(id);
    }
}
