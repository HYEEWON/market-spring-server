package com.baechoo.member.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int memberIdx;

    @Column
    private String encryptPassword;

    @Column
    private String nickname;

    @Column
    private Date birthdate;
}
