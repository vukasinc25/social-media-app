package com.ftn.kvtsvtprojekat.controller;

import com.ftn.kvtsvtprojekat.indexmodel.dto.GroupSearchQueryDTO;
import com.ftn.kvtsvtprojekat.indexmodel.dto.GroupSearchResultDTO;
import com.ftn.kvtsvtprojekat.indexmodel.dto.SearchQueryDTO;
import com.ftn.kvtsvtprojekat.indexservice.GroupSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class GroupSearchController {

    @Qualifier("groupSearchService")
    private final GroupSearchService groupSearchService;

    @PostMapping("/groups/nameAndDescription")
    public Page<GroupSearchResultDTO> nameAndDescriptionSearch(@RequestBody SearchQueryDTO simpleSearchQuery,
                                                               Pageable pageable) {
        return groupSearchService.nameAndDescriptionSearch(simpleSearchQuery.keywords(), pageable);
    }

    @PostMapping("/groups/phrase/nameAndDescription")
    public Page<GroupSearchResultDTO> nameAndDescriptionPhraseSearch(@RequestBody SearchQueryDTO simpleSearchQuery,
                                                                     Pageable pageable) {
        return groupSearchService.nameAndDescriptionPhraseSearch(simpleSearchQuery.keywords(), pageable);
    }

    @PostMapping("/groups/rules")
    public Page<GroupSearchResultDTO> rulesSearch(@RequestBody SearchQueryDTO simpleSearchQuery,
                                                  Pageable pageable) {
        return groupSearchService.rulesSearch(simpleSearchQuery.keywords(), pageable);
    }

    @PostMapping("/groups/phrase/rules")
    public Page<GroupSearchResultDTO> rulesPhraseSearch(@RequestBody SearchQueryDTO simpleSearchQuery,
                                                        Pageable pageable) {
        return groupSearchService.rulesPhraseSearch(simpleSearchQuery.keywords(), pageable);
    }

    @GetMapping("/groups/postCountRange")
    public Page<GroupSearchResultDTO> searchByPostCountRange(
            @RequestParam(value = "from", required = false) Integer from,
            @RequestParam(value = "to", required = false) Integer to,
            Pageable pageable) {
        return groupSearchService.searchByPostCountRange(from, to, pageable);
    }

    @GetMapping("/groups/averageLikeCountRange")
    public Page<GroupSearchResultDTO> searchByAverageLikeCountRange(
            @RequestParam(value = "from", required = false) Float from,
            @RequestParam(value = "to", required = false) Float to,
            Pageable pageable) {
        return groupSearchService.searchByAverageLkeCountRange(from, to, pageable);
    }

    @PostMapping("/groups/combined")
    public Page<GroupSearchResultDTO> combinedSearch(@RequestBody GroupSearchQueryDTO combinedSearchQuery, Pageable pageable) {
        return groupSearchService.combinedSearch(combinedSearchQuery, pageable);
    }

    @PostMapping("/groups/phrase/combined")
    public Page<GroupSearchResultDTO> combinedPhraseSearch(@RequestBody GroupSearchQueryDTO combinedSearchQuery, Pageable pageable) {
        return groupSearchService.combinedPhraseSearch(combinedSearchQuery, pageable);
    }

}
