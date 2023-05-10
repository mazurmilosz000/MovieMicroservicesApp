package com.milosz000.service;

import com.milosz000.dto.DirectorDto;
import com.milosz000.model.Director;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DirectorService {
    ResponseEntity<String> addDirector(DirectorDto directorDto);

    List<Director> getAllDirectors();

    String deleteDirector(Long id);

    Director getDirector(Long id);

    String addMovieToDirector(Long directorId, Long movieId);
}
