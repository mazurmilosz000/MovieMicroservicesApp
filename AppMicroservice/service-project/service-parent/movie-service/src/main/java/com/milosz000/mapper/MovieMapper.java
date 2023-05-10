package com.milosz000.mapper;


import com.milosz000.dto.MovieDto;
import com.milosz000.model.Movie;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface MovieMapper {

    MovieDto modelToDto(Movie movie);
    Movie dtoToModel(MovieDto movieDto);
}
