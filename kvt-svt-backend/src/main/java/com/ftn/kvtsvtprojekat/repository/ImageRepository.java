package com.ftn.kvtsvtprojekat.repository;

import com.ftn.kvtsvtprojekat.model.Image;
import com.ftn.kvtsvtprojekat.model.Post;
import com.ftn.kvtsvtprojekat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {
    Image findImageById(Long id);

    List<Image> findAllByPost(Post post);

    Image findOneByUser(User user);
}
