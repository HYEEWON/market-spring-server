package com.market.baechoo.service;

import com.market.baechoo.dto.MemberJoinDto;
import com.market.baechoo.dto.MemberLoginDto;

public interface MemberService {
    public Integer join(MemberJoinDto memberJoinDto);
    public Integer login(MemberLoginDto memberLoginDto);
}
