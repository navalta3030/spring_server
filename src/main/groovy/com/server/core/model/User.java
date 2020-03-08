package com.server.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "`user`")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First Name is mandatory")
    @Column(name = "first_name")
    private String first_name;

    @NotBlank(message = "Last Name is mandatory")
    @Column(name = "last_name")
    private String last_name;

    @NotBlank(message = "Username is mandatory")
    @Column(name = "username", unique = true)
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Column(name = "password")
    private String password;

    @NotBlank(message = "Email is mandatory")
    @Column(name = "email", unique = true)
    @Email
    private String email;


    // TODO - @NotBlank(message = "Role is mandatory") is not working. I need to make this one not null.
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    //Not persistent. There is no column on database table.
    @Transient
    private String token;
}
