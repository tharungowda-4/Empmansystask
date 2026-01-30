package com.employeemansys.Empmansys_backend.Repository;

import com.employeemansys.Empmansys_backend.Model.Userauth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<Userauth,Integer> {
    Optional<Userauth> findByPhoneNumber(Long phoneNumber);
}
