package com.ftn.kvtsvtprojekat.service.impl;

import com.ftn.kvtsvtprojekat.model.Banned;
import com.ftn.kvtsvtprojekat.repository.BannedRepository;
import com.ftn.kvtsvtprojekat.service.BannedService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannedServiceImpl implements BannedService {

    public final BannedRepository bannedRepository;
    private final ModelMapper modelMapper;

    public BannedServiceImpl(BannedRepository bannedRepository, ModelMapper modelMapper) {
        this.bannedRepository = bannedRepository;
        this.modelMapper = modelMapper;
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
        bannedRepository.deleteById(id);
    }
}
