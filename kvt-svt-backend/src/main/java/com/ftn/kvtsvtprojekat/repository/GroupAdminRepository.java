package com.ftn.kvtsvtprojekat.repository;

import com.ftn.kvtsvtprojekat.model.GroupAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupAdminRepository extends JpaRepository<GroupAdmin,Long> {
    GroupAdmin findGroupAdminById(Long id);
}
