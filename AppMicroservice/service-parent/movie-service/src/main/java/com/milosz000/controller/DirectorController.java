package com.milosz000.controller;

import com.milosz000.dto.DirectorDto;
import com.milosz000.model.Director;
import com.milosz000.service.DirectorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/director")
public class DirectorController {

    private final DirectorService directorService;

    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @PostMapping
    public String addDirector(@RequestBody DirectorDto directorDto) {
        directorService.addDirector(directorDto);

        return "Director added successfully!";
    }

    @GetMapping
    public List<Director> getAllDirectors() {
        return directorService.getAllDirectors();
    }

}
