package com.ftn.kvtsvtprojekat.repository;

import com.ftn.kvtsvtprojekat.model.Group;
import com.ftn.kvtsvtprojekat.model.Post;
import com.ftn.kvtsvtprojekat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Post findPostById(Long id);

    List<Post> findAllByGroup(Group group);

    List<Post> findAllByUser(User user);

    List<Post> findAllByOrderByCreationDateDesc();

    List<Post> findAllByOrderByCreationDate();
}
