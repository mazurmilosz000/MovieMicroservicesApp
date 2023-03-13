package com.milosz000.service.impl;

import com.milosz000.dto.DirectorDto;
import com.milosz000.exception.ApiException;
import com.milosz000.exception.ApiRequestException;
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

    private Director mapDtoToDirector(DirectorDto directorDto) {
        return Director.builder()
                .firstname(directorDto.getFirstname())
                .lastname(directorDto.getLastname())
                .build();
    }
}
