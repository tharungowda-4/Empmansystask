package com.employeemansys.Empmansys_backend.Repository;

import com.employeemansys.Empmansys_backend.Model.Empmodel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpRepo extends JpaRepository<Empmodel,Integer> {
    Optional<Empmodel> findByUserId(Integer id);
}
