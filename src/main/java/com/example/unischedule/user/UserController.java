package com.example.unischedule.user;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/signUp")
    public ResponseEntity<User> createUser(User user) {
        if(user == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(userService.createUser(user));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/SignIn")
    public ResponseEntity<User> getUserByEmail(String Email){
        return ResponseEntity.ok(userService.getUserByEmail(Email));
    }

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}


