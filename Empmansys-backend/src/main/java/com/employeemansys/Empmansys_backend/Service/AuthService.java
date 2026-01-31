package com.employeemansys.Empmansys_backend.Service;

import com.employeemansys.Empmansys_backend.Model.Empmodel;
import com.employeemansys.Empmansys_backend.Model.Userauth;
import com.employeemansys.Empmansys_backend.Repository.EmpRepo;
import com.employeemansys.Empmansys_backend.Repository.UserRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class AuthService {

    @Autowired
    private EmpRepo empRepo;

    @Autowired
    private UserRepo userRepo;

    public Userauth signup(Userauth user) {
        return userRepo.save(user);
    }

    // Update this method signature
    public Userauth login(Long phone, String password) {
        return userRepo.findByPhoneNumber(phone)
                .filter(u -> u.getPassword().equals(password))
                .orElseThrow(() -> new RuntimeException("Invalid Credentials"));
    }

    public Optional<Empmodel> getProfileByUserId(Integer user_id) {
        return empRepo.findByUserId(user_id);
    }

    public Empmodel createProfile(Integer user_id, Empmodel details) {
        // Check if profile exists to avoid Duplicate Entry error
        if (empRepo.findByUserId(user_id).isPresent()) {
            throw new RuntimeException("Profile already exists for this user.");
        }

        Userauth user = userRepo.findById(user_id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!details.getKnowsEnglish()) {
            throw new RuntimeException("English proficiency is mandatory");
        }

        details.setUser(user);
        return empRepo.save(details);
    }

    public Empmodel updateExistingProfile(Integer user_id, Empmodel incoming) {
        return empRepo.findByUserId(user_id)
                .map(existing -> {
                    existing.setName(incoming.getName());
                    existing.setEmail(incoming.getEmail());
                    existing.setGender(incoming.getGender());
                    existing.setCity(incoming.getCity());
                    existing.setExpectedSalary(incoming.getExpectedSalary());
                    existing.setHscPercentage(incoming.getHscPercentage());
                    existing.setKnowsEnglish(incoming.getKnowsEnglish());
                    return empRepo.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Profile not found"));
    }

    public void deleteProfile(Integer user_id) {
        empRepo.findByUserId(user_id).ifPresentOrElse(
                empRepo::delete,
                () -> { throw new RuntimeException("No profile found to delete"); }
        );
    }
}