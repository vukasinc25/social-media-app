package com.ftn.kvtsvtprojekat.repository;

import com.ftn.kvtsvtprojekat.model.GroupRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRequestRepository extends JpaRepository<GroupRequest,Long> {
    GroupRequest findGroupRequestById(Long id);
}
