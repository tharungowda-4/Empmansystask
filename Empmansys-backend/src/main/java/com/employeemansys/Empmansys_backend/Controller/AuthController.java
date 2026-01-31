package com.employeemansys.Empmansys_backend.Controller;

import com.employeemansys.Empmansys_backend.Model.Empmodel;
import com.employeemansys.Empmansys_backend.Model.Userauth; // Import added
import com.employeemansys.Empmansys_backend.Service.AuthService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Data
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
@NoArgsConstructor
@AllArgsConstructor
public class AuthController {

    @Autowired
    private AuthService authService;

    // --- NEW: SIGNUP ENDPOINT ---
    // URL: http://localhost:8080/api/auth/signup
    @PostMapping("/signup")
    public ResponseEntity<Userauth> signup(@RequestBody Userauth user) {
        return new ResponseEntity<>(authService.signup(user), HttpStatus.CREATED);
    }

    // --- NEW: LOGIN ENDPOINT ---
    // URL: http://localhost:8080/api/auth/login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Userauth loginRequest) {
        try {
            Userauth user = authService.login(loginRequest.getPhoneNumber(), loginRequest.getPassword());
            return ResponseEntity.ok(user); // Returns the Userauth object (including the id)
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    // --- CREATE PROFILE (First time registration) ---
    @PostMapping("/profile/{user_id}")
    public ResponseEntity<?> saveProfile(@PathVariable("user_id") Integer user_id, @RequestBody Empmodel details) {
        try {
            return ResponseEntity.ok(authService.createProfile(user_id, details));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // --- UPDATE PROFILE (Modify existing data) ---
    @PutMapping("/profile/{user_id}")
    public ResponseEntity<?> updateProfile(@PathVariable("user_id") Integer user_id, @RequestBody Empmodel details) {
        try {
            Empmodel updated = authService.updateExistingProfile(user_id, details);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // --- GET PROFILE ---
    @GetMapping("/profile/{user_id}")
    public ResponseEntity<Empmodel> getProfile(@PathVariable("user_id") Integer user_id) {
        return authService.getProfileByUserId(user_id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // --- DELETE PROFILE ---
    @DeleteMapping("/profile/{user_id}")
    public ResponseEntity<String> deleteProfile(@PathVariable("user_id") Integer user_id) {
        try {
            authService.deleteProfile(user_id);
            return ResponseEntity.ok("Profile deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}