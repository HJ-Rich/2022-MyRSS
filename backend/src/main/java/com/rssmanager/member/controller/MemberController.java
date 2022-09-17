package com.rssmanager.member.controller;

import com.rssmanager.member.controller.dto.MemberResponse;
import com.rssmanager.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/members")
@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberResponse> findById(@PathVariable long memberId) {
        MemberResponse memberResponse = memberService.findById(memberId);

        return ResponseEntity.ok(memberResponse);
    }
}
