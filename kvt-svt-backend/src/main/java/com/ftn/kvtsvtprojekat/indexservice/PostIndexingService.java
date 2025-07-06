package com.ftn.kvtsvtprojekat.indexservice;

import com.ftn.kvtsvtprojekat.indexmodel.PostIndex;
import com.ftn.kvtsvtprojekat.model.Comment;
import com.ftn.kvtsvtprojekat.model.Post;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface PostIndexingService {
    PostIndex save(PostIndex postIndex);
    void deleteById(String id);
    void indexPost(MultipartFile documentFile, Post post);
    void updatePostIndex(Post post);
    void deletePostIndex(Post post);
    void updateLikeCount(String postId);
    void deleteLikeCount(String postId);
    void updateCommentCount(String postId);
    void deleteCommentCount(String postId);
    void createCommentTextIndex(Comment comment);
    void updateCommentTextIndex(Comment comment);
    void deleteCommentTextIndex(Comment comment);
}
