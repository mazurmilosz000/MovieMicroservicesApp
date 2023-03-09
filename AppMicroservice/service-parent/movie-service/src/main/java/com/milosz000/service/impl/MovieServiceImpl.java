package com.milosz000.service.impl;


import com.milosz000.dto.MovieDto;
import com.milosz000.model.Movie;
import com.milosz000.repository.MovieRepository;
import com.milosz000.service.MovieService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void addMovie(MovieDto movieRequest) {
        Movie movie = mapDtoToMovie(movieRequest);
        movieRepository.save(movie);
    }

    @Override
    public List<MovieDto> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();

        if (movies.size() == 0) return new ArrayList<>();

        // map every movie to movieDto and return as list
        return movies.stream().map(this::mapMovieToDto).toList();
    }


    // method that convert Movie to movieDto
    private MovieDto mapMovieToDto(Movie movie) {

        return MovieDto.builder()
                .name(movie.getName())
                .director(movie.getDirector())
                .genre(movie.getGenre())
                .description(movie.getDescription())
                .build();
    }

    // method that convert movieDto to Movie
    private Movie mapDtoToMovie(MovieDto movieDto) {

        return Movie.builder()
                .name(movieDto.getName())
                .director(movieDto.getDirector())
                .genre(movieDto.getGenre())
                .description(movieDto.getDescription())
                .release(movieDto.getRelease())
                .build();
    }
}
