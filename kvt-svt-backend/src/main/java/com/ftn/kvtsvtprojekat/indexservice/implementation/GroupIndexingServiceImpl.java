package com.ftn.kvtsvtprojekat.indexservice.implementation;

import com.ftn.kvtsvtprojekat.exceptionhandling.exception.LoadingException;
import com.ftn.kvtsvtprojekat.exceptionhandling.exception.NotFoundException;
import com.ftn.kvtsvtprojekat.exceptionhandling.exception.StorageException;
import com.ftn.kvtsvtprojekat.indexmodel.GroupIndex;
import com.ftn.kvtsvtprojekat.repository.GroupIndexRepository;
import com.ftn.kvtsvtprojekat.indexservice.FileService;
import com.ftn.kvtsvtprojekat.indexservice.GroupIndexingService;
import com.ftn.kvtsvtprojekat.model.Group;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.tika.Tika;
import org.apache.tika.language.detect.LanguageDetector;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

//import javax.transaction.Transactional;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupIndexingServiceImpl implements GroupIndexingService {
    private final LanguageDetector languageDetector;
    private final FileService fileService;
    private final GroupIndexRepository groupIndexingRepository;


    @Override
    public GroupIndex save(GroupIndex groupIndex) {
        return groupIndexingRepository.save(groupIndex);
    }

    @Override
    public void deleteById(String id) {
        groupIndexingRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void indexGroup(MultipartFile documentFile, Group group) {
        var groupIndex = new GroupIndex();

        if (documentFile != null && !documentFile.isEmpty()) {
            var pdfName = Objects.requireNonNull(documentFile.getOriginalFilename()).split("\\.")[0];
            groupIndex.setPdfFileUrl(pdfName);

            var documentContent = extractDocumentContent(documentFile);
            if (detectLanguage(documentContent).equals("SR")) {
                groupIndex.setContentSr(documentContent);
            } else {
                groupIndex.setContentEn(documentContent);
            }

            var serverFilename = fileService.store(documentFile, UUID.randomUUID().toString());
            groupIndex.setServerFilename(serverFilename);
            group.setPdfFile(serverFilename);
        }

        groupIndex.setId(group.getId().toString());
        groupIndex.setName(group.getName());
        groupIndex.setDescription(group.getDescription());
//        groupIndex.setRules(group.getRules());
        groupIndex.setCreationDate(group.getCreationDate());
        groupIndex.setSuspended(group.getIsSuspended());
        groupIndex.setSuspendedReason(group.getSuspensionReason());
        groupIndex.setPostCount(0);
        groupIndex.setNumberOfPostsWithLikes(0);
        groupIndex.setTotalLikes(0);

        groupIndexingRepository.save(groupIndex);
    }

    @Override
    @Transactional
    public void updateGroupIndex(Group group) {
        var groupIndex = groupIndexingRepository.findById(group.getId().toString())
                .orElseThrow(() -> new NotFoundException("Group index not found"));

        groupIndex.setName(group.getName());
        groupIndex.setDescription(group.getDescription());
//        groupIndex.setRules(group.getRules());
        groupIndex.setCreationDate(group.getCreationDate());
        groupIndex.setSuspended(group.getIsSuspended());
        groupIndex.setSuspendedReason(group.getSuspensionReason());

        groupIndexingRepository.save(groupIndex);
    }

    @Override
    @Transactional
    public void suspendGroupIndex(Group group) {
        var groupIndex = groupIndexingRepository.findById(group.getId().toString())
                .orElseThrow(() -> new NotFoundException("Group index not found"));

        groupIndexingRepository.delete(groupIndex);
    }

    @Override
    @Transactional
    public void updatePostCount(String groupId) {
        var groupIndex = groupIndexingRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Group index not found"));

        groupIndex.setPostCount(groupIndex.getPostCount() + 1);

        groupIndexingRepository.save(groupIndex);
    }

    @Override
    @Transactional
    public void deletePostCount(String groupId) {
        var groupIndex = groupIndexingRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Group index not found"));

        groupIndex.setPostCount(groupIndex.getPostCount() - 1);

        groupIndexingRepository.save(groupIndex);
    }

    @Override
    @Transactional
    public void updateLikeCount(String groupId) {
        var groupIndex = groupIndexingRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Group index not found"));

        groupIndex.setTotalLikes(groupIndex.getTotalLikes() + 1);
        groupIndex.setNumberOfPostsWithLikes(groupIndex.getNumberOfPostsWithLikes() + 1);

        if (groupIndex.getNumberOfPostsWithLikes() != 0) {
            groupIndex.setPostAverageLikes((float) groupIndex.getTotalLikes() / groupIndex.getNumberOfPostsWithLikes());
        } else {
            groupIndex.setPostAverageLikes(0.0f);
        }
        groupIndexingRepository.save(groupIndex);
    }

    @Override
    @Transactional
    public void deleteLikeCount(String groupId) {
        var groupIndex = groupIndexingRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Group index not found"));

        if (groupIndex.getTotalLikes() > 0) {
            groupIndex.setTotalLikes(groupIndex.getTotalLikes() - 1);
        }

        if (groupIndex.getNumberOfPostsWithLikes() > 0) {
            groupIndex.setNumberOfPostsWithLikes(groupIndex.getNumberOfPostsWithLikes() - 1);
        }

        if (groupIndex.getNumberOfPostsWithLikes() != 0) {
            groupIndex.setPostAverageLikes((float) groupIndex.getTotalLikes() / groupIndex.getNumberOfPostsWithLikes());
        } else {
            groupIndex.setPostAverageLikes(0.0f);
        }

        groupIndexingRepository.save(groupIndex);
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

    private String detectMimeType(MultipartFile file) {
        var contentAnalyzer = new Tika();

        String trueMimeType;
        String specifiedMimeType;
        try {
            trueMimeType = contentAnalyzer.detect(file.getBytes());
            specifiedMimeType =
                    Files.probeContentType(Path.of(Objects.requireNonNull(file.getOriginalFilename())));
        } catch (IOException e) {
            throw new StorageException("Failed to detect mime type for file.");
        }

        if (!trueMimeType.equals(specifiedMimeType) &&
                !(trueMimeType.contains("zip") && specifiedMimeType.contains("zip"))) {
            throw new StorageException("True mime type is different from specified one, aborting.");
        }

        return trueMimeType;
    }
}
