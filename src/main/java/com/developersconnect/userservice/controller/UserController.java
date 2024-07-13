package com.developersconnect.userservice.controller;

import com.developersconnect.userservice.model.AppUser;
import com.developersconnect.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/demo")
    public ResponseEntity<String> demo() {
        return ResponseEntity.ok("Hello From secured end point");
    }
    @GetMapping
    public List<AppUser> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<AppUser> getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping
    public AppUser createUser(@RequestBody AppUser user) {
        return userService.save(user);
    }

    @GetMapping("/fetch-user")
    public List<AppUser> fetchAndSaveRandomUser() {
        return userService.fetchAndSaveUserResponse();
    }

    @PutMapping("/{id}")
    public AppUser updateUser(@PathVariable Long id, @RequestBody AppUser user) {
        user.setId(id);
        return userService.save(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
