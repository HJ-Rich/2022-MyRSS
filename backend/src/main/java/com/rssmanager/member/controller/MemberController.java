package com.rssmanager.member.controller;

import com.rssmanager.member.controller.dto.MemberResponse;
import com.rssmanager.member.domain.Member;
import com.rssmanager.member.service.MemberService;
import com.rssmanager.util.SessionManager;
import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/members")
@RestController
public class MemberController {

    private final MemberService memberService;
    private final SessionManager sessionManager;

    public MemberController(final MemberService memberService, final SessionManager sessionManager) {
        this.memberService = memberService;
        this.sessionManager = sessionManager;
    }

    @GetMapping("/me")
    public ResponseEntity<MemberResponse> me() throws URISyntaxException {
        if (!sessionManager.isLoggedIn()) {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(new URI("http://localhost:3000/"));
            return new ResponseEntity<>(httpHeaders, HttpStatus.FOUND);
        }

        Long memberId = sessionManager.getAttribute("memberId");
        Member member = memberService.findById(memberId);

        return ResponseEntity.ok(MemberResponse.from(member));
    }
}
