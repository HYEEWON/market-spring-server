package com.baechoo.member.service;

import com.baechoo.member.domain.Member;
import com.baechoo.member.dto.MemberDTO;
import com.baechoo.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /*public Member save(MemberDTO memberDTO) {
        return memberRepository.save(memberDTO);
    }*/
}
