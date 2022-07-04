package com.market.baechoo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int memberIdx;

    @Column(nullable = false)
    private String id;

    //@Column(nullable = false)
    private String encryptPassword;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String birthdate;

    @ManyToMany
    @JoinTable(
            name="Member_Authority",
            joinColumns = {@JoinColumn(name="member_idx", referencedColumnName = "memberIdx")},
            inverseJoinColumns = {@JoinColumn(name="authority", referencedColumnName = "authority")}
    )
    private Set<Authority> authorities;
}
