// package com.chun.springboot.security;

// import java.util.HashSet;
// import java.util.Set;

// import com.chun.springboot.entity.RoleEntity;
// import com.chun.springboot.entity.UserEntity;
// import com.chun.springboot.repository.RoleRepository;
// import com.chun.springboot.repository.UserRepository;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.ApplicationListener;
// import org.springframework.context.event.ContextRefreshedEvent;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Component;

// @Component
// public class DataSeedingListener implements ApplicationListener<ContextRefreshedEvent> {

//     @Autowired
//     private UserRepository userRepository;

//     @Autowired
//     private RoleRepository roleRepository;

//     @Autowired 
//     private PasswordEncoder passwordEncoder;

//     @Override
//     public void onApplicationEvent(ContextRefreshedEvent arg0) {
//         // Roles
//         if (roleRepository.findRoleByName("ROLE_ADMIN") == null) {
//             roleRepository.save(new RoleEntity("ROLE_ADMIN"));
//         }

//         if (roleRepository.findRoleByName("ROLE_MEMBER") == null) {
//             roleRepository.save(new RoleEntity("ROLE_MEMBER"));
//         }

//         // Admin account
//         if (userRepository.findByUsername("admin@gmail.com") == null) {
//             UserEntity admin = new UserEntity();
//             admin.setUsername("admin@gmail.com");
//             admin.setPassword(passwordEncoder.encode("123456"));
//             Set<RoleEntity> roles = new HashSet<>();
//             roles.add(roleRepository.findRoleByName("ROLE_ADMIN"));
//             roles.add(roleRepository.findRoleByName("ROLE_MEMBER"));
//             admin.setRoles(roles);
//             userRepository.save(admin);
//         }

//         // Member account
//         if (userRepository.findByUsername("member@gmail.com") == null) {
//             UserEntity user = new UserEntity();
//             user.setUsername("member@gmail.com");
//             user.setPassword(passwordEncoder.encode("123456"));
//             Set<RoleEntity> roles = new HashSet<>();
//             roles.add(roleRepository.findRoleByName("ROLE_MEMBER"));
//             user.setRoles(roles);
//             userRepository.save(user);
//         }
//     }

// }