package com.habibian.tweeterclone.controller;

import com.habibian.tweeterclone.config.JwtProvider;
import com.habibian.tweeterclone.exception.UserException;
import com.habibian.tweeterclone.model.User;
import com.habibian.tweeterclone.model.Verification;
import com.habibian.tweeterclone.repository.UserRepository;
import com.habibian.tweeterclone.response.AuthResponse;
import com.habibian.tweeterclone.service.CustomUserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling user authentication operations.
 * Provides endpoints for user signup and signin.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsServiceImpl customUserDetailsService;

    /**
     * Handles the user signup process.
     *
     * @param user The User object containing signup details.
     * @return ResponseEntity containing AuthResponse and HTTP status.
     * @throws UserException if the email is already used by another account.
     */
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHnadler(@RequestBody User user) throws UserException {
        String email = user.getEmail();
        String password = user.getPassword();
        String fullname = user.getFullname();
        String birthdate = user.getBirthDate();

        User isEmailExist = userRepository.findByEmail(email);

        if (isEmailExist != null) {
            throw new UserException("Email is already used with another account");
        }

        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setFullname(fullname);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setBirthDate(birthdate);
        createdUser.setVerification(new Verification());

        User savedUser = userRepository.save(createdUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse(token, true);

        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
    }

    /**
     * Handles user signin process.
     *
     * @param user The User object containing signin details.
     * @return ResponseEntity containing AuthResponse and HTTP status.
     */
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody User user) {
        String username = user.getEmail();
        String password = user.getPassword();

        Authentication authentication = authenticate(username, password);

        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse(token, true);

        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.ACCEPTED);
    }

    /**
     * Authenticates the user using the provided username and password.
     *
     * @param username The username (email) of the user.
     * @param password The password of the user.
     * @return Authentication object representing the authenticated user.
     * @throws BadCredentialsException if the username or password is invalid.
     */
    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username...");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
