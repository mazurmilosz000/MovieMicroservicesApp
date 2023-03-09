package com.milosz000.service;

import com.milosz000.dto.MovieDto;

import java.util.List;

public interface MovieService {
    void addMovie(MovieDto movieRequest);

    List<MovieDto> getAllMovies();
}
