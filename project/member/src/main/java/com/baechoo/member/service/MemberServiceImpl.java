package com.baechoo.member.service;

import com.baechoo.member.domain.Member;
import com.baechoo.member.dto.MemberDTO;
import com.baechoo.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member join(MemberDTO memberDTO) {
        return memberRepository.save(memberDTO.toEntity());
    }
}
