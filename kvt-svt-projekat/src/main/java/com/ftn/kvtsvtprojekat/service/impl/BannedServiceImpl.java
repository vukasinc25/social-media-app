package com.ftn.kvtsvtprojekat.service.impl;

import com.ftn.kvtsvtprojekat.model.Banned;
import com.ftn.kvtsvtprojekat.repository.BannedRepository;
import com.ftn.kvtsvtprojekat.service.BannedService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannedServiceImpl implements BannedService {

    public final BannedRepository bannedRepository;

    public BannedServiceImpl(BannedRepository bannedRepository) {
        this.bannedRepository = bannedRepository;
    }

    public List<Banned> findAll(){
        return bannedRepository.findAll();
    }

    public Banned findOneById(Long id){
        return bannedRepository.findBannedById(id);
    }

    public Banned addBanned(Banned banned){
        return bannedRepository.save(banned);
    }

    public Banned updateBanned(Banned banned){
        return bannedRepository.save(banned);
    }

    public Banned deleteBanned(Long id){
        return bannedRepository.deleteBannedById(id);
    }
}
