package com.market.baechoo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table
@Builder
@AllArgsConstructor
public class Member implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer memberIdx;

    @Column(nullable = false, unique = true)
    private String id;

    @Column(nullable = false)
    private String encryptPassword;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String birthdate;

    @ManyToMany(targetEntity = Authority.class)
    @JoinTable(
            name="Member_Authority",
            joinColumns = {@JoinColumn(name="member_idx", referencedColumnName = "memberIdx")},
            inverseJoinColumns = {@JoinColumn(name="authority", referencedColumnName = "authority")}
    )
    private Set<Authority> authorities = new HashSet<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
        for (Authority auth : authorities)
            roles.add(new SimpleGrantedAuthority(auth.getAuthority()));
        return roles;
    }

    @Override
    public String getPassword() {
        return encryptPassword;
    }

    @Override
    public String getUsername() {
        return id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
