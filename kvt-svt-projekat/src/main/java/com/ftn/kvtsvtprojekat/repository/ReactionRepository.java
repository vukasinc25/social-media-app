package com.ftn.kvtsvtprojekat.repository;

import com.ftn.kvtsvtprojekat.model.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    Reaction findReactionById(Long id);
}
