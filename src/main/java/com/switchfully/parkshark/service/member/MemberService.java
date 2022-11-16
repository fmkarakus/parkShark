package com.switchfully.parkshark.service.member;

import com.switchfully.parkshark.domain.member.Member;
import com.switchfully.parkshark.domain.member.MemberRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
        memberMapper = new MemberMapper();
    }

    public MemberDTO registerANewMember(CreateMemberDTO createMemberDTO) {
        Member member = memberRepository.save(memberMapper.mapDTOToMember(createMemberDTO));
        return memberMapper.mapMemberToDTO(member);
    }
}
