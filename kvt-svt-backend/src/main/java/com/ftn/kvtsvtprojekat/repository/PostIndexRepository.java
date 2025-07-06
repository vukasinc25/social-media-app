package com.ftn.kvtsvtprojekat.repository;

import com.ftn.kvtsvtprojekat.indexmodel.PostIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostIndexRepository
    extends ElasticsearchRepository<PostIndex, String> {
}
