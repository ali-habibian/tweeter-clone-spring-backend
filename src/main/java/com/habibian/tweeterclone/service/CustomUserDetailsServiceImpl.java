package com.habibian.tweeterclone.service;

import com.habibian.tweeterclone.model.User;
import com.habibian.tweeterclone.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom implementation of the Spring Security UserDetailsService.
 * Retrieves user details from the UserRepository for authentication.
 */
@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Constructs a CustomUserDetailsServiceImpl with the specified UserRepository.
     *
     * @param userRepository The UserRepository to retrieve user details.
     */
    public CustomUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads user details by username for authentication.
     *
     * @param username The username (email) of the user.
     * @return UserDetails object representing the authenticated user.
     * @throws UsernameNotFoundException if the user is not found or is a Google login user.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);

        if (user == null || user.isLogin_with_google()) {
            throw new UsernameNotFoundException("Username not found with email " + username);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }
}
