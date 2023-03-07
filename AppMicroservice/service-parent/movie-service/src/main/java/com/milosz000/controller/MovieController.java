package com.milosz000.controller;

import com.milosz000.dto.MovieRequest;
import com.milosz000.dto.MovieResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    @PostMapping
    public ResponseEntity<MovieResponse> addMovie(@RequestBody MovieRequest movieRequest) {
        // TODO: add service layer and save request data in database
    }
}
