package com.ftn.kvtsvtprojekat.indexmodel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "post_index")
@Setting(settingPath = "/configuration/serbian-analyzer-config.json")
public class PostIndex {
    @Id
    private String id;

    @Field(type = FieldType.Text, store = true, name = "title", analyzer = "serbian_simple", searchAnalyzer = "serbian_simple")
    private String title;

    @Field(type = FieldType.Text, store = true, name = "content", analyzer = "serbian_simple", searchAnalyzer = "serbian_simple")
    private String content;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second, store = true, name = "creationDate")
    private LocalDateTime creationDate;

    @Field(type = FieldType.Boolean, store = true, name = "isDeleted")
    private boolean isDeleted;

    @Field(type = FieldType.Text, store = true, name = "content_sr", analyzer = "serbian_simple", searchAnalyzer = "serbian_simple")
    private String contentSr;

    @Field(type = FieldType.Text, store = true, name = "content_en", analyzer = "english", searchAnalyzer = "english")
    private String contentEn;

    @Field(type = FieldType.Text, store = true, name = "server_filename", index = false)
    private String serverFilename;

    @Field(type = FieldType.Text, store = true, name = "pdfFile", index = false)
    private String pdfFileUrl;

    @Field(type = FieldType.Text, store = true, name = "groupId")
    private String groupId;

    @Field(type = FieldType.Integer, store = true, name = "likeCount", index = false)
    private Integer likeCount;

    @Field(type = FieldType.Integer, store = true, name = "commentCount", index = false)
    private Integer commentCount;

    @Field(type = FieldType.Nested, store = true, name = "comments")
    private List<CommentIndex> comments = new ArrayList<>();

    private Map<String, List<String>> highlights;
}