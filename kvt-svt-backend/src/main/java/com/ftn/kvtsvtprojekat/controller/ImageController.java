package com.ftn.kvtsvtprojekat.controller;

import com.ftn.kvtsvtprojekat.model.Image;
import com.ftn.kvtsvtprojekat.model.Post;
import com.ftn.kvtsvtprojekat.model.User;
import com.ftn.kvtsvtprojekat.model.dto.ImageDTO;
import com.ftn.kvtsvtprojekat.service.ImageService;
import com.ftn.kvtsvtprojekat.service.PostService;
import com.ftn.kvtsvtprojekat.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/image")
public class ImageController {

    public final ModelMapper modelMapper;
    private final ImageService imageService;
    private final PostService postService;
    private final UserService userService;

    public ImageController(ModelMapper modelMapper, ImageService imageService, PostService postService, UserService userService) {
        this.modelMapper = modelMapper;
        this.imageService = imageService;
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping("/byPost/{id}")
    public ResponseEntity<List<ImageDTO>> getImagesForPost(@PathVariable("id") Long postId) {

        Post post = postService.findOneById(postId);
        List<Image> images = imageService.findAllByPost(post);
        List<ImageDTO> imagesDTO = new ArrayList<>();
        for (Image image : images) {
            ImageDTO imageDTO = modelMapper.map(image, ImageDTO.class);
            imagesDTO.add(imageDTO);
        }

        return new ResponseEntity<>(imagesDTO, HttpStatus.OK);
    }

    @GetMapping("/byUser/{id}")
    public ResponseEntity<ImageDTO> getImagesForUser(@PathVariable("id") Long userId) {

        User user = userService.findOneById(userId);
        Image image = imageService.findOneByUser(user);
        if(image != null){
            ImageDTO imageDTO = modelMapper.map(image, ImageDTO.class);
            return new ResponseEntity<>(imageDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ImageDTO> getImage(@PathVariable("id") Long id) {
        Image image = imageService.findOneById(id);

        if (image == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ImageDTO imageDTO = modelMapper.map(image, ImageDTO.class);

        return status(HttpStatus.OK).body(imageDTO);
    }

    @PostMapping(value = "/create", consumes = "application/json")
    public ResponseEntity<Image> createImage(@Valid @RequestBody ImageDTO imageDTO) {
        Image image = modelMapper.map(imageDTO, Image.class);
        imageService.save(image);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<ImageDTO> updateImage(@PathVariable("id") Long id, @RequestBody ImageDTO imageDTO) {
        Image image = imageService.findOneById(id);
        if(image == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        image.setPath(imageDTO.getPath());

        imageService.save(image);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        if(id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Image image = imageService.findOneById(id);

        if (image != null) {
            imageService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }
}
