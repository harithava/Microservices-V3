package com.maersk.multiplication.user.domain;


import jakarta.persistence.*;
import lombok.*;


/**
 * Stores information to identify the user
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String alias;

    public User(final String alias) {
        this(null, alias);
    }
}
