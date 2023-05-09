package com.ftn.kvtsvtprojekat.repository;

import com.ftn.kvtsvtprojekat.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findCommentById(Long id);

    Comment deleteCommentById(Long id);
}
