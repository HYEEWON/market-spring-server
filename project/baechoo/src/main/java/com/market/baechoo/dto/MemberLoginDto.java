package com.market.baechoo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginDto {
    @Size(min=5, max=20)
    private String id;

    //@Size(min=8, max=16)
    private String password;
}
