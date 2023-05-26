package com.ftn.kvtsvtprojekat.repository;

import com.ftn.kvtsvtprojekat.model.Comment;
import com.ftn.kvtsvtprojekat.model.Post;
import com.ftn.kvtsvtprojekat.model.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    Reaction findReactionById(Long id);

    List<Reaction> findAllByPost(Post post);

    List<Reaction> findAllByComment(Comment comment);
}
