package com.milosz000.service.impl;


import com.milosz000.dto.MovieDto;
import com.milosz000.mapper.MovieMapper;
import com.milosz000.model.Movie;
import com.milosz000.repository.MovieRepository;
import com.milosz000.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    private final MovieMapper movieMapper;


    @Override
    public void addMovie(MovieDto movieRequest) {
        Movie movie = movieMapper.dtoToModel(movieRequest);
        movieRepository.save(movie);
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();

    }

}
