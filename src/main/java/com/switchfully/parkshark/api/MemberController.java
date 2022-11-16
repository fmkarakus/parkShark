package com.switchfully.parkshark.api;

import com.switchfully.parkshark.service.member.CreateMemberDTO;
import com.switchfully.parkshark.service.member.MemberDTO;
import com.switchfully.parkshark.service.member.MemberService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public MemberDTO registerANewMember(@RequestBody CreateMemberDTO createMemberDTO) {
        return memberService.registerANewMember(createMemberDTO);
    }
}
