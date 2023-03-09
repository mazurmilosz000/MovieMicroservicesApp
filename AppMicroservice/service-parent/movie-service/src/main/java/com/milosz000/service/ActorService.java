package com.milosz000.service;

import com.milosz000.dto.ActorDto;

import java.util.List;

public interface ActorService {
    void addActor(ActorDto actorDto);

    List<ActorDto> getAllActors();
}
