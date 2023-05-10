package com.milosz000.service.impl;

import com.milosz000.dto.DirectorDto;
import com.milosz000.mapper.DirectorMapper;
import com.milosz000.model.Director;
import com.milosz000.model.Movie;
import com.milosz000.repository.DirectorRepository;
import com.milosz000.repository.MovieRepository;
import com.milosz000.service.DirectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

@RequiredArgsConstructor
@Service
public class DirectorServiceImpl implements DirectorService {

    private final DirectorRepository directorRepository;
    private final MovieRepository movieRepository;

    private final DirectorMapper directorMapper;


    @Override
    public ResponseEntity<String> addDirector(DirectorDto directorDto) {

        // TODO: predicate doesn't work properly

        Director director = directorMapper.dtoToModel(directorDto);
        Predicate<Director> existingDirectorFilter = d -> d.getFirstname().equals(director.getFirstname())
                && d.getLastname().equals(director.getLastname())
                && d.getDateOfBirth().equals(director.getDateOfBirth());

        Optional<Director> existingDirector = directorRepository.findAll().stream()
                .filter(existingDirectorFilter)
                .findFirst();

        if (existingDirector.isPresent()) {
            return new ResponseEntity<>("Director already exists", HttpStatusCode.valueOf(400));
        }

        directorRepository.save(director);
        return new ResponseEntity<>("Director created", HttpStatusCode.valueOf(200));
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

}
