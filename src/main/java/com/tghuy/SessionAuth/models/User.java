package com.tghuy.SessionAuth.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "user_info")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false, length = 125)
    private String password;
    @Column(nullable = false, length = 35)
    private String fullName;
    @Column(nullable = false, length = 30)
    private String email;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Roles> rolesList;
}
