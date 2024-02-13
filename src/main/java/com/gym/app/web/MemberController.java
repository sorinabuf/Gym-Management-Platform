package com.gym.app.web;

import com.gym.app.service.MemberService;
import com.gym.app.service.dto.MemberPostDTO;
import com.gym.app.service.dto.MembershipDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.gym.app.service.dto.MemberDTO;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @PostMapping("/members")
    public ResponseEntity<MemberPostDTO> addMember(@RequestBody MemberPostDTO memberPostDTO)
            throws URISyntaxException {
        MemberPostDTO addedMember = memberService.addMember(memberPostDTO);

        return ResponseEntity.created(new URI("/api/members/" + addedMember.getId()))
                .body(addedMember);
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<MemberDTO> getMember(@PathVariable int id) {
        Optional<MemberDTO> memberDTO = memberService.getMember(id);

        return memberDTO.map(dto -> ResponseEntity.ok().body(dto))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/members")
    public ResponseEntity<List<MemberDTO>> getAllMembers() {
        return ResponseEntity.ok().body(memberService.getAllMembers());
    }

    @PutMapping("/renew-membership")
    public ResponseEntity<MemberDTO> renewMembership(@RequestBody MembershipDTO membershipDTO) {
        return ResponseEntity.ok().body(memberService.renewMembership(membershipDTO));
    }
}
