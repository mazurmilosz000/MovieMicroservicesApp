package com.milosz000.controller;

import com.milosz000.dto.DirectorDto;
import com.milosz000.exception.ApiRequestException;
import com.milosz000.model.Director;
import com.milosz000.service.DirectorService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/api/director")
public class DirectorController {

    private final DirectorService directorService;

    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @PostMapping
    public String addDirector(@Valid @RequestBody DirectorDto directorDto) {
        directorService.addDirector(directorDto);

        return "Director added successfully!";
    }

    @GetMapping
    public List<Director> getAllDirectors() {
        return directorService.getAllDirectors();
    }

    @DeleteMapping("delete/{id}")
    public String deleteDirector(@PathVariable("id") Long id) {
        if (directorService.deleteDirector(id).equals("deleted")) {
            return "Director with id " + id + " deleted successfully!";
        }
        throw new ApiRequestException("Director with that id does not exist!");
    }

    @GetMapping("/{id}")
    public Director getDirectorById(@PathVariable("id") Long id){
        try {
            return directorService.getDirector(id);
        } catch (NoSuchElementException e) {
            throw  new ApiRequestException("Director with that id does not exist!");
        }
    }

    @PostMapping("{directorId}/addMovie/{movieId}")
    public String addMovieToDirector(
            @PathVariable("directorId") Long directorId,
            @PathVariable("movieId") Long movieId) {

        log.info("directorId: " + directorId);
        log.info("movieId: " + movieId);

        return directorService.addMovieToDirector(directorId, movieId);
    }


}
