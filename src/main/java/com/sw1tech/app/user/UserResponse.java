package com.sw1tech.app.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
    
    private String username;
    private boolean enabled;
    private boolean accountNonLocked;
}