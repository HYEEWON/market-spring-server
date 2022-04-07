package com.baechoo.member.dto;

import com.baechoo.member.domain.Member;
import lombok.*;
import javax.validation.constraints.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class MemberDTO {

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
