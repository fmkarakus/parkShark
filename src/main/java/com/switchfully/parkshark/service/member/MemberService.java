package com.switchfully.parkshark.service.member;

import com.switchfully.parkshark.domain.member.Member;
import com.switchfully.parkshark.domain.member.MemberRepository;
import com.switchfully.parkshark.security.KeycloakService;
import com.switchfully.parkshark.security.KeycloakUserDTO;
import com.switchfully.parkshark.security.Role;
import com.switchfully.parkshark.service.exceptions.EmailNotValidException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
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
        isValidEmailAddress(createMemberDTO.emailAddress());
        Member member = memberRepository.save(memberMapper.mapDTOToMember(createMemberDTO));
//        For test purposes this line is add to avoid adding test members to keycloak
        if (!member.getEmail().startsWith("test")) {
            keycloakService.addUser(new KeycloakUserDTO(member.getEmail(), createMemberDTO.password(), Role.MEMBER));
        }
        return memberMapper.mapMemberToDTO(member);
    }

    public void isValidEmailAddress(String email) {
        if (email == null) {
            throw new EmailNotValidException();
        }
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            throw new EmailNotValidException();
        }
    }
}
