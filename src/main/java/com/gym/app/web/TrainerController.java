package com.gym.app.web;

import com.gym.app.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.gym.app.service.dto.TrainerDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TrainerController {
    @Autowired
    private TrainerService trainerService;

    @PostMapping("/trainers")
    public ResponseEntity<TrainerDTO> addTrainer(@RequestBody TrainerDTO trainerDTO)
            throws URISyntaxException {
        TrainerDTO addedTrainer = trainerService.addTrainer(trainerDTO);

        return ResponseEntity.created(new URI("/api/trainers/" + addedTrainer.getId()))
                .body(addedTrainer);
    }

    @GetMapping("/trainers/{id}")
    public ResponseEntity<TrainerDTO> getTrainer(@PathVariable int id) {
        Optional<TrainerDTO> trainerDTO = trainerService.getTrainer(id);

        return trainerDTO.map(dto -> ResponseEntity.ok().body(dto))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping("/trainers")
    public ResponseEntity<List<TrainerDTO>> getAllTrainers() {
        return ResponseEntity.ok().body(trainerService.getAllTrainers());
    }
}