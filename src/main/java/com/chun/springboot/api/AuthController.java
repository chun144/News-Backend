package com.chun.springboot.api;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.chun.springboot.entity.RoleEntity;
import com.chun.springboot.entity.UserEntity;
import com.chun.springboot.jwt.JwtUtils;
import com.chun.springboot.jwt.dto.JwtResponse;
import com.chun.springboot.jwt.dto.LoginRequest;
import com.chun.springboot.jwt.dto.MessageResponse;
import com.chun.springboot.jwt.dto.SignupRequest;
import com.chun.springboot.repository.RoleRepository;
import com.chun.springboot.repository.UserRepository;
import com.chun.springboot.security.CustomUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Validated @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUser().getId(), userDetails.getUsername(),
                userDetails.getUser().getEmail(), userDetails.getUser().getPhone(), userDetails.getUser().getFullName(),
                userDetails.getUser().getStatus(), roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Validated @RequestBody SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
            .badRequest()
            .body(new MessageResponse("Error: Email is already in use"));
        }

        UserEntity user = new UserEntity(signupRequest.getUsername(), signupRequest.getEmail(),
                                        encoder.encode(signupRequest.getPassword()), signupRequest.getPhone(),
                                        signupRequest.getFullName(), signupRequest.getStatus());
        
        Set<String> strRoles = signupRequest.getRole();
        Set<RoleEntity> roles = new HashSet<>();

        if (strRoles == null) {
            RoleEntity userRole = roleRepository.findRoleByName("ROLE_MEMBER");
            if (userRole == null) {
                throw new RuntimeException("Error: Role is not found.");
            } else {
                roles.add(userRole);
            }
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        RoleEntity adminRole = roleRepository.findRoleByName("ROLE_ADMIN");
                        if (adminRole == null) {
                            throw new RuntimeException("Error: Role is not found.");
                        } else {
                            roles.add(adminRole);
                            roles.add(roleRepository.findRoleByName("ROLE_MEMBER"));
                        }
                        break;

                    case "mod":
                        RoleEntity modRole = roleRepository.findRoleByName("ROLE_MODERATOR");
                        if (modRole == null) {
                            throw new RuntimeException("Error: Role is not found.");
                        } else {
                            roles.add(modRole);
                            roles.add(roleRepository.findRoleByName("ROLE_MEMBER"));
                        } 
                        break;

                    default:
                        RoleEntity userRole = roleRepository.findRoleByName("ROLE_MEMBER");
                        if (userRole == null) {
                            throw new RuntimeException("Error: Role is not found.");
                        } else {
                            roles.add(userRole);
                        } 
                        break;
                }
            });
        }


        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

}
