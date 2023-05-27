package com.ftn.kvtsvtprojekat.repository;

import com.ftn.kvtsvtprojekat.model.Comment;
import com.ftn.kvtsvtprojekat.model.Post;
import com.ftn.kvtsvtprojekat.model.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    Reaction findReactionById(Long id);

    List<Reaction> findAllByPost(Post post);

    List<Reaction> findAllByComment(Comment comment);

    @Query("SELECT r FROM Reaction r WHERE r.post.id = :postId AND r.user.id = :userId")
    Reaction findOneByPostIdAndUserId(@Param("postId") Long postId, @Param("userId") Long userId);

    @Query("SELECT r FROM Reaction r WHERE r.comment.id = :commentId AND r.user.id = :userId")
    Reaction findOneByCommentIdAndUserId(@Param("commentId") Long postId, @Param("userId") Long userId);
}
