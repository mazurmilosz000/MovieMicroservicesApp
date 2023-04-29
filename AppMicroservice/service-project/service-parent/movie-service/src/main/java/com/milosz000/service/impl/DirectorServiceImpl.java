package com.milosz000.service.impl;

import com.milosz000.dto.DirectorDto;
import com.milosz000.model.Director;
import com.milosz000.model.Movie;
import com.milosz000.repository.DirectorRepository;
import com.milosz000.repository.MovieRepository;
import com.milosz000.service.DirectorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DirectorServiceImpl implements DirectorService {

    private final DirectorRepository directorRepository;
    private final MovieRepository movieRepository;

    public DirectorServiceImpl(DirectorRepository directorRepository, MovieRepository movieRepository) {
        this.directorRepository = directorRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public void addDirector(DirectorDto directorDto) {

        directorRepository.save(mapDtoToDirector(directorDto));
    }

    @Override
    public List<Director> getAllDirectors() {
        return directorRepository.findAll();
    }

    @Override
    public String deleteDirector(Long id) {
        if (directorRepository.existsById(id)) {
            directorRepository.deleteById(id);
            return "deleted";
        }
        return "cannot find";
    }

    @Override
    public Director getDirector(Long id) {
        return directorRepository.findById(id).orElseThrow();
    }

    // TODO: director isn't updated, need to fix
    @Override
    public String addMovieToDirector(Long directorId, Long movieId) {

        if (directorRepository.findById(directorId).isPresent() && movieRepository.findById(movieId).isPresent()) {
            Director director = directorRepository.findById(directorId).get();
            Set<Movie> movies = director.getMovies();
            Movie movie = movieRepository.findById(movieId).get();
            if (movies.contains(movie)) return "contains";
            movies.add(movie);
            director.setMovies(movies);
            directorRepository.save(director);
            return "added";
        }
        return "error";
    }

    private Director mapDtoToDirector(DirectorDto directorDto) {
        return Director.builder()
                .firstname(directorDto.getFirstname())
                .lastname(directorDto.getLastname())
                .build();
    }
}
