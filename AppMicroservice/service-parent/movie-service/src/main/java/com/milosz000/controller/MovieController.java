package com.milosz000.controller;

import com.milosz000.dto.MovieDto;
import com.milosz000.model.Movie;
import com.milosz000.service.MovieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public String addMovie(@RequestBody MovieDto movieRequest) {

        movieService.addMovie(movieRequest);

        return "Movie added successfully!";
    }

    @GetMapping
    public List<Movie> getAllMovies() {

        return movieService.getAllMovies();
    }
}
