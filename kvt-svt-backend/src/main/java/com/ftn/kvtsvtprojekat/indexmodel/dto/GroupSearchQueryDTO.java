package com.ftn.kvtsvtprojekat.indexmodel.dto;

import java.util.List;

public record GroupSearchQueryDTO(
        String name,
        String description,
        String pdfContent,
        String rules,
        List<Integer> postAverageLikes,
        List<Integer> postCount,
        String operation
) {
}
