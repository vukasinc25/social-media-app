package com.ftn.kvtsvtprojekat.indexservice.implementation;

import com.ftn.kvtsvtprojekat.exceptionhandling.exception.LoadingException;
import com.ftn.kvtsvtprojekat.exceptionhandling.exception.NotFoundException;
import com.ftn.kvtsvtprojekat.indexmodel.CommentIndex;
import com.ftn.kvtsvtprojekat.indexmodel.PostIndex;
import com.ftn.kvtsvtprojekat.repository.PostIndexRepository;
import com.ftn.kvtsvtprojekat.indexservice.FileService;
import com.ftn.kvtsvtprojekat.indexservice.PostIndexingService;
import com.ftn.kvtsvtprojekat.model.Comment;
import com.ftn.kvtsvtprojekat.model.Post;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.tika.language.detect.LanguageDetector;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

//import javax.transaction.Transactional;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostIndexingServiceImpl implements PostIndexingService {
    private final LanguageDetector languageDetector;
    private final FileService fileService;
    private final PostIndexRepository postIndexingRepository;


    @Override
    public PostIndex save(PostIndex postIndex) {
        return postIndexingRepository.save(postIndex);
    }

    @Override
    public void deleteById(String id) {
        postIndexingRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void indexPost(MultipartFile documentFile, Post post) {
        var postIndex = new PostIndex();

        var pdfName = Objects.requireNonNull(documentFile.getOriginalFilename()).split("\\.")[0];
        postIndex.setPdfFileUrl(pdfName);

        var documentContent = extractDocumentContent(documentFile);
        if (detectLanguage(documentContent).equals("SR")) {
            postIndex.setContentSr(documentContent);
        } else {
            postIndex.setContentEn(documentContent);
        }

        var serverFilename = fileService.store(documentFile, UUID.randomUUID().toString());
        postIndex.setServerFilename(serverFilename);
        post.setPdfFile(serverFilename);

        postIndex.setId(post.getId().toString());
//        postIndex.setTitle(post.getTitle());
        postIndex.setContent(post.getContent());
        postIndex.setCreationDate(post.getCreationDate());
        postIndex.setLikeCount(0);
        postIndex.setCommentCount(0);

        if (post.getGroup() != null) {
            var groupId = post.getGroup().getId().toString();
            postIndex.setGroupId(groupId);
        }

        postIndexingRepository.save(postIndex);
    }

    @Override
    @Transactional
    public void updatePostIndex(Post post) {
        var postIndex = postIndexingRepository.findById(post.getId().toString())
                .orElseThrow(() -> new NotFoundException("Post index not found"));

//        postIndex.setTitle(post.getTitle());
        postIndex.setContent(post.getContent());
        postIndex.setCreationDate(post.getCreationDate());
        postIndex.setGroupId(post.getGroup().getId().toString());

        postIndexingRepository.save(postIndex);
    }

    @Override
    @Transactional
    public void deletePostIndex(Post post) {
        var postIndex = postIndexingRepository.findById(post.getId().toString())
                .orElseThrow(() -> new NotFoundException("Post index not found"));

        postIndexingRepository.delete(postIndex);
    }

    @Override
    @Transactional
    public void updateLikeCount(String postId) {
        var postIndex = postIndexingRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post index not found"));

        postIndex.setLikeCount(postIndex.getLikeCount() + 1);

        postIndexingRepository.save(postIndex);
    }

    @Override
    @Transactional
    public void deleteLikeCount(String postId) {
        var postIndex = postIndexingRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post index not found"));

        postIndex.setLikeCount(postIndex.getLikeCount() - 1);

        postIndexingRepository.save(postIndex);
    }

    @Override
    @Transactional
    public void updateCommentCount(String postId) {
        var postIndex = postIndexingRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post index not found"));

        postIndex.setCommentCount(postIndex.getCommentCount() + 1);

        postIndexingRepository.save(postIndex);
    }

    @Override
    @Transactional
    public void deleteCommentCount(String postId) {
        var postIndex = postIndexingRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post index not found"));

        postIndex.setCommentCount(postIndex.getCommentCount() - 1);

        postIndexingRepository.save(postIndex);
    }

    @Override
    @Transactional
    public void createCommentTextIndex(Comment comment) {
        var postIndex = postIndexingRepository.findById(comment.getPost().getId().toString())
                .orElseThrow(() -> new NotFoundException("Post index not found"));

        List<CommentIndex> comments = postIndex.getComments();
        if (comments == null) {
            comments = new ArrayList<>();
        }

        comments.add(new CommentIndex(comment.getId().toString(), comment.getText()));
        postIndex.setComments(comments);

        postIndexingRepository.save(postIndex);
    }

    @Override
    @Transactional
    public void updateCommentTextIndex(Comment comment) {
        var postIndex = postIndexingRepository.findById(comment.getPost().getId().toString())
                .orElseThrow(() -> new NotFoundException("Post index not found"));

        List<CommentIndex> comments = postIndex.getComments();
        if (comments != null) {
            for (CommentIndex commentIndex : comments) {
                if (commentIndex.getId().equals(comment.getId().toString())) {
                    commentIndex.setText(comment.getText());
                    break;
                }
            }
        }

        postIndexingRepository.save(postIndex);
    }

    @Override
    @Transactional
    public void deleteCommentTextIndex(Comment comment) {
        var postIndex = postIndexingRepository.findById(comment.getPost().getId().toString())
                .orElseThrow(() -> new NotFoundException("Post index not found"));

        List<CommentIndex> comments = postIndex.getComments();
        if (comments != null) {
            comments.removeIf(commentIndex -> commentIndex.getId().equals(comment.getId().toString()));
            postIndex.setComments(comments);
        }

        postIndexingRepository.save(postIndex);
    }


    private String extractDocumentContent(MultipartFile multipartPdfFile) {
        String documentContent;
        try (var pdfFile = multipartPdfFile.getInputStream()) {
            var pdDocument = PDDocument.load(pdfFile);
            var textStripper = new PDFTextStripper();
            documentContent = textStripper.getText(pdDocument);
            pdDocument.close();
        } catch (IOException e) {
            throw new LoadingException("Error while trying to load PDF file content.");
        }

        return documentContent;
    }

    private String detectLanguage(String text) {
        var detectedLanguage = languageDetector.detect(text).getLanguage().toUpperCase();
        if (detectedLanguage.equals("HR")) {
            detectedLanguage = "SR";
        }

        return detectedLanguage;
    }
}
