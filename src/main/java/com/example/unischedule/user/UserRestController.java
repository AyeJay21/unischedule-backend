package com.example.unischedule.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/signUp")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if(user == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(userService.createUser(user));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/signIn")
    public ResponseEntity<User> loginUser(@RequestBody User user) {
        User existingUser = userService.getUserByEmail(user.getEmail());
        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }
        if (!existingUser.getPassword().equals(user.getPassword())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(existingUser);
    }

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
