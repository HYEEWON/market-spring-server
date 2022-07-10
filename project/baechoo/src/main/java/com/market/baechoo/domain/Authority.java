package com.market.baechoo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*
    ROLE_USER, ROLE_ADMIN
 */

@Entity
@Table
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Authority {
    @Id
    @Column(length = 25)
    private String authority;
}
