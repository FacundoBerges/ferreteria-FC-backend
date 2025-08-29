package com.ferreteriafc.api.backend.persistence.entity;

import java.util.Set;

import jakarta.persistence.*;

import lombok.*;

import com.ferreteriafc.api.backend.domain.utils.Constant;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id;

    @Column(nullable = false, unique = true, length = Constant.USERNAME_MAX_LENGTH)
    private String username;

    @Column(name = "hashed_password", nullable = false)
    private String hashedPassword;

    @Column(nullable = false, unique = true, length = Constant.EMAIL_MAX_LENGTH)
    private String email;

    @Column(nullable = false)
    private Boolean locked;

    @Column(nullable = false)
    private Boolean disabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = { @JoinColumn(name = "user_id") },
        inverseJoinColumns = { @JoinColumn(name = "role_id") }
    )
    private Set<Role> roles;

}
