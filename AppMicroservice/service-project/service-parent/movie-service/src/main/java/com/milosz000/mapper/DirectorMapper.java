package com.milosz000.mapper;

import com.milosz000.dto.DirectorDto;
import com.milosz000.model.Director;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DirectorMapper {

    DirectorDto modelToDto(Director director);
    Director dtoToModel(DirectorDto directorDto);
}
