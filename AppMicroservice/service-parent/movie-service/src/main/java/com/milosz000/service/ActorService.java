package com.milosz000.service;

import com.milosz000.dto.ActorDto;
import com.milosz000.model.Actor;

import java.util.List;

public interface ActorService {
    void addActor(ActorDto actorDto);

    List<Actor> getAllActors();
}
