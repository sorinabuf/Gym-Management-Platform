package com.gym.app.web;

import com.gym.app.service.MembershipTypeService;
import com.gym.app.service.dto.MembershipTypeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
public class MembershipTypeController {
    @Autowired
    private MembershipTypeService membershipTypeService;

    @PostMapping("/membership-types")
    public ResponseEntity<MembershipTypeDTO> addMembershipType(
            @RequestBody MembershipTypeDTO membershipTypeDTO)
            throws URISyntaxException {
        MembershipTypeDTO addedMembershipType =
                membershipTypeService.addMembershipType(membershipTypeDTO);

        return ResponseEntity.created(
                        new URI("/api/membership-types/" + addedMembershipType.getId()))
                .body(addedMembershipType);
    }
}
