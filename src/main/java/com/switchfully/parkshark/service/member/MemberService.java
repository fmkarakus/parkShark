package com.switchfully.parkshark.service.member;

import com.switchfully.parkshark.domain.member.Member;
import com.switchfully.parkshark.domain.member.MemberRepository;
import com.switchfully.parkshark.security.KeycloakService;
import com.switchfully.parkshark.security.KeycloakUserDTO;
import com.switchfully.parkshark.security.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class MemberService {
    private final Logger log = LoggerFactory.getLogger(MemberService.class);
    private final MemberRepository memberRepository;
    private final KeycloakService keycloakService;
    private final MemberMapper memberMapper;

    public MemberService(MemberRepository memberRepository, KeycloakService keycloakService) {
        this.memberRepository = memberRepository;
        this.keycloakService = keycloakService;
        memberMapper = new MemberMapper();
    }

    public MemberDTO registerANewMember(CreateMemberDTO createMemberDTO) {
        Member member = memberRepository.save(memberMapper.mapDTOToMember(createMemberDTO));
        keycloakService.addUser(new KeycloakUserDTO(member.getEmail(), createMemberDTO.password(), Role.MEMBER));
        return memberMapper.mapMemberToDTO(member);
    }
}
