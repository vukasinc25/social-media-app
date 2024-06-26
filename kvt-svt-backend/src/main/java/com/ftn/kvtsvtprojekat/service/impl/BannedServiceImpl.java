package com.ftn.kvtsvtprojekat.service.impl;

import com.ftn.kvtsvtprojekat.model.Banned;
import com.ftn.kvtsvtprojekat.repository.BannedRepository;
import com.ftn.kvtsvtprojekat.service.BannedService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BannedServiceImpl implements BannedService {

    public final BannedRepository bannedRepository;

    public BannedServiceImpl(BannedRepository bannedRepository) {
        this.bannedRepository = bannedRepository;
    }

    @Override
    public List<Banned> findAll(){
        return bannedRepository.findAll();
    }

    @Override
    public Banned findOneById(Long id){
        return bannedRepository.findBannedById(id);
    }

    @Override
    public Banned save(Banned banned){
        return bannedRepository.save(banned);
    }

    @Override
    public void delete(Long id){
        Banned banned = bannedRepository.findBannedById(id);
        banned.setIsDeleted(true);
        bannedRepository.save(banned);
    }
}
