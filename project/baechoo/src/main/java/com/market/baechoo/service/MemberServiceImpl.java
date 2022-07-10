package com.market.baechoo.service;

import com.market.baechoo.domain.Authority;
import com.market.baechoo.domain.Member;
import com.market.baechoo.dto.MemberJoinDto;
import com.market.baechoo.dto.MemberLoginDto;
import com.market.baechoo.jwt.TokenProvider;
import com.market.baechoo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;

    public Integer join(MemberJoinDto memberJoinDto) {
        if (memberRepository.findById(memberJoinDto.getId()).isPresent())
            return 1; // 이미 존재하는 ID

        Authority authority = Authority.builder()
                .authority("ROLE_USER")
                .build();

        memberRepository.save(Member.builder()
                        .id(memberJoinDto.getId())
                        .encryptPassword(passwordEncoder.encode(memberJoinDto.getPassword()))
                        .nickname(memberJoinDto.getNickname())
                        .birthdate(memberJoinDto.getBirthdate())
                        .roles(Collections.singleton(authority))
                .build());
        return 0;
    }

    public String login(MemberLoginDto memberLoginDto) {
        Optional<Member> member = memberRepository.findById(memberLoginDto.getId());
        if (member.isEmpty()) // 존재하지 않는 회원
            return "1";
        else if(!passwordEncoder.matches(memberLoginDto.getPassword(), member.get().getPassword())) // 잘못된 비밀번호
            return "2";
        return tokenProvider.createToken(member.get().getMemberIdx(), member.get().getNickname(), member.get().getRoles());
    }
}

