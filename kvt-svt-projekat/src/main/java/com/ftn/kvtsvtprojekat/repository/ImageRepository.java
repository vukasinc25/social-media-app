package com.ftn.kvtsvtprojekat.repository;

import com.ftn.kvtsvtprojekat.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,Long> {
    Image findImageById(Long id);

    Image deleteImageById(Long id);
}
