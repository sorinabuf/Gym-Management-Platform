package com.gym.app.web;

import com.gym.app.service.GymClassService;
import com.gym.app.service.dto.GymClassDTO;
import com.gym.app.service.dto.GymClassMemberPostDTO;
import com.gym.app.service.dto.GymClassPostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class GymClassController {
    @Autowired
    private GymClassService gymClassService;

    @PostMapping("/gym-classes")
    public ResponseEntity<GymClassPostDTO> addMembershipType(
            @RequestBody GymClassPostDTO gymClassPostDTO)
            throws URISyntaxException {
        GymClassPostDTO addedGymClass = gymClassService.addGymClass(gymClassPostDTO);

        return ResponseEntity.created(
                new URI("/api/gym-classes?gymClassTypeId=" + addedGymClass.getGymClassTypeId() +
                        "&startTime=" + addedGymClass.getStartTime())).body(addedGymClass);
    }

    @GetMapping("/gym-classes")
    public ResponseEntity<GymClassDTO> getGymClass(
            @RequestParam int gymClassTypeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime startTime) {
        Optional<GymClassDTO> gymClassDTO = gymClassService.getGymClass(gymClassTypeId, startTime);

        return gymClassDTO.map(dto -> ResponseEntity.ok().body(dto))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/gym-classes/signup")
    public ResponseEntity<GymClassDTO> signupMember(@RequestBody
                                                    GymClassMemberPostDTO gymClassMemberPostDTO) {
        return ResponseEntity.ok().body(gymClassService.signupMember(gymClassMemberPostDTO));
    }
}
