package com.med_store.main.Service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.med_store.main.Exception.EmailExistsException;
import com.med_store.main.Exception.UserNotFoundException;
import com.med_store.main.Model.User;
import com.med_store.main.Repository.UserRepository;
import com.med_store.main.Response.AuthenticationResponse;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse signUp(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new EmailExistsException("Email already exists");
        }

        User newUser = new User();

        newUser.setFirst_name(user.getFirst_name());
        newUser.setLast_name(user.getLast_name());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        newUser = userRepository.save(newUser);

        String token = jwtService.generateToken(newUser);

        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse signIn(User user) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid credentials");
        }

        User userToLogin = userRepository.findByEmail(user.getUsername())
                .orElseThrow(() -> new UserNotFoundException("Invalid credentials"));

        String token = jwtService.generateToken(userToLogin);
        return new AuthenticationResponse(token);
    }

}
