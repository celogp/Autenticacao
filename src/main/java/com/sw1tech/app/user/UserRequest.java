package com.sw1tech.app.user;

import java.util.HashSet;

import com.sw1tech.app.authoritie.AuthoritieRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    
    private int id;
    private String username;
    private String password;
    private HashSet<AuthoritieRequest> authorities;

}