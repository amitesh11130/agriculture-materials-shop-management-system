package com.monocept.controller;

import com.monocept.entity.User;
import com.monocept.request.LoginRequest;
import com.monocept.request.UserDTO;
import com.monocept.response.ApiResponse;
import com.monocept.response.Meta;
import com.monocept.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/login")
    public ApiResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        String token = userService.loginPage(loginRequest);
        if (token.isEmpty()) {
            Meta meta = new Meta(HttpStatus.CREATED.value(), true, "Invalid request");
            return new ApiResponse(meta, null, null);
        }
        Meta meta = new Meta(HttpStatus.CREATED.value(), true, "Token generated successfully");
        return new ApiResponse(meta, token, null);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ApiResponse createUser(@Valid @RequestBody UserDTO userDTO) {
        User user = userService.createUser(userDTO);
        Meta meta = new Meta(HttpStatus.CREATED.value(), true, "User created successfully");

        return new ApiResponse(meta, user, null);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ApiResponse getUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        Meta meta = new Meta(HttpStatus.OK.value(), true, "User retrieved successfully");
        return new ApiResponse(meta, user, null);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ApiResponse getAllUsers() {
        List<User> users = userService.getAllUsers();
        Meta meta = new Meta(HttpStatus.OK.value(), true, "Users retrieved successfully");
        return new ApiResponse(meta, users, null);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ApiResponse deleteUser(@PathVariable Long userId) {
        boolean deleted = userService.deleteUser(userId);
        Meta meta;
        if (!deleted) {
            meta = new Meta(HttpStatus.OK.value(), true, "User deleted successfully");
        } else {
            meta = new Meta(HttpStatus.NOT_FOUND.value(), false, "User not found or could not be deleted");
        }
        return new ApiResponse(meta, null, null);
    }


}
