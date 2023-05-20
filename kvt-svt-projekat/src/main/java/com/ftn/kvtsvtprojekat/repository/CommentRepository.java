package com.ftn.kvtsvtprojekat.repository;

import com.ftn.kvtsvtprojekat.model.Comment;
import com.ftn.kvtsvtprojekat.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findCommentById(Long id);

    List<Comment> findAllByPost(Post post);
}
