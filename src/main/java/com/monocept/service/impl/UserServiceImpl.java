package com.monocept.service.impl;

import com.monocept.config.JwtUtil;
import com.monocept.entity.User;
import com.monocept.repository.UserRepository;
import com.monocept.request.LoginRequest;
import com.monocept.request.UserDTO;
import com.monocept.security.UserSecurity;
import com.monocept.service.UserService;
import com.monocept.util.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager manager;
    private final UserSecurity userSecurity;

    @Override
    public User createUser(UserDTO userDTO) {
        return userRepository.save(Converter.convertUserDtoToEntity(userDTO));
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new RuntimeException("User not found with ID: " + userId));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean deleteUser(Long userId) {
        userRepository.deleteById(userId);
        return userRepository.existsById(userId);
    }

    @Override
    public String loginPage(LoginRequest loginRequest) {
        Authentication authentication = manager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));
        if (authentication.isAuthenticated()) {
            UserDetails userDetails = userSecurity.loadUserByUsername(loginRequest.username());
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();
            return jwtUtil.generateToken(userDetails.getUsername(), roles);
        }
        return "";
    }
}
