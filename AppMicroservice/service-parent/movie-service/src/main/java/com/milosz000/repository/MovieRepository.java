package com.milosz000.repository;

import com.milosz000.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Long, Movie> {
}
