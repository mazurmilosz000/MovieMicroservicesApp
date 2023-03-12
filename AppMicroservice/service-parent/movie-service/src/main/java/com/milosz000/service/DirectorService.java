package com.milosz000.service;

import com.milosz000.dto.DirectorDto;
import com.milosz000.model.Director;

import java.util.List;
import java.util.Set;

public interface DirectorService {
    void addDirector(DirectorDto directorDto);

    List<Director> getAllDirectors();
}
