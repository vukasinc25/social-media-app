package com.ftn.kvtsvtprojekat.indexmodel.dto;

import java.util.List;

public record GroupSearchQueryDTO(
        List<String> keywords,
        String name,
        String title,
        String content,
        String description,
        String pdfContent,
        String rules,
        List<Integer> postAverageLikes,
        List<Integer> postCount,
        List<Integer> likeCount,
        List<Integer> commentCount,
        String operation
) {
}
