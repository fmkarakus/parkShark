package com.switchfully.parkshark.api;

import com.switchfully.parkshark.service.exceptions.EmailNotValidException;
import com.switchfully.parkshark.service.member.CreateMemberDTO;
import com.switchfully.parkshark.service.member.MemberDTO;
import com.switchfully.parkshark.service.member.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("members")
public class MemberController {
    private final Logger log = LoggerFactory.getLogger(MemberController.class);
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public MemberDTO registerANewMember(@RequestBody CreateMemberDTO createMemberDTO) {
        log.debug("Request to add new member");
        return memberService.registerANewMember(createMemberDTO);
    }
}
