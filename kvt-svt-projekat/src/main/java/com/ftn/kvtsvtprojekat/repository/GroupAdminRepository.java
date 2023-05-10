package com.ftn.kvtsvtprojekat.repository;

import com.ftn.kvtsvtprojekat.model.GroupAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupAdminRepository extends JpaRepository<GroupAdmin,Long> {
    GroupAdmin findGroupAdminById(Long id);

    GroupAdmin delete(Long id);
}
