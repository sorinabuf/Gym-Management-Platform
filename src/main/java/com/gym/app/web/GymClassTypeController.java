package com.gym.app.web;

import com.gym.app.service.dto.GymClassTypeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gym.app.service.GymClassTypeService;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
public class GymClassTypeController {
    @Autowired
    private GymClassTypeService gymClassTypeService;

    @PostMapping("/gym-class-types")
    public ResponseEntity<GymClassTypeDTO> addMembershipType(
            @RequestBody GymClassTypeDTO gymClassTypeDTO)
            throws URISyntaxException {
        GymClassTypeDTO addedGymClassType = gymClassTypeService.addGymClassType(gymClassTypeDTO);

        return ResponseEntity.created(new URI("/api/gym-class-types/" + addedGymClassType.getId()))
                .body(addedGymClassType);
    }
}
