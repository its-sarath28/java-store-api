package com.med_store.main.Model;

import java.util.Collection;

import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    @Column(name = "id")
    private Integer id;

    @NotNull(message = "First name is required")
    @Size(min = 3, max = 10)
    @Column(name = "first_name")
    private String first_name;

    @Size(max = 10)
    @Column(name = "last_name")
    private String last_name;

    @NotNull(message = "Email is required")
    @Email(message = "Enter a valid email")
    @UniqueElements
    @Column(name = "email", unique = true)
    private String email;

    @NotNull(message = "Password is required")
    @Size(min = 6)
    @Column(name = "password")
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
