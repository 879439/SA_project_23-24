package org.example.backend.controllers;

import java.util.Optional;


import org.example.backend.jwt.JwtUtils;
import org.example.backend.models.User;
import org.example.backend.repositories.UserRepository;
import org.example.backend.requests.LoginRequest;
import org.example.backend.requests.SignupRequest;
import org.example.backend.responses.MessageResponse;
import org.example.backend.responses.UserInfoResponse;
import org.example.backend.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;
    //signin
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        System.out.println(loginRequest.getUsername()+" "+loginRequest.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwt(userDetails);

        Optional<User> user = userRepository.findByUsername(userDetails.getUsername());
        if(user.isPresent()) {
            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                    .body(new UserInfoResponse(user.get().getId(),
                            userDetails.getUsername(),
                            userDetails.getEmail(),user.get().getRole()
                            ,user.get().getFirstname(),user.get().getLastname(),user.get().getBirthday(),user.get().getSex()));
        }else{
            return ResponseEntity.badRequest().body("Bad credentials");
        }
    }

    @GetMapping("/hello")
    public ResponseEntity<?> hello(){
        return ResponseEntity.ok(new MessageResponse("Hello my friend"));
    }

    //signup
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),signUpRequest.getLastname(),signUpRequest.getFirstname(), signUpRequest.getBirthday(),signUpRequest.getSex());

        String role = signUpRequest.getRole();

        if (role==null || role.isEmpty()) {
            user.setRole("user");
        } else {
            if(role.equals("admin")){
                user.setRole(role);
            }else{
                user.setRole("user");
            }
        }
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
