package com.example.testtasktelros.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String surname;


    @Column(nullable = false, unique = true)
    private String username;

    @Column
    private String fullName;

    @Column
    private String patronymic;

    @Column
    private String birthday;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String numberPhone;

    @Column
    private String image;

    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles;

    public User(String surname, String username, String fullName, String patronymic, String birthday, String email, String numberPhone, String image, String password, Set<Role> roles) {
        this.surname = surname;
        this.username = username;
        this.fullName = fullName;
        this.patronymic = patronymic;
        this.birthday = birthday;
        this.email = email;
        this.numberPhone = numberPhone;
        this.image = image;
        this.password = password;
        this.roles = roles;
    }
}
