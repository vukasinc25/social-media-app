package com.ftn.kvtsvtprojekat.repository;

import com.ftn.kvtsvtprojekat.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
