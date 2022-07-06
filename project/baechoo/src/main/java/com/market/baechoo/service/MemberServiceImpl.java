package com.market.baechoo.service;

import com.market.baechoo.domain.Authority;
import com.market.baechoo.domain.Member;
import com.market.baechoo.dto.MemberJoinDto;
import com.market.baechoo.dto.MemberLoginDto;
import com.market.baechoo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    public Integer join(MemberJoinDto memberJoinDto) {
        if (memberRepository.findById(memberJoinDto.getId()).equals(memberJoinDto.getId())) {
            return 1; // 이미 존재하는 ID
        }
        Authority authority = Authority.builder()
                .authority("ROLE_USER")
                .build();

        memberRepository.save(Member.builder()
                        .id(memberJoinDto.getId())
                        .encryptPassword(passwordEncoder.encode(memberJoinDto.getPassword()))
                        .nickname(memberJoinDto.getNickname())
                        .birthdate(memberJoinDto.getBirthdate())
                        .authorities(Collections.singleton(authority))
                .build());
        return 0;
    }

    public Integer login(MemberLoginDto memberLoginDto) {
        return 0;
    }
}

