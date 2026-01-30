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
    @PostMapping("/profile/{userId}")
    public ResponseEntity<?> saveProfile(@PathVariable Integer userId, @RequestBody Empmodel details) {
        try {
            return ResponseEntity.ok(authService.createProfile(userId, details));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // --- UPDATE PROFILE (Modify existing data) ---
    @PutMapping("/profile/{userId}")
    public ResponseEntity<?> updateProfile(@PathVariable Integer userId, @RequestBody Empmodel details) {
        try {
            Empmodel updated = authService.updateExistingProfile(userId, details);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // --- GET PROFILE ---
    @GetMapping("/profile/{userId}")
    public ResponseEntity<Empmodel> getProfile(@PathVariable Integer userId) {
        return authService.getProfileByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // --- DELETE PROFILE ---
    @DeleteMapping("/profile/{userId}")
    public ResponseEntity<String> deleteProfile(@PathVariable Integer userId) {
        try {
            authService.deleteProfile(userId);
            return ResponseEntity.ok("Profile deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}