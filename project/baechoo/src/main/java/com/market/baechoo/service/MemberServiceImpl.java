package com.market.baechoo.service;

import com.market.baechoo.dto.MemberJoinDto;
import com.market.baechoo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public Integer join(MemberJoinDto memberJoinDto) {
        /*if (memberRepository.findById(memberJoinDto.getId()).equals(null)) {
            return 1; // 이미 존재하는 ID
        }*/

        memberRepository.save(memberJoinDto.toEntity());
        return 0;
    }
}

/*
if (memberRepository.findById(memberJoinDto.getId()).equals(memberJoinDto.getId())) {
            return 1; // 이미 존재하는 ID
        }

        memberRepository.save(Member.builder()
                .id(memberJoinDto.getId())
                .encryptPassword(passwordEncoder.encode(memberJoinDto.getPassword()))
                .nickname(memberJoinDto.getNickname())
                .birthdate(memberJoinDto.getBirthdate())
                .authorities(Collections.singleton("ROLE_USER"))
                .build());
        return 0;
 */
