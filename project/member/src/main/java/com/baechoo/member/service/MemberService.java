package com.baechoo.member.service;

import com.baechoo.member.domain.Member;
import com.baechoo.member.dto.MemberDTO;

public interface MemberService {
    public Member join(MemberDTO memberDTO);
}
