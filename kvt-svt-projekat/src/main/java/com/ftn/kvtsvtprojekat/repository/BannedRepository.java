package com.ftn.kvtsvtprojekat.repository;

import com.ftn.kvtsvtprojekat.model.Banned;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannedRepository extends JpaRepository<Banned,Long> {
    Banned findBannedById(Long id);

    Banned deleteBannedById(Long id);
}
