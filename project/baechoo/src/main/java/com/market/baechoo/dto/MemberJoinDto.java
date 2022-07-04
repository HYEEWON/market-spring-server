package com.market.baechoo.dto;

import com.market.baechoo.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberJoinDto {

    @Size(min=5, max=20)
    private String id;

    //@Size(min=8, max=16)
    private String password;

    @Size(min=5, max=20)
    private String nickname;

    //@Pattern(regexp="(19|20)\\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])")
    private String birthdate;

    public Member toEntity() {
        return Member.builder()
                .id(id)
                .encryptPassword(password)
                .nickname(nickname)
                .birthdate(birthdate)
                .build();
    }
}
