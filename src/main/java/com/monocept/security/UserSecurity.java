package com.monocept.security;

import com.monocept.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserSecurity implements UserDetailsService {

    private final UserRepository userRepository;

    //    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        log.info("Attempting to load admin details for username: {}", username);
//        com.monocept.entity.User user = userRepository.findByUsername(username);
//        if (user == null) {
//            log.error("User with username '{}' not found", username);
//            throw new UsernameNotFoundException("User not found with username: " + username);
//        }
//        log.info("Successfully loaded admin details for username: {}", username);
//        return User.builder()
//                .username(user.getUsername())
//                .password(user.getPassword())
//                .roles(String.valueOf(user.getRole()))
//                .build();
//    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Attempting to load admin details for username: {}", username);

        return userRepository.findByUsername(username)
                .map(user -> {
                    log.info("Successfully loaded admin details for username: {}", username);
                    return User.builder()
                            .username(user.getUsername())
                            .password(user.getPassword())
                            .roles(String.valueOf(user.getRole()))
                            .build();
                })
                .orElseThrow(() -> {
                    log.error("User with username '{}' not found", username);
                    return new UsernameNotFoundException("User not found with username: " + username);
                });
    }
}
