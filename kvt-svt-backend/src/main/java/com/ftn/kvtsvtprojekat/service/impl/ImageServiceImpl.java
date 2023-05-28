package com.ftn.kvtsvtprojekat.service.impl;

import com.ftn.kvtsvtprojekat.model.Image;
import com.ftn.kvtsvtprojekat.model.Image;
import com.ftn.kvtsvtprojekat.model.Post;
import com.ftn.kvtsvtprojekat.model.User;
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
    public List<Image> findAllByPost(Post post) {
        return imageRepository.findAllByPost(post);
    }

    @Override
    public Image findOneByUser(User user) {
        return imageRepository.findOneByUser(user);
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
//        image.setIsDeleted(true);
        imageRepository.delete(image);
    }
}
