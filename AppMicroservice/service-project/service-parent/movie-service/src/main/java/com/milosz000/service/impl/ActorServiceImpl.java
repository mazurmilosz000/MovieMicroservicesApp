package com.milosz000.service.impl;

import com.milosz000.dto.ActorDto;
import com.milosz000.mapper.ActorMapper;
import com.milosz000.model.Actor;
import com.milosz000.repository.ActorRepository;
import com.milosz000.service.ActorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ActorServiceImpl implements ActorService {

    private final ActorRepository actorRepository;
    private final ActorMapper actorMapper;

    @Override
    public void addActor(ActorDto actorDto) {
        actorRepository.save(actorMapper.dtoToModel(actorDto));
    }

    @Override
    public List<Actor> getAllActors() {
        return actorRepository.findAll();
        
    }
}
