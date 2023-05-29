package com.ftn.kvtsvtprojekat.repository;

import com.ftn.kvtsvtprojekat.model.Comment;
import com.ftn.kvtsvtprojekat.model.Post;
import com.ftn.kvtsvtprojekat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findCommentById(Long id);

    List<Comment> findAllByPost(Post post);

    List<Comment> findAllByParentComment(Comment parentComment);

    List<Comment> findAllByUser(User user);

    List<Comment> findAllByPostOrderByIdDesc(Post post);

    @Query("SELECT c FROM Comment c JOIN Reaction r WHERE c.id = r.comment.id AND r.reactionType = 0 ORDER BY r.reactionTime DESC")
    List<Comment> findAllByPostOrderByReactionDesc();

    @Query("SELECT c FROM Comment c JOIN Reaction r WHERE c.id = r.comment.id AND r.reactionType = 0 ORDER BY r.reactionTime ASC")
    List<Comment> findAllByPostOrderByReactionLikeAsc();

    @Query("SELECT c FROM Comment c JOIN Reaction r WHERE c.id = r.comment.id AND r.reactionType = 1 ORDER BY r.reactionTime DESC")
    List<Comment> findAllByPostOrderByReactionDislikeDesc();

    @Query("SELECT c FROM Comment c JOIN Reaction r WHERE c.id = r.comment.id AND r.reactionType = 1 ORDER BY r.reactionTime ASC")
    List<Comment> findAllByPostOrderByReactionDislikeAsc();

    @Query("SELECT c FROM Comment c JOIN Reaction r WHERE c.id = r.comment.id AND r.reactionType = 2 ORDER BY r.reactionTime DESC")
    List<Comment> findAllByPostOrderByReactionHeartDesc();

    @Query("SELECT c FROM Comment c JOIN Reaction r WHERE c.id = r.comment.id AND r.reactionType = 2 ORDER BY r.reactionTime ASC")
    List<Comment> findAllByPostOrderByReactionHeartAsc();
}
