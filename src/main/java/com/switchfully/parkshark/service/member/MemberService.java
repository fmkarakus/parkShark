package com.switchfully.parkshark.service.member;

import com.switchfully.parkshark.domain.member.Member;
import com.switchfully.parkshark.domain.member.MemberRepository;
import com.switchfully.parkshark.security.KeycloakService;
import com.switchfully.parkshark.security.KeycloakUserDTO;
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
    private final MemberValidator memberValidator;

    public MemberService(MemberRepository memberRepository, KeycloakService keycloakService) {
        this.memberRepository = memberRepository;
        this.keycloakService = keycloakService;
        memberMapper = new MemberMapper();
        memberValidator = new MemberValidator();
    }

    public MemberDTO registerANewMember(CreateMemberDTO createMemberDTO) {
        memberValidator.validateMember(createMemberDTO);
        Member member = memberRepository.save(memberMapper.mapDTOToMember(createMemberDTO));
        //TODO: ask how we could do these tests without the if-statement
//        For test purposes this line is add to avoid adding test members to keycloak
        if (!member.getEmail().startsWith("test")) {
            keycloakService.addUser(new KeycloakUserDTO(member.getEmail(), createMemberDTO.password(), member.getRole()));
        }
        return memberMapper.mapMemberToDTO(member);
    }

    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(()->new IllegalArgumentException("Member id does not exist"));
    }
}
