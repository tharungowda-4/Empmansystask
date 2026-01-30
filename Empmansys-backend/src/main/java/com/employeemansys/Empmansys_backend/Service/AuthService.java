package com.employeemansys.Empmansys_backend.Service;

import com.employeemansys.Empmansys_backend.Model.Empmodel;
import com.employeemansys.Empmansys_backend.Model.Userauth;
import com.employeemansys.Empmansys_backend.Repository.EmpRepo;
import com.employeemansys.Empmansys_backend.Repository.UserRepo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
@NoArgsConstructor
@AllArgsConstructor
public class AuthService {
    @Autowired
    private EmpRepo empRepo;
    @Autowired
    private UserRepo userRepo;
    // Signup Logic
    public Userauth signup(Userauth user) {
        return userRepo.save(user);
    }

    // Login Logic
    public Userauth login(Long phone, String password) {
        return userRepo.findByPhoneNumber(phone)
                .filter(u -> u.getPassword().equals(password))
                .orElseThrow(() -> new RuntimeException("Invalid Credentials"));
    }

    public Optional<Empmodel> getProfileByUserId(Integer userId) {
        return empRepo.findByUserId(userId);
    }

    // Logic for @PostMapping (Create)
    public Empmodel createProfile(Integer userId, Empmodel details) {
        Userauth user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // English Requirement Check
        if (!details.getKnowsEnglish()) {
            throw new RuntimeException("Does not meet requirements: English is mandatory");
        }

        details.setUser(user);
        return empRepo.save(details);
    }

    // Logic for @PutMapping (Update)
    public Empmodel updateExistingProfile(Integer userId, Empmodel incoming) {
        return empRepo.findByUserId(userId)
                .map(existing -> {
                    existing.setName(incoming.getName());
                    existing.setEmail(incoming.getEmail()); // Added email per your model
                    existing.setAge(incoming.getAge());
                    existing.setCity(incoming.getCity());
                    existing.setExpectedSalary(incoming.getExpectedSalary());
                    existing.setHscPercentage(incoming.getHscPercentage());
                    existing.setKnowsEnglish(incoming.getKnowsEnglish());
                    existing.setPhoneNumber(incoming.getPhoneNumber());
                    return empRepo.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Profile not found for this user"));
    }

    public void deleteProfile(Integer userId) {
        // Find the profile first to ensure it exists
        empRepo.findByUserId(userId).ifPresentOrElse(
                profile -> empRepo.delete(profile),
                () -> { throw new RuntimeException("No profile found to delete for User ID: " + userId); }
        );
    }


}
