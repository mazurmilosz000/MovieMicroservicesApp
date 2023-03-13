package com.milosz000.service.impl;

import com.milosz000.dto.ActorDto;
import com.milosz000.model.Actor;
import com.milosz000.repository.ActorRepository;
import com.milosz000.service.ActorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorServiceImpl implements ActorService {

    private final ActorRepository actorRepository;

    public ActorServiceImpl(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    @Override
    public void addActor(ActorDto actorDto) {
        actorRepository.save(mapDtoToActor(actorDto));
    }

    @Override
    public List<Actor> getAllActors() {
        return actorRepository.findAll();
        
    }

    private Actor mapDtoToActor(ActorDto actorDto) {
        return Actor.builder()
                .firstname(actorDto.getFirstname())
                .lastname(actorDto.getLastname())
                .movies(actorDto.getMovies())
                .build();
    }


    private ActorDto mapActorToDto(Actor actor) {
        return ActorDto.builder()
                .firstname(actor.getFirstname())
                .lastname(actor.getLastname())
                .movies(actor.getMovies())
                .build();
    }
}
