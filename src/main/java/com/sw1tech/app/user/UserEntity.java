package com.sw1tech.app.user;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.sw1tech.app.authoritie.AuthoritieEntity;

import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
@Entity
@Table(name = "TUsers")
public class UserEntity implements UserDetails {

    private static final long serialVersionUID = -2200459028959826281L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="password")
    @NotEmpty
    private String password;

    @Column(name="username")
    @NotEmpty
    private String username;

    @Column(name="accountnonexpired")
    private boolean accountnonexpired;


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="tusersauthorities",
        joinColumns=
            @JoinColumn(name="userid", referencedColumnName="id"),
        inverseJoinColumns=
            @JoinColumn(name="authoritieid", referencedColumnName="id")
        )
    private Set<AuthoritieEntity> authorities;

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountnonexpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}