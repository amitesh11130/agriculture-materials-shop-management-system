package com.monocept.service;

import com.monocept.entity.User;
import com.monocept.request.LoginRequest;
import com.monocept.request.UserDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface UserService {

    User createUser(UserDTO userDTO);

    User getUserById(Long userId);

    List<User> getAllUsers();


    boolean deleteUser(Long userId);

    String loginPage(@Valid LoginRequest loginRequest);
}
