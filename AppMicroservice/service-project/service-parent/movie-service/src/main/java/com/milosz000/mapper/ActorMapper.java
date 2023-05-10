package com.milosz000.mapper;

import com.milosz000.dto.ActorDto;
import com.milosz000.dto.DirectorDto;
import com.milosz000.model.Actor;
import com.milosz000.model.Director;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActorMapper {

    ActorDto modelToDto(ActorDto actorDto);
    Actor dtoToModel(ActorDto actorDto);
}
