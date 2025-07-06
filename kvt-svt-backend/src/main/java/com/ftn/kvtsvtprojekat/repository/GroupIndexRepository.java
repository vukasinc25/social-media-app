package com.ftn.kvtsvtprojekat.repository;

import com.ftn.kvtsvtprojekat.indexmodel.GroupIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupIndexRepository
    extends ElasticsearchRepository<GroupIndex, String> {
}
