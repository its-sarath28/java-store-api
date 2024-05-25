package com.med_store.main.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.med_store.main.Exception.EmailExistsException;
import com.med_store.main.Exception.UserNotFoundException;
import com.med_store.main.Model.User;
import com.med_store.main.Response.AuthenticationResponse;
import com.med_store.main.Response.ErrorResponse;
import com.med_store.main.Service.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authService;

    public AuthController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<AuthenticationResponse> signUp(@RequestBody User user) {
        return ResponseEntity.ok(authService.signUp(user));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthenticationResponse> signIn(@RequestBody User user) {
        return ResponseEntity.ok(authService.signIn(user));
    }

    @ExceptionHandler({ UserNotFoundException.class, BadCredentialsException.class })
    public ResponseEntity<ErrorResponse> handleAuthenticationExceptions(RuntimeException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailExistsException(EmailExistsException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
