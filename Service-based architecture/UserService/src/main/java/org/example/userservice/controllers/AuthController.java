package org.example.userservice.controllers;

import jakarta.validation.Valid;
import org.example.userservice.jwt.JwtUtils;
import org.example.userservice.models.User;
import org.example.userservice.repositories.UserRepository;
import org.example.userservice.requests.LoginRequest;
import org.example.userservice.requests.SignupRequest;
import org.example.userservice.responses.MessageResponse;
import org.example.userservice.responses.UserInfoResponse;
import org.example.userservice.services.UserDetailsImpl;
import org.example.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@CrossOrigin(origins = {"http://localhost:4200","http://localhost:8081","http://localhost:8082"})
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;
    private final UserService userService;

    @Autowired
    JwtUtils jwtUtils;
    public AuthController(UserService userService){
        this.userService = userService;
    }
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

    @GetMapping("/{username}")
    public User loadUserByUsername(@PathVariable String username){
       return userService.getUserByUsername(username);
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
