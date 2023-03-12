package com.milosz000.service.impl;

import com.milosz000.dto.DirectorDto;
import com.milosz000.model.Director;
import com.milosz000.repository.DirectorRepository;
import com.milosz000.service.DirectorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectorServiceImpl implements DirectorService {

    private final DirectorRepository directorRepository;

    public DirectorServiceImpl(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    @Override
    public void addDirector(DirectorDto directorDto) {
        directorRepository.save(mapDtoToDirector(directorDto));
    }

    @Override
    public List<Director> getAllDirectors() {
        return directorRepository.findAll();
    }

    private Director mapDtoToDirector(DirectorDto directorDto) {
        return Director.builder()
                .firstname(directorDto.getFirstname())
                .lastname(directorDto.getLastname())
                .build();
    }
}
