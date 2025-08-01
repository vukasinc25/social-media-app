package com.ftn.kvtsvtprojekat.repository;

import com.ftn.kvtsvtprojekat.model.GroupAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupAdminRepository extends JpaRepository<GroupAdmin,Long> {
    GroupAdmin findGroupAdminById(Long id);
    
    // Check if a group admin already exists for the given user and group
    boolean existsByUser_IdAndGroup_IdAndIsDeletedFalse(Long userId, Long groupId);
}
