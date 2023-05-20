package com.ftn.kvtsvtprojekat.service;

import com.ftn.kvtsvtprojekat.model.Banned;

import java.util.List;

public interface BannedService {

    List<Banned> findAll();

    Banned findOneById(Long id);

    Banned save(Banned banned);

    void delete(Long id);
}
