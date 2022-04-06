package com.baechoo.member.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int memberIdx;

    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private String encryptPassword;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private Date birthdate;
}
