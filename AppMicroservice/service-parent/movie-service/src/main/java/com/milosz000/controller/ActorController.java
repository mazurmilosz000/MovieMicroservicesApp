package com.milosz000.controller;

import com.milosz000.dto.ActorDto;
import com.milosz000.model.Actor;
import com.milosz000.service.ActorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/actor")
public class ActorController {

    private final ActorService actorService;

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @PostMapping
    public String addActor(@RequestBody ActorDto actorDto) {

        actorService.addActor(actorDto);

        return "actor added successfully!";
    }

    @GetMapping
    public List<Actor> getAllActors() {
        return actorService.getAllActors();
    }



}
