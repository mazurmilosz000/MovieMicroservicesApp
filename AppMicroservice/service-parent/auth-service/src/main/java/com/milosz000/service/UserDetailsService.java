package com.milosz000.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsService {
    UserDetails getUserByUsername(String username);
}
