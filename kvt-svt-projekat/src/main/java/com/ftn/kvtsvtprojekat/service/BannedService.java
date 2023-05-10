package com.ftn.kvtsvtprojekat.service;

import com.ftn.kvtsvtprojekat.model.Banned;

import java.util.List;

public interface BannedService {

    List<Banned> findAll();

    Banned findOneById(Long id);

    Banned addBanned(Banned banned);

    Banned updateBanned(Banned banned);

    Banned deleteBanned(Long id);
}
