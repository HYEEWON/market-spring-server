package com.baechoo.member.dto;

import lombok.*;
import javax.validation.constraints.*;

import java.util.Date;

@Getter
@Setter
public class MemberDTO {

    @Size(min=5, max=20)
    private String id;

    @Size(min=8, max=16)
    private String encryptPassword;

    @Size(min=5, max=20)
    private String nickname;

    @Pattern(regexp="(19|20)\\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])")
    @NotBlank
    private Date birthdate;
}
